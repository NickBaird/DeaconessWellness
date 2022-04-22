package sdev265proj;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import org.json.JSONObject;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import buttons.CustomButton;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.util.Pair;

public class TestMainController {
	
	public double R = 154 / 255.0;
	public double G = 150 / 255.0;
	public double B = 173 / 255.0;
	
	private String username;
	
	Thread dbThread;
	public int i = 0;
	
	Thread chatThread;
	
	@FXML
    private Pane sidebar;
	@FXML
	private Pane background;
	@FXML
	private Pane chatPane;
	@FXML
	private Pane chatMessagesPane;
	@FXML
    private Pane loginPane;
	
	
	
	@FXML
	private Pane noChatsPlaceholder;
	
	@FXML
	private Pane chatPreview;
	
	@FXML
	private ScrollPane chatPreviewScroll;
	
	@FXML Pane chatPreviewBGPane;
	
	@FXML
	private VBox chatPreviewScrollPane;
	
	@FXML
	private ListView<?> chatPreviewListView;
	public static ListView<String> test;
	
	public static VBox chatPreviews;
	
	public static ArrayList<Node> ongoingChats = new ArrayList<>();
	
	public static ArrayList<User> userChats = new ArrayList<>();
	public static ArrayList<ChatController> userControllers = new ArrayList<>();
	
    

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;
    
    
    
  
    
    
    
    @FXML
    private Button loginBtn;
    private CustomButton customLoginBtn;

    @FXML
    private Button sidebarBtn;
    private CustomButton customSidebarBtn;
    boolean toggle = false;
    
    @FXML
    private Button chatBtn;
    public static CustomButton customChatBtn;
    
    
    
    
    //private Button[][] buttons = {{sidebarBtn, customSidebarBtn}, {chatBtn, customChatBtn}};
    
    
    
    public void initialize() {
    	
    	customLoginBtn = new CustomButton("Login", R + (15/255.0), G + (15/255.0), B + (15/255.0), loginBtn);
    	loginPane.getChildren().add(customLoginBtn);
    	
    	customSidebarBtn = new CustomButton(">", R, G, B, sidebarBtn);
    	background.getChildren().add(customSidebarBtn);
    	customSidebarBtn.setOpacity(sidebarBtn.getOpacity());
    	customSidebarBtn.setDisable(sidebarBtn.isDisabled());
    	
    	customChatBtn = new CustomButton("Chat", R + (15/255.0), G + (15/255.0), B + (15/255.0), chatBtn);
    	sidebar.getChildren().add(customChatBtn);
    	
    	noChatsPlaceholder.setOpacity(0);
    	
    	
    	
		
		
		//dbThread = new Thread(this::handleThread);
		//dbThread.start();
		
		chatThread = new Thread(this::refreshChatThread);
		
		customChatBtn.setOnMouseClicked(event -> {
			chatThread.start();
			
		});
		
		
		
		CompletableFuture.runAsync(() -> {
			ProcessTask pt = new ProcessTask(chatPane);
			pt.start();
			pt.run();
			
				
			
		});
		try {
		//URL url = new File("C:\\Users\\Nick\\eclipse-workspace-remastered\\sdev265proj\\lib\\src\\test\\resources\\chatPreview.fxml").toURI().toURL();
		//Parent root = FXMLLoader.load(url);
		//chatPreviewScrollPane.getChildren().add(root);
		} catch(Exception e) {e.printStackTrace();}
		
		
		
    	    	
    	matchBG(sidebar);
    	matchBG(chatPane, 70, 160, 100);
    	matchBG(loginPane);
    	matchBG(chatMessagesPane, 165, 165, 190);
    	matchBG(chatPreviewScrollPane, 165, 165, 190);
    	matchBG(chatPreviewBGPane, 165, 165, 190);
    	sidebar.toFront();
    	
    	
    	
    	customLoginBtn.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				String user = usernameField.getText(), password = passwordField.getText();
				if(user.length() == 0 || password.length() == 0) {
					System.out.println("Must Enter Both a Username and Password");
				} else {
					username = user;
					pageFadeOut(loginPane, true);
					pageFadeIn(sidebar, false);
					customSidebarBtn.setOpacity(1);
			    	customSidebarBtn.setDisable(false);
				}
			}
    	});
    	
    	
    	
    	customSidebarBtn.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				customSidebarBtn.press();
				if(!toggle) {
					sidebarRight(sidebar);
					sidebarRight(customSidebarBtn);
					customSidebarBtn.setText("<");
					toggle = true;
				} else {
					sidebarLeft(sidebar);
					sidebarLeft(customSidebarBtn);
					customSidebarBtn.setText(">");
					toggle = false;
				}
			}
		});
    	
    	customChatBtn.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				if(chatPane.isDisabled()) {
					pageFadeIn(chatPane, false);
					chatPane.setDisable(false);
				}
			}
    	});
    	
    	
    }
    
    


	public void pageFadeIn(Node pane, boolean disable) {
		pane.setDisable(disable);
    	FadeTransition ft = new FadeTransition();
    	ft.setDuration(Duration.millis(500));
		ft.setNode(pane);
		ft.setFromValue(0.0);
		ft.setToValue(1.0);
		ft.setAutoReverse(false); 
		ft.setInterpolator(Interpolator.EASE_BOTH);
		ft.play();	
    }
    
    public void pageFadeOut(Node pane, boolean disable) {
    	pane.setDisable(disable);
    	FadeTransition ft = new FadeTransition();
    	ft.setDuration(Duration.millis(500));
		ft.setNode(pane);
		ft.setFromValue(1.0);
		ft.setToValue(0.0);
		ft.setAutoReverse(false); 
		ft.setInterpolator(Interpolator.EASE_BOTH);
		ft.play();
    }
    
    public void pageLeft(Node pane, boolean disable) {
    	pane.setDisable(disable);	
    	
    	TranslateTransition tt = new TranslateTransition();
    	tt.setDuration(Duration.millis(1000));
		tt.setNode(pane);
		tt.setByX(-1280);
		tt.setAutoReverse(false); 
		tt.setInterpolator(Interpolator.EASE_BOTH);
		tt.play();
    }
    
    public void pageRight(Node pane, boolean disable) {
    	pane.setDisable(disable);
    	
    	TranslateTransition tt = new TranslateTransition();
    	tt.setDuration(Duration.millis(1000));
		tt.setNode(pane);
		tt.setByX(1280);
		tt.setAutoReverse(false);
		tt.setInterpolator(Interpolator.EASE_BOTH);
		tt.play();
    }
    
    public void sidebarRight(Node pane) {
    	TranslateTransition tt = new TranslateTransition();
    	tt.setDuration(Duration.millis(350));
		tt.setNode(pane);
		tt.setByX(sidebar.getWidth() - 1);
		tt.setAutoReverse(false);
		tt.setInterpolator(Interpolator.EASE_BOTH);
		tt.play();
    }
    
    public void sidebarLeft(Node pane) {
    	TranslateTransition tt = new TranslateTransition();
    	tt.setDuration(Duration.millis(350));
		tt.setNode(pane);
		tt.setByX(-(sidebar.getWidth() - 1));
		tt.setAutoReverse(false); 
		tt.setInterpolator(Interpolator.EASE_BOTH);
		tt.play();
    }
    
    public void matchBG(Node node) {
    	node.setStyle("-fx-background-color: rgb(" + (R * 255) + ", " + (G * 255) + ", " + (B * 255) + ");");
    }
    
    public void matchBG(Node node, int R, int G, int B) {
    	node.setStyle("-fx-background-color: rgb(" + R + ", " + G + ", " + B + ");");
    }
    
    public void createCustomButton(CustomButton custom, Button button, String text, Pane pane) {
    	custom = new CustomButton(text, R, G, B, sidebarBtn);
    	pane.getChildren().add(customSidebarBtn);
    }
    
    
    
    public static void refreshChatMessages() {
    	chatPreviews.getChildren().clear();
    	chatPreviews.getChildren().addAll(ongoingChats);
    	System.out.println("Refreshed");
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
	
	
	private void handleThread() {
		
		while(true) {
			
			Platform.runLater(() -> {
				customChatBtn.setText("TEST:" + i++);
			});
			
			try {
				Thread.sleep(2000);
			} catch(Exception e ) {}
		}
	}
	
	private void refreshChatThread() {
			DatabaseReference Users = TestMain.database.getReference("Main/Users");
			Users.addValueEventListener(new ValueEventListener() {
				@Override public void onDataChange(DataSnapshot snapshot) {
					if(snapshot.getChildrenCount() != 0) {
						snapshot.getChildren().forEach(user -> {
							if(user.hasChild("Coach123")) {
								
								userChats.add(new User(user.toString(), 100L, "This was sent from him"));
								System.out.println("added user:" + userChats.get(0));
								
									try {
										URL url = new File("C:\\Users\\Nick\\eclipse-workspace-remastered\\sdev265proj\\lib\\chatPreview.fxml").toURI().toURL();
										FXMLLoader fxmlLoader = new FXMLLoader();
										fxmlLoader.setLocation(url);
										Pane chatPreview = fxmlLoader.load();
										ChatController chatController = fxmlLoader.getController();
										chatController.setData(new User(user.toString(), 100L, "This was sent from him"));
										
										System.out.println(user);

										Platform.runLater(() -> {		
											chatPreviewScrollPane.getChildren().add(chatPreview);	
										});
									} catch(Exception e) { e.printStackTrace(); }
							}	
						});
					} else {
						//Add No Ongoing Chats FXML Element Here
					}

				}
				@Override public void onCancelled(DatabaseError error) {

				}		
			});
			
	
			
	}
	
}
