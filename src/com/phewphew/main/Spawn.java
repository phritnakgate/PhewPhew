package com.phewphew.main;

import java.util.Random;

public class Spawn {
	private Handler handler;
	private HUD hud;
	private Game game;
	private Random r = new Random();
	
	private int scoreStorer = 0;
	
	public Spawn(Handler handler,HUD hud,Game game) {
		this.handler = handler;
		this.hud = hud;
		this.game = game;
	}
	
	public void tick() {
		scoreStorer++;
		if(scoreStorer >= 1000) {
			scoreStorer = 0;
			hud.setLevel(hud.getLevel()+1);
			hud.setCredit(hud.getCredit()+10);
			
			if(game.diff == 0) {
				if(hud.getLevel() == 2) {
					handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH-50),r.nextInt(Game.HEIGHT-50),ID.BasicEnemy,handler));
				}else if(hud.getLevel() == 3) {
					handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH-50),r.nextInt(Game.HEIGHT-50),ID.FastEnemy,handler));
				}else if(hud.getLevel() == 4) {
					handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH-50),r.nextInt(Game.HEIGHT-50),ID.BasicEnemy,handler));
				}else if(hud.getLevel() == 5) {
					handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH-50),r.nextInt(Game.HEIGHT-50),ID.FastEnemy,handler));
				}else if(hud.getLevel() == 6) {
					handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH-50),r.nextInt(Game.HEIGHT-50),ID.SmartEnemy,handler));
				}else if(hud.getLevel() == 10) {
					handler.clearEnemy();
					handler.addObject(new EnemyBoss((Game.WIDTH / 2)-48,-140,ID.EnemyBoss,handler));
				}else if(hud.getLevel() == 11) {
					handler.clearEnemy();
					handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH-50),r.nextInt(Game.HEIGHT-50),ID.FastEnemy,handler));
				}
			}else {
				if(hud.getLevel() == 2) {
					handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH-50),r.nextInt(Game.HEIGHT-50),ID.FastEnemy,handler));
				}else if(hud.getLevel() == 3) {
					handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH-50),r.nextInt(Game.HEIGHT-50),ID.FastEnemy,handler));
				}else if(hud.getLevel() == 4) {
					handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH-50),r.nextInt(Game.HEIGHT-50),ID.BasicEnemy,handler));
				}else if(hud.getLevel() == 5) {
					handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH-50),r.nextInt(Game.HEIGHT-50),ID.FastEnemy,handler));
				}else if(hud.getLevel() == 6) {
					handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH-50),r.nextInt(Game.HEIGHT-50),ID.SmartEnemy,handler));
				}else if(hud.getLevel() == 10) {
					handler.clearEnemy();
					handler.addObject(new EnemyBoss((Game.WIDTH / 2)-48,-140,ID.EnemyBoss,handler));
				}else if(hud.getLevel() == 11) {
					handler.clearEnemy();
					handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH-50),r.nextInt(Game.HEIGHT-50),ID.FastEnemy,handler));
					handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH-50),r.nextInt(Game.HEIGHT-50),ID.FastEnemy,handler));
				}
			}
			
		}
	}
}
