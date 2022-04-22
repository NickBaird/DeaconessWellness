package sdev265proj;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.json.JSONObject;

import sdev265proj.Message;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import buttons.CustomButton;
import io.grpc.internal.JsonParser;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import labels.CustomLabel;

public class TestMain extends Application {
			
	public static Stage primaryStage;
	
	private static FirebaseApp app;
	public static FirebaseDatabase database;
	
	private Curler curler;
	private String curl = "curl -X PUT -d \"";
	
	private JSONObject object = new JSONObject();
	
	private String coachKey = "Coach123";
	
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		
		long curr = System.currentTimeMillis();
		
		
		try {
			JSONObject test = StringArrayToJSONObject(new String[][] {{String.valueOf(curr), "Hey"}, {String.valueOf(curr + 1), "Hello"}} );
			curl += JSONObjectToCurlString(test);
			System.out.println(curl);
		} catch(Exception e) {};
			
		this.curler = new Curler(curl, "\" https://axiomatic-skill-318016-default-rtdb.firebaseio.com/Main/Messaging/UsersRead.json?auth=qkMb5eBV94vr7mvESyeEICx4zehcj3Dzk7XJw70O");
		System.out.println(curler.execute());
		
		try {
			URL url = new File("mainWindow.fxml").toURI().toURL();
			Parent root = FXMLLoader.load(url);
			
		    primaryStage.setScene(new Scene(root));
		    primaryStage.setResizable(true);
		    primaryStage.setTitle("Canvas LMS Interface");
		    primaryStage.show();
		    
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		try {
			FileInputStream serviceAccount = new FileInputStream("C:/Users/Nick/Desktop/databaseKey.json");
			FirebaseOptions options = new FirebaseOptions.Builder()
					  .setCredentials(GoogleCredentials.fromStream(serviceAccount))
					  .setDatabaseUrl("https://axiomatic-skill-318016-default-rtdb.firebaseio.com")
					  .build();
			app = FirebaseApp.initializeApp(options);
			database = FirebaseDatabase.getInstance(app);	
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		
		
		/*TestMainController.customChatBtn.setOnMouseClicked(event -> {
			DatabaseReference Users = database.getReference("Main/Users");
			Users.addValueEventListener(new ValueEventListener() {
				@Override public void onDataChange(DataSnapshot snapshot) {
					snapshot.getChildren().forEach(user -> {
						if(user.hasChild(coachKey)) {
							System.out.println("USER: " + user);
						}	
					});
				}
				@Override public void onCancelled(DatabaseError error) {

				}
			});
		});*/
		
		
    	
		
	}

	public static void main(String[] args) {
		
		
		
		launch(args);
	}
	
	
	
	public String JSONObjectToCurlString(JSONObject object) {
		return object.toString().replace("\"", "\\\"");
	}
	
	public JSONObject StringArrayToJSONObject(String[][] strings) throws Exception {
		
		JSONObject output = new JSONObject();
		
		for(String[] string : strings) {
			if(string.length != 2)
				throw new Exception("Inner Array Has More Multiple Keys/Values.");
			
			output.put(string[0], string[1]);
		}
		System.out.println(output);
		return output;
	}
	
	
}
	
	

