package ClientSide;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Snake {
	
	//-------------------------------------
	// Statics to handle "global" variables...
	//-------------------------------------
	public static int MOVING_UP = 1;
	public static int MOVING_DOWN = 2;
	public static int MOVING_LEFT = 3;
	public static int MOVING_RIGHT = 4;
	
	//------------------------------------------
	// Position, speed, direction and size data
	//------------------------------------------
	private int posX, posY; //position of snake. Might need to replace this.. need some form of Array to store the position of each Snake segment
	private int sizeX, sizeY;
	private int direction; //which direction is the snake heading in. This value should be sent to the server, so that it knows how to move the snake
	private int speed = 1; //default is 1xMap Speed
	
	//------------------------------------------
	// Resources
	//------------------------------------------
	private URL imageURL; //URL of resource
	private BufferedImage image; //each section of the snake will be a small image like this
	
	//------------------------------------------
	// Misc
	//------------------------------------------
	private Level level;//used to call draw() methods from, etc
	
	public Snake(Level level){
		try {
			image = ImageIO.read(imageURL); //loads the image
		} catch (IOException e) {
			System.out.println("Image failed to load");
			e.printStackTrace();
		}
		posX = image.getHeight();
		posY = image.getWidth();
	}
	
	public void update(){
		//updates movement, etc and stuff
		
	}
	
	public void eatApple(){
		//reaches an apple
	}
	
	public void death(){
		//calls when the snake dies
	}
	
	public void pressUp(){
		direction = Snake.MOVING_UP;
	}
	public void pressDown(){
		direction = Snake.MOVING_DOWN;
	}
	public void pressLeft(){
		direction = Snake.MOVING_LEFT;
	}
	public void pressRight(){
		direction = Snake.MOVING_RIGHT;
	}
	public int getDirection(){
		return direction;
	}

}
