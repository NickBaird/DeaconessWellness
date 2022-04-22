package labels;

import javafx.geometry.Insets;
import javafx.scene.paint.Color;

public class CustomLabel extends javafx.scene.control.Label {
	
	public Color color;
	public Color colorD;
	public String regular;
	
	public CustomLabel() {
		
	}
	
	public CustomLabel(String label) {
		this.setText(label);
	}
	
	public CustomLabel(String label, double r, double g, double b) {
		
		this.setText(label);
		
		this.setPadding(new Insets(5));
		this.color = new Color(r, g, b, 1.0);
		colorD = color.darker();
		this.regular = "-fx-border-radius: 10px; -fx-background-color: rgb(" + r * 255 + ", " + g * 255 + ", " + b * 255 + "); -fx-effect: dropshadow(three-pass-box, rgb(" + color.darker().getRed() * 255 + ", " + color.darker().getGreen() * 255 + ", " + color.darker().getBlue() * 255 + "), 0, 0, 0, 4);"; 
		//this.pressed = "-fx-background-color: rgb(" + colorD.getRed() * 255 + ", " + colorD.getGreen() * 255 + ", " + colorD.getBlue() * 255 + "); -fx-effect: dropshadow(three-pass-box, rgb(" + colorD.darker().getRed() * 255 + ", " + colorD.darker().getGreen() * 255 + ", " + colorD.darker().getBlue() * 255 + "), 0, 0, 0, 0);";
		this.setStyle(regular);
	}
}
