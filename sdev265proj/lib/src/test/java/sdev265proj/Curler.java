package sdev265proj;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class Curler {
	
	private String command;
	private String url;
	String value = "";
	
	public Curler() {
		
	}
	
	public Curler(String command, String url) {
		this.command = command;
		this.url = url;
	}
	
	public void setCommand(String command) {
		this.command = command;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String execute() {
		if(command != null) {
			try {
				
				Process process = Runtime.getRuntime().exec(command + " " + url);
				return new String(process.getInputStream().readAllBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Could not execute Curl: Empty Command");
		}
		return "";
	}
	
	public String execute_async() {
		
		CompletableFuture.runAsync(() -> {
			if(command != null) {
				try {
					
					Process process = Runtime.getRuntime().exec(command + " " + url);
					value = new String(process.getInputStream().readAllBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("Could not execute Curl: Empty Command");
			}
		});
		return value;
	}
}
