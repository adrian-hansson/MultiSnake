package ServerSide;

import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.Shape;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Iterator;

import ClientSide.View;

public class Server extends Thread{
	
	private static final int GREEN = 0;
	private static final int YELLOW = 1;
	private static final int BLUE = 2;
	private static final int PURPLE = 3;
	private static final int APPLE = 0, SNAKEHEADDEAD = 1, SNAKEBODYDEAD = 2, SNAKEGREENBODY = 3, SNAKEGREENHEAD = 4, SNAKEGREENBODYGHOST = 5, SNAKEGREENHEADGHOST = 6, SNAKEBLUEBODY = 7, SNAKEBLUEHEAD = 8, SNAKEBLUEBODYGHOST = 9, SNAKEBLUEHEADGHOST = 10, SNAKEYELLOWBODY = 11, SNAKEYELLOWHEAD = 12, SNAKEYELLOWBODYGHOST = 13, SNAKEYELLOWHEADGHOST = 14, SNAKEPURPLEBODY = 15, SNAKEPURPLEHEAD = 16, SNAKEPURPLEBODYGHOST = 17, SNAKEPURPLEHEADGHOST = 18;
	
	ArrayList<Snake> snakes;
	LinkedList<Integer> colors;
	int appleX, appleY;
	int mapSize = 500; //must be divisible by 10
	
	public Server(){
		snakes = new ArrayList<Snake>();
		colors = new LinkedList<Integer>();
		colors.add(GREEN);
		colors.add(YELLOW);
		colors.add(BLUE);
		colors.add(PURPLE);
		newApple();
	}
	
	public void run(){
		try{
			while(true){
				try {
				updateAndSendSnakes();
					Thread.sleep(100);
				} catch(InterruptedException ex) {
					Thread.currentThread().interrupt();
				} catch(ConcurrentModificationException e) {
					System.out.println("No clients");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("No clients");
	}
	
	//not sure if this method should be 'here', though it should be somewhere..
	public void updateAndSendSnakes(){
		
		for(Snake s : snakes){
			if(s.isGhost()) {
				s.move();
				checkBorder(s);
				s.haunt();
			} else if(!s.isDead()) {
				s.move();
				checkBorder(s);
			} else {
				if(s.getDecay() < 20) {
					s.decay();
				} else {
					s.revive();
				}
			}
		}
		
		LinkedList<Integer> listToSend = new LinkedList<Integer>();
		LinkedList<int[]> CollisionCoords = getCollisionCoords();
		
		for(Snake s : snakes){
			if(!s.isGhost() && !s.isDead()) {
				if(countCollisions(CollisionCoords, s.getX(), s.getY()) > 1) {
					s.death();
				}
			}
			if(s.getDecay() < 20) {
				int[][] positions = s.getPositions();
				for(int i = 0; i < s.getSize(); ++i){
					if(i == 0){
						if(s.isDead()) {
							listToSend.addFirst(SNAKEHEADDEAD);
						} else if(s.isGhost()) {
							if(s.getColor() == GREEN){
								listToSend.addFirst(SNAKEGREENHEADGHOST);
							} else if(s.getColor() == YELLOW){
								listToSend.addFirst(SNAKEYELLOWHEADGHOST);
							} else if(s.getColor() == BLUE){
								listToSend.addFirst(SNAKEBLUEHEADGHOST);
							} else if(s.getColor() == PURPLE){
								listToSend.addFirst(SNAKEPURPLEHEADGHOST);
							}
						} else {
							if(s.getColor() == GREEN){
								listToSend.addFirst(SNAKEGREENHEAD);
							} else if(s.getColor() == YELLOW){
								listToSend.addFirst(SNAKEYELLOWHEAD);
							} else if(s.getColor() == BLUE){
								listToSend.addFirst(SNAKEBLUEHEAD);
							} else if(s.getColor() == PURPLE){
								listToSend.addFirst(SNAKEPURPLEHEAD);
							}
						}
					}
					else{
						if(s.isDead()) {
							listToSend.addFirst(SNAKEBODYDEAD);
						} else if(s.isGhost()) {
							if(s.getColor() == GREEN){
								listToSend.addFirst(SNAKEGREENBODYGHOST);
							} else if(s.getColor() == YELLOW){
								listToSend.addFirst(SNAKEYELLOWBODYGHOST);
							} else if(s.getColor() == BLUE){
								listToSend.addFirst(SNAKEBLUEBODYGHOST);
							} else if(s.getColor() == PURPLE){
								listToSend.addFirst(SNAKEPURPLEBODYGHOST);
							}
						} else {
							if(s.getColor() == GREEN){
								listToSend.addFirst(SNAKEGREENBODY);
							} else if(s.getColor() == YELLOW){
								listToSend.addFirst(SNAKEYELLOWBODY);
							} else if(s.getColor() == BLUE){
								listToSend.addFirst(SNAKEBLUEBODY);
							} else if(s.getColor() == PURPLE){
								listToSend.addFirst(SNAKEPURPLEBODY);
							}
						}	
					}
					listToSend.addFirst(positions[i][1]);
					listToSend.addFirst(positions[i][0]);
				}
			}
		}
		
		// Check if any snake hits apple
		isAtApple();
		
		//Add apple position
		listToSend.add(appleX);
		listToSend.add(appleY);
		listToSend.add(APPLE);
		
		int[] positionsToSend = new int[listToSend.size()];
		for(int i = 0; i<positionsToSend.length; ++i){
			positionsToSend[i] = listToSend.get(i);
		}
		for(Snake s : snakes){
			s.getProtocol().serverWrite(positionsToSend);
		}
	}
	
	public void removeSnake(Snake s) {
		colors.add(s.getColor());
		snakes.remove(s);
	}
	
	public void isAtApple(){
		for(Snake s : snakes){
			if(s.getX() == appleX && s.getY() == appleY && !s.isGhost()){
				eatApple(s);
				break;
			}
		}
	}
	
	public void eatApple(Snake snake){
		snake.addTail(1);
		newApple();
	}
	
	public void newApple(){ //review.. not sure if shape position is counted from upper-left or lower-left corner
		LinkedList<int[]> Coords = getCollisionCoords();
		do {
			Random rand = new Random();
			appleX = 10*rand.nextInt((mapSize/10)-1);
			appleY = 10*rand.nextInt((mapSize/10)-1); //this restricts the largest possible nbr to be 10px from the map-edge.. (since apple is 10px wide)
		} while(checkCollision(Coords, appleX, appleY));
	}
	
	public void addSnake(Socket socket, int startLength) {
		snakes.add(new Snake(this, socket, mapSize, startLength, colors.poll()));
	}
	
	
	private LinkedList<int[]> getCollisionCoords() {
		LinkedList<int[]> Coords = new LinkedList<int[]>();
		for(Snake s : snakes) {
			if(!s.isGhost()) {
				int[][] positions = s.getPositions();
				int size = s.getSize();
				for(int i = 0; i < size; i++) {
					int[] pos = new int[2];
					pos[0] = positions[i][0];
					pos[1] = positions[i][1];
					Coords.offerFirst(pos);
				}
			}
		}
		return Coords;
	}
	
	private boolean checkCollision(LinkedList<int[]> CollCoords, int posX, int posY) {
		Iterator<int[]> iter = CollCoords.iterator();
		int[] coords = new int[2];
		while(iter.hasNext()) {
			coords = iter.next();
			if(coords[0] == posX && coords[1] == posY) {
				return true;
			}
		}
		return false;	
	}
	
	private int countCollisions(LinkedList<int[]> CollCoords, int posX, int posY) {
		int Collisions = 0;
		Iterator<int[]> iter = CollCoords.iterator();
		int[] coords = new int[2];
		while(iter.hasNext()) {
			coords = iter.next();
			if(coords[0] == posX && coords[1] == posY) {
				Collisions++;
			}
		}
		return Collisions;	
	}
	
	private void checkBorder(Snake s) {
		if(s.getX() < 0) {
			s.setX(500);
		} else if(s.getX() > 500) {
			s.setX(0);
		}
		
		if(s.getY() < 0) {
			s.setY(500);
		} else if(s.getY() > 500) {
			s.setY(0);
		}
	}
	
	public boolean isFull() {
		return colors.isEmpty();
	}
}