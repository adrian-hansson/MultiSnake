package ServerSide;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
	
	

	public static void main(String[] args) {
		Server server = new Server();
		server.start();
		ServerSocket serverSocket = null;
		Socket socket = null;
		try {
			serverSocket = new ServerSocket(30000);
		} catch (IOException e) {
			System.out.println("Could not open serversocket");
		}
		while(true) {
			try {
				socket = serverSocket.accept();
			} catch(IOException e) {
				System.out.println("Could not open socket");
			}
			server.addSnake(socket, 100, 100, 10);
			System.out.println("Connected!");
		}

	}

}
