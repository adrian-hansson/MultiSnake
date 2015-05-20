package ClientSide;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

//This will probably EXTEND a Swing panel or something. Will have draw() methods, etc
//Might or might not hold the info about sockets,etc...
//..OR rename to Server and move 

public class Level extends JPanel{
	
	//------------------------------------------
	// Resources
	//------------------------------------------
	private URL urlAPPLE, urlSNAKEHEADDEAD, urlSNAKEBODYDEAD, urlSNAKEGREENBODY, urlSNAKEGREENHEAD, urlSNAKEGREENBODYGHOST, urlSNAKEGREENHEADGHOST, urlSNAKEBLUEBODY, urlSNAKEBLUEHEAD, urlSNAKEBLUEBODYGHOST, urlSNAKEBLUEHEADGHOST, urlSNAKEYELLOWBODY, urlSNAKEYELLOWHEAD, urlSNAKEYELLOWBODYGHOST, urlSNAKEYELLOWHEADGHOST, urlSNAKEPURPLEBODY, urlSNAKEPURPLEHEAD, urlSNAKEPURPLEBODYGHOST, urlSNAKEPURPLEHEADGHOST; 
	private BufferedImage imageAPPLE, imageSNAKEHEADDEAD, imageSNAKEBODYDEAD, imageSNAKEGREENBODY, imageSNAKEGREENHEAD, imageSNAKEGREENBODYGHOST, imageSNAKEGREENHEADGHOST, imageSNAKEBLUEBODY, imageSNAKEBLUEHEAD, imageSNAKEBLUEBODYGHOST, imageSNAKEBLUEHEADGHOST, imageSNAKEYELLOWBODY, imageSNAKEYELLOWHEAD, imageSNAKEYELLOWBODYGHOST, imageSNAKEYELLOWHEADGHOST, imageSNAKEPURPLEBODY, imageSNAKEPURPLEHEAD, imageSNAKEPURPLEBODYGHOST, imageSNAKEPURPLEHEADGHOST; 
	Client client;
	private int x, y, index;
	private LinkedList<GridObject> newGrid = new LinkedList<GridObject>();
	
	private static final int APPLE = 0, SNAKEHEADDEAD = 1, SNAKEBODYDEAD = 2, SNAKEGREENBODY = 3, SNAKEGREENHEAD = 4, SNAKEGREENBODYGHOST = 5, SNAKEGREENHEADGHOST = 6, SNAKEBLUEBODY = 7, SNAKEBLUEHEAD = 8, SNAKEBLUEBODYGHOST = 9, SNAKEBLUEHEADGHOST = 10, SNAKEYELLOWBODY = 11, SNAKEYELLOWHEAD = 12, SNAKEYELLOWBODYGHOST = 13, SNAKEYELLOWHEADGHOST = 14, SNAKEPURPLEBODY = 15, SNAKEPURPLEHEAD = 16, SNAKEPURPLEBODYGHOST = 17, SNAKEPURPLEHEADGHOST = 18;
	
	public Level(Socket socket){
		loadResources();
		client = new Client(this, socket, 30000);
	}
	
	public void update(int x, int y, int index){
		newGrid.add(new GridObject(x, y, index));
//		this.x = x;
//		this.y = y;
//		this.index = index;
//		this.repaint();
	}
	
	public void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);
		draw(g2d);
	}
	
	public void draw(Graphics graphics){
		GridObject o;
		while(!newGrid.isEmpty()) {
			o = newGrid.poll();
			BufferedImage imageToDraw = null;
			if(o.getIndex() == APPLE) {
				imageToDraw = imageAPPLE;
			}else if(o.getIndex() == SNAKEHEADDEAD){
				imageToDraw = imageSNAKEHEADDEAD;
			}else if(o.getIndex() == SNAKEBODYDEAD){
				imageToDraw = imageSNAKEBODYDEAD;
			}else if(o.getIndex() == SNAKEGREENBODY){
				imageToDraw = imageSNAKEGREENBODY;
			}else if(o.getIndex() == SNAKEGREENHEAD){
				imageToDraw = imageSNAKEGREENHEAD;
			}else if(o.getIndex() == SNAKEGREENBODYGHOST){
				imageToDraw = imageSNAKEGREENBODYGHOST;
			}else if(o.getIndex() == SNAKEGREENHEADGHOST){
				imageToDraw = imageSNAKEGREENHEADGHOST;
			}else if(o.getIndex() == SNAKEBLUEBODY){
				imageToDraw = imageSNAKEBLUEBODY;
			}else if(o.getIndex() == SNAKEBLUEHEAD){
				imageToDraw = imageSNAKEBLUEHEAD;
			}else if(o.getIndex() == SNAKEBLUEBODYGHOST){
				imageToDraw = imageSNAKEBLUEBODYGHOST;
			}else if(o.getIndex() == SNAKEBLUEHEADGHOST){
				imageToDraw = imageSNAKEBLUEHEADGHOST;
			}else if(o.getIndex() == SNAKEYELLOWBODY){
				imageToDraw = imageSNAKEYELLOWBODY;
			}else if(o.getIndex() == SNAKEYELLOWHEAD){
				imageToDraw = imageSNAKEYELLOWHEAD;
			}else if(o.getIndex() == SNAKEYELLOWBODYGHOST){
				imageToDraw = imageSNAKEYELLOWBODYGHOST;
			}else if(o.getIndex() == SNAKEYELLOWHEADGHOST){
				imageToDraw = imageSNAKEYELLOWHEADGHOST;
			}else if(o.getIndex() == SNAKEPURPLEBODY){
				imageToDraw = imageSNAKEPURPLEBODY;
			}else if(o.getIndex() == SNAKEPURPLEHEAD){
				imageToDraw = imageSNAKEPURPLEHEAD;
			}else if(o.getIndex() == SNAKEPURPLEBODYGHOST){
				imageToDraw = imageSNAKEPURPLEBODYGHOST;
			}else if(o.getIndex() == SNAKEPURPLEHEADGHOST){
				imageToDraw = imageSNAKEPURPLEHEADGHOST;
			}
			try {
				graphics.drawImage(imageToDraw, o.getX(), o.getY(), null);
			} catch(NullPointerException e) {
				System.out.println("Reference to nonexsistent image index, no image drawn");
			}

		}
	}
	
	public void pressUp(){
		client.sendToServer(0);
	}
	public void pressDown(){
		client.sendToServer(1);
	}
	public void pressLeft(){
		client.sendToServer(2);
	}
	public void pressRight(){
		client.sendToServer(3);
	}
	
	private void loadResources(){
		urlAPPLE  = (this.getClass().getResource("/resources/AppleRed.png"));
		urlSNAKEHEADDEAD  = (this.getClass().getResource("/resources/SnakeHeadDead.png"));
		urlSNAKEBODYDEAD  = (this.getClass().getResource("/resources/SnakeBodyDead.png"));
		urlSNAKEGREENBODY  = (this.getClass().getResource("/resources/SnakeGreenBody.png"));
		urlSNAKEGREENHEAD  = (this.getClass().getResource("/resources/SnakeGreenHead.png"));
		urlSNAKEGREENBODYGHOST  = (this.getClass().getResource("/resources/SnakeGreenBodyGhost.png"));
		urlSNAKEGREENHEADGHOST  = (this.getClass().getResource("/resources/SnakeGreenHeadGhost.png"));
		urlSNAKEBLUEBODY  = (this.getClass().getResource("/resources/SnakeBlueBody.png"));
		urlSNAKEBLUEHEAD  = (this.getClass().getResource("/resources/SnakeBlueHead.png"));
		urlSNAKEBLUEBODYGHOST  = (this.getClass().getResource("/resources/SnakeBlueBodyGhost.png"));
		urlSNAKEBLUEHEADGHOST  = (this.getClass().getResource("/resources/SnakeBlueHeadGhost.png"));
		urlSNAKEYELLOWBODY  = (this.getClass().getResource("/resources/SnakeYellowBody.png"));
		urlSNAKEYELLOWHEAD  = (this.getClass().getResource("/resources/SnakeYellowHead.png"));
		urlSNAKEYELLOWBODYGHOST  = (this.getClass().getResource("/resources/SnakeYellowBodyGhost.png"));
		urlSNAKEYELLOWHEADGHOST  = (this.getClass().getResource("/resources/SnakeYellowHeadGhost.png"));
		urlSNAKEPURPLEBODY  = (this.getClass().getResource("/resources/SnakePurpleBody.png"));
		urlSNAKEPURPLEHEAD  = (this.getClass().getResource("/resources/SnakePurpleHead.png"));
		urlSNAKEPURPLEBODYGHOST  = (this.getClass().getResource("/resources/SnakePurpleBodyGhost.png"));
		urlSNAKEPURPLEHEADGHOST  = (this.getClass().getResource("/resources/SnakePurpleHeadGhost.png"));
		try {
			imageAPPLE = ImageIO.read(urlAPPLE);
			imageSNAKEHEADDEAD = ImageIO.read(urlSNAKEHEADDEAD);
			imageSNAKEBODYDEAD = ImageIO.read(urlSNAKEBODYDEAD);
			imageSNAKEGREENBODY = ImageIO.read(urlSNAKEGREENBODY);
			imageSNAKEGREENHEAD = ImageIO.read(urlSNAKEGREENHEAD);
			imageSNAKEGREENBODYGHOST = ImageIO.read(urlSNAKEGREENBODYGHOST);
			imageSNAKEGREENHEADGHOST = ImageIO.read(urlSNAKEGREENHEADGHOST);
			imageSNAKEBLUEBODY = ImageIO.read(urlSNAKEBLUEBODY);
			imageSNAKEBLUEHEAD = ImageIO.read(urlSNAKEBLUEHEAD);
			imageSNAKEBLUEBODYGHOST = ImageIO.read(urlSNAKEBLUEBODYGHOST);
			imageSNAKEBLUEHEADGHOST = ImageIO.read(urlSNAKEBLUEHEADGHOST);
			imageSNAKEYELLOWBODY = ImageIO.read(urlSNAKEYELLOWBODY);
			imageSNAKEYELLOWHEAD = ImageIO.read(urlSNAKEYELLOWHEAD);
			imageSNAKEYELLOWBODYGHOST = ImageIO.read(urlSNAKEYELLOWBODYGHOST);
			imageSNAKEYELLOWHEADGHOST = ImageIO.read(urlSNAKEYELLOWHEADGHOST);
			imageSNAKEPURPLEBODY = ImageIO.read(urlSNAKEPURPLEBODY);
			imageSNAKEPURPLEHEAD = ImageIO.read(urlSNAKEPURPLEHEAD);
			imageSNAKEPURPLEBODYGHOST = ImageIO.read(urlSNAKEPURPLEBODYGHOST);
			imageSNAKEPURPLEHEADGHOST = ImageIO.read(urlSNAKEPURPLEHEADGHOST);
		} catch (IOException e) {
			System.out.println("Image failed to load");
			e.printStackTrace();
		}
	}
	
	class GridObject {
		int xCoord, yCoord, ImgIdx;
	
		public GridObject(int x, int y, int index) {
			xCoord = x;
			yCoord = y;
			ImgIdx = index;
		}
		
		public int getX() {
			return xCoord;
		}
		public int getY() {
			return yCoord;
		}
		public int getIndex() {
			return ImgIdx;
		}
	}
}
