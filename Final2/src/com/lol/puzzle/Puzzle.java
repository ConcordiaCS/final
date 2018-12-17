package com.lol.puzzle;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class Puzzle extends JFrame {

	private static final long serialVersionUID = 8524463713749297411L;
	
	private JPanel panel;
	private GridLayout grid;
	
	private BufferedImage meme;
	
	public Puzzle(BufferedImage selectedMeme, int size) {
		meme = selectedMeme;
		
		panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.setLayout(grid = new GridLayout(size, size));
		add(panel);
		
		initPicture();
		openWindow();
	}
	
	private void initPicture() {
		int columns = grid.getColumns();
		int rows = grid.getRows();
		
		int width = meme.getWidth();
		int height = meme.getHeight();
		
		for (int i = 0; i < columns; i++) {
			for (int j = 0; j < rows; j++) {
				Image img = this.createImage(new FilteredImageSource(meme.getSource(), 
						new CropImageFilter(j * width / columns, i * height / rows, width / columns, height / rows)));
				Button button = new Button(new ImageIcon(img));
				panel.add(button);
			}
		}
	}
	
	private void openWindow() {
		pack();
		setTitle(Main.TITLE);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
}
