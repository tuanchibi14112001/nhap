package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
	GamePanel gp;
	public Tile[] tile;	// represent our tile
	public int mapTileNum[][];		// represent a map with tile index
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[10];	// we have 10 different tiles
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];		// store map
		
		getTileImage();
		loadMap("/maps/world01.txt");
	}
	
	public void getTileImage() {
		try {
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
			tile[1].collision = true;
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
			tile[2].collision = true;
			
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));
			
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
			tile[4].collision = true;
			
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadMap(String filePath) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath);		// read map info from .txt map data file
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
				String line = br.readLine();	// read a single line in text file
				
				while (col < gp.maxWorldCol) {
					String numbers[] = line.split(" ");		// split the string at a space
					
					int num = Integer.parseInt(numbers[col]);		// convert String to Integer
					
					mapTileNum[col][row] = num;		// store the tile number
					col++;
				}
				
				if (col == gp.maxWorldCol) {	// move to new line
					col = 0;
					row++;
				}
			}
			
			br.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2) {
		int worldCol = 0;
		int worldRow = 0;
		
		while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			int tileNum = mapTileNum[worldCol][worldRow];
			
			int worldX = worldCol * gp.tileSize;	// get the x and y of the tile
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;	// vị trí vẽ tile khi lấy player làm gốc
			int screenY = worldY - gp.player.worldY + gp.player.screenY;	// chú ý rằng player luôn được vẽ ở giữa cửa sổ game, nên cần cộng thêm screenX và screenY của player để lấy được tọa độ chính xác
			
			if (worldX > gp.player.worldX - gp.player.screenX - gp.tileSize
				&& worldX < gp.player.worldX + gp.player.screenX + gp.tileSize
				&& worldY > gp.player.worldY - gp.player.screenY - gp.tileSize
				&& worldY < gp.player.worldY + gp.player.screenY + gp.tileSize) {	// only draw tiles within screen's range around player
				
				g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			}
			
			worldCol++;
			
			if (worldCol == gp.maxWorldCol) {
				worldCol = 0;
				
				worldRow++;
			}
		}
	}
}
