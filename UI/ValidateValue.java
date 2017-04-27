package UI;

import javax.swing.*;
import java.awt.*;

public class ValidateValue{

	public Boolean valid;
	public Integer value;
	public String validationString;

	public ValidateValue(Boolean valid, Integer value, String validationString) {
		this.valid = valid;
		this.value = value;
		this.validationString= validationString;
	}

	public static ValidateValue validateNumber(String input, int min, int max) {
		boolean valid = true;
		int value = 0;
		String validation = "<html>";
		if (input.matches("\\d+")) {
			if (input.length() < 9) { //prevents crashing
				value = Integer.parseInt(input);
				if (value < min) {
					validation += "Value must be equal or greater than " + min + ".<br>";
					valid = false;
				}
				if (value > max) {
					validation += "Value must be equal or less than " + max + ".<br>";
					valid = false;
				}
			} else {
				validation += "Value must be equal or less than " + max + ".<br>";
				valid = false;
			}
		} else {
			validation += "Input not valid.<br>";
			valid = false;
		}
		validation += "</html>";
		return new ValidateValue(valid, value, validation);
	}

	public static JLabel createValidationTitleJLabel (ValidateValue validateValue, String labelTitle) {
		JLabel label = new JLabel(labelTitle);
		if (validateValue.valid) {
			label.setForeground(Color.GREEN);
		} else {
			label.setForeground(Color.RED);
		}
		return  label;
	}
}
