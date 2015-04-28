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

public class Server {
	
	ArrayList<Snake> snakes;
	ArrayList<Player> players;
	int appleX, appleY;
	int mapSize = 500; //must be divisible by 10
	ServerSocket serverSocket;
	Socket server;
	
	public Server(int portNbr){
		try{
			serverSocket = new ServerSocket(portNbr);
			players = new ArrayList<Player>();
			snakes = new ArrayList<Snake>();
			//server = serverSocket.accept();
			
		}catch(IOException e){
			e.printStackTrace();
		}
		//test
	}
	
	public void loop(){
		try{
			while(true){
				updateAndSendSnakes();
				try {
					Thread.sleep(300);
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
		//int[] positionsToSend;
		for(Snake s : snakes){
			int[][] positions = s.getPositions();
			for(int i = 0; i < s.getSize(); ++i){
				listToSend.add(positions[i][0]);
				listToSend.add(positions[i][1]);
				//positionsToSend[i] = positions[i][0]; //x
				//positionsToSend[++i] = positions[i][1]; //y
				if(i == 0){
					listToSend.add(0);
					//positionsToSend[++i] = 0;//index head
				}
				else{
					listToSend.add(1);
				}
				//positionsToSend[++i] = 1; //index default
			}
		}
		int[] positionsToSend = new int[listToSend.size()];
		System.out.println(listToSend);
		for(int i = 0; i<positionsToSend.length; ++i){
			positionsToSend[i] = listToSend.get(i);
		}
		//listToSend.toArray(positionsToSend);
		for(Snake s : snakes){
			s.getProtocol().serverWrite(positionsToSend);
			s.move();
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

	public void testConnection(){
		try {
			while((server = serverSocket.accept()) == null){
				System.out.println("Waiting for connection");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		snakes.add(new Snake(server, 100, 100, 1));
		System.out.println("Connected!");
	}
	
	public static void main(String[] args) {
		Server server = new Server(30000);
		server.testConnection();
		server.loop();
	}
}