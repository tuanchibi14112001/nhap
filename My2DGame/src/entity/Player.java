package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{
	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	
//	public int hasKey = 0;		// how many keys that player has
	
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		
		screenX = (gp.screenWidth / 2) - (gp.tileSize / 2);		// lấy vị trí chính giữa của cửa sổ game, nhưng vị trí này được dùng làm vị trí trái trên
		screenY = (gp.screenHeight / 2) - (gp.tileSize / 2);	// khi vẽ player, nên phải trừ đi 1 nửa tile size, để player được vẽ ở vị trí chính giữa của cửa sổ game
		
		solidArea = new Rectangle();	// set the solid area of player
		solidArea.x = 9;
		solidArea.y = 21;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 27;
		solidArea.height = 21;
		
		setDefaultValue();
		getPlayerImage();
	}
	
	public void setDefaultValue() {		// default value for player
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		speed = 4;
		direction = "down";
		// player status(27/05/2022)
		 maxLife = 6;
		 life = maxLife;// 2 life = 1 heart
	}
	
	public void getPlayerImage() {		// get the images of player
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		// update only when a key is pressed
		if (keyH.upPressed == true || keyH.downPressed == true ||
				keyH.leftPressed == true || keyH.rightPressed == true) {
			
			if (keyH.upPressed == true) {
				direction = "up";	// update direction
			}
			else if (keyH.downPressed == true) {
				direction = "down";
			}
			else if (keyH.leftPressed == true) {
				direction = "left";
			}
			else if (keyH.rightPressed == true) {
				direction = "right";
			}
			
			collisionOn = false;	// reset collision status
			
			// check tile collision
			gp.cChecker.checkTile(this);
			
			// check object collision
			int objectIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objectIndex);
			
			// if collision is false, player can move
			if (collisionOn == false) {
				switch (direction) {
				case "up":
					worldY -= speed;	// update position
					break;
				case "down":
					worldY += speed;
					break;
				case "left":
					worldX -= speed;
					break;
				case "right":
					worldX += speed;
					break;
				}
			}
			
			spriteCounter++;	// everytime a frame passed, increase the counter
			
			if (spriteCounter > 10) {
				if (spriteNum == 1) {	// change the image
					spriteNum = 2;
				} else {
					spriteNum = 1;
				}
				
				spriteCounter = 0;		// reset counter
			}	
		} else {
			standCounter++;
			
			if (standCounter == 20) {
				spriteNum = 1;
				standCounter = 0;
			}
		}
	}
	
	public void pickUpObject(int index) {	// handle pick up action
		if (index != 999) {
			
		}
	}
	
	public void draw(Graphics2D g2) {
//		g2.setColor(Color.white);
//		
//		g2.fillRect(x, y, gp.tileSize, gp.tileSize);	// draw a rectangle
		
		BufferedImage image = null;
		
		switch(direction) {
		case "up":
			if (spriteNum == 1) {
				image = up1;
			}
			if (spriteNum == 2) {
				image = up2;
			}
			break;
		case "down":
			if (spriteNum == 1) {
				image = down1;
			}
			if (spriteNum == 2) {
				image = down2;
			}
			break;
		case "left":
			if (spriteNum == 1) {
				image = left1;
			}
			if (spriteNum == 2) {
				image = left2;
			}
			break;
		case "right":
			if (spriteNum == 1) {
				image = right1;
			}
			if (spriteNum == 2) {
				image = right2;
			}
			break;
		}
		
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
	}
}
