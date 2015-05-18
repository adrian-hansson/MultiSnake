package Transport;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import ServerSide.Snake;
import ClientSide.Client;

public class ProtocolClient {
	private static final int UPDATELEVEL = 0;
	private static final int UPDATEDIRECTION = 1;

	private Socket socket;
	private InputStream is;
	private OutputStream os;
	private Client client;
	private Snake snake;

	public ProtocolClient(Socket socket, Client client){
		this.socket = socket;
		this.client = client;
		try {
			is = socket.getInputStream();
			os = socket.getOutputStream();
		} catch (IOException e) {
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
		//System.out.println(res[0 + offset] + " " +  res[1 + offset]+ " " +  res[2 + offset]+ " " +  res[3 + offset] + " converted from " + b);
	}

	public void clientRead() {

		try {
			byte[] temp = new byte[4];
			is.read(temp);

			int messageSize = convertToInt(temp, 0);
			byte[] message = new byte[messageSize];
			is.read(message);
			
			for (int i = 0; i < (messageSize-1)/4; i++) {
				int command = (int) message[0];
				switch (command) {
				case UPDATELEVEL:
					int x = convertToInt(message, 4*i +1);
					int y = convertToInt(message,4*++i +1);
					int imageIndex = convertToInt(message,4*++i +1);
					client.getLevel().update(x, y, imageIndex);
					break;
				case UPDATEDIRECTION:
					System.out.println("error, client doesnt get direction updates");
					break;
				default:
					System.out.println("error");
					break;
				}
			}
			client.getLevel().repaint();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void clientWrite(int direction) {
		byte[] message = new byte[2];
		message[0] = (byte) UPDATEDIRECTION;
		message[1] = (byte) direction;
		try {
			os.write(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
