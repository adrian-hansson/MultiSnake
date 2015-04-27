package ServerSide;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import ClientSide.Snake;

public class Player extends Thread{
	//holds info about sockets, etc.
	//this thread runs on the serverside.. and keeps track of which commands to pass along to the client-side snake class
	
	private Snake snake;
	private Socket socket;
	private Server server;
	private String name;
	OutputStream out;
	BufferedReader bf;
	
	public Player(Server serer, Socket socket){
		this.server = server;
		this.socket = socket;
		
		try{
			name = socket.getInetAddress().toString(); //IP address becomes name
			out = socket.getOutputStream();
			server.addPlayer(this); //adds the created player to the Level's list of players
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void sendSnakes(ArrayList<Snake> snakes){
		//out.send(snakes); not working code.. but a similar method should be used
	}
	
	public Snake getSnake(){
		return snake;
	}
	
	public Socket getSocket(){
		return socket;
	}
	
	public void run(){
		String str;
		try{
			while((str = bf.readLine()) != null){
				
			}
		} catch(IOException e){
			e.printStackTrace();
		}
	}

}
