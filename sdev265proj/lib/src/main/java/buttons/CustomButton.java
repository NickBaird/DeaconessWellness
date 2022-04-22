package buttons;

import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class CustomButton extends javafx.scene.control.Button {
	
	public Color color;
	public Color colorD;
	public String pressed;
	public String regular;
	
	ScaleTransition st = new ScaleTransition();
	TranslateTransition tt = new TranslateTransition();
	
	Button ofThis;

	public CustomButton() {
		
	}
	
	public CustomButton(String label) {
		this.setText(label);
	}
	
	public CustomButton(String label, double r, double g, double b) {
		
		ofThis = this;
		st.setDuration(Duration.millis(175.0));
		st.setInterpolator(Interpolator.EASE_BOTH);
		st.setAutoReverse(false);
		st.setNode(ofThis);
		
		tt.setDuration(Duration.millis(20.0));
		tt.setAutoReverse(false);
		tt.setNode(ofThis);

		this.setText(label);
		this.setPadding(new Insets(5));
		this.color = new Color(r, g, b, 1.0);
		colorD = color.darker();
		this.regular = "-fx-background-color: rgb(" + r * 255 + ", " + g * 255 + ", " + b * 255 + "); -fx-effect: dropshadow(three-pass-box, rgb(" + color.darker().getRed() * 255 + ", " + color.darker().getGreen() * 255 + ", " + color.darker().getBlue() * 255 + "), 0, 0, 0, 4);"; 
		this.pressed = "-fx-background-color: rgb(" + colorD.getRed() * 255 + ", " + colorD.getGreen() * 255 + ", " + colorD.getBlue() * 255 + "); -fx-effect: dropshadow(three-pass-box, rgb(" + colorD.darker().getRed() * 255 + ", " + colorD.darker().getGreen() * 255 + ", " + colorD.darker().getBlue() * 255 + "), 0, 0, 0, 0);";
		this.setStyle(regular);
		
		this.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				setStyle(pressed);
				tt.setToY(4.0);
				tt.play();
			}
		});
		this.setOnMouseReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				setStyle(regular);
				tt.setToY(0.0);
				tt.play();
			}
		});
		this.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				st.stop();
				st.setToX(1.15);
				st.setToY(1.15);
				st.play();
			}
		});
		this.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				st.stop();
				st.setToX(1.0);
				st.setToY(1.0);
				st.play();
			}
		});
	}
	
	public CustomButton(String label, double r, double g, double b, Button existing) {
		
		ofThis = this;
		st.setDuration(Duration.millis(175.0));
		st.setInterpolator(Interpolator.EASE_BOTH);
		st.setAutoReverse(false);
		st.setNode(ofThis);
		
		tt.setDuration(Duration.millis(20.0));
		tt.setAutoReverse(false);
		tt.setNode(ofThis);

		this.setText(label);
		this.setPadding(new Insets(5));
		this.color = new Color(r, g, b, 1.0);
		colorD = color.darker();
		this.regular = "-fx-background-color: rgb(" + r * 255 + ", " + g * 255 + ", " + b * 255 + "); -fx-effect: dropshadow(three-pass-box, rgb(" + color.darker().getRed() * 255 + ", " + color.darker().getGreen() * 255 + ", " + color.darker().getBlue() * 255 + "), 0, 0, 0, 4);"; 
		this.pressed = "-fx-background-color: rgb(" + colorD.getRed() * 255 + ", " + colorD.getGreen() * 255 + ", " + colorD.getBlue() * 255 + "); -fx-effect: dropshadow(three-pass-box, rgb(" + colorD.darker().getRed() * 255 + ", " + colorD.darker().getGreen() * 255 + ", " + colorD.darker().getBlue() * 255 + "), 0, 0, 0, 0);";
		this.setStyle(regular);
		
		this.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				setStyle(pressed);
				tt.setToY(4.0);
				tt.play();
			}
		});
		this.setOnMouseReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				setStyle(regular);
				tt.setToY(0.0);
				tt.play();
			}
		});
		this.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				st.stop();
				st.setToX(1.1);
				st.setToY(1.1);
				st.play();
			}
		});
		this.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				st.stop();
				st.setToX(1.0);
				st.setToY(1.0);
				st.play();
			}
		});
		
		this.setLayoutX(existing.getLayoutX());
		this.setLayoutY(existing.getLayoutY());
		this.setPrefWidth(existing.getPrefWidth());
		this.setPrefHeight(existing.getPrefHeight());
    	existing.setDisable(true);
    	existing.setOpacity(0);
	}
	
public CustomButton(String label, Color color, Button existing) {
	
		double r = color.getRed();
		double g = color.getGreen();
		double b = color.getBlue();
		
		ofThis = this;
		st.setDuration(Duration.millis(175.0));
		st.setInterpolator(Interpolator.EASE_BOTH);
		st.setAutoReverse(false);
		st.setNode(ofThis);
		
		tt.setDuration(Duration.millis(20.0));
		tt.setAutoReverse(false);
		tt.setNode(ofThis);

		this.setText(label);
		this.setPadding(new Insets(5));
		this.color = new Color(r, g, b, 1.0);
		colorD = color.darker();
		this.regular = "-fx-background-color: rgb(" + r * 255 + ", " + g * 255 + ", " + b * 255 + "); -fx-effect: dropshadow(three-pass-box, rgb(" + color.darker().getRed() * 255 + ", " + color.darker().getGreen() * 255 + ", " + color.darker().getBlue() * 255 + "), 0, 0, 0, 4);"; 
		this.pressed = "-fx-background-color: rgb(" + colorD.getRed() * 255 + ", " + colorD.getGreen() * 255 + ", " + colorD.getBlue() * 255 + "); -fx-effect: dropshadow(three-pass-box, rgb(" + colorD.darker().getRed() * 255 + ", " + colorD.darker().getGreen() * 255 + ", " + colorD.darker().getBlue() * 255 + "), 0, 0, 0, 0);";
		this.setStyle(regular);
		
		this.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				setStyle(pressed);
				tt.setToY(4.0);
				tt.play();
			}
		});
		this.setOnMouseReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				setStyle(regular);
				tt.setToY(0.0);
				tt.play();
			}
		});
		this.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				st.stop();
				st.setToX(1.1);
				st.setToY(1.1);
				st.play();
			}
		});
		this.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				st.stop();
				st.setToX(1.0);
				st.setToY(1.0);
				st.play();
			}
		});
		
		this.setLayoutX(existing.getLayoutX());
		this.setLayoutY(existing.getLayoutY());
		this.setPrefWidth(existing.getPrefWidth());
		this.setPrefHeight(existing.getPrefHeight());
    	existing.setDisable(true);
    	existing.setOpacity(0);
	}
	
	
public CustomButton(String label, int r, int g, int b, Button existing) {
		
		ofThis = this;
		st.setDuration(Duration.millis(175.0));
		st.setInterpolator(Interpolator.EASE_BOTH);
		st.setAutoReverse(false);
		st.setNode(ofThis);
		
		tt.setDuration(Duration.millis(20.0));
		tt.setAutoReverse(false);
		tt.setNode(ofThis);

		this.setText(label);
		this.setPadding(new Insets(5));
		//this.color = new Color(r, g, b, 1.0);
		this.color = Color.rgb(r, g, b);
		colorD = color.darker();
		this.regular = "-fx-background-color: rgb(" + r * 255 + ", " + g * 255 + ", " + b * 255 + "); -fx-effect: dropshadow(three-pass-box, rgb(" + color.darker().getRed() * 255 + ", " + color.darker().getGreen() * 255 + ", " + color.darker().getBlue() * 255 + "), 0, 0, 0, 4);"; 
		this.pressed = "-fx-background-color: rgb(" + colorD.getRed() * 255 + ", " + colorD.getGreen() * 255 + ", " + colorD.getBlue() * 255 + "); -fx-effect: dropshadow(three-pass-box, rgb(" + colorD.darker().getRed() * 255 + ", " + colorD.darker().getGreen() * 255 + ", " + colorD.darker().getBlue() * 255 + "), 0, 0, 0, 0);";
		this.setStyle(regular);
		
		this.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				setStyle(pressed);
				tt.setToY(4.0);
				tt.play();
			}
		});
		this.setOnMouseReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				setStyle(regular);
				tt.setToY(0.0);
				tt.play();
			}
		});
		this.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				st.stop();
				st.setToX(1.15);
				st.setToY(1.15);
				st.play();
			}
		});
		this.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				st.stop();
				st.setToX(1.0);
				st.setToY(1.0);
				st.play();
			}
		});
		
		this.setLayoutX(existing.getLayoutX());
		this.setLayoutY(existing.getLayoutY());
		this.setPrefWidth(existing.getPrefWidth());
		this.setPrefHeight(existing.getPrefHeight());
    	existing.setDisable(true);
    	existing.setOpacity(0);
	}
	
	
	
	public void press() {
		setStyle(pressed);
		tt.setToY(4.0);
		tt.play();
	}
	
	public void release() {
		setStyle(regular);
		tt.setToY(0.0);
		tt.play();
	}
	
	
	
	
		
	

}
