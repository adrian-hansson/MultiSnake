package Transport;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import ServerSide.Server;
import ServerSide.Snake;
import ClientSide.Client;

public class ProtocolServer {
	private static final int UPDATELEVEL = 0;
	private static final int UPDATEDIRECTION = 1;

	private Socket socket;
	private InputStream is;
	private OutputStream os;
	private Snake snake;

	public ProtocolServer(Socket socket, Snake snake){
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
		int a = (b[0 + offset] << 24) & 0xFF000000;
		int k = (b[1 + offset] << 16) & 0x00FF0000;
		int c = (b[2 + offset] << 8) & 0x0000FF00;
		int d = b[3 + offset] & 0x000000FF;
		int res = a | k | c | d;
		
		return res;
	}
	
	private void convertToByteArray(int b, byte[] res, int offset) {
		res[0 + offset] = (byte)(b >> 24);
		res[1 + offset] = (byte)(b >> 16);
		res[2 + offset] = (byte)(b >> 8);
		res[3 + offset] = (byte) b;
	}

	public void serverRead() {

		try {
			byte[] message = new byte[2];
			is.read(message);

			int command = (int) message[0];
			switch (command) {
			case UPDATELEVEL:
				System.out.println("error, server doesn't get update levels");
				break;
			case UPDATEDIRECTION:
				int dir = (int)message[1];
				snake.setDirection(dir);
				break;
			default:
				System.out.println("error");
				break;
			}

		} catch (IOException e) {
			try {
				socket.close();
			} catch (IOException ioe) {
				System.out.println("Could not close socket");
			}

		}
	}

	public void serverWrite(int[] positions) {
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
