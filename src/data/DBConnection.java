package data;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBList;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import templates.Document;

public class DBConnection {
	
	public static List<Document> getDocuments(String dbName, String collectionName){	
		List<Document> documents = new ArrayList<Document>();
		DB db = getDatabase(dbName);
		DBCollection table = db.getCollection(collectionName);
		DBCursor cursor = table.find();
		while (cursor.hasNext()) {
		   DBObject obj = cursor.next();
		   Document d = new Document();
		   d.setId((int)obj.get("document_id"));
		   d.setContent((String)obj.get("content"));
		   d.setAnnotatedSentiment((String)obj.get("annotated_sentiment"));	   //(String)obj.get("annotated_sentiment")
		   List<String> temp = new ArrayList<String>();
		   BasicDBList entities = (BasicDBList)obj.get("identified_entities");
		   for (Object ent : entities) {
			    temp.add((String) ent) ;
			}
		   d.setIdentifiedEntities(temp);	
		   documents.add(d);
		}
		return documents;
	}
	
	private MongoClient getMongoConn(){
		try {
			return  new MongoClient( "localhost" , 27017 );
		} catch (UnknownHostException e) {
			System.out.println("An error occurred whec creating mon go client");
			e.printStackTrace();
		}
		return null;
	}
	
	private static DB getDatabase(String dbName){
		DBConnection dbConn = new DBConnection();
		MongoClient mc = dbConn.getMongoConn();
		return mc.getDB(dbName);
	}
	

}
