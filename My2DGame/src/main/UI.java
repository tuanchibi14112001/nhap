package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Heart;
import object.OBJ_Key;
import object.SuperObject;

public class UI {
	GamePanel gp;
	Graphics2D g2;
	public int commandNum = 0;
	
//	BufferedImage keyImage;		// store key image
	BufferedImage heart_full, heart_half, heart_blank; // store heart
	Boolean messageOn = false;		// used to display notification message
	String message = "";
	int messageCounter = 0;
	
	public Boolean gameFinished = false;	// the game is finished or not yet
	
	DecimalFormat dFormat = new DecimalFormat("#0.00");		// used to format play time when it is printed
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
//		arial_40 = new Font("Arial", Font.PLAIN, 40);	// set the font for text
//		arial_80B = new Font("Arial", Font.BOLD, 80);
		
//		OBJ_Key key = new OBJ_Key();	// get key's image to display on UI
//		keyImage = key.image;
		SuperObject heart = new OBJ_Heart();
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
	}
	
	public void showMessage(String text) {	// set the message to display
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		
//		g2.setFont(arial_40);
//		g2.setColor(Color.white);
		
		// TITLE STATE
		if (gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		
		// PLAY STATE
		if (gp.gameState == gp.playState) {
			// do play state stuff
			drawPlayerLife();
		}
		
		// PAUSE STATE
		if (gp.gameState == gp.pauseState) {
			drawPauseScreen();
			drawPlayerLife();
		}
	}
	
	private void drawPlayerLife() {
		// TODO Auto-generated method stub
		//draw maxlife
		int x = gp.tileSize/2;
		int y = gp.tileSize/2;
		int i = 0;
		while (i < gp.player.maxLife/2) {
			g2.drawImage(heart_blank, x, y,gp.tileSize,gp.tileSize,null);
			i++;
			x+= gp.tileSize;
		}
		//draw heart_full
		x = gp.tileSize/2;
		y = gp.tileSize/2;
		i = 0;
		while (i < gp.player.life) {
			g2.drawImage(heart_half, x, y,gp.tileSize,gp.tileSize,null);
			i++;
			if(i<gp.player.life) {
			g2.drawImage(heart_full, x, y,gp.tileSize,gp.tileSize,null);
			}
			i++;
			x+= gp.tileSize;
		}
		
		
		// draw heart half
		
	}

	public void drawTitleScreen() {
		// screen color
		g2.setColor(new Color(70, 150, 80));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		// title name
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
		String text = "Blue Boy Adventure";
		int x = getXForCenteredText(text);
		int y = gp.tileSize * 3;
		
		// shadow title
		g2.setColor(Color.black);
		g2.drawString(text, x + 5, y + 5);
		
		// main title
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		// display character
		x = gp.screenWidth / 2 - gp.tileSize;
		y += gp.tileSize * 2;
		g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);
		
		// menu
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
		
		text = "NEW GAME";
		x = getXForCenteredText(text);
		y += gp.tileSize * 4;
		g2.drawString(text, x, y);
		if (commandNum == 0) {
			g2.drawString(">", x - gp.tileSize, y);
		}
		
		text = "LOAD GAME";
		x = getXForCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if (commandNum == 1) {
			g2.drawString(">", x - gp.tileSize, y);
		}
		
		text = "QUIT";
		x = getXForCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if (commandNum == 2) {
			g2.drawString(">", x - gp.tileSize, y);
		}
	}
	
	public void drawPauseScreen() {
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
		g2.setColor(Color.white);
		
		String text = "PAUSED";
		
		int x = getXForCenteredText(text);
		int y = gp.screenHeight / 2;
		
		g2.drawString(text, x, y);
	}
	
	public int getXForCenteredText(String text) {
		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		return gp.screenWidth / 2 - length / 2;
	}
}
