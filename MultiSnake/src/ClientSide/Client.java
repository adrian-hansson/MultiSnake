package ClientSide;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
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
	InetAddress address;
	int port;
	ProtocolClient protocol;
	
	public Client(Level level, InetAddress address, int port){
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
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void sendToServer(int i){
		protocol.clientWrite(i);
	}
	
	public void run(){
		while(true){
			protocol.clientRead();
		}
	
	}
	
	public Level getLevel(){
		return level;
	}	
}
