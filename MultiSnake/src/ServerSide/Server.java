package ServerSide;

import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.Shape;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Random;

import ClientSide.View;

public class Server extends Thread{
	
	ArrayList<Snake> snakes;
	int appleX, appleY;
	int mapSize = 500; //must be divisible by 10
	
	public Server(){
		snakes = new ArrayList<Snake>();
		newApple();
	}
	
	public void run(){
		try{
			while(true){
				updateAndSendSnakes();
				try {
					Thread.sleep(100);
				} catch(InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("No clients");
	}
	
	//not sure if this method should be 'here', though it should be somewhere..
	public void updateAndSendSnakes(){
		
		ArrayList<Integer> listToSend = new ArrayList<Integer>();
		for(Snake s : snakes){
			s.move();
			int[][] positions = s.getPositions();
			for(int i = 0; i < s.getSize(); ++i){
				listToSend.add(positions[i][0]);
				listToSend.add(positions[i][1]);
				if(i == 0){
					listToSend.add(0);
				}
				else{
					listToSend.add(1);
				}
			}
		}
		
		// Check if any snake hits apple
		isAtApple();
		
		//Add apple position
		listToSend.add(appleX);
		listToSend.add(appleY);
		listToSend.add(2);
		
		int[] positionsToSend = new int[listToSend.size()];
		for(int i = 0; i<positionsToSend.length; ++i){
			positionsToSend[i] = listToSend.get(i);
		}
		for(Snake s : snakes){
			s.getProtocol().serverWrite(positionsToSend);
		}
	}
	
	public void removeSnake(Snake s) {
		snakes.remove(s);
	}
	
	public void isAtApple(){
		for(Snake s : snakes){
			if(s.getX() == appleX && s.getY() == appleY){
				eatApple(s);
				break;
			}
		}
	}
	
	public void eatApple(Snake snake){
		System.out.println("YUMMY!");
		snake.addTail(5);
		newApple();
	}
	
	public void newApple(){ //review.. not sure if shape position is counted from upper-left or lower-left corner
		Random rand = new Random();
		appleX = 10*rand.nextInt((mapSize/10)-1);
		appleY = 10*rand.nextInt((mapSize/10)-1); //this restricts the largest possible nbr to be 10px from the map-edge.. (since apple is 10px wide)
	}
	
	public void addSnake(Socket socket, int startX, int startY, int startLength) {
		snakes.add(new Snake(this, socket, startX, startY, startLength));
	}
}