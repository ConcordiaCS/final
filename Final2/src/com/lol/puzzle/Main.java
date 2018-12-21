package com.lol.puzzle;

import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Main {

	public static final String TITLE = "MEME PUZZLE 2000";
	
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
	private static String name;
	
	private static void findMeme(String input) throws IOException {
		selectedMeme = null;
		
		//find meme
		for (int i = 0; i < memeNames.length; i++) {
			if (memeNames[i].contains(input)) {
				selectedMeme = ImageIO.read(new File("src/" + memeNames[i]));
				name = memeNames[i];
			}
		}
		
		//if no meme is found, repeat search with different input
		if (selectedMeme == null) {
			findMeme(JOptionPane.showInputDialog(null, "\"" + input + "\" returned no results. Please choose another meme:", TITLE, JOptionPane.QUESTION_MESSAGE));
		}
		
		//resize to max of 800 by 450
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
		findMeme(JOptionPane.showInputDialog(null, "Search for a meme for your puzzle:", TITLE, JOptionPane.QUESTION_MESSAGE));
		int size = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter puzzle size:", TITLE, JOptionPane.QUESTION_MESSAGE));
		
		if (size <= 1) {
			while (size <= 1) {
				size = Integer.parseInt(JOptionPane.showInputDialog(null, "Size can't be one or less! Enter a new size:", TITLE, JOptionPane.QUESTION_MESSAGE));
			}
		}
		
		int tooBig = 0;
		String message = "You shouldn't see this.";
		if (size >= 1000) {
			while (size >= 1000) {
				tooBig++;
				switch (tooBig) {
				
				case 1:
					message = "Maybe you shouldn't do that... Enter a new size:";
					break;
					
				case 2:
					message = "You probably shouldn't do that... Enter a new size:";
					break;
					
				case 3:
					message = "You definitely don't want to do that... Enter a new size:";
					break;
					
				case 4:
					message = "DON'T DO IT. STOP. NOW. Enter a new size:";
					break;
					
				case 5:
					JOptionPane.showMessageDialog(null, "You are no longer able to play this game.\n"
							+ "It's not funny. You actually can't. You definitely cannot simply reopen the app. It won't work.\n"
							+ "Don't even try it.\n"
							+ "In order to reactivate this game, you must pay the developer $1,000,000. Have a nice day...\n\n\n"
							+ "...I know I will.", TITLE, JOptionPane.ERROR_MESSAGE);
					System.exit(0);
					break;
				
				}
				size = Integer.parseInt(JOptionPane.showInputDialog(null, message, TITLE, JOptionPane.QUESTION_MESSAGE));
			}
		}
		
		new Puzzle(selectedMeme, name, size);
	}

}
