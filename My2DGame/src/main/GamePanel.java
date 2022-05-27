package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	// SCREEN SETTINGS
	final int originalTileSize = 16;	// 16x16 tile
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale;	// 48x48 tile
	public final int maxScreenCol = 16;	// 16 tiles
	public final int maxScreenRow = 12;	// 12 tiles
	public final int screenWidth = tileSize * maxScreenCol;	// 768 pixels
	public final int screenHeight = tileSize * maxScreenRow;	// 576 pixels
	
	// WORLD SETTINGS
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	
	// FPS
	int FPS = 60;
	
	// SYSTEM
	public TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler(this);
	Sound music = new Sound();
	Sound se = new Sound();
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);		// setup game environment
	Thread gameThread;
	public double playTime = 0;
	
	// UI
	public UI ui = new UI(this);
	
	// PLAYER AND OBJECTS
	public Player player = new Player(this, keyH);
	public SuperObject obj[] = new SuperObject[10];		// store game's object, upto 10 objects can be displayed in the map at the same time
	
	// GAME STATE
	// determine which state the game is: the game is being played, the game is paused,...
	// based on game's state, key handler may be different
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	
	// CONSTRUCTOR
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));	// set size for game panel
		this.setBackground(Color.black);	// background color: black
		this.setDoubleBuffered(true);	// improve game performance
		this.addKeyListener(keyH);	// add key listener to game panel
		this.setFocusable(true);	// this game panel can be focused to receive key input
	}
	
	public void setupGame() {	// pre-setup for our game
		aSetter.setObject();
		playMusic(0);
		gameState = titleState;
	}
	
	public void startGameThread() {		// start game loop
		gameThread = new Thread(this);
		gameThread.start();		// this will call run method
	}

	@Override
	// sleep method
//	public void run() {
//		double drawInterval = 1000000000 / FPS;	// draw after every 0.01666s
//		double nextDrawTime = System.nanoTime() + drawInterval;		// set next draw time
//		
//		while (gameThread != null) {
//			// UPDATE: update information
//			update();
//			
//			// DRAW: draw the screen with the updated information
//			repaint();
//			
//			try {
//				double remainingTime = nextDrawTime - System.nanoTime();	// calculate the remaining time to sleep the game loop
//				remainingTime = remainingTime / 1000000;
//				
//				if (remainingTime < 0) {
//					remainingTime = 0;
//				}
//				
//				Thread.sleep((long) remainingTime);		// make the game loop sleep until next draw time
//				
//				nextDrawTime += drawInterval;	// set the time for next draw
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
	
	// delta method
	public void run() {
		double drawInterval = 1000000000 / FPS;		// draw after every 0.01666s
		double delta = 0;	// the number of passed drawInterval
		long lastTime = System.nanoTime();		// time in previous loop
		long currentTime;	// current system time
		
		long timer = 0;		// the amount of time has passed
		int drawCount= 0;	// the number of times we updated and drawn
		
		while (gameThread != null) {
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			playTime += (currentTime - lastTime) / 1000000000F;
			
			lastTime = currentTime;
			
			if (delta >= 1) {	// if 1 or more drawInterval have passed, let's update and repaint
				update();
				repaint();
				
				delta--;	// minus 1 loop that we just did
				drawCount++;
			}
			
			if (timer >= 1000000000) {
				System.out.println("FPS: " + drawCount);
				
				timer = 0;
				drawCount = 0;
			}
		}
	}
	
	public void update() {
		if (gameState == playState) {
			player.update();
		}
		if (gameState == pauseState) {
			// do nothing
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		if (gameState == titleState) {
			ui.draw(g2);
		} else {
			// TILES
			tileM.draw(g2);		// make sure to draw tile before player, otherwise, we can not see player
			
			// OBJECTS
			for (int i = 0; i < obj.length; i++) {
				if (obj[i] != null) {
					obj[i].draw(g2, this);
				}
			}
			
			// PLAYER
			player.draw(g2);
			
			// UI
			ui.draw(g2);
		}
		
		g2.dispose();	// release memory
	}
	
	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}
	
	public void stopMusic() {
		music.stop();
	}
	
	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}
}
