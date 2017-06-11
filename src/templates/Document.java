package templates;

import java.util.Date;
import java.util.List;

public class Document {

	int id;
	Date time;
	String content;
	String annotatedSentiment;
	List<String> identifiedEntities;
	List<String> lemmaWords;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAnnotatedSentiment() {
		return annotatedSentiment;
	}

	public void setAnnotatedSentiment(String annotatedSentiment) {
		this.annotatedSentiment = annotatedSentiment;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getIdentifiedEntities() {
		return identifiedEntities;
	}

	public void setIdentifiedEntities(List<String> identifiedEntities) {
		this.identifiedEntities = identifiedEntities;
	}

	public List<String> getLemmaWords() {
		return lemmaWords;
	}

	public void setLemmaWords(List<String> lemmaWords) {
		this.lemmaWords = lemmaWords;
	}

}
