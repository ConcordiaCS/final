package com.lol.puzzle;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

public class Button extends JToggleButton {

	private static final long serialVersionUID = 3624600372653366122L;

	public Button(ImageIcon image) {
		super(image);
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		this.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					
				}
			}
		}); 
	}
	
}
