package ClientSide;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Snake {
	
	//-------------------------------------
	// Statics to handle "global" variables...
	//-------------------------------------
	public static int MOVING_UP = 0;
	public static int MOVING_DOWN = 1;
	public static int MOVING_LEFT = 2;
	public static int MOVING_RIGHT = 3;
	
	//------------------------------------------
	// Position, speed, direction and size data
	//------------------------------------------
	private int posX, posY; //position of snake HEAD. Might need to replace this.. need some form of Array to store the position of each Snake segment
	private int[][] positions; //TEST: first bracket holds segment number, second bracket holds posX at [0] and posY at [1]
	//example: [5][0] == posX of 5th segment. [5][1] == posY of 5th segment
	
	private int sizeX, sizeY;
	private int direction; //which direction is the snake heading in. This value should be sent to the server, so that it knows how to move the snake
	private int speed = 1; //default is 1xMap Speed
	private int size = 25;
	private boolean isDead;
	
	//------------------------------------------
	// Resources
	//------------------------------------------
	private URL imageURL, headURL; //URL of resource
	private BufferedImage image, headImage; //each section of the snake will be a small image like this
	
	//------------------------------------------
	// Misc
	//------------------------------------------
	private Level level;//used to call draw() methods from, etc
	
	public Snake(Level level){
		this.level = level;
		loadResources();
		positions = new int[500][2];
		sizeX = image.getHeight();
		sizeY = image.getWidth();
		
		//TEMP
			posX = 200;
			posY = 200;
			positions[0][0] = 200;
			positions[0][1] = 200;
			direction = Snake.MOVING_UP;
	//END OF TEMP
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
	
	public void draw(Graphics graphics){
		//possibly obsolete. keeping the method here to remind it 'might' be needed.
		for(int i = 0; i < size; i++){
			if(i == 0){
				graphics.drawImage(headImage, positions[i][0], positions[i][1], null); //first image is always the head
			}
			else{
				graphics.drawImage(image, positions[i][0], positions[i][1], null);
			}
		}
	}
	
	public void move(){
		//updates movement, etc and stuff
		//if(!isDead){
		if(direction == Snake.MOVING_UP){
			int xPrev = positions[0][0];
			int yPrev = positions[0][1];
			int xNew, yNew;
			positions[0][1] = positions[0][1]-10;
			for(int i = 1; i < size; i++){
				xNew = positions[i][0];
				yNew = positions[i][1];
				positions[i][0] = xPrev;
				positions[i][1] = yPrev;
				xPrev = xNew;
				yPrev = yNew;
			}
			level.repaint();
		}
		else if(direction == Snake.MOVING_DOWN){
			int xPrev = positions[0][0];
			int yPrev = positions[0][1];
			int xNew, yNew;
			positions[0][1] = positions[0][1]+10;
			for(int i = 1; i < size; i++){
				xNew = positions[i][0];
				yNew = positions[i][1];
				positions[i][0] = xPrev;
				positions[i][1] = yPrev;
				xPrev = xNew;
				yPrev = yNew;
			}
			level.repaint();
		}
		else if(direction == Snake.MOVING_LEFT){
			int xPrev = positions[0][0];
			int yPrev = positions[0][1];
			int xNew, yNew;
			positions[0][0] = positions[0][0]-10;
			for(int i = 1; i < size; i++){
				xNew = positions[i][0];
				yNew = positions[i][1];
				positions[i][0] = xPrev;
				positions[i][1] = yPrev;
				xPrev = xNew;
				yPrev = yNew;
			}
			level.repaint();
		}
		else if(direction == Snake.MOVING_RIGHT){
			int xPrev = positions[0][0];
			int yPrev = positions[0][1];
			int xNew, yNew;
			positions[0][0] = positions[0][0]+10;
			for(int i = 1; i < size; i++){
				xNew = positions[i][0];
				yNew = positions[i][1];
				positions[i][0] = xPrev;
				positions[i][1] = yPrev;
				xPrev = xNew;
				yPrev = yNew;
			}
			level.repaint();
		}
		//}
	}
	
	public void eatApple(){
		//reaches an apple
	}
	
	public void death(){
		//calls when the snake dies
	}
	
	public void pressUp(){
		if(direction != Snake.MOVING_DOWN){
			direction = Snake.MOVING_UP;
			//TEMP BELOW
			int xPrev = positions[0][0];
			int yPrev = positions[0][1];
			int xNew, yNew;
			positions[0][1] = positions[0][1]-10;
			for(int i = 1; i < size; i++){
				xNew = positions[i][0];
				yNew = positions[i][1];
				positions[i][0] = xPrev;
				positions[i][1] = yPrev;
				xPrev = xNew;
				yPrev = yNew;
			}
			level.repaint();
		}
	}
	public void pressDown(){
		if(direction != Snake.MOVING_UP){
			direction = Snake.MOVING_DOWN;
			//TEMP BELOW
			int xPrev = positions[0][0];
			int yPrev = positions[0][1];
			int xNew, yNew;
			positions[0][1] = positions[0][1]+10;
			for(int i = 1; i < size; i++){
				xNew = positions[i][0];
				yNew = positions[i][1];
				positions[i][0] = xPrev;
				positions[i][1] = yPrev;
				xPrev = xNew;
				yPrev = yNew;
			}
			level.repaint();
		}
	}
	public void pressLeft(){
		if(direction != Snake.MOVING_RIGHT){
			direction = Snake.MOVING_LEFT;
			//TEMP BELOW
			int xPrev = positions[0][0];
			int yPrev = positions[0][1];
			int xNew, yNew;
			positions[0][0] = positions[0][0]-10;
			for(int i = 1; i < size; i++){
				xNew = positions[i][0];
				yNew = positions[i][1];
				positions[i][0] = xPrev;
				positions[i][1] = yPrev;
				xPrev = xNew;
				yPrev = yNew;
			}
			level.repaint();
		}
	}
	public void pressRight(){
		if(direction != Snake.MOVING_LEFT){
			direction = Snake.MOVING_RIGHT;
			//TEMP BELOW
			int xPrev = positions[0][0];
			int yPrev = positions[0][1];
			int xNew, yNew;
			positions[0][0] = positions[0][0]+10;
			for(int i = 1; i < size; i++){
				xNew = positions[i][0];
				yNew = positions[i][1];
				positions[i][0] = xPrev;
				positions[i][1] = yPrev;
				xPrev = xNew;
				yPrev = yNew;
			}
			level.repaint();
		}
	}
	public int getDirection(){
		return direction;
	}
	public void setDirection(int s){
		direction = s; //for testing purposes
	}
	
	public int getX(){
		return posX; //return posX for Snake Head
	}
	
	public int getY(){
		return posY; //return posY for Snake Head
	}
	
	public int[][] getPositions(){
		return positions;
	}
	
	public int getSize(){
		return size;
	}
	public void setSize(int s){
		size = s;
	}
	
//	public static void main(String[] args){
//		int[][] array = new int[1100][10000];
//		System.out.println(array.length);
//	}

}
