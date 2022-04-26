package project;

import java.io.File;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Popup;
import javafx.stage.Stage;



public class MainLauncher extends Application {
	

	
	@Override
	public void start(Stage primaryStage) {
		try {
			URL url = new File("LaunchedWindow.fxml").toURI().toURL();
			Parent root = FXMLLoader.load(url);
			
		    primaryStage.setScene(new Scene(root));
		    primaryStage.setResizable(true);
		    primaryStage.setTitle("Deaconess Wellness Pathway Desktop Application v0.1");
		    primaryStage.show();
		    
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getLocalizedMessage());
		}
	}
	
	public static void onLaunch(String[] args) {
		launch(args);
	}
	
	@Override
	public void stop(){
	    System.out.println("Stage is closing");
	    MainLauncherController.clientThread.stop();
	    MainLauncherController.updateChatThread.stop();
	}
}
