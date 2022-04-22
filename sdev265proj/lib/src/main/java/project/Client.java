package project;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.firebase.database.utilities.Pair;

public class Client {
	
	private String key;
	private String firstname;
	private String lastname;
	public ArrayList<Goal> goals;
	public ArrayList<Pair<String, String>> messages;
	public ArrayList<Pair<String, String>> messagesTo;
	public HashMap<String, ArrayList<Pair<String, String>>> messagesOther;
	
	public Client(String key, String firstname, String lastname) {
		this.key = key;
		this.firstname = firstname;
		this.lastname = lastname;
		goals = new ArrayList<>();
		messages = new ArrayList<>();
		messagesTo = new ArrayList<>();
		messagesOther = new HashMap<>();
	}
	
	public void resetMessages() {
		messages = new ArrayList<>();
		messagesTo = new ArrayList<>();
		messagesOther = new HashMap<>();
	}
	
	public String toString() {
		return firstname + " " + lastname;
	}
	
	public String getKey() {
		return this.key;
	}
	
	public void readMessages() {
		ArrayList<Pair<String, String>> temp = new ArrayList<>();
		for(Pair<String, String> message : messages)
			temp.add(new Pair<String, String>("1" + message.getFirst().substring(1), message.getSecond()));
		messages = temp;
		temp.clear();
		/*for(Pair<String, String> message : messagesOther)
			temp.add(new Pair<String, String>("1" + message.getFirst().substring(1), message.getSecond()));
		messagesOther = temp;*/
	}
	
}
