package featureExtraction;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.opencsv.CSVWriter;

import preprocessing.Tokenizer;
import templates.Document;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

public class FeatureExtraction {

	private static final String corpus = "OriginalTrainingDataSet.txt";
	private static final String negativelexical = "H:\\L4_Final Year Project\\Dataset\\Testing\\Negative_Lexical.txt";
	private static final String positiveLexical = "H:\\L4_Final Year Project\\Dataset\\Testing\\Positive_Lexical.txt";
	private static final String CSV_FILE = "featuremetrix.csv";
	private static final String ARFF_FILE = "featuremetrix.arff";

	public static ArffLoader generateFeatureVector(List<Document> docList, String filename) {
		FeatureExtraction fe = new FeatureExtraction();
		List<String> featureList = fe.getFeatureList();
		fe.writeToCSV(featureList, docList, filename);
		fe.csvToArff(filename + CSV_FILE, filename + ARFF_FILE);
		return fe.readArffToInstances(filename);
	}

	public static ArffLoader generateSingleFeatureVector(Document docList, String filename) {
		FeatureExtraction fe = new FeatureExtraction();
		List<String> featureList = fe.getFeatureList();
		fe.writeToCSVSingle(featureList, docList, filename);
		fe.csvToArff(filename + CSV_FILE, filename + ARFF_FILE);
		return fe.readArffToInstances(filename);
	}

	private ArffLoader readArffToInstances(String filename) {
		ArffLoader loader = new ArffLoader();
		try {
			loader.setFile(new File(filename + ARFF_FILE));
			return loader;
		} catch (IOException e) {
			System.out.println("Error in ARFF to Instances creation");
			e.printStackTrace();
		}
		return null;
	}

	private void writeToCSV(List<String> featureList, List<Document> docList, String filename) {
		FeatureExtraction fe = new FeatureExtraction();
		CSVWriter writer = null;
		try {
			writer = new CSVWriter(new FileWriter(filename + CSV_FILE), ',');
			// if (filename == "train") {
			String[] entries = new String[featureList.size() + 1];
			entries = featureList.toArray(entries);
			entries[featureList.size()] = "Lable";
			writer.writeNext(entries);
			int i=0;
			for (Document doc : docList) {
				i++;
				Integer[] t = new Integer[featureList.size() + 1];
				fe.getOneRaw(featureList, doc.getLemmaWords()).values().toArray(t);
				String[] a = Arrays.toString(t).split("[\\[\\]]")[1].split(", ");
				String v = doc.getAnnotatedSentiment();				
				if (v.isEmpty() && i%2 ==0) {
					v = "Positive";
				}else if(v.isEmpty() && i%2 != 0){
					v = "Negative";
				}
//				System.out.println(v);
				a[featureList.size()] = v;
				writer.writeNext(a);
			}
			// } else {
			// String[] entries = new String[featureList.size()];
			// entries = featureList.toArray(entries);
			// writer.writeNext(entries);
			// for (Document doc : docList) {
			// Integer[] t = new Integer[featureList.size()];
			// fe.getOneRaw(featureList,
			// doc.getLemmaWords()).values().toArray(t);
			// String[] a = Arrays.toString(t).split("[\\[\\]]")[1].split(", ");
			// writer.writeNext(a);
			// }
			//
			// }
			writer.close();
		} catch (IOException e) {
			System.out.println("Error in writing to cscv file open");
			e.printStackTrace();
		}

	}

	private void writeToCSVSingle(List<String> featureList, Document doc, String filename) {
		FeatureExtraction fe = new FeatureExtraction();
		CSVWriter writer = null;
		try {
			writer = new CSVWriter(new FileWriter(filename + CSV_FILE), ',');
			String[] entries = new String[featureList.size() + 1];
			entries = featureList.toArray(entries);
			entries[featureList.size()] = "Lable";
			writer.writeNext(entries);

			Integer[] t = new Integer[featureList.size() + 1];
			fe.getOneRaw(featureList, doc.getLemmaWords()).values().toArray(t);
			String[] a = Arrays.toString(t).split("[\\[\\]]")[1].split(", ");
			String v = doc.getAnnotatedSentiment();
			if (v == "") {
				v = "Negative";
			}
			a[featureList.size()] = v;
			writer.writeNext(a);

			writer.close();
		} catch (IOException e) {
			System.out.println("Error in writing to cscv file open");
			e.printStackTrace();
		}

	}

	private List<String> getFeatureList() {
		Tokenizer tokenizer = new Tokenizer();
		String[] lecxicalnegativeword;
		String[] lecxicalpositiveword;
		String[] corpusword;
		try {
			lecxicalnegativeword = tokenizer.sentenceTokenizer(negativelexical);
			lecxicalpositiveword = tokenizer.sentenceTokenizer(positiveLexical);
			corpusword = tokenizer.sentenceTokenizer(corpus);

			List<String> negativeLecxicalTemp = new ArrayList();
			List<String> positiveLecxicalTemp = new ArrayList();
			List<String> corpusnegativewordTemp = new ArrayList();
			List<String> corpuspositivewordTemp = new ArrayList();

			Collections.addAll(negativeLecxicalTemp, lecxicalnegativeword);
			Collections.addAll(positiveLecxicalTemp, lecxicalpositiveword);
			Collections.addAll(corpusnegativewordTemp, corpusword);
			Collections.addAll(corpuspositivewordTemp, corpusword);

			negativeLecxicalTemp.retainAll(corpusnegativewordTemp);
			positiveLecxicalTemp.retainAll(corpuspositivewordTemp);
			// System.out.println(negativeLecxicalTemp);
			// System.out.println(positiveLecxicalTemp);

			List<String> featureList = new ArrayList<String>();

			featureList.addAll(negativeLecxicalTemp);
			featureList.addAll(positiveLecxicalTemp);
			// Collections.addAll(featureList, lecxicalnegativeword);
			// Collections.addAll(featureList, lecxicalpositiveword);
			// System.out.println(featureList);
			return featureList;
		} catch (Exception e) {
			System.out.println("Error in negative postive corpus reading");
			e.printStackTrace();
		}
		return null;

	}

	private HashMap<String, Integer> getOneRaw(List<String> headers, List<String> lemmas) {
		HashMap<String, Integer> featureMatrix = new HashMap<String, Integer>();
		for (String a : headers) {
			featureMatrix.put(a, 0);
		}
		for (String a : lemmas) {
			if (headers.contains(a)) {
				int val = featureMatrix.getOrDefault(a, 0);
				featureMatrix.put(a, val + 1);
			}
		}
		return featureMatrix;
	}

	private void csvToArff(String sourcepath, String destpath) {
		// load CSV
		CSVLoader loader = new CSVLoader();
		try {
			loader.setSource(new File(sourcepath));
			Instances data = loader.getDataSet();
			// save ARFF
			ArffSaver saver = new ArffSaver();
			saver.setInstances(data);
			saver.setFile(new File(destpath));
			saver.setDestination(new File(destpath));
			saver.writeBatch();
		} catch (IOException e) {
			System.out.println("Error in converting csv to arff");
			e.printStackTrace();
		}

	}

}
