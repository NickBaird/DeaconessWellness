package project;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hashing;

public class Coach {
	
	private String key;
	private String firstname;
	private String lastname;
	private String display;
	private String email;
	private String hash;
	
	public Coach(String key, String firstname, String lastname, String display, String email, String hash) {
		this.key = key;
		this.firstname = firstname;
		this.lastname = lastname;
		this.display = display;
		this.email = email;
		this.hash = hash;
	}
	
	public boolean check(String nonHash) {
		if(this.hash.equals(Hashing.sha256().hashString("deaconesswellnessdesktopapplication" + nonHash, StandardCharsets.UTF_8).toString()))
			return true;
		else
			return false;
	}
	
	public String getEmail() {
		 return this.email;
	}
	
	public String getKey() {
		 return this.key;
	}
	
	public String toString() {
		 return this.display;
	}
	
}
