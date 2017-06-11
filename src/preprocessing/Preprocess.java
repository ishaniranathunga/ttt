package preprocessing;

import java.util.List;
import templates.Document;

public class Preprocess {
	
	public static List<Document>  getPreprocessContent(List<Document> docList) {
		Lemmatizer lmtzr = new Lemmatizer();
		for(Document doc : docList) {
			doc.setLemmaWords(lmtzr.lemmatize(doc.getContent()));
        }		
		return docList;
	}

	
}
