package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import classifier.NaiveBayes;
import data.DBConnection;
import data.FileHandle;
import entityClassify.EntityClassifier;
import featureExtraction.FeatureExtraction;
import preprocessing.Preprocess;
import templates.Document;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

public class Main {
	final static String nameNB = "naiveBayes";
	final static String nameSVM = "svm";
	final static String trainingDataFile = "OriginalTrainingDataSet.csv";

	public static void main(String args[]) throws IOException {

		List<Document> docListTrain = FileHandle.getTrainingData(trainingDataFile);
		docListTrain = Preprocess.getPreprocessContent(docListTrain);
		ArffLoader loader_train = FeatureExtraction.generateFeatureVector(docListTrain, "train");

//		Training classifier 
		
// START *****	
		
		//		 ***********For Naive Bayes *************
		
//		Classifier nb = NaiveBayes.trainModel(loader_train);
//		NaiveBayes.saveModel(nb, nameNB);
//		

		//		 ***********For SVM *************
		
		// svm = SVM.trainModelSVM(loader_train);///
		// SVM.saveModel(svm, nameSVM);///
		
// ENDS *****
		
		
		

		
		
		
		
		
//		Test classifier 
		
// START *****			

		NaiveBayesUpdateable loaded_nb = (NaiveBayesUpdateable) NaiveBayes.loadModel(nameNB);
		//LibSVM loaded_svm = (LibSVM) SVM.loadModel(nameSVM);///

		List<Document> docListTest = DBConnection.getDocuments("fyp", "documents");
		docListTest = Preprocess.getPreprocessContent(docListTest);

		ArffLoader loader_test = FeatureExtraction.generateFeatureVector(docListTest, "test");

		Instances testInstances = loader_test.getDataSet();
		testInstances.setClassIndex(testInstances.numAttributes() - 1);
		for (int i = 0; i < testInstances.numInstances(); i++) {
			// int i = testInstances.numInstances()-1; // only for one instance
			double clsLabel;
			try {
				// classifier return double value
				clsLabel = loaded_nb.classifyInstance(testInstances.instance(i));
				testInstances.instance(i).setClassValue(clsLabel);
				System.out.println("Document id " + docListTest.get(i).getId() + " is classified as : "
						+ testInstances.instance(i).stringValue(testInstances.numAttributes() - 1));
			} catch (Exception e) {
				System.out.println("Predicting error in naivebayes");
				e.printStackTrace();
			}

		}

// ENDS *****
		
		
		
		
		
		
		
		
		
		
		
//		Entity classification 
		
// START *****		

		//		 ***********For whole data set*************
		
		// for (Document doc : docListTest) {
		// System.out.println(EntityClassifier.getDocumentEntityClassification(doc));
		// }
		
		
		//		 ***********For SINGLE OBJECT*************
		
		Document doc = new Document();
		doc.setContent("Google is good. Amazon is bad. It is raining.");
		List<String> a = new ArrayList<String>();
		a.add("Google");
		a.add("Amazon");
		doc.setIdentifiedEntities(a);
		System.out.println(EntityClassifier.getDocumentEntityClassification(doc));
		
// ENDS *****	
		
	}

}
