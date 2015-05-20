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
				server.addSnake(socket, 5);
				System.out.println("Connected!");
			} else {
				System.out.println("Client tried to connect but server is full");
				OutputStream os = null;
				try {
					os = socket.getOutputStream();
					byte[] mess = new byte[1];
					mess[0] = 2; 
					os.write(mess);
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
