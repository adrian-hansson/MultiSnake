package ServerSide;

import java.net.Socket;

import ClientSide.Snake;

public class Player extends Thread{
	//holds info about sockets, etc.
	//this thread runs on the serverside.. and keeps track of which commands to pass along to the client-side snake class
	
	private Snake snake;
	private Socket socket;
	private Server server;
	
	public Player(Server serer, Socket socket){
		this.server = server;
		this.socket = socket;
		
		server.addPlayer(this); //adds the created player to the Level's list of players
	}
	
	public Snake getSnake(){
		return snake;
	}

}
