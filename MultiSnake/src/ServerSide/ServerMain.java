package ServerSide;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
			if(!server.isFull()) {
				OutputStream os = null;
				server.addSnake(socket, 5);
				try {
					os = socket.getOutputStream();
					String message = "Connected to server";
					byte[] msg = message.getBytes();
					os.write(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("Client connected!");
			} else {
				OutputStream os = null;
				try {
					os = socket.getOutputStream();
					String message = "Server is full";
					byte[] msg = message.getBytes();
					os.write(msg);
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("Client tried to connect but server is full");
			}
		}

	}
}
