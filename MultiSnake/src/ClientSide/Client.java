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

	//listens to input from the server.. and gives it to the Level.. which will draw the graphics.
	//also listens to the GUI and sends info about KeyPressed-events to the server
	//holds Socket data, etc
	
	Level level;
	Snake snake;
	
	Socket socket;
	int port;
	ProtocolClient protocol;
	
	public Client(Level level, Socket socket, int port){
		this.level = level;
		this.socket = socket;
		this.port = port;
		initiate();
		this.start();
	}
	
	private void initiate(){
		protocol = new ProtocolClient(socket, this);
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
