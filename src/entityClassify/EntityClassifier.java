package entityClassify;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import preprocessing.Lemmatizer;
import preprocessing.Tokenizer;
import templates.Document;

public class EntityClassifier {

	private static final String negativewordsList = "H:\\L4_Final Year Project\\Dataset\\Testing\\Negative_Lexical.txt";
	private static final String positivewordsList = "H:\\L4_Final Year Project\\Dataset\\Testing\\Positive_Lexical.txt";
	private static final String MAIN = "main";
	private static final String SUB = "sub";
	private static final String HIGHLY_NEGATIVE = "highly_negative";
	private static final String NEGATIVE = "negative";
	private static final String NEUTRAL = "neutral";
	private static final String POSITIVE = "positive";
	private static final String HIGHLY_POSITIVE = "highly_positive";

	public static HashMap<String, HashMap<String, Double>> getDocumentEntityClassification(Document doc) {
		Tokenizer tokn = new Tokenizer();
		Lemmatizer lmtzr = new Lemmatizer();
		EntityClassifier ec = new EntityClassifier();

		String[] negativewords = tokn.sentenceTokenizer(negativewordsList);
		String[] positivewords = tokn.sentenceTokenizer(positivewordsList);

		List<String> entities = doc.getIdentifiedEntities();
		String[] sentences = tokn.getSentences(doc.getContent());
		HashMap<String, HashMap<String, Double>> netEntitySentiment = new HashMap<String, HashMap<String, Double>>();

		for (String entity : entities) {
			List<Integer> scores = new ArrayList<Integer>();
			for (String sen : sentences) {
				if (sen.contains(entity)) {
					int score = 0;
					List<String> words = lmtzr.lemmatize(sen);
					String prc = ec.getPrecedenceOfEntity(words, entity);
					if (words.contains("but")) {
						List<String> first = words.subList(0, words.indexOf("but"));
						List<String> second = words.subList(words.indexOf("but") + 1, words.size());
						if (first.contains(entity)) {
							score = ec.getSentimentWordScore(first, negativewords, positivewords, prc);
							scores.add(score);
							// System.out.println(score);
						}
						if (second.contains(entity)) {
							score = ec.getSentimentWordScore(second, negativewords, positivewords, prc);
							scores.add(score);
							// System.out.println(score);
						}
					} else {
						score = ec.getSentimentWordScore(words, negativewords, positivewords, prc);
						scores.add(score);
						// System.out.println(score);
					}

				} else {
					scores.add(0);
				}
			}
			HashMap<String, Double> percentages = new HashMap<String, Double>();
			int pos_count = 0;
			int neg_count = 0;
			int neu_count = 0;
			double tot_count = scores.size();
			for (int j : scores) {
				if (j == 1) {
					pos_count++;
				} else if (j == -1) {
					neg_count++;
				} else if (j == 0) {
					neu_count++;
				}
			}
			percentages.put("Positive", (double)Math.round(pos_count*100 / tot_count));
			percentages.put("Negative", (double)Math.round(neg_count*100 / tot_count));
			percentages.put("Neutral", (double)Math.round(neu_count*100 / tot_count));
//			System.out.println(tot_count);

			netEntitySentiment.put(entity, percentages);
		}
		return netEntitySentiment;
	}

	private String getPrecedenceOfEntity(List<String> lemmas, String entity) {
		if ((lemmas.contains("than"))) {
			int thanIndex = lemmas.indexOf("than");
			int entityIndex = lemmas.indexOf(entity);
			if (thanIndex < entityIndex) {
				return SUB;
			} else {
				return MAIN;
			}
		} else {
			return MAIN;
		}
	}

	private int getSentimentWordScore(List<String> words, String[] negativewordlist, String[] positivewordlist,
			String precedence) {
		// System.out.println(words);
		// System.out.println(precedence);
		List<String> nCorpus = Arrays.asList(negativewordlist);
		List<String> pCorpus = Arrays.asList(positivewordlist);
		int sentimentScore = 0;
		if (words.contains("than")) {
			String beforeThan = words.get(words.indexOf("than") - 1);
			if (nCorpus.contains(beforeThan)) {
				sentimentScore = -1;
			}
			if (pCorpus.contains(beforeThan)) {
				sentimentScore = 1;
			}
			if (words.contains("not") && words.indexOf("not") < words.indexOf(beforeThan)) {
				sentimentScore = -1 * sentimentScore;
			}
			if (precedence == SUB) {
				sentimentScore = -1 * sentimentScore;
			}
			return sentimentScore;
		}
		if (words.contains("not")) {
			for (int i = words.indexOf("not") + 1; i < words.size(); i++) {
				if (nCorpus.contains(words.get(i))) {
					sentimentScore = +1;
					return sentimentScore;
				}
				if (pCorpus.contains(words.get(i))) {
					sentimentScore = -1;
					return sentimentScore;
				}
			}
			for (int i = words.indexOf("not") - 1; i >= 0; i--) {
				int p_temp = 0;
				int n_temp = 0;
				if (nCorpus.contains(words.get(i))) {
					n_temp = n_temp - 1;
				}
				if (pCorpus.contains(words.get(i))) {
					p_temp = p_temp + 1;
				}
				if ((p_temp == 0 && n_temp == 0) || (p_temp - n_temp) == 0) {
					sentimentScore = 0;
				} else if (p_temp > n_temp) {
					sentimentScore = +1;
				} else {
					sentimentScore = -1;
				}
			}
		} else {
			int p_temp = 0;
			int n_temp = 0;
			// System.out.println("normal sentence");
			for (int i = 0; i < words.size(); i++) {
				if (nCorpus.contains(words.get(i))) {
					// System.out.println("negative : " + words.get(i));
					n_temp = 1 + n_temp;
				}
				if (pCorpus.contains(words.get(i))) {
					// System.out.println("positive : " + words.get(i));
					p_temp = 1 + p_temp;
				}
			}
			if ((p_temp == 0 && n_temp == 0) || (p_temp - n_temp) == 0) {
				sentimentScore = 0;
			} else if (p_temp > n_temp) {
				sentimentScore = +1;
			} else {
				sentimentScore = -1;
			}
		}
		return sentimentScore;

	}

}
