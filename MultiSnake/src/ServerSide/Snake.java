package ServerSide;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.URL;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import Transport.ProtocolServer;
import ClientSide.Level;

public class Snake extends Thread{
	
	//-------------------------------------
	// Statics to handle "global" variables...
	//-------------------------------------
	public static final int MOVING_UP = 0;
	public static final int MOVING_DOWN = 1;
	public static final int MOVING_LEFT = 2;
	public static final int MOVING_RIGHT = 3;
	
	//------------------------------------------
	// Position, speed, direction and size data
	//------------------------------------------
	private int[][] positions; //TEST: first bracket holds segment number, second bracket holds posX at [0] and posY at [1]
	//example: [5][0] == posX of 5th segment. [5][1] == posY of 5th segment
	
	private int direction; //which direction is the snake heading in. This value should be sent to the server, so that it knows how to move the snake
	private int speed = 1; //default is 1xMap Speed
	private int size = 1; //All snakes start with length 1
	private int growth;
	private int decay = 0;
	private boolean isDead;
	private LinkedList<Integer> dirBuffer = new LinkedList<Integer>();
	
	//------------------------------------------
	// Resources
	//------------------------------------------
	private URL imageURL, headURL; //URL of resource
	private BufferedImage image, headImage; //each section of the snake will be a small image like this
	
	//------------------------------------------
	// Misc
	//------------------------------------------
	private Server server;
	private ProtocolServer protocol;
	private Socket socket;
	
	public Snake(Server server, Socket socket, int posX, int posY, int growth){
		this.server = server;
		this.growth = growth;
		this.socket = socket;

		protocol = new ProtocolServer(socket, this);
		positions = new int[2500][2];
		
		// Set start positions
		positions[0][0] = posX;
		positions[0][1] = posY;

		direction = Snake.MOVING_UP;

		this.start();
	}
	
	public void run(){
		while(!socket.isClosed()){
			protocol.serverRead();
		}
		server.removeSnake(this);
		System.out.println("Client left");
	}
	
	public void move(){
		//if(!isDead){
		int xPrev = positions[0][0];
		int yPrev = positions[0][1];
		int xNew, yNew;
		
		if(growth > 0) {	//If snake hasn't reached full length add to the tail
			size++;
			growth--;
		}
		
		if(!dirBuffer.isEmpty()) {	//Get queued user commands
			direction = dirBuffer.poll();
		}
		
		if(direction == Snake.MOVING_UP){
			positions[0][1] = positions[0][1]-10;
			for(int i = 1; i < size; i++){
				xNew = positions[i][0];
				yNew = positions[i][1];
				positions[i][0] = xPrev;
				positions[i][1] = yPrev;
				xPrev = xNew;
				yPrev = yNew;
			}
		}
		else if(direction == Snake.MOVING_DOWN){
			positions[0][1] = positions[0][1]+10;
			for(int i = 1; i < size; i++){
				xNew = positions[i][0];
				yNew = positions[i][1];
				positions[i][0] = xPrev;
				positions[i][1] = yPrev;
				xPrev = xNew;
				yPrev = yNew;
			}
		}
		else if(direction == Snake.MOVING_LEFT){
			positions[0][0] = positions[0][0]-10;
			for(int i = 1; i < size; i++){
				xNew = positions[i][0];
				yNew = positions[i][1];
				positions[i][0] = xPrev;
				positions[i][1] = yPrev;
				xPrev = xNew;
				yPrev = yNew;
			}
		}
		else if(direction == Snake.MOVING_RIGHT){
			positions[0][0] = positions[0][0]+10;
			for(int i = 1; i < size; i++){
				xNew = positions[i][0];
				yNew = positions[i][1];
				positions[i][0] = xPrev;
				positions[i][1] = yPrev;
				xPrev = xNew;
				yPrev = yNew;
			}
		}
		//}
	}
	
	public void addTail(int growth) {
		this.growth = this.growth + growth; 
	}
	
	public void death(){
		isDead = true;
	}
	
	public boolean isDead() {
		return isDead;
	}
	
	public void decay() {
		decay++;
	}
	
	public int getDecay() {
		return decay;
	}
	
	public int getDirection(){
		return direction;
	}
	
	public void setDirection(int s){
		if(dirBuffer.size() <= 2) {	//Maximum two commands in waiting
			switch(s) {
			case MOVING_UP: pressUp();
			break;
			case MOVING_DOWN: pressDown();
			break;
			case MOVING_LEFT: pressLeft();
			break;
			case MOVING_RIGHT: pressRight();
			break;
			default: System.out.println("Invalid direction request");
			break;
			}
		} else {
			// Buffer full, direction not added
		}
	}
	
	private void pressUp(){
		int nextDir = finalDirection();
		if(nextDir != Snake.MOVING_DOWN && nextDir != Snake.MOVING_UP){
			dirBuffer.add(Snake.MOVING_UP);
		}
	}
	private void pressDown(){
		int nextDir = finalDirection();
		if(nextDir != Snake.MOVING_UP && nextDir != Snake.MOVING_DOWN){
			dirBuffer.add(Snake.MOVING_DOWN);
		}
	}
	private void pressLeft(){
		int nextDir = finalDirection();
		if(nextDir != Snake.MOVING_RIGHT && nextDir != Snake.MOVING_LEFT){
			dirBuffer.add(Snake.MOVING_LEFT);
		}
	}
	private void pressRight(){
		int nextDir = finalDirection();
		if(nextDir != Snake.MOVING_LEFT && nextDir != Snake.MOVING_RIGHT){
			dirBuffer.add(Snake.MOVING_RIGHT);
		}
	}
	
	public int getX(){
		return positions[0][0]; //return posX for Snake Head
	}
	
	public int getY(){
		return positions[0][1]; //return posY for Snake Head
	}
	
	public void setX(int newX) {
		positions[0][0] = newX;
	}
	
	public void setY(int newY) {
		positions[0][1] = newY;
	}
	
	private int finalDirection() {
		if(dirBuffer.isEmpty()) {
			return direction;
		}
		return dirBuffer.peekLast();
	}
	
	public int[][] getPositions(){
		return positions;
	}
	
	public int getSize(){
		return size;
	}
	
	public ProtocolServer getProtocol(){
		return protocol;
	}
	
	public void send(){
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream() ;
			ObjectOutputStream out = new ObjectOutputStream(bos);
			out.writeObject(this);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
