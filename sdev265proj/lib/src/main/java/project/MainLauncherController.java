package project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.hash.Hashing;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.utilities.Pair;

import buttons.CustomButton;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;




public class MainLauncherController {
	
	public double R = 154 / 255.0;
	public double G = 150 / 255.0;
	public double B = 173 / 255.0;
	
	public final Color PC = Color.valueOf("#006990");
	public final Color APC = Color.valueOf("#2980b9");
	
	public final Color SC = Color.valueOf("#e74c3c");
	public final Color ASC = Color.valueOf("#c0392b");
	public final Color OSC = Color.valueOf("#2ecc71");
	
	public final Color TC = Color.valueOf("#e67e22");
	public final Color ATC = Color.valueOf("#f39c12");
	
	private String salt = "deaconesswellnessdesktopapplication";
	
	public static Thread coachThread;
	public static Thread clientThread;
	public static Thread updateChatThread;
	
	private FirebaseApp app;
	private FirebaseDatabase database;
	
	@FXML
	private Pane background;
	@FXML
	private Pane overlayPane;
	@FXML
    private Pane loginPane;
	@FXML
	private Pane createPane;
	
	@FXML
    private Pane sidebar;
		@FXML
		private Pane chatPane;
			@FXML
			private Pane chatBGPane;
			@FXML
			private Pane chatMessagesPane;
		@FXML
		private Pane clientPane;
		
	private Pane[] panes;
	
	private Alert alert = new Alert(AlertType.INFORMATION);
	
	/*////////////////////////////////////////////////////////////////////////*/
	//                          CLIENT PANE CONTROLS                          //
	/*////////////////////////////////////////////////////////////////////////*/
	@FXML
	private TextField clientSearchTextField;
	
	@FXML
	private ScrollPane clientListViewScroll;
	@FXML
	private ListView<Client> clientListView;
	private ArrayList<Client> clients = new ArrayList<>();
	private HashMap<String, Client> allClients = new HashMap<>();
	private HashMap<String, Coach> allCoaches = new HashMap<>();
	private Coach loggedIn;
	
	@FXML
	private DatePicker clientViewGoalDatePicker;
	
	@FXML
	private Button clientViewAllGoalsBtn;
	private CustomButton customClientViewAllGoalsBtn;
	
	@FXML
	private Button clientCreateNewGoalBtn;
	private CustomButton customClientCreateNewGoalBtn;
	
	@FXML
    private Pane whiteOverlay;
	
	@FXML
	private Button addGoalBtn;
	private CustomButton customAddGoalBtn;
    
    @FXML
    private Pane addPopupPane;

    @FXML
    private TextArea addPopupText;

    @FXML
    private Button addPopupClose;
    private CustomButton customAddPopupClose;
    
    @FXML
    private Button addPopupFitnessAdd;
    private CustomButton customAddPopupFitnessAdd;
    
    @FXML
    private Button addPopupNutritionalAdd;
    private CustomButton customAddPopupNutritionalAdd;
    
    @FXML
    private Pane editPopupPane;

    @FXML
    private TextArea editPopupText;

    @FXML
    private Button editPopupClose;
    private CustomButton customEditPopupClose;
    
    @FXML
    private Button editPopupSave;
    private CustomButton customEditPopupSave;
    
    @FXML
    private Button editPopupDelete;
    private CustomButton customEditPopupDelete;
    
    @FXML
    private Label editPopupSelected;
    
    @FXML
    private ListView<Goal> goalListView;
	private ArrayList<Goal> goals = new ArrayList<>();
	
	@FXML
	private ListView<String> fitnessListView;
	@FXML
	private ListView<String> nutritionalListView;
	
	boolean fitnessSelected = false;
	boolean nutritionalSelected = false;
    
    
    
	@FXML
	private Pane noChatsPlaceholder;
	
	@FXML
	private Pane chatPreview;
	
	@FXML
	private VBox chatPreviewsPane;
	
	@FXML 
	private Pane chatPreviewBGPane;
	
	@FXML
	private ScrollPane chatDisplayScrollPane;
	
	@FXML
	private VBox chatDisplayMessages;
	
	@FXML
	private TextArea chatMessage;
	
	@FXML
	private Button sendMessageBtn;
	private CustomButton customSendMessageBtn;
	
	private Client selectedClient;
	private HashMap<String, ChatController> chatControllers = new HashMap<>();
	private HashMap<String, Pane> chatPreviews = new HashMap<>();
	
    
	// LOGIN 
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    
    
    // ALERT STUFF
    @FXML
    private Pane alertPane;
    @FXML
    private Label alertHeader;
    @FXML
    private Label alertBody;
    @FXML
    private Button alertBtn;
    private CustomButton customAlertBtn;
    
    // BUTTONS
    @FXML
    private Button loginBtn;
    private CustomButton customLoginBtn;
    
    @FXML
    private Button loginCreateBtn;
    private CustomButton customLoginCreateBtn;

    @FXML
    private Button sidebarBtn;
    private CustomButton customSidebarBtn;
    boolean toggle = false;
    
    @FXML
    private Button chatBtn;
    public static CustomButton customChatBtn;
    
    @FXML
    private Button clientBtn;
    private CustomButton customClientBtn;
    
    
    
    
    
    
    
    @FXML
    private Button createAccountBtn;
    private CustomButton customCreateAccountBtn;
    
    @FXML
    private Button createCloseBtn;
    private CustomButton customCreateCloseBtn;

    @FXML
    private TextField createFirst;

    @FXML
    private TextField createLast;

    @FXML
    private TextField createDisplay;

    @FXML
    private TextField createEmail;

    @FXML
    private PasswordField createPassword;

    @FXML
    private PasswordField createConfirm;

    
    
    public void initialize() {
    	
    	// Connect to Firebase API with credentials
    	try {
			FileInputStream serviceAccount = new FileInputStream("databaseKey.json");
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
    	
    	// Create and Start Individual Threads
    	coachThread = new Thread(this::loadCoaches);
    	coachThread.start();
    	
    	clientThread = new Thread(this::loadClients);
    	
    	updateChatThread = new Thread(this::refreshChat);    	
    	
    	/*////////////////////////////////////////////////////////////////////////*/
    	//                      INITIALIZE ALL CUSTOMBUTTONS                      //
    	/*////////////////////////////////////////////////////////////////////////*/
    	panes = new Pane[]{ loginPane, createPane, chatPane, clientPane, overlayPane };
    	
    	//Login Buttons
        customLoginBtn = new CustomButton("Login", SC, loginBtn);
        customLoginCreateBtn = new CustomButton("Create Account", SC, loginCreateBtn);
    	loginPane.getChildren().addAll(customLoginBtn, customLoginCreateBtn);
    	
    	//Sidebar Buttons
    	customSidebarBtn = new CustomButton(">", ASC, sidebarBtn);
    	background.getChildren().add(customSidebarBtn);
    	customSidebarBtn.setOpacity(sidebarBtn.getOpacity());
    	customSidebarBtn.setDisable(sidebarBtn.isDisabled());
    	
    	customChatBtn = new CustomButton("Chat", SC, chatBtn);
    	customClientBtn = new CustomButton("Clients", SC, clientBtn);
    	sidebar.getChildren().addAll(customChatBtn, customClientBtn);
    	
    	//Client Pane Buttons
    	customClientViewAllGoalsBtn = new CustomButton("View All Goals", OSC, clientViewAllGoalsBtn);   	
    	customClientCreateNewGoalBtn = new CustomButton("Create New Goal", OSC, clientCreateNewGoalBtn);
    	customAddGoalBtn = new CustomButton("Add", OSC, addGoalBtn);
    	clientPane.getChildren().addAll(customClientViewAllGoalsBtn, customClientCreateNewGoalBtn, customAddGoalBtn);
    	
	    	//Client - Add Popup Buttons
	    	customAddPopupClose = new CustomButton("X", SC, addPopupClose);
	    	customAddPopupFitnessAdd = new CustomButton("Add as Fitness Goal", OSC, addPopupFitnessAdd);
	    	customAddPopupNutritionalAdd = new CustomButton("Add as Nutritional Goal", OSC, addPopupNutritionalAdd);
	    	addPopupPane.getChildren().addAll(customAddPopupClose, customAddPopupFitnessAdd, customAddPopupNutritionalAdd);
	    	
	    	//Client - Edit Popup Buttons
	    	customEditPopupClose = new CustomButton("X", SC, editPopupClose);
	    	customEditPopupSave = new CustomButton("Save Changes", OSC, editPopupSave);
	    	customEditPopupDelete = new CustomButton("Delete Goal", SC, editPopupDelete);
	    	editPopupPane.getChildren().addAll(customEditPopupClose, customEditPopupSave, customEditPopupDelete);
    	
	    	
    	// Chat - Send Message Button
    	customSendMessageBtn = new CustomButton("Send", OSC, sendMessageBtn);
    	chatPane.getChildren().add(customSendMessageBtn);
    	
    	// Alert - Alert 'Ok' Button
    	customAlertBtn = new CustomButton("Ok", OSC, alertBtn);
    	alertPane.getChildren().add(customAlertBtn);
    	
    	// Create Account - Create Button
    	customCreateAccountBtn = new CustomButton("Create", OSC, createAccountBtn);
    	customCreateCloseBtn = new CustomButton("X", SC, createCloseBtn);
    	createPane.getChildren().addAll(customCreateAccountBtn, customCreateCloseBtn);
    	
    	/*////////////////////////////////////////////////////////////////////////*/
    	//                         ADDING GOALS TO CLIENT                         //
    	/*////////////////////////////////////////////////////////////////////////*/
    	
    	// Adds a Fitness/Nutritional Goal to Selected Client & Goal Date
    	customAddGoalBtn.setOnMousePressed(event -> {
    		customAddGoalBtn.press();
    		if(clientListView.getSelectionModel().getSelectedItem() != null) {
	    		if(goalListView.getSelectionModel().getSelectedItem() != null) {
	    			addPopupText.clear();
	    			clientPane.setDisable(true);
	    			clientPane.setEffect(new GaussianBlur());
	    			whiteOverlay.toFront();
	    			pageFadeIn(whiteOverlay, true);
	    			addPopupPane.toFront();
	    			pageFadeIn(addPopupPane, false);
	    		} else
	    			showAlert("Cannot Add Goal", "You must select a weekly goal from the list to add a goal.", clientPane);
    		} else
    			showAlert("Cannot Add Goal", "You must select a client, and a weekly goal from the lists to add a goal.", clientPane);
    	});
    	
    	// In Add Popup, adds entered information as a fitness goal
    	customAddPopupFitnessAdd.setOnMousePressed(event -> {
    		customAddPopupFitnessAdd.press();
    		if(addPopupText.getText().length() > 0) {
	    		clientPane.setDisable(false);
	    		clientPane.setEffect(null);
	    		pageFadeOut(whiteOverlay, true);
	    		pageFadeOut(addPopupPane, true);
	    		String key = clientListView.getSelectionModel().getSelectedItem().getKey();
	    		int Gindex = getIndexFromView(goalListView, allClients.get(key).goals);
	    		allClients.get(key).goals.get(Gindex).fitnessGoals.add(addPopupText.getText());
	    		fitnessListView.getItems().add(addPopupText.getText());
				DatabaseReference goals = database.getReference("Main/Users/" + key + "/goals/" + allClients.get(key).goals.get(Gindex).toString() + "/fitness");
				goals.setValue(allClients.get(key).goals.get(Gindex).fitnessGoals, null);
    		} else {
    			// Popup an Alert stating that text must be entered in TextArea in order to create a fitness goal
    			showAlert("Cannot Add Fitness Goal", "You must enter something in the text-box to set a goal.", clientPane);
    		}
    	});
    	
    	// In Add Popup, adds entered information as a nutritional goal
    	customAddPopupNutritionalAdd.setOnMousePressed(event -> {
    		customAddPopupNutritionalAdd.press();
    		if(addPopupText.getText().length() > 0) {
	    		clientPane.setDisable(false);
	    		clientPane.setEffect(null);
	    		pageFadeOut(whiteOverlay, true);
	    		pageFadeOut(addPopupPane, true);
	    		String key = clientListView.getSelectionModel().getSelectedItem().getKey();
	    		int Gindex = getIndexFromView(goalListView, allClients.get(key).goals);
	    		allClients.get(key).goals.get(Gindex).nutritionalGoals.add(addPopupText.getText());
	    		nutritionalListView.getItems().add(addPopupText.getText());
	    		DatabaseReference goals = database.getReference("Main/Users/" + key + "/goals/" + allClients.get(key).goals.get(Gindex).toString() + "/nutritional");
				goals.setValue(allClients.get(key).goals.get(Gindex).nutritionalGoals, null);
    		} else
    			showAlert("Cannot Add Nutritional Goal", "You must enter something in the text-box to set a goal.", clientPane);
    	});
    	
    	// In Add Popup, the 'X' button, closes out of adding a goal
    	customAddPopupClose.setOnMousePressed(event -> {
    		customAddPopupClose.press();
    		clientPane.setDisable(false);
    		clientPane.setEffect(null);
    		pageFadeOut(whiteOverlay, true);
    		pageFadeOut(addPopupPane, true);
    	});
    	
    	
    	
    	/*////////////////////////////////////////////////////////////////////////*/
    	//                        EDITING GOALS FOR CLIENT                        //
    	/*////////////////////////////////////////////////////////////////////////*/
    	
    	// If user clicks on a fitness goal from the ListView. It will fade in the Edit Popup [WITH FITNESS SELECTED]
    	fitnessListView.setOnMousePressed(event -> {
    		if((fitnessListView.getItems().size() != 0) && (event.getPickResult().getIntersectedNode() instanceof Text || !event.getPickResult().getIntersectedNode().toString().contains("\'null\'"))) {
    			clientPane.setDisable(true);
    			clientPane.setEffect(new GaussianBlur());
    			whiteOverlay.toFront();
    			pageFadeIn(whiteOverlay, true);
    			editPopupPane.toFront();
    			pageFadeIn(editPopupPane, false);
    			editPopupText.setText(fitnessListView.getSelectionModel().getSelectedItem());
    			fitnessSelected = true;
    		} 
    	});
    	
    	// If user clicks on a nutritional goal from the ListView. It will fade in the Edit Popup [WITH NUTRITIONAL SELECTED]
    	nutritionalListView.setOnMousePressed(event -> {
    		if((nutritionalListView.getItems().size() != 0) && (event.getPickResult().getIntersectedNode() instanceof Text || !event.getPickResult().getIntersectedNode().toString().contains("\'null\'"))) {
    			clientPane.setDisable(true);
    			clientPane.setEffect(new GaussianBlur());
    			whiteOverlay.toFront();
    			pageFadeIn(whiteOverlay, true);
    			editPopupPane.toFront();
    			pageFadeIn(editPopupPane, false);
    			editPopupText.setText(nutritionalListView.getSelectionModel().getSelectedItem());
    			nutritionalSelected = true;
    		} 
    	});
    	
    	// In Edit Popup, Saves changes done to fitness/nutritional goal
    	customEditPopupSave.setOnMousePressed(event -> {
    		customEditPopupSave.press();
    		if(editPopupText.getText().length() != 0) {
	    		clientPane.setDisable(false);
	    		clientPane.setEffect(null);
	    		pageFadeOut(whiteOverlay, true);
	    		pageFadeOut(editPopupPane, true);
	    		String key = clientListView.getSelectionModel().getSelectedItem().getKey();
	    		int Gindex = getIndexFromView(goalListView, allClients.get(key).goals);
	    		
	    		if(fitnessSelected) {
	    			allClients.get(key).goals.get(Gindex).getFitness().clear();
		    		fitnessListView.getItems().set(fitnessListView.getSelectionModel().getSelectedIndex(), editPopupText.getText());
		    		String[] strings = new String[fitnessListView.getItems().size()];
		    		fitnessListView.getItems().toArray(strings);
		    		for(String string : strings )
		    			allClients.get(key).goals.get(Gindex).fitnessGoals.add(string);
		    		DatabaseReference goals = database.getReference("Main/Users/" + key + "/goals/" + allClients.get(key).goals.get(Gindex).toString() + "/fitness");
		    		goals.setValue(allClients.get(key).goals.get(Gindex).fitnessGoals, null);
		    		fitnessSelected = false;
	    		} else {
	    			allClients.get(key).goals.get(Gindex).getNutritional().clear();
		    		nutritionalListView.getItems().set(nutritionalListView.getSelectionModel().getSelectedIndex(), editPopupText.getText());
		    		String[] strings = new String[nutritionalListView.getItems().size()];
		    		nutritionalListView.getItems().toArray(strings);
		    		for(String string : strings )
		    			allClients.get(key).goals.get(Gindex).nutritionalGoals.add(string);
		    		DatabaseReference goals = database.getReference("Main/Users/" + key + "/goals/" + allClients.get(key).goals.get(Gindex).toString() + "/nutritional");
		    		goals.setValue(allClients.get(key).goals.get(Gindex).nutritionalGoals, null);
		    		nutritionalSelected = false;
	    		}
    		} else
    			showAlert("Cannot Edit Goal", "You must enter something in the text-box to edit a goal.", clientPane);
    	});
    	
    	// In Edit Popup, Deletes the fitness/nutritional goal
    	customEditPopupDelete.setOnMousePressed(event -> {
    		customEditPopupDelete.press();
    		clientPane.setDisable(false);
    		clientPane.setEffect(null);
    		pageFadeOut(whiteOverlay, true);
    		pageFadeOut(editPopupPane, true);
    		String key = clientListView.getSelectionModel().getSelectedItem().getKey();
    		int Gindex = getIndexFromView(goalListView, allClients.get(key).goals);
    		
    		if(fitnessSelected) {
    			allClients.get(key).goals.get(Gindex).getFitness().clear();
	    		fitnessListView.getItems().remove(fitnessListView.getSelectionModel().getSelectedIndex());
	    		String[] strings = new String[fitnessListView.getItems().size()];
	    		fitnessListView.getItems().toArray(strings);
	    		for(String string : strings )
	    			allClients.get(key).goals.get(Gindex).fitnessGoals.add(string);
	    		DatabaseReference goals = database.getReference("Main/Users/" + key + "/goals/" + allClients.get(key).goals.get(Gindex).toString() + "/fitness");
	    		goals.setValue(allClients.get(key).goals.get(Gindex).fitnessGoals, null);
	    		fitnessSelected = false;
    		} else {
    			allClients.get(key).goals.get(Gindex).getNutritional().clear();
	    		nutritionalListView.getItems().remove(nutritionalListView.getSelectionModel().getSelectedIndex());
	    		String[] strings = new String[nutritionalListView.getItems().size()];
	    		nutritionalListView.getItems().toArray(strings);
	    		for(String string : strings )
	    			allClients.get(key).goals.get(Gindex).nutritionalGoals.add(string);
	    		DatabaseReference goals = database.getReference("Main/Users/" + key + "/goals/" + allClients.get(key).goals.get(Gindex).toString() + "/nutritional");
	    		goals.setValue(allClients.get(key).goals.get(Gindex).nutritionalGoals, null);
	    		nutritionalSelected = false;
    		}
    	});
    	
    	// In Edit Popup, the 'X' button, closes out of editing a goal
    	customEditPopupClose.setOnMousePressed(event -> {
    		customEditPopupClose.press();
    		clientPane.setDisable(false);
    		clientPane.setEffect(null);
    		pageFadeOut(whiteOverlay, true);
    		pageFadeOut(editPopupPane, true);
    	});
    	
    	
    	/*////////////////////////////////////////////////////////////////////////*/
    	//                        ALTERING PANE AND POPUP                         //
    	/*////////////////////////////////////////////////////////////////////////*/
    	
    	// TODO FINALIZE ALL THE COLORS OF THE DESKTOP APPLICATION
    	matchBG(loginPane, PC);
    	matchBG(sidebar, ASC);
    	matchBG(chatBGPane, PC);
    	matchBG(chatMessagesPane, APC);
    	matchBG(clientPane, PC);
    	matchBG(background, PC);
    	
    	roundedPopup(addPopupPane, APC);
    	roundedPopup(editPopupPane, APC);
    	roundedPopup(alertPane, APC);

    	whiteOverlay.setStyle("-fx-background-color: rgba(1, 1, 1, 0.3)");
    	overlayPane.setStyle("-fx-background-color: rgba(1, 1, 1, 0.3);");
    	whiteOverlay.setDisable(true);
    	whiteOverlay.setOpacity(0);
    	sidebar.toFront();
    	

    	/*////////////////////////////////////////////////////////////////////////*/
    	//                         LOGIN/SIDEBAR CONTROLS                         //
    	/*////////////////////////////////////////////////////////////////////////*/
    	
    	//TODO IMPLEMENT A LOGIN FEATURE FOR COACHES
    	// 'LOGS' In a coach to access the rest of the application
    	customLoginBtn.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				String user = usernameField.getText(), password = passwordField.getText();
				if(user.length() == 0 || password.length() == 0) {
					showAlert("Invalid Login", "Must Enter Both a Username and Password", loginPane);
				} else {
					
					allCoaches.forEach((key, coach) -> {
						if(coach.getEmail().toLowerCase().equals(user.toLowerCase())) {
							if(coach.check(password)) {
								loggedIn = coach;
								clientThread.start();
								updateChatThread.start();
								pageFadeOut(loginPane, true);
								pageFadeIn(sidebar, false);
								customSidebarBtn.setOpacity(1);
						    	customSidebarBtn.setDisable(false); 
							}
							else
								showAlert("Invalid Login", "Password is incorrect.", loginPane);
						}
					});
					
					
				}
			}
    	});
    	
    	customLoginCreateBtn.setOnMousePressed(event -> {
    		customLoginCreateBtn.press();
    		pageFadeOut(loginPane, true);
			pageFadeIn(createPane, false);
    	});
    	
    	
    	
    	// Sidebar Arrow Button that toggle open/closes the sidebar
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
    	
    	// Sidebar Button: Transitions to chatPane
    	customChatBtn.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				if(chatPane.isDisabled()) {
					pageFadeIn(chatPane, false);
					closeOtherPanes(chatPane);
				}
			}
    	});
    	
    	// Sidebar Buttons: Transitions to clientPane
    	customClientBtn.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				if(clientPane.isDisabled()) {
					pageFadeIn(clientPane, false);
					closeOtherPanes(clientPane);
				}
			}
    	});
    	
    	// Create Account Buttons: Creates an account when pressed and valid information is entered
    	customCreateAccountBtn.setOnMousePressed(event -> {
    		customCreateAccountBtn.press();
    		String firstname = createFirst.getText(), lastname = createLast.getText(), display = createDisplay.getText(), email = createEmail.getText(), password = createPassword.getText(), confirm = createConfirm.getText();
    		if(firstname.length() == 0 || lastname.length() == 0 || display.length() == 0 || email.length() == 0 || password.length() == 0 || confirm.length() == 0) 
    			showAlert("Cannot Create Account", "Make sure all text fields are filled out with valid information.", createPane);
    		else if(!email.contains("@") || !email.contains("."))
    			showAlert("Cannot Create Account", "Make sure the entered email address is valid.", createPane);
    		else if(!password.equals(confirm)) 
    			showAlert("Cannot Create Account", "Passwords do not match. Please re-enter both fields carefully.", createPane);
    		else {
    			String coachDir = Hashing.sha256().hashString(salt + firstname + lastname + email, StandardCharsets.UTF_8).toString();
    			String hashPassword = Hashing.sha256().hashString(salt + password, StandardCharsets.UTF_8).toString();
    			
    			DatabaseReference coaches = database.getReference("Main/Coaches/" + coachDir);
    			Map<String, String> coachAccount = new HashMap<>();
    			coachAccount.put("firstname", firstname);
    			coachAccount.put("lastname", lastname);
    			coachAccount.put("display", display);
    			coachAccount.put("email", email);
    			coachAccount.put("hash", hashPassword);
    			coaches.setValue(coachAccount, null);
				
    			allCoaches.put(coachDir, new Coach(coachDir, firstname, lastname, display, email, hashPassword));
    			
    			createFirst.setText("");
    			createLast.setText("");
    			createDisplay.setText("");
    			createEmail.setText("");
    			createPassword.setText("");
    			createConfirm.setText("");
    			
    			showAlert("Account Created", "Your account was successfully create. Welcome " + firstname + "! You should now be able to log in.", loginPane);
    			
    			pageFadeOut(createPane, true);
        		pageFadeIn(loginPane, false);
    		}
    	});
    	
    	// Create Account Buttons: Closes the create account option, transitions to loginPane
    	customCreateCloseBtn.setOnMousePressed(event -> {
    		customCreateCloseBtn.press();
    		pageFadeOut(createPane, true);
    		pageFadeIn(loginPane, false);
    	});
    	
    	
    	/*////////////////////////////////////////////////////////////////////////*/
    	//                          CLIENT PANE CONTROLS                          //
    	/*////////////////////////////////////////////////////////////////////////*/
    	
    	// For searching through clients, filters out clients that do not contain the query
    	clientSearchTextField.setOnKeyTyped(input -> {
    		if(!clientListView.getItems().isEmpty())
    			clientListView.getItems().clear();
    		allClients.forEach((key, client) -> {
    			if(client.toString().toLowerCase().contains(clientSearchTextField.getText().toLowerCase()))
    				clientListView.getItems().add(client);
    		});
    	});
    	
    	// Once a client is selected from ListView, load the client goals
    	clientListView.setOnMouseClicked(event -> {
    		if(clientListView.getSelectionModel().getSelectedIndex() != -1) {
    			clientViewGoalDatePicker.setValue(null);
				goalListView.getItems().clear();
				fitnessListView.getItems().clear();
				nutritionalListView.getItems().clear();
				for(Goal goal : allClients.get(clientListView.getSelectionModel().getSelectedItem().getKey()).goals)
					goalListView.getItems().add(goal);
    		}
		});
    	
    	// TODO POSSIBLY REMOVE THIS?
    	// Once a date is selected from DatePicker, pick that weeks Sunday date (BEFORE NOT AFTER)
    	clientViewGoalDatePicker.setOnAction(event -> {
    		if(clientListView.getSelectionModel().getSelectedItem() != null) {
    			fitnessListView.getItems().clear();
    			nutritionalListView.getItems().clear();
	    		String key = clientListView.getSelectionModel().getSelectedItem().getKey();
	    		if(clientViewGoalDatePicker.getValue() != null) {
					LocalDate date = clientViewGoalDatePicker.getValue();
					DayOfWeek dow = date.getDayOfWeek();
					int count = 0;
					while(!dow.equals(DayOfWeek.SUNDAY)) {
						dow = dow.minus(1);
						count++;
					}
					date = date.minusDays(count);
					clientViewGoalDatePicker.setValue(date);
					
					goalListView.getItems().clear();
					boolean found = false;
					for(Goal goal : allClients.get(key).goals)
						if(goal.equals(date)) {
							goalListView.getItems().add(goal);
							found = true;
						}
	    		} else {
	    			goalListView.getItems().clear();
	    			goalListView.getItems().addAll(allClients.get(key).goals);
	    		}
    		} else {
    			clientViewGoalDatePicker.setValue(null);
    			showAlert("No Client Selected", "Make sure you select a client before searching for weekly goals.", clientPane);
    		}
		});
    	
    	
    	
    	// Creates a weekly goal, if not already created, from the week selected from the DatePicker. Creates the goal under the beginning Sunday of that week. 
    	customClientCreateNewGoalBtn.setOnMousePressed(event -> {
    		customClientCreateNewGoalBtn.press();
    		if(clientListView.getSelectionModel().getSelectedItem() != null && clientViewGoalDatePicker.getValue() != null) {
    			LocalDate date = clientViewGoalDatePicker.getValue();
				DayOfWeek dow = date.getDayOfWeek();
				int count = 0;
				while(!dow.equals(DayOfWeek.SUNDAY)) {
					dow = dow.minus(1);
					count++;
				}
				date = date.minusDays(count);	
				String key = clientListView.getSelectionModel().getSelectedItem().getKey();
				
				boolean found = false;
				for(Goal goal : allClients.get(key).goals)
					if(goal.equals(date))
						found = true;
				
				if(!found) {
					Goal newGoal = new Goal(date);
					allClients.get(key).goals.add(newGoal);
					goalListView.getItems().add(newGoal);
					DatabaseReference goals = database.getReference("Main/Users/" + key + "/goals/" + newGoal.date.toString());
					Map<String, String> bothGoalTypes = new HashMap<>();
					bothGoalTypes.put("fitness", "");
					bothGoalTypes.put("nutritional", "");
					goals.setValue(bothGoalTypes, null);
					
				} else 
					showAlert("Cannot Create Weekly Goal", "There is already a weekly goal with that date.", clientPane);	
    		} else
    			showAlert("Cannot Create Weekly Goal", "You need to select a date for the desired date of the weekly goal. Perferably the beginning Sunday of the week.", clientPane);
    	});
    	
    	
    	// Once a goal is selected from the ListView, load in the fitness/nutritional goals, if any.
    	goalListView.setOnMouseClicked(event -> {
    		if(goalListView.getSelectionModel().getSelectedIndex() != -1) {
    			String key = clientListView.getSelectionModel().getSelectedItem().getKey();
        		int Gindex = getIndexFromView(goalListView, allClients.get(key).goals);
	    		
	    		if(allClients.get(key).goals.size() > 0) {
		    		fitnessListView.getItems().clear();
		    		nutritionalListView.getItems().clear();
		    		for(String fitnessGoal : allClients.get(key).goals.get(Gindex).getFitness())
		    			fitnessListView.getItems().add(fitnessGoal);
		    		for(String nutritionalGoal : allClients.get(key).goals.get(Gindex).getNutritional())
		    			nutritionalListView.getItems().add(nutritionalGoal);
	    		}
    		}
    		
    	});
    	
    	
    	
    	
    	// CHAT FUNCTION -----------
    	
    	// TODO Make this much smoother - the longer the scrollpane the faster the scroll gets
    	// Makes ScrollPane must faster in scrolling
    	chatDisplayMessages.setOnScroll(new EventHandler<ScrollEvent>() {
    		@Override
    		public void handle(ScrollEvent event) {
    			chatDisplayScrollPane.setVvalue(chatDisplayScrollPane.getVvalue() + -event.getDeltaY() / 200);
    		}
    	});
    	
    	// Sends a messages to the 'User', In reality to the database
    	customSendMessageBtn.setOnMousePressed(event -> {
    		if(chatMessage.getText().length() != 0) {
    					String time = "0-" + String.valueOf(System.currentTimeMillis());
    					allClients.get(selectedClient.getKey()).messagesTo.add(new Pair<String, String>(time, chatMessage.getText()));
    					refreshChatMessages(allClients.get(selectedClient.getKey()));
    					chatDisplayScrollPane.setVvalue(1.0);
    					DatabaseReference user = database.getReference("Main/Users/" + selectedClient.getKey() + "/coaches/" + loggedIn.getKey() + "/");
    					HashMap<String, String> messagesTo = new HashMap<>();
    					for(Pair<String, String> message : allClients.get(selectedClient.getKey()).messagesTo)
    						messagesTo.put(message.getFirst(), message.getSecond());
    					user.setValue(messagesTo, null);
    					chatMessage.setText("");
    					chatControllers.get(selectedClient.getKey()).refresh();
    		}	
    	});
    }
    
    
    // getIndexFromView()
    // Precondition: A ListView that is based off an ArrayList of data
    //		Returns the index of the list item based on the selected item from the list view
    public <E> int getIndexFromView(ListView<E> view, ArrayList<E> list) {
    	for(int i = 0; i < list.size(); i++)
    		if(list.get(i).equals(view.getSelectionModel().getSelectedItem()))
    			return i;
		return -1;
    }
    
    // closeOtherPanes()
    // Precondition: A pane that will not be 'closed'
    //		'Closes' (Disables & sets opacity -> 0) for all other panes beside the given
    public void closeOtherPanes(Pane pane) {
		for(Pane otherPane : panes)
			if(!otherPane.isDisabled() && !otherPane.equals(pane)) 
				pageFadeOut(otherPane, true);		 
	}
    
    public void loadCoaches() {
    	DatabaseReference coaches = database.getReference("Main/Coaches");
    	coaches.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				snapshot.getChildren().forEach(coach -> {
					allCoaches.put(coach.getKey(), new Coach(coach.getKey(), coach.child("firstname").getValue().toString(), coach.child("lastname").getValue().toString(), coach.child("display").getValue().toString(), coach.child("email").getValue().toString(), coach.child("hash").getValue().toString()));
				});
			}
			@Override public void onCancelled(DatabaseError error) { System.out.println(error.getCode() + ": " + error.getDetails() + "\n--- " + error.getMessage()); }
    	});
    }
    
    
    // Loads in all of the clients from the database and reads their goals and messages. Establishes ValueEventListener's for User Messages and each Coaches Messages
    //		in order to update chat once a message is received.
    public void loadClients() {
    	DatabaseReference users = database.getReference("Main/Users");
    	users.addListenerForSingleValueEvent(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot snapshot) {
				if(snapshot.getChildrenCount() != 0) {
					clientListView.getItems().clear();
					snapshot.getChildren().forEach(user -> {
						String key, firstname, lastname;
						key = user.getKey();
						if(user.hasChild("firstname") && user.hasChild("lastname")) {
							// Creates clients with information from Firebase database
							firstname = user.child("firstname").getValue().toString();
							lastname = user.child("lastname").getValue().toString();
							Client client = new Client(key, firstname, lastname);
							allClients.put(key, client);
							if(user.hasChild("goals")) {
								user.child("goals").getChildren().forEach(goal -> {
									Goal newGoal = new Goal(LocalDate.parse(goal.getKey()));
									client.goals.add(newGoal);
									if(goal.hasChild("fitness"))
										if(!goal.child("fitness").equals(""))
											goal.child("fitness").getChildren().forEach(fitness -> {
												newGoal.fitnessGoals.add((String) fitness.getValue());
											});
									if(goal.hasChild("nutritional"))
										if(!goal.child("nutritional").equals(""))
											goal.child("nutritional").getChildren().forEach(nutritional -> {
												newGoal.nutritionalGoals.add((String) nutritional.getValue());
											});
								});
							}
							// Loads in coaches Messages
							user.child("coaches").getChildren().forEach(coach -> {
								// TODO CHANGE COACH123 FROM BEING THE DEFAULT LOGGED IN COACH
								// Separates message from the logged in coach and other coaches
								if(coach.getKey().equals(loggedIn.getKey()))
									coach.getRef().addValueEventListener(new ValueEventListener() {
										@Override
										public void onDataChange(DataSnapshot messages) {
											 allClients.get(user.getKey()).messagesTo = new ArrayList<>();
											 messages.getChildren().forEach(message -> {
												allClients.get(user.getKey()).messagesTo.add(new Pair<String, String>(message.getKey(), (String) message.getValue()));
											 });
											 loadMessages(user);
										}
										@Override public void onCancelled(DatabaseError error) { System.out.println(error.getCode() + ": " + error.getDetails() + "\n--- " + error.getMessage()); }
									});
								else
									coach.getRef().addValueEventListener(new ValueEventListener() {
										@Override
										public void onDataChange(DataSnapshot messages) {
											 allClients.get(user.getKey()).messagesOther.put(coach.getKey(), new ArrayList<>());
											 messages.getChildren().forEach(message -> {
											     ((ArrayList<Pair<String, String>>)allClients.get(user.getKey()).messagesOther.get(coach.getKey())).add(new Pair<String, String>(message.getKey(), (String) message.getValue()));
											 });
											 loadMessages(user);
											 chatControllers.get(user.getKey()).ping();
										}
										@Override public void onCancelled(DatabaseError error) { System.out.println(error.getCode() + ": " + error.getDetails() + "\n--- " + error.getMessage()); }
									});
							});
							// Loads in 'Client' Messages
							user.child("messages").getRef().addValueEventListener(new ValueEventListener() {
								@Override
								public void onDataChange(DataSnapshot messages) {
									allClients.get(user.getKey()).messages = new ArrayList<>();
									messages.getChildren().forEach(message -> {
										allClients.get(user.getKey()).messages.add(new Pair<String, String>(message.getKey(), (String) message.getValue()));
									});
									loadMessages(user);
									chatControllers.get(user.getKey()).ping();
								}
								@Override public void onCancelled(DatabaseError error) { System.out.println(error.getCode() + ": " + error.getDetails() + "\n--- " + error.getMessage()); }
							});
						}
					});
					
					allClients.forEach((key, client) -> {
						clientListView.getItems().add(client);
					});
				}
			}
			@Override
			public void onCancelled(DatabaseError error) { System.out.println(error.getCode() + ": " + error.getDetails() + "\n--- " + error.getMessage()); }
    	});
    }
    
    // Loades messages from a user DataSnapshot
    public void loadMessages(DataSnapshot user) {	
			if(chatPreviews.get(user.getKey()) != null) {
				Platform.runLater(() -> {
					ObservableList<Node> workingCollection = FXCollections.observableArrayList(chatPreviewsPane.getChildren());
					Collections.sort(workingCollection, new Comparator<Node>() {
						@Override
						public int compare(Node o1, Node o2) {
							if(Long.valueOf(o1.getId().substring(2)) > Long.valueOf(o2.getId().substring(2)))
								return -1;
							else if(Long.valueOf(o1.getId().substring(2)) == Long.valueOf(o2.getId().substring(2)))
								return 0;
							else
								return 1;
						}
					});
					chatPreviewsPane.getChildren().setAll(workingCollection);
					refreshChatMessages(allClients.get(user.getKey()));
					chatControllers.get(user.getKey()).setData(allClients.get(user.getKey()));
				});
			} else {
				try {
					if(allClients.get(user.getKey()).messages.size() != 0) {
						java.net.URL url = new File("chatPreview.fxml").toURI().toURL();
						FXMLLoader fxmlLoader = new FXMLLoader();
						fxmlLoader.setLocation(url);
						Pane chatPreview = fxmlLoader.load();
						chatPreview.setId(user.getKey());
						chatPreview.toFront();
						ChatController chatController = fxmlLoader.getController();
						chatController.setData(allClients.get(user.getKey()));
						chatPreview.setOnMouseClicked(event -> {
							selectedClient = allClients.get(user.getKey());
							refreshChatMessages(allClients.get(user.getKey()));
							chatPreviews.forEach((key, value) -> {
								value.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
							});
							chatPreview.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
							chatController.read();
						});
	
						chatControllers.put(user.getKey(), chatController);
						chatPreviews.put(user.getKey(), chatPreview);
						Platform.runLater(() -> {	
							chatPreviewsPane.getChildren().add(0, chatPreview);
							ObservableList<Node> workingCollection = FXCollections.observableArrayList(chatPreviewsPane.getChildren());
							Collections.sort(workingCollection, new Comparator<Node>() {
								@Override
								public int compare(Node o1, Node o2) {
									if(Long.valueOf(o1.getId().substring(2)) > Long.valueOf(o2.getId().substring(2)))
										return -1;
									else if(Long.valueOf(o1.getId().substring(2)) == Long.valueOf(o2.getId().substring(2)))
										return 0;
									else
										return 1;
								}
							});
							chatPreviewsPane.getChildren().setAll(workingCollection);
						});
						
					}
				} catch(Exception e) { e.printStackTrace(); }
			}
			
    }
    
    public ArrayList<Pair<String, Pane>> createMessageDisplay(ArrayList<Pair<String, String>> messages, String name, String color, boolean floatRight) {
    	ArrayList<Pair<String, Pane>> currMessages = new ArrayList<>();
    	for(Pair<String, String> message : messages) {
			FlowPane parent = new FlowPane(); // Parent Width Spans the Width of Chat Display
			FlowPane pane = new FlowPane(); // Child of Parent - Restricts Message Side to 1/3 of Chat Display
			pane.setMaxWidth(chatDisplayMessages.getWidth() / 3);
			pane.setMinWidth(chatDisplayMessages.getWidth() / 3); 
			
			// Pane Style/Background for Rounded Text Bubbles and Custom Color
			pane.setStyle("-fx-background-size: 1200 900;\r\n-fx-background-radius: 18 18 18 18;\r\n-fx-border-radius: 0 0 18 18;\r\n-fx-background-color:" + color + ";");
			pane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
			
			// Placement of Text Messages (Chat Messages From Logged Coach floatRight others do not)
			if(floatRight)
				pane.setTranslateX(chatDisplayMessages.getWidth() / 3 * 2 - 20);
			else
				pane.setTranslateX(20);
			
			// Label holds the Message
			Label header = new Label(name);
			header.setFont(new Font("System Bold", 10));
			header.setPadding(new Insets(10));
			Label label = new Label(message.getSecond());
			label.setWrapText(true);
			label.setMaxWidth(pane.getMaxWidth());
			label.setPadding(new Insets(10));
			pane.getChildren().addAll(header, label);
			
			parent.getChildren().add(pane);	
			currMessages.add(new Pair<String, Pane>(message.getFirst(), parent));
			
		}
		return currMessages;
    }
    
    
	public void refreshChatMessages(Client clientKey) {
			if(selectedClient.equals(clientKey)) {
				chatDisplayMessages.getChildren().clear();
				ArrayList<Pair<String, Pane>> allMessages = new ArrayList<>();
				
				for(Pair<String, Pane> message : createMessageDisplay(allClients.get(clientKey.getKey()).messagesTo, loggedIn.toString(), "#FC3D44", true))
					allMessages.add(message);
				
				for(Pair<String, Pane> message : createMessageDisplay(allClients.get(clientKey.getKey()).messages, allClients.get(clientKey.getKey()).toString(), "#EFEFEF", false))
					allMessages.add(message);
				
				allClients.get(clientKey.getKey()).messagesOther.forEach((coach, messages) -> {
					for(Pair<String, Pane> message : createMessageDisplay(messages, allCoaches.get(coach).toString(), "lightblue", false))
							allMessages.add(message);
				});
				
				Collections.sort(allMessages, new Comparator<Pair<String, Pane>>() {
				    @Override
				    public int compare(final Pair<String, Pane> o1, final Pair<String, Pane> o2) {
						if(Long.valueOf(o1.getFirst().substring(2)) > Long.valueOf(o2.getFirst().substring(2)))
							return 1;
						else if(Long.valueOf(o1.getFirst().substring(2)) == Long.valueOf(o2.getFirst().substring(2)))
							return 0;
						else
							return -1;
				    }
				});
				
				for(Pair<String, Pane> message : allMessages) {
					Pane sep = new Pane();
					sep.setMinHeight(10);
					chatDisplayMessages.getChildren().addAll(sep, message.getSecond());							
				}
				
				Platform.runLater(() -> {
					chatControllers.get(clientKey.getKey()).read();
					chatDisplayScrollPane.setVvalue(1.0);
				});
			}
	}
    
	
	// refreshChat()
	// Ran by refreshChatThread and repeats every minute to update each chatPreview (ChatController of each preview), in which updates the last messages sent time from now
    public void refreshChat() {
    	while(true) {
	    	chatControllers.forEach((key, value) -> {
	    		Platform.runLater(() -> {	
	    			value.refresh();
	    		});
	    	});

	    		System.out.println("REFRESHING");
	    	try {
				Thread.sleep(60000L);
			} catch (InterruptedException e) { e.printStackTrace(); }
    	}
    }
    
    // showAlert()
    // Pop's up an alertPane with header text, content text and an 'Ok' button for display alerts to inform user
    public void showAlert(String headerText, String contentText, Pane background) {
    	alertPane.toFront();
    	background.setDisable(true);
    	background.setEffect(new GaussianBlur());
    	pageFadeIn(overlayPane, true);
    	pageFadeIn(alertPane, false);
    	alertHeader.setText(headerText);
    	alertBody.setText(contentText);
    	customAlertBtn.setOnMousePressed(event -> {
    		customAlertBtn.press();
    		background.setDisable(false);
    		background.setEffect(null);
    		pageFadeOut(overlayPane, true);
    		pageFadeOut(alertPane, true);
    	});
    }
    
    
    
    
    /*////////////////////////////////////////////////////////////////////////*/
	//                       TRANSITION & STYLE METHODS                       //
	/*////////////////////////////////////////////////////////////////////////*/
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
    
    public void matchBG(Node node, Color c) {
    	node.setStyle("-fx-background-color: rgb(" + (c.getRed()*255.0) + ", " + (c.getGreen()*255.0) + ", " + (c.getBlue()*255.0) + ");");
    }
    
    public void matchBG(ListView<?> node, Color c) {
    	node.setStyle("-fx-control-inner-background: rgb(" + (c.getRed()*255.0) + ", " + (c.getGreen()*255.0) + ", " + (c.getBlue()*255.0) + "); -fx-border-insets: 0; -fx-padding: 0; -fx-border-color: black; -fx-border-width: 1px;");
    }
    
    public void roundedPopup(Pane pane, int R, int G, int B) {
    	pane.setStyle("-fx-background-size: 1200 900;\r\n-fx-background-radius: 18 18 18 18;\r\n-fx-border-radius: 0 0 18 18;\r\n-fx-background-color: rgb(" + R + ", " + G + ", " + B + ");\r\n-fx-effect: dropshadow(three-pass-box, rgb(" + Color.color(R/255.0, B/255.0, G/255.0).darker().getRed() * 255 + ", " + Color.color(R/255.0, B/255.0, G/255.0).darker().getGreen() * 255 + ", " + Color.color(R/255.0, B/255.0, G/255.0).darker().getBlue() * 255 + "), 0, 0, 0, 6)");
		pane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
    }
    
    public void roundedPopup(Pane pane, Color c) {
    	pane.setStyle("-fx-background-size: 1200 900;\r\n-fx-background-radius: 18 18 18 18;\r\n-fx-border-radius: 0 0 18 18;\r\n-fx-background-color: rgb(" + c.getRed()*255 + ", " + c.getGreen()*255 + ", " + c.getBlue()*255 + ");\r\n-fx-effect: dropshadow(three-pass-box, rgb(" + c.darker().getRed() * 255 + ", " + c.darker().getGreen() * 255 + ", " + c.darker().getBlue() * 255 + "), 0, 0, 0, 6)");
		pane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
    }
}
