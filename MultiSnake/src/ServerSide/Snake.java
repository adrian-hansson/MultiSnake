package ServerSide;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import ClientSide.Level;

public class Snake {
	
	//----------------------------------
	// Statics to handle "global" variables
	//----------------------------------
	public static int MOVING_UP = 1;
	public static int MOVING_DOWN = 2;
	public static int MOVING_LEFT = 3;
	public static int MOVING_RIGHT = 4;
	
	//------------------------------------------
	// Position, speed, direction and size data
	//------------------------------------------
	private int posX, posY; //position of snake. Lower-left corner of snake's head
	private int sizeX, sizeY;
	private int direction; //which direction is the snake heading in?
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
