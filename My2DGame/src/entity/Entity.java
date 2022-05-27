package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {	// parent class for every entity in the game
	public int worldX, worldY;	// position of player in world map
	public int speed;
	
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;	// used to store player's image
	public String direction;	// the direction of player
	
	public int spriteCounter = 0;	// the number of frames that the player moves
	public int standCounter = 0;	// the number of frames that the player stands still
	public int spriteNum = 1;	// the index of current displayed image
	
	public Rectangle solidArea;		// the solid area of player, if it hit a tile that is not passable, player can not move
	public int solidAreaDefaultX, solidAreaDefaultY;	// store default value of solidArea, because we may change the x and y of solidArea later
	public boolean collisionOn = false;		// chekc if player is in collision or not
    
	//Character status
	public int maxLife;
	public int life;



}
