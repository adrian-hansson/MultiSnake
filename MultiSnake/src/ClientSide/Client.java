package ClientSide;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import ServerSide.Snake;
import Transport.ProtocolClient;

public class Client extends Thread{

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
	ProtocolClient protocol;
	
	public Client(Level level, String address, int port){
		this.level = level;
		this.address = address;
		this.port = port;
		initiate();
		this.start();
	}
	
	private void initiate(){
		try{
			socket = new Socket(address, port);
			protocol = new ProtocolClient(socket, this);
			out = socket.getOutputStream();
		}catch(IOException e){
			e.printStackTrace();
		}
		//protocol.start();
	}
	
	public void sendToServer(int i){
		protocol.clientWrite(i);
		//stuff from client to server
	}
	
	public void run(){
		while(true){
			protocol.clientRead();
		}
		//gameLoop(); //not used? is on server-side instead?
	}
	
	public Level getLevel(){
		return level;
	}
	
//	public void connectToServer(){
//		try {
//			socket = new Socket("localhost", 30000);
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
//	public void gameLoop(){//Not used?? is on server-side instead?
//		//Maybe some of the game-loop stuff should happen on serverside..
//		while(true){
//			ArrayList<Snake> snakes = level.getSnakes();
//			for(int i = 0; i < snakes.size(); i++){
//				snakes.get(i).move();
//			}
//			//IF CLIENT RECEIVES SOMETHING:
//			level.update(snakes);
//			try {
//				Thread.sleep(150);
//			} catch(InterruptedException ex) {
//				Thread.currentThread().interrupt();
//			}
//		}
//	}
	
}
