package com.lol.puzzle;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

public class Button extends JToggleButton {

	private static final long serialVersionUID = 3624600372653366122L;

	public Button(ImageIcon image) {
		super(image);
		
		//add borders based on selection state
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		this.addItemListener((e) -> {
			Button b = (Button) e.getItem();
			if (b.isSelected()) {
				b.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
			} else {
				b.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
			}
		});
	}
	
}
