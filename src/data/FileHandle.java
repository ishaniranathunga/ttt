package data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

import templates.Document;

public class FileHandle {

	public static List<Document> getTrainingData(String filepath){
		CSVReader reader;
		List<Document> documents = new ArrayList<Document>();
		try {
			reader = new CSVReader(new FileReader(filepath));
			String [] nextLine;
		     while ((nextLine = reader.readNext()) != null) {
		        Document d =new Document();
		        d.setContent(nextLine[1]);
		        d.setAnnotatedSentiment(nextLine[2]);
		        documents.add(d);
		     }
		} catch (Exception e) {
			System.out.println("Error in training data read");
			e.printStackTrace();
		}
		return documents;
	     
	}
}
