package sdev265proj;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class ProcessTask extends Thread {
	
	private boolean isActive = true;
	private Node node;
	
	public ProcessTask(Node node) {
		this.node = node;
	}
	
	@Override
	public void run() {
		while(isActive) {
			try {
				Thread.sleep(5000);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("Test");
			
			
			FirebaseDatabase db = TestMain.database;
			DatabaseReference dr = db.getReference("Main/Users");
			dr.addValueEventListener(new ValueEventListener() {

				@Override
				public void onDataChange(DataSnapshot snapshot) {
					
						snapshot.getChildren().forEach(user-> {
							try {
								//URL url = new File("C:\\Users\\Nick\\eclipse-workspace-remastered\\sdev265proj\\lib\\src\\test\\resources\\chatPreview.fxml").toURI().toURL();
								//Parent root = FXMLLoader.load(url);
								//((Pane) node).getChildren().add(new Label("THIS IS A TEST"));
							} catch(Exception e1) {
								e1.printStackTrace();
							}
						});
						
					
				}

				@Override
				public void onCancelled(DatabaseError error) {
					
					
				}
			});
		}
	}
	
	public void start() {
		isActive = true;
	}
	
	public void kill() {
		isActive = false;
	}

}
