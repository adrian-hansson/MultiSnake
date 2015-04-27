package ClientSide;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Client{

	//listens to input from the server.. and gives it to the Level.. which will repaint.
	//also listens to the GUI and sends commands from that to the server
	//holds Socket data, etc
	
	//----INFO PROTOCOL SUGGESTION----
	//
	//  Arrays are serializable. Send all positions[][] arrays in the right order. (same order as Player-list)
	//
	
	Level level;
	Snake snake;
	
	Socket socket;
	OutputStream out;
	String address;
	int port;
	
	public Client(Level level, String address, int port){
		snake = level.getPlayer();
		this.level = level;
		this.address = address;
		this.port = port;
		initiate();
	}
	
	private void initiate(){
		try{
			socket = new Socket(address, port);
			out = socket.getOutputStream();
		}catch(IOException e){
			e.printStackTrace();
		}
		(new ClientListener(socket, this)).start();
	}
	
	public void receiveUpdateFromServer(ArrayList<Snake> snakes){
		level.update(snakes);
		//this is where the client gets the stuff from the server.
	}
	
	public void sendToServer(int i){
		try {
			out.write(i);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//stuff from client to server
	}
	
	public void run(){
		gameLoop(); //not used? is on server-side instead?
	}
	
	public void gameLoop(){//Not used?? is on server-side instead?
		//Maybe some of the game-loop stuff should happen on serverside..
		while(true){
			ArrayList<Snake> snakes = level.getSnakes();
			for(int i = 0; i < snakes.size(); i++){
				snakes.get(i).move();
			}
			//IF CLIENT RECEIVES SOMETHING:
			level.update(snakes);
			try {
				Thread.sleep(150);
			} catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}
	}
	
}
