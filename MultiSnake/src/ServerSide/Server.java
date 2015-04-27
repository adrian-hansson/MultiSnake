package ServerSide;

import java.awt.Rectangle;
import java.awt.Shape;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

import ClientSide.Snake;

public class Server {
	
	ArrayList<Player> players;
	int appleX, appleY;
	int mapSize = 500; //must be divisible by 10
	ServerSocket serverSocket;
	
	public Server(int portNbr){
		try{
			serverSocket = new ServerSocket(portNbr);
			players = new ArrayList<Player>();
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	//not sure if this method should be 'here', though it should be somewhere..
	public void updateAndSendSnakes(){
		ArrayList<Snake> snakesToSend = new ArrayList<Snake>();
		for(Player p : players){
			snakesToSend.add(p.getSnake());
		}
		
		for(Player p : players){
			p.sendSnakes(snakesToSend);
		}
	}
	
	public void newPlayer(Socket socket){
		Thread player = new Player(this, socket);
	}
	
	//Adds new player. Called from within each Player-thread constructor
	public synchronized void addPlayer(Player player){
		players.add(player);
	}
	
	public void isAtApple(){
		Snake temp;
		for(int i = 0; i < players.size(); i++){
			temp = players.get(i).getSnake();
			Rectangle shape1 = new Rectangle(temp.getX(), temp.getY());
			Rectangle shape2 = new Rectangle(appleX, appleY);
			if(shape1.intersects(shape2)){
				eatApple(players.get(i).getSnake());
				break;
			}
		}
	}
	
	public void eatApple(Snake snake){
		//This snake's head intersects with the APPLE
		//Code for passing along this info to other players and the one who ATE the apple
	}
	
	public void newApple(){ //review.. not sure if shape position is counted from upper-left or lower-left corner
		Random rand = new Random();
		appleX = rand.nextInt(mapSize-9);
		appleY = rand.nextInt(mapSize-9); //this restricts the largest possible nbr to be 10px from the map-edge.. (since apple is 10px wide)
	}

}