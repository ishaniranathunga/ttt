//package classifier;
//
//import java.io.BufferedWriter;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//
//import weka.classifiers.Classifier;
//import weka.classifiers.Evaluation;
//import weka.classifiers.functions.LibSVM;
//import weka.core.Instances;
//import weka.core.converters.ArffLoader;
//
//public class SVM {
//	public static Classifier loadModel(String name) {
//
//		Classifier classifier;
//
//		FileInputStream fis;
//		try {
//			fis = new FileInputStream(name + ".model");
//			ObjectInputStream ois = new ObjectInputStream(fis);
//			classifier = (Classifier) ois.readObject();
//			ois.close();
//			return classifier;
//		} catch (Exception e) {
//			System.out.println("Error in saving the model");
//			e.printStackTrace();
//		}
//
//		return null;
//	}
//
//	public static void saveModel(Classifier c, String name) {
//
//		ObjectOutputStream oos = null;
//		try {
//			oos = new ObjectOutputStream(new FileOutputStream(name + ".model"));
//			oos.writeObject(c);
//			oos.flush();
//			oos.close();
//		} catch (FileNotFoundException e1) {
//			e1.printStackTrace();
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//
//	}
//
//	public static Classifier trainModelSVM(ArffLoader loader) {
//		Instances structure;
//		int percentage = 90;
//		try {
//			structure = loader.getDataSet();			
////			structure.randomize(new java.util.Random(0));			 
//			int trainSize = (int) Math.round(structure.numInstances() * percentage/ 100);
//			int testSize = structure.numInstances() - trainSize;
//			Instances train = new Instances(structure, 0, trainSize);
//			Instances test = new Instances(structure, trainSize, testSize);
////			train.trainCV(arg0, arg1)
//			
//			System.out.println("train size:"+trainSize);
//			System.out.println("test size:"+testSize);
//			 
//			train.setClassIndex((train.numAttributes() - 1) );
//			LibSVM svm = new LibSVM();
//			svm.buildClassifier(train);
//			
//
//			// set class attribute
//			test.setClassIndex(test.numAttributes() - 1);
//			Instances labeled = new Instances(test);
//			// label instances
//			for (int i = 0; i < test.numInstances(); i++) {
//				double clsLabel = svm.classifyInstance(test.instance(i));
//				labeled.instance(i).setClassValue(clsLabel);
//			}
////			 save labeled data
//			 BufferedWriter writer = new BufferedWriter(new
//			 FileWriter("1.arff"));
//			 writer.write(labeled.toString());
//			 writer.newLine();
//			 writer.flush();
//			 writer.close();
//
//			// evaluate classifier and print some statistics
//			Evaluation eval = new Evaluation(labeled);
//			eval.evaluateModel(svm, test);
//			System.out.println(eval.toSummaryString("\nResults SVM\n======\n", false));
//			// System.out.println(eval.correct());
//			// System.out.println(eval.pctCorrect());
//			// System.out.println(eval.unclassified());
//			System.out.println(eval.toMatrixString());
//			System.out.println("***********************************************************************************");
//			System.out.println(" FalseNegative :" + eval.numFalseNegatives(1));
//			System.out.println(" FalsePositive :" + eval.numFalsePositives(1));
//			System.out.println(" TrueNegative :" + eval.numTrueNegatives(1));
//			System.out.println(" TruePositive :" + eval.numTruePositives(1));
//			return svm;
//		} catch (Exception e) {
//			System.out.println("Exception thrown in naive bayes training");
//			e.printStackTrace();
//		}
//		return null;
//	}
//}
