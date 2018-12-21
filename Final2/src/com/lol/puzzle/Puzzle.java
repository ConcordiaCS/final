package com.lol.puzzle;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Puzzle extends JFrame {

	private static final long serialVersionUID = 8524463713749297411L;
	
	private JPanel panel;
	private GridLayout grid;
	
	private BufferedImage meme;
	private String name;
	private int size;
	
	private ArrayList<Button> solution;
	private ArrayList<Button> scrambled;
	
	private long startTime;
	
	public Puzzle(BufferedImage selectedMeme, String name, int size) {
		meme = selectedMeme;
		this.name = name;
		this.size = size;
		
		panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.setLayout(grid = new GridLayout(size, size));
		add(panel);
		
		solution = new ArrayList<>(size * size);
		
		initPicture();
		openWindow();
		startTime = System.currentTimeMillis();
	}
	
	private void initPicture() {
		int columns = grid.getColumns();
		int rows = grid.getRows();
		
		int width = meme.getWidth();
		int height = meme.getHeight();
		
		SwapAction action = new SwapAction();
		for (int i = 0; i < columns; i++) {
			for (int j = 0; j < rows; j++) {
				Image img = this.createImage(new FilteredImageSource(meme.getSource(), 
						new CropImageFilter(j * width / columns, i * height / rows, width / columns, height / rows)));
				Button button = new Button(new ImageIcon(img));
				button.addItemListener(action);
				solution.add(button);
			}
		}
		
		scrambled = scramble(solution);
		scrambled.forEach((b) -> { panel.add(b); });
	}
	
	private void openWindow() {
		pack();
		setTitle(Main.TITLE);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private ArrayList<Button> scramble(ArrayList<Button> original) {
		Random r = new Random();
		ArrayList<Button> scr = new ArrayList<>(original.size());
		
		for (int i = 0; i < original.size(); i++) {
			scr.add(null);
		}
		
		for (Button b : original) {
			boolean success = false;
			while (!success) {
				int i = r.nextInt(original.size());
				if (scr.get(i) == null) {
					scr.set(i, b);
					success = true;
				}
			}
		}
		return scr;
	}
	
	private void solved() {
		this.dispose();
		String finish = new DecimalFormat("#.##").format((System.currentTimeMillis() - startTime) / 1000D);
		JOptionPane.showMessageDialog(null, "You won! It took you " + finish + " seconds.", Main.TITLE, JOptionPane.INFORMATION_MESSAGE);
		highScore(finish);
	}
	
	private void highScore(String s) {
		double score = Double.parseDouble(s);
		String fileName = name.split("\\.")[0] + "_scores.txt";
		File file = new File(fileName);
		
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		double highScore = 0.0;
		String message = "You got a HIGH SCORE for size " + size + " of the \"" + name + "\" meme puzzle!\nYour score was " + score + " seconds!";
		
		ArrayList<String> lines = new ArrayList<>();
		try (Scanner scan = new Scanner(file)) {
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				if (line.startsWith(Integer.toString(size))) {
					highScore = Double.parseDouble(line.split(":")[1]);
					if (score < highScore) {
						JOptionPane.showMessageDialog(null, message, Main.TITLE, JOptionPane.INFORMATION_MESSAGE);
						line = size + ":" + score;
					}
				}
				
				lines.add(line);
			}
			
			if (highScore == 0.0) {
				JOptionPane.showMessageDialog(null, message, Main.TITLE, JOptionPane.INFORMATION_MESSAGE);
				lines.add(size + ":" + score);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
				
		try (PrintWriter writer = new PrintWriter(fileName)) {
			for (String line : lines) {
				writer.println(line);
			}
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private class SwapAction implements ItemListener {

		private Button[] selected = new Button[2];
		private int index = 0;
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				selected[index] = (Button) e.getItem();
				index++;
				if (index >= 2) {
					index = 0;
					swapButtons(selected[0], selected[1]);
					if (checkSolution()) {
						solved();
					}
				}
			} else index = 0;
		}
		
		private void swapButtons(Button b1, Button b2) {
			Collections.swap(scrambled, scrambled.indexOf(b1), scrambled.indexOf(b2));
			
			scrambled.forEach((b) -> { 
				if (b.isSelected()) {
					b.setSelected(false);
				}
			});
			
			panel.removeAll();
			for (Button b : scrambled) {
				panel.add(b);
			}
			panel.validate();
		}
		
		private boolean checkSolution() {
			boolean solved = true;
			for (Button b : solution) {
				solved &= (scrambled.get(solution.indexOf(b)).equals(b));
			}
			return solved;
		}
		
	}
	
}
