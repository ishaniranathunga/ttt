package classifier;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

public class NaiveBayes {

	public static Classifier loadModel(String name) {

		Classifier classifier;

		FileInputStream fis;
		try {
			fis = new FileInputStream(name + ".model");
			ObjectInputStream ois = new ObjectInputStream(fis);
			classifier = (Classifier) ois.readObject();
			ois.close();
			return classifier;
		} catch (Exception e) {
			System.out.println("Error in saving the model");
			e.printStackTrace();
		}

		return null;
	}

	public static void saveModel(Classifier c, String name) {

		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(name + ".model"));
			oos.writeObject(c);
			oos.flush();
			oos.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public static Classifier trainModel(ArffLoader loader) {
		Instances structure;
		int percentage = 90;
		try {
			structure = loader.getDataSet();			
			structure.randomize(new java.util.Random(0));			 
			int trainSize = (int) Math.round(structure.numInstances() * percentage/ 100);
			int testSize = structure.numInstances() - trainSize;
			Instances train = new Instances(structure, 0, trainSize);
			Instances test = new Instances(structure, trainSize, testSize);
//			train.trainCV(arg0, arg1)
			
			System.out.println("train size:"+trainSize);
			System.out.println("test size:"+testSize);
			 
			train.setClassIndex((train.numAttributes() - 1) );
			NaiveBayesUpdateable nb = new NaiveBayesUpdateable();
			nb.buildClassifier(train);
			
//			Instance current;
//			while ((current = train.getNextInstance(structure)) != null){
//				nb.updateClassifier(current);
//			}
//			 System.out.println(nb);

			// load unlabeled data
//			test = new Instances(new BufferedReader(new FileReader("testfeaturemetrix.arff")));

			// set class attribute
			test.setClassIndex(test.numAttributes() - 1);
			Instances labeled = new Instances(test);
			// label instances
			for (int i = 0; i < test.numInstances(); i++) {
				double clsLabel = nb.classifyInstance(test.instance(i));
				labeled.instance(i).setClassValue(clsLabel);
			}
//			 save labeled data
//			 BufferedWriter writer = new BufferedWriter(new
//			 FileWriter("1.arff"));
//			 writer.write(labeled.toString());
//			 writer.newLine();
//			 writer.flush();
//			 writer.close();

			// evaluate classifier and print some statistics
			Evaluation eval = new Evaluation(labeled);
			eval.evaluateModel(nb, test);
			System.out.println(eval.toSummaryString("\nResults Naive Bayes\n======\n", false));
			// System.out.println(eval.correct());
			// System.out.println(eval.pctCorrect());
			// System.out.println(eval.unclassified());
			System.out.println(eval.toMatrixString());
			System.out.println("***********************************************************************************");
			System.out.println(" FalseNegative :" + eval.numFalseNegatives(1));
			System.out.println(" FalsePositive :" + eval.numFalsePositives(1));
			System.out.println(" TrueNegative :" + eval.numTrueNegatives(1));
			System.out.println(" TruePositive :" + eval.numTruePositives(1));
			return nb;
		} catch (Exception e) {
			System.out.println("Exception thrown in naive bayes training");
			e.printStackTrace();
		}
		return null;
	}
}

/*
public static Classifier trainModel(ArffLoader loader) {
	Instances structure;
	int percentage = 70;
	try {
//		loader.setFile(new File("C:\\Users\\Ishee\\Desktop\\FYPdata2\\OriginalTrainingDataSetFeatures.arff"));
		structure = loader.getStructure();
		structure.setClassIndex((structure.numAttributes() - 1));


		// structure.randomize(new java.util.Random(0));
		// System.out.println(structure.numInstances());
		// System.out.println(structure.lastInstance());
//		int trainSize = (int) Math.round(structure.numInstances() * percentage / 100);
//		// int trainSize = 10;
//		int testSize = structure.numInstances() - trainSize;
//		Instances train = new Instances(structure, 0, trainSize);
//		Instances test = new Instances(structure, trainSize, testSize);
//		System.out.println(trainSize);
//		System.out.println(testSize);
//		System.out.println(test.numInstances());
		

		// train model;
		NaiveBayesUpdateable nb = new NaiveBayesUpdateable();
		nb.buildClassifier(structure);
		Instance current;
		while ((current = loader.getNextInstance(structure)) != null)
			nb.updateClassifier(current);

		// test data
		// create copy
		Instances predictedVals = new Instances(structure);

		// label instances
		for (int i = 0; i < structure.numInstances(); i++) {
			double clsLabel = nb.classifyInstance(structure.instance(i));
			predictedVals.instance(i).setClassValue(clsLabel);
		}
		// save labeled data
		BufferedWriter writer = new BufferedWriter(new FileWriter("asdasd.arff"));
		writer.write(predictedVals.toString());
		writer.newLine();
		writer.flush();
		writer.close();

		// evaluate classifier and print some statistics
		Evaluation eval = new Evaluation(predictedVals);
		System.out.println(eval.evaluateModel(nb, structure));
		System.out.println(eval.toSummaryString("\nResults\n======\n", false));
		// System.out.println(eval.correct());
		// System.out.println(eval.pctCorrect());
		// System.out.println(eval.unclassified());
		System.out.println(eval.toMatrixString());
		System.out.println("***********************************************************************************");
		System.out.println(" FalseNegative :" + eval.numFalseNegatives(1));
		System.out.println(" FalsePositive :" + eval.numFalsePositives(1));
		System.out.println(" TrueNegative :" + eval.numTrueNegatives(1));
		System.out.println(" TruePositive :" + eval.numTruePositives(1));

	} catch (Exception e) {
		System.out.println("Exception thrown in naive bayes training");
		e.printStackTrace();
	}
	return null;

}
*/
