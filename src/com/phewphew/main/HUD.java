package com.phewphew.main;

import java.awt.Color;
import java.awt.Graphics;

public class HUD {
	
	public static float HEALTH = 100;
	
	public int bounds = 0;
	private int score = 0;
	private int level = 1;
	private float greenValue = 255f;
	
	private int credit = 0;
	
	
	public void tick() {
		
		HEALTH = Game.clamp(HEALTH, 0, 100 + (bounds/2));
		
		greenValue = Game.clamp(greenValue, 0, 255);
		greenValue = HEALTH*2;
		
		score++;
	}
	public void render(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(15, 15, 200 + bounds, 32);
		//Change color to dark red when health low
		g.setColor(new Color(75,(int)greenValue,0));
		g.fillRect(15, 15, (int)HEALTH*2, 32);
		
		//String in HUD
		g.setColor(Color.white);
		g.drawString("Score: "+score,15,64);
		g.drawString("Level: "+level,15,80);
		g.drawString("Difficulty: "+Game.diff_label,15,95);
		g.drawString("Press SPACE for shop | P for PAUSE",15,110);
		g.drawString("Made by..65010604_Phrit Nakgate | Code Ref..RealtutsGML",10,425);
		g.drawString("Credit: "+credit, 520, 40);
	}
	
	public void score(int score) {
		this.score = score;
	}
	public int getScore() {
		return score;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	public int getCredit() {
		return credit;
	}
}
