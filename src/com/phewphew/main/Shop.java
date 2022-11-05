package com.phewphew.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Shop extends MouseAdapter{
	
	Handler handler;
	HUD hud;
	
	//Price
	private int B1 = 10;
	private int B2 = 10;
	private int B3 = 30;
	
	//Money+Level of Upgrade
	private int money;
	private int upg_lv1 = 1;
	private int upg_lv2 = 1;
	
	private float tempHealth;
	
	public Shop(Handler handler, HUD hud) {
		this.handler = handler;
		this.hud = hud;
		
	}
	public void render(Graphics g) {
		g.setColor(Color.white);
		Font fnt = new Font("Silkscreen",0,48);
		Font fnt2 = new Font("arial",0,12);
		Font fnt3 = new Font("arial",0,18);
		
		g.setFont(fnt);
		g.drawString("Shop", Game.WIDTH/2 - 70, 55);
		
		g.setFont(fnt2);
		
		//HEALTH
		g.drawRect(100,100,100,80);
		g.drawString("Upgrade Health", 110, 120);
		g.drawString("Cost: "+B1, 110, 140);
		g.drawString("Level: "+upg_lv1, 110, 155);
		
		//SPEED
		g.drawRect(100,200,100,80);
		g.drawString("Upgrade Speed", 110, 220);
		g.drawString("Cost: "+B2, 110, 240);
		g.drawString("Level: "+upg_lv2, 110, 255);
				
		//REFILL HEALTH
		g.drawRect(100,300,100,80);
		g.drawString("Refill Health", 110, 320);
		g.drawString("Cost: "+B3, 110, 340);
		
		g.setFont(fnt3);
		//Money
		g.drawString("Current Credit: "+hud.getCredit(), 250, 400);
	}
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if(mx >= 100 && mx <= 200) {
			if(my >= 100 && my <= 180) {
				 //upgrade health
				if(upg_lv1 < 5 && hud.getCredit() >= B1) {
					money = hud.getCredit();
					upg_lv1 += 1;
					hud.bounds += 20;
					tempHealth = hud.HEALTH;
					hud.HEALTH = 100 + (hud.bounds / 2);
					hud.HEALTH = tempHealth;
					money -= B1;
					hud.setCredit(money);
					B1 += 10;
				}
			}
		}
		
		if(mx >= 100 && mx <= 200) {
			if(my >= 200 && my <= 280) {
				 if(upg_lv2 < 5 && hud.getCredit() >= B2) {
					 money = hud.getCredit();
					 upg_lv2 += 1;
					 handler.speed++;
					 money -= B2;
					 hud.setCredit(money);
					 B2 += 10;
				 }
				
			}
		}
		
		if(mx >= 100 && mx <= 200) {
			if(my >= 300 && my <= 380) {
				 //refill health
				money = hud.getCredit();
				if(money >= B3) {
					hud.HEALTH = 100 + (hud.bounds / 2);
					money -= B3;
					hud.setCredit(money);
				}
				
			}
		}
	}
	public void setB1(int B1) {
		this.B1 = B1;
	}
	public void setB2(int B2) {
		this.B2 = B2;
	}
	public void setupglv1(int upg_lv1) {
		this.upg_lv1 = upg_lv1;
	}
	public void setupglv2(int upg_lv2) {
		this.upg_lv2 = upg_lv2;
	}
	public int getB1() {
		return B1;
	}
	public int getB2() {
		return B2;
	}
	public int getupglv1() {
		return upg_lv1;
	}
	public int getupglv2() {
		return upg_lv2;
	}
}
