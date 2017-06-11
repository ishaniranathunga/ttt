package preprocessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class Tokenizer {

	public String fileReaderAll(String FILENAME) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(FILENAME));
		String sentence = br.readLine();
		String temp = "";
		while ((temp = br.readLine()) != null) {
			sentence += " " + temp;
		}
		br.close();
		return sentence;
	}

	public String fileReader(String FILENAME) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(FILENAME));
		String sentence = br.readLine();
		br.close();
		return sentence;
	}

	public String[] sentenceTokenizer(String FILENAME) {
		try {
			InputStream inputStream = new FileInputStream(
					"H:/L4_Final Year Project/Implementation/OpenNLP/Liberaries/en-token.bin");
			TokenizerModel tokenModel = new TokenizerModel(inputStream);
			TokenizerME tokenizer = new TokenizerME(tokenModel);
			String tokens[] = tokenizer.tokenize(fileReader(FILENAME));
			return tokens;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String[] sentenceTokenizerCustom(String document) throws IOException {
		try {
			InputStream inputStream = new FileInputStream(
					"H:/L4_Final Year Project/Implementation/OpenNLP/Liberaries/en-token.bin");
			TokenizerModel tokenModel = new TokenizerModel(inputStream);
			TokenizerME tokenizer = new TokenizerME(tokenModel);
			String tokens[] = tokenizer.tokenize(document);
			return tokens;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String[] stopwordremover(String FILENAME) throws IOException {
		String[] stopwords = { "a", "about", "above", "above", "across", "after", "afterwards", "again", "against",
				"all", "almost", "alone", "along", "already", "also", "although", "always", "am", "among", "amongst",
				"amoungst", "amount", "an", "and", "another", "any", "anyhow", "anyone", "anything", "anyway",
				"anywhere", "are", "around", "as", "at", "back", "be", "became", "because", "become", "becomes",
				"becoming", "been", "before", "beforehand", "behind", "being", "below", "beside", "besides", "between",
				"beyond", "bill", "both", "bottom", "but", "by", "call", "can", "cannot", "cant", "co", "con", "could",
				"couldnt", "cry", "de", "describe", "detail", "do", "done", "down", "due", "during", "each", "eg",
				"eight", "either", "eleven", "else", "elsewhere", "empty", "enough", "etc", "even", "ever", "every",
				"everyone", "everything", "everywhere", "except", "few", "fifteen", "fify", "fill", "find", "fire",
				"first", "five", "for", "former", "formerly", "forty", "found", "four", "from", "front", "full",
				"further", "get", "give", "go", "had", "has", "hasnt", "have", "he", "hence", "her", "here",
				"hereafter", "hereby", "herein", "hereupon", "hers", "herself", "him", "himself", "his", "how",
				"however", "hundred", "ie", "if", "in", "inc", "indeed", "interest", "into", "is", "it", "its",
				"itself", "keep", "last", "latter", "latterly", "least", "less", "ltd", "made", "many", "may", "me",
				"meanwhile", "might", "mill", "mine", "more", "moreover", "most", "mostly", "move", "much", "must",
				"my", "myself", "name", "namely", "neither", "never", "nevertheless", "next", "nine", "now", "nowhere",
				"of", "off", "often", "on", "once", "one", "only", "onto", "or", "other", "others", "otherwise", "our",
				"ours", "ourselves", "out", "over", "own", "part", "per", "perhaps", "please", "put", "rather", "re",
				"same", "see", "seem", "seemed", "seeming", "seems", "serious", "several", "she", "should", "show",
				"side", "since", "sincere", "six", "sixty", "so", "some", "somehow", "someone", "something", "sometime",
				"sometimes", "somewhere", "still", "such", "system", "take", "ten", "than", "that", "the", "their",
				"them", "themselves", "then", "thence", "there", "thereafter", "thereby", "therefore", "therein",
				"thereupon", "these", "they", "thickv", "thin", "third", "this", "those", "though", "three", "through",
				"throughout", "thru", "thus", "to", "together", "too", "top", "toward", "towards", "twelve", "twenty",
				"two", "un", "under", "until", "up", "upon", "us", "very", "via", "was", "we", "well", "were", "what",
				"whatever", "when", "whence", "whenever", "where", "whereafter", "whereas", "whereby", "wherein",
				"whereupon", "wherever", "whether", "which", "while", "whither", "who", "whoever", "whole", "whom",
				"whose", "why", "will", "with", "within", "without", "would", "yet", "you", "your", "yours", "yourself",
				"yourselves", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "1.", "2.", "3.", "4.", "5.", "6.",
				"11", "7.", "8.", "9.", "12", "13", "14", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
				"M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "terms", "CONDITIONS",
				"conditions", "values", "interested.", "care", "sure", ".", "!", "@", "#", "$", "%", "^", "&", "*", "(",
				")", "{", "}", "[", "]", ":", ";", ",", "<", ".", ">", "/", "?", "_", "-", "+", "=", "a", "b", "c", "d",
				"e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y",
				"z", "contact", "grounds", "buyers", "tried", "said,", "plan", "value", "principle.", "forces", "sent:",
				"is,", "was", "like", "discussion", "tmus", "diffrent.", "layout", "area.", "thanks", "thankyou",
				"hello", "bye", "rise", "fell", "fall", "psqft.", "http://", "km", "miles" };

		Scanner fip1 = new Scanner(new File(FILENAME));
		Map<String, Integer> map = new TreeMap<String, Integer>();

		Integer ONE = new Integer(1);
		while (fip1.hasNext()) {
			int flag = 1;
			String s1 = fip1.next();
			s1 = s1.toLowerCase();
			for (int i = 0; i < stopwords.length; i++) {
				if (s1.equals(stopwords[i])) {
					flag = 0;
				}
			}
			if (flag != 0) {
				if (s1.length() > 0) {

					Integer frequency = (Integer) map.get(s1);
					if (frequency == null) {
						frequency = ONE;
					} else {
						int value = frequency.intValue();
						frequency = new Integer(value + 1);
					}
					map.put(s1, frequency);
				}
			}
		}
		fip1.close();
		ArrayList<String> trendyList = new ArrayList<String>(map.keySet());
		String[] stockArr = new String[trendyList.size()];
		stockArr = trendyList.toArray(stockArr);
		return stockArr;

	}

	public String[] sentimentwordsfilter(String sentence) throws IOException {
		try {
			String[] negativesentimentlist = sentenceTokenizer(sentence);
			return negativesentimentlist;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String[] getSentences(String doc){	
		// Loading Sentence Detection MODEL
		InputStream inputStream;
		try {
			inputStream = new FileInputStream(
					"H:/L4_Final Year Project/Implementation/OpenNLP/Liberaries/en-sent.bin");
			SentenceModel sentenceModel = new SentenceModel(inputStream);
			SentenceDetectorME detectorME = new SentenceDetectorME(sentenceModel);				
			String[] splitsentences = detectorME.sentDetect(doc);
			return splitsentences;
		} catch (Exception e) {
			System.out.println("Error in Sentence tokenizer");
			e.printStackTrace();
		}
		return null;
	}

	
}