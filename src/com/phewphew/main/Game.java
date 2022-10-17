package com.phewphew.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = -2734233349537750526L;
	
	public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;
	private Thread thread;
	
	private boolean running = false;
	public static boolean pause = false;
	
	//Difficulty 0 = Normal 1= Hard 
	public int diff = 0;
	public static String diff_label = "";
	
	private Handler handler;
	private HUD hud;
	private Shop shop;
	private Spawn spawner;
	//Make GameState for making some menu and other HUD
	private Menu menu;
	public enum STATE{
		Menu,
		Scoreboard,
		Game,
		End,
		Select,
		Shop
	};
	public STATE gameState = STATE.Menu;
	
	private Random r;
	
	public Game() {
		handler = new Handler();
		
		//All hud+ui
		hud = new HUD();
		menu = new Menu(this,handler,hud);
		shop = new Shop(handler,hud);
		this.addMouseListener(menu);
		this.addMouseListener(shop);
		
		
		//AudioPlayer.load();
		//AudioPlayer.getMusic("music").loop();
		
		spawner = new Spawn(handler,hud,this);
		
		r = new Random();
		
		this.addKeyListener(new KeyInput(handler,this));
		
		new Window(WIDTH,HEIGHT,"PhewPhew",this);
		
		if(gameState != STATE.Game) {
			for(int i = 0; i < 20 ;i++) {
				handler.addObject(new MenuParticle(r.nextInt(WIDTH),r.nextInt(HEIGHT),ID.MenuParticle,handler));
			}
		}
		
		//For keylistener
		this.requestFocusInWindow();
	}

	public synchronized void start() {
		thread = new Thread(this);	//handle multitasking
		thread.start();
		running = true;
	}
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void run() {
		//Game Loop
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				delta--;
			}
			if(running) {
				render();
			}
			frames++;
			if(System.currentTimeMillis() - timer > 1000) {
				timer+=1000;
				//System.out.println("FPS: "+frames);
				frames = 0;
			}
		}
		stop();
	}
	
	private void tick() {
		if(gameState == STATE.Game) {
			if(!pause) {
				hud.tick();
				spawner.tick();
				handler.tick();
			
				if(HUD.HEALTH <= 0) {
					HUD.HEALTH = 100;
					handler.clearPlayer();
					handler.clearEnemy();
					gameState = STATE.End;
				for(int i = 0; i < 20 ;i++) {
					handler.addObject(new MenuParticle(r.nextInt(WIDTH),r.nextInt(HEIGHT),ID.MenuParticle,handler));
				}
			}
			}
		}else if(gameState == STATE.Menu || gameState == STATE.Scoreboard || gameState == STATE.End || gameState == STATE.Select) {
			handler.tick();
			menu.tick();
		}
		
	}
	
	private void render() {
		// organize complex memory on a particular Canvas or Window
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3); //Create 3 buffer
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		//Black Screen
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		
		if(pause) {
			g.setColor(Color.white);
			g.drawString("PAUSED", WIDTH/2 - 32, HEIGHT/2 - 32);
		}
		
		if(gameState == STATE.Game) {
			hud.render(g);
			handler.render(g);
		}else if(gameState == STATE.Menu || gameState == STATE.Scoreboard || gameState == STATE.End || gameState == STATE.Select){
			g.setColor(Color.white);
			menu.render(g);
			handler.render(g);
		}else if(gameState == STATE.Shop) {
			shop.render(g);
		}
		g.dispose();
		bs.show();
	}
	
	//Limit object to some value
	public static float clamp(float var,float min,float max) {
		if(var >= max) {
			return var = max;
		}else if(var <= min) {
			return var = min;
		}else {
			return var;
		}
	}
	public static void main(String args[]) {
		new Game();
	}
}
