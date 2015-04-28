package Transport;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import ServerSide.Snake;
import ClientSide.Client;

public class ProtocolClient extends Thread{
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private int convertToInt(byte[] b, int offset) {
		int a = (b[0 + offset] << 24) & 0xFF000000;
		System.out.println("a: " + a);
		int k = (b[0 + offset] << 16) & 0x00FF0000;
		System.out.println("k: " + k);
		int c = (b[0 + offset] << 8) & 0x0000FF00;
		System.out.println("c: " + c);
		int d = b[0 + offset] & 0x000000FF;
		System.out.println("d: " + d);
		int res = a | k | c | d;
		//res = (res << 8) + b[1 + offset];
		//res = (res << 8) + b[2 + offset];
		//res = (res << 8) + b[3 + offset];
		
		System.out.println( b[offset +0] + " " + b[offset +1] + " " + b[offset + 2] + " " + b[offset + 3] + " Converted to int: " + res);
		return res;
	}
	
	private void convertToByteArray(int b, byte[] res, int offset) {
		System.out.println("Writing someting");
		res[0 + offset] = (byte)(b >>> 24);
		res[1 + offset] = (byte)(b >>> 16);
		res[2 + offset] = (byte)(b >>> 8);
		res[3 + offset] = (byte) b;
		//System.out.println(res[0 + offset] + " " +  res[1 + offset]+ " " +  res[2 + offset]+ " " +  res[3 + offset] + " converted from " + b);
	}

	public void clientRead() {

		try {
			byte[] temp = new byte[4];
			is.read(temp);

			int messageSize = convertToInt(temp, 0);
			System.out.println(messageSize);
			byte[] message = new byte[messageSize];
			is.read(message);
			
			for (int i = 0; i < (messageSize-1)/4; i++) {
				System.out.println(i);
				int command = (int) message[0];
				switch (command) {
				case UPDATELEVEL:
					int x = convertToInt(message, 4*i +1);
					int y = convertToInt(message,4*++i +1);
					int imageIndex = convertToInt(message,4*++i +1);
					client.getLevel().update(x, y, imageIndex);
					System.out.println("X: "+x+" Y: "+y);//TEST
					break;
				case UPDATEDIRECTION:
					System.out.println("error, client doesnt get direction updates");
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

	public void clientWrite(int direction) {
		byte[] message = new byte[2];
		message[0] = (byte) UPDATEDIRECTION;
		message[1] = (byte) direction;
		try {
			os.write(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
