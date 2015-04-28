package Transport;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import ServerSide.Server;
import ServerSide.Snake;
import ClientSide.Client;

public class ProtocolServer extends Thread{
	private static final int UPDATELEVEL = 0;
	private static final int UPDATEDIRECTION = 1;

	private Socket socket;
	private InputStream is;
	private OutputStream os;
	private Snake snake;
	//private Server server;

	public ProtocolServer(Socket socket, Snake snake){
		//this.server = server;
		this.socket = socket;
		this.snake = snake;
		try {
			is = socket.getInputStream();
			os = socket.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private int convertToInt(byte[] b, int offset) {
		int res = b[0 + offset];
		res = res << 8 + b[1 + offset];
		res = res << 8 + b[2 + offset];
		res = res << 8 + b[3 + offset];
		System.out.println( b[0] + " " + b[1] + " " + b[2] + " " + b[3] + "Converted int: " + res);
		return res;
	}
	
	private void convertToByteArray(int b, byte[] res, int offset) {
		res[0 + offset] = (byte)(b >>> 24);
		res[1 + offset] = (byte)(b >>> 16);
		res[2 + offset] = (byte)(b >>> 8);
		res[3 + offset] = (byte) b;
		System.out.println( res[offset +0] + " " + res[offset +1] + " " + res[offset +2] + " " + res[offset +3] + "Converted from: " + b);
	}

	public void serverRead() {

		try {
			
			byte[] message = new byte[2];
			is.read(message);
			
			for (int i = 0; i < 2; i++) {
				int command = (int) message[0];
				switch (command) {
				case UPDATELEVEL:
					System.out.println("error, server doesnt get update levels");
					
					break;
				case UPDATEDIRECTION:
					int dir = (int)message[1];
					System.out.println("changing dir: " + dir);
					snake.setDirection(dir);
					break;

				default:
					System.out.println("error");
					break;

				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void serverWrite(int[] positions) {
		System.out.println("Want to send: " + positions[0] + " " + positions[1]);
		byte[] message = new byte[4*positions.length + 5];
		convertToByteArray(4*positions.length + 1, message, 0);
		message[4] = (byte) UPDATELEVEL;
		for(int i = 0; i< positions.length; ++i){
			convertToByteArray(positions[i], message, 4*i +5);
		}
		try {
			os.write(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
