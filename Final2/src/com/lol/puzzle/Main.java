package com.lol.puzzle;

import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Main {

	public static final String TITLE = "MEME CHOOSER 2000";
	
	public static String[] memeNames = {
			"bald_alex.png",
			"be_good.jpg",
			"deal.jpeg",
			"gulp.jpg",
			"missing_the_bus.jpg",
			"pfw_rep.png",
			"sneaky_storm.jpg",
			"thanos_2.png",
			"thanos.png",
			"the_smart_one.jpg",
			"whoops_pi.png"
	};
	
	private static BufferedImage selectedMeme;
	
	private static void findMeme(String input) throws IOException {
		selectedMeme = null;
		
		//find meme
		for (int i = 0; i < memeNames.length; i++) {
			if (memeNames[i].contains(input)) {
				selectedMeme = ImageIO.read(new File("src/" + memeNames[i]));
			}
		}
		
		//if no meme is found, repeat search with different input
		if (selectedMeme == null) {
			findMeme(JOptionPane.showInputDialog(null, "\"" + input + "\" returned no results. Please choose another meme:", "MEME CHOOSER 2000", JOptionPane.QUESTION_MESSAGE));
		}
		
		if (selectedMeme.getWidth() > 800 || selectedMeme.getHeight() > 450) {
			selectedMeme = resizeImage(800, 450, selectedMeme);
		}
	}
	
	private static BufferedImage resizeImage(int maxWidth, int maxHeight, BufferedImage image) {
		int origWidth = image.getWidth();
		int origHeight = image.getHeight();
		double aspectRatio = (double) origWidth / origHeight;
		
		int newWidth = maxWidth;
		int newHeight = maxHeight;
		
		if (origWidth > maxWidth) {
			newWidth = maxWidth;
			newHeight = (int) (newWidth / aspectRatio);
		}
		
		if (newHeight > maxHeight) {
			newHeight = maxHeight;
			newWidth = (int) (newHeight * aspectRatio);
		}
		
		BufferedImage resized = new BufferedImage(newWidth, newHeight, image.getType());
		Graphics2D g = resized.createGraphics();
		
		g.drawImage(image, 0, 0, newWidth, newHeight, 0, 0, origWidth, origHeight, null);
		
		g.dispose();
		return resized;
	}
	
	public static void main(String[] args) throws HeadlessException, IOException {
		//char flag = 'y';
		//do {
			findMeme(JOptionPane.showInputDialog(null, "Search for a meme for your puzzle:", TITLE, JOptionPane.QUESTION_MESSAGE));
			int size = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter puzzle size:", TITLE, JOptionPane.QUESTION_MESSAGE));
			new Puzzle(selectedMeme, size);
			//JOptionPane.showMessageDialog(null, "Your meme:", "MEME CHOOSER 2000", JOptionPane.PLAIN_MESSAGE, selectedMeme);
		//	flag = JOptionPane.showInputDialog("Would you like to see another meme? (Y/N)").trim().toLowerCase().charAt(0);
		//} while (flag != 'n');
	}

}
