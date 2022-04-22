package sdev265proj;

import java.util.List;

import com.google.firebase.database.GenericTypeIndicator;

public class Message extends GenericTypeIndicator<Object> {
	private String author;
	private String text;
	
	private Message() {};
	
	public Message(String author, String text) {
		this.author = author;
		this.text = text;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public String getText() {
		return text;
	}
	
	
}
