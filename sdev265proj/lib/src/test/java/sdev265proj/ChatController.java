package sdev265proj;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ChatController {
	
	Thread chatThread;
	
	@FXML
    private Label chatPreviewUsername;

    @FXML
    private Label chatPreviewLastMessage;

    @FXML
    private Label chatPreviewMessageDisplay;
    
    private User user;
    
	public void setData(User user) {
		this.user = user;
		//Platform.runLater(() -> {
			chatPreviewUsername.setText(user.username);
			chatPreviewLastMessage.setText(String.valueOf(user.lastMessageTime));
			chatPreviewMessageDisplay.setText(user.lastMessage);
		//});
	}



}
