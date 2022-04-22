package project;

import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;



import com.google.firebase.database.utilities.Pair;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class ChatController {
	
	Thread chatThread;
	
	@FXML
	private Pane previewPane;
	
	@FXML
    private Label chatPreviewUsername;

    @FXML
    private Label chatPreviewLastMessage;

    @FXML
    private Label chatPreviewMessageDisplay;
    
    @FXML
    private Circle notReadCircle;
    
    @FXML
    private Label unreadMessagesLabel;
    
    private Client client;
    public Pair<String, String> lastMessage;
    private int unreadMessages = 0;

	public void setData(Client client) {
		this.client = client;
		if(client.messages.size() > 0)
			Collections.sort(client.messages, new Comparator<Pair<String, String>>() {
			    @Override
			    public int compare(final Pair<String, String> o1, final Pair<String, String> o2) {
					if(Long.valueOf(o1.getFirst().substring(2)) > Long.valueOf(o2.getFirst().substring(2)))
						return 1;
					else if(Long.valueOf(o1.getFirst().substring(2)) == Long.valueOf(o2.getFirst().substring(2)))
						return 0;
					else
						return -1;
			    }
			});
		if(client.messagesTo.size() > 0)
			Collections.sort(client.messagesTo, new Comparator<Pair<String, String>>() {
			    @Override
			    public int compare(final Pair<String, String> o1, final Pair<String, String> o2) {
					if(Long.valueOf(o1.getFirst().substring(2)) > Long.valueOf(o2.getFirst().substring(2)))
						return 1;
					else if(Long.valueOf(o1.getFirst().substring(2)) == Long.valueOf(o2.getFirst().substring(2)))
						return 0;
					else
						return -1;
			    }
			});
		if(client.messagesOther.size() > 0)
			client.messagesOther.forEach((coach, messages) -> {
				Collections.sort(messages, new Comparator<Pair<String, String>>() {
				    @Override
				    public int compare(final Pair<String, String> o1, final Pair<String, String> o2) {
						if(Long.valueOf(o1.getFirst().substring(2)) > Long.valueOf(o2.getFirst().substring(2)))
							return 1;
						else if(Long.valueOf(o1.getFirst().substring(2)) == Long.valueOf(o2.getFirst().substring(2)))
							return 0;
						else
							return -1;
				    }
				});
			});
		refresh();
		
		for(Pair<String, String> message : client.messages)
			if(message.getFirst().charAt(0) == '0')
				unreadMessages++;
		
		client.messagesOther.forEach((coach, messages) -> {
			for(Pair<String, String> message : messages)
				if(message.getFirst().charAt(0) == '0')
					unreadMessages++;
		});
		
		
		if(unreadMessages > 0) {
			if(unreadMessages > 9)
				unreadMessagesLabel.setText("9+");
			else 
				unreadMessagesLabel.setText(unreadMessages + "");
			
			notReadCircle.setOpacity(1.0);
			unreadMessagesLabel.setOpacity(1.0);
		}
		
		 previewPane.getChildren().add(new Label("TEST"));
		
	}
	
	public void refreshTime() {
		if(this.lastMessage != null) {
			LocalDateTime curr = LocalDateTime.now();
			LocalDateTime last = LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.valueOf(this.lastMessage.getFirst().substring(2))), ZoneId.systemDefault());
			
			long months = ChronoUnit.MONTHS.between(last, curr);
			long days = ChronoUnit.DAYS.between(last, curr);
			long hours = ChronoUnit.HOURS.between(last, curr);
			long minutes = ChronoUnit.MINUTES.between(last, curr);
	
			if(months == 0) {
				if(days == 0) {
					if(hours == 0) {
						if(minutes == 0) {
							chatPreviewLastMessage.setText(1 + "m");
						} else {
							chatPreviewLastMessage.setText(minutes + "m");
						}
					} else {
						chatPreviewLastMessage.setText(hours + "h");
					}
				} else {
					chatPreviewLastMessage.setText(days + "d");
				}
			} else {
				chatPreviewLastMessage.setText(months + "mo");
			}
		}
	}
	
	
	
	public void refresh() {
		if(client.messages.size() + client.messagesTo.size() + client.messagesOther.size() != 0) {
			chatPreviewUsername.setText(client.toString());
			Long messages = (client.messages.size() > 0) ? Long.valueOf(client.messages.get(client.messages.size() - 1).getFirst().substring(2)) : -1;
			Long messageTo = (client.messagesTo.size() > 0) ? Long.valueOf(client.messagesTo.get(client.messagesTo.size() - 1).getFirst().substring(2)) : -1;
			Long messageOther = 0L;
			ArrayList<Pair<String, String>> otherMessages = new ArrayList<>();
			client.messagesOther.forEach((coach, messagez) -> {
				otherMessages.add(new Pair<>(messagez.get(messagez.size() - 1).getFirst(), messagez.get(messagez.size() - 1).getSecond()));
			});
			Collections.sort(otherMessages, new Comparator<Pair<String, String>>() {
			    @Override
			    public int compare(final Pair<String, String> o1, final Pair<String, String> o2) {
					if(Long.valueOf(o1.getFirst().substring(2)) > Long.valueOf(o2.getFirst().substring(2)))
						return 1;
					else if(Long.valueOf(o1.getFirst().substring(2)) == Long.valueOf(o2.getFirst().substring(2)))
						return 0;
					else
						return -1;
			    }
			});
			
			if(otherMessages.size() > 0)
				messageOther = Long.valueOf(otherMessages.get(otherMessages.size() - 1).getFirst().substring(2));
			
			if(messages > messageTo && messages > messageOther)
				this.lastMessage = client.messages.get(client.messages.size() - 1);
			else if(messageTo > messages && messageTo > messageOther)
				this.lastMessage = client.messagesTo.get(client.messagesTo.size() - 1);
			else
				this.lastMessage = otherMessages.get(otherMessages.size() - 1);
			
			chatPreviewMessageDisplay.setText(lastMessage.getSecond());
			previewPane.setId(lastMessage.getFirst());
			refreshTime();
		}
	}



}
