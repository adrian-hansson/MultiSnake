package ClientSide;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

//This will probably EXTEND a Swing panel or something. Will have draw() methods, etc
//Might or might not hold the info about sockets,etc...
//..OR rename to Server and move 

public class Level extends JPanel{
	
	//------------------------------------------
	// Resources
	//------------------------------------------
	private URL imageURL, headURL; //URL of resource
	private BufferedImage image, headImage; //each section of the snake will be a small image like this
	Client client;
	private int x, y, index;
	
	public Level(){
		loadResources();
		client = new Client(this, "localhost", 30000);
	}
	
	public void update(int x, int y, int index){
		this.x = x;
		this.y = y;
		this.index = index;
		this.repaint();
	}
	
//	public void updateSnakes(){
//		for(int i = 0; i < snakes.size(); i++){
//			snakes.get(i); //do something here..
//		}
//	}
//	
//	public Snake getPlayer(){
//		return snake;
//	}
//	
//	public ArrayList<Snake> getSnakes(){
//		return snakes;
//	}
	
	public void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);
		draw(g2d);
	}
	
	public void draw(Graphics graphics){
		BufferedImage imageToDraw = image;
		if(index == 0){
			imageToDraw = headImage;
		}
		graphics.drawImage(imageToDraw, x, y, null);		
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
		try {
			image = ImageIO.read(imageURL); //loads the image
			headImage = ImageIO.read(headURL);
		} catch (IOException e) {
			System.out.println("Image failed to load");
			e.printStackTrace();
		}
	}
}
