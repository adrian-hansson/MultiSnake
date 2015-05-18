package ClientSide;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetAddress;
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
	private URL imageURL, headURL, appleURL, headDeadURL, imageDeadURL; //URL of resource
	private BufferedImage image, headImage, appleImage, headImageDead, ImageDead; //each section of the snake will be a small image like this
	Client client;
	private int x, y, index;
	private LinkedList<GridObject> newGrid = new LinkedList<GridObject>();
	
	public Level(){
		loadResources();
		try {
			InetAddress addr = InetAddress.getByName("83.251.168.36");
		} catch(UnknownHostException e) {
			System.out.println("Could not connect to host");
		}
		client = new Client(this, "localhost", 30000);	//need to find way to connect to other than "localhost"
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
			if(o.getIndex() == 1) {
				imageToDraw = image;
			}else if(o.getIndex() == 0){
				imageToDraw = headImage;
			}else if(o.getIndex() == 2){
				imageToDraw = appleImage;
			}else if(o.getIndex() == 3){
				imageToDraw = headImageDead;
			}else if(o.getIndex() == 4){
				imageToDraw = ImageDead;
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
		imageURL = (this.getClass().getResource("/resources/SnakeGreen.png"));
		headURL = (this.getClass().getResource("/resources/SnakeHead.png"));
		imageDeadURL = (this.getClass().getResource("/resources/SnakeGreenDead.png"));
		headDeadURL = (this.getClass().getResource("/resources/SnakeHeadDead.png"));
		appleURL = (this.getClass().getResource("/resources/AppleRed.png"));
		try {
			image = ImageIO.read(imageURL); //loads the image
			headImage = ImageIO.read(headURL);
			appleImage = ImageIO.read(appleURL);
			headImageDead = ImageIO.read(headDeadURL);
			ImageDead = ImageIO.read(imageDeadURL);
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
