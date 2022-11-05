package com.phewphew.main;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends Canvas {

	private static final long serialVersionUID = -1838568332250739078L;
	
	public Window(int width,int height,String title,Game game) {
		JFrame frame = new JFrame(title);
		//Window Size Properties
		frame.setPreferredSize(new Dimension(width,height));
		frame.setMaximumSize(new Dimension(width,height));
		frame.setMinimumSize(new Dimension(width,height));
		
		//Other Window Properties
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null); //Middle of Screen
		frame.setFocusable(true);
		
		//Add Game to Window
		frame.add(game);
		frame.setVisible(true);
		game.start();
	}
	
}
