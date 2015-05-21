package Transport;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import ServerSide.Snake;
import ClientSide.Client;

public class ProtocolClient {
	private static final int UPDATELEVEL = 0;
	private static final int UPDATEDIRECTION = 1;
	private static final int SERVERFULL = 2;

	private Socket socket;
	private InputStream is;
	private OutputStream os;
	private Client client;
	private Snake snake;
	
	DataOutputStream dos;
	DataInputStream dis;

	public ProtocolClient(Socket socket, Client client){
		this.socket = socket;
		this.client = client;
		try {
			is = socket.getInputStream();
			os = socket.getOutputStream();
			
			//TEST
			dos = new DataOutputStream(os);
			dis = new DataInputStream(is);
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
			int tempSize = dis.readInt();
			int[] ints = new int[tempSize];
			
			//System.out.println("Received Size: "+tempSize);
			for(int i = 0; i < tempSize; i++){
				ints[i] = dis.readInt();
			}
			
			for (int i = 0; i < ints.length-2; i++) {
				//int command = ints[i];
				int x = ints[i];
				int y = ints[i+1];
				int imageIndex = ints[i+2];
				client.getLevel().update(x,  y,  imageIndex);
				i = i+2;
				
//				switch (command) {
//				case UPDATELEVEL:
//					int x = ints[i+1];
//					int y = ints[i+2];
//					int imageIndex = ints[i+3];
//					client.getLevel().update(x,  y,  imageIndex);
//					i = i+3;
//					break;
//				case UPDATEDIRECTION:
//					System.out.println("error, client doesnt get direction updates");
//					break;
//				case SERVERFULL:
//					System.out.println("Could not connect, server is full");
//					break;
//				default:
//					System.out.println("error");
//					break;
//				}
			}
			
			
//			byte[] temp = new byte[4];
//			is.read(temp);
//
//			
//			
//			
//			
//			
//			int messageSize = convertToInt(temp, 0);
//			System.out.println("MessageSize: "+messageSize); //temp edit
//			byte[] message = new byte[messageSize];
//			is.read(message);
//			
//			for (int i = 0; i < (messageSize-1)/4; i++) {
//				int command = (int) message[0];
//				switch (command) {
//				case UPDATELEVEL:
//					int x = convertToInt(message, 4*i +1);
//					int y = convertToInt(message,4*++i +1);
//					int imageIndex = convertToInt(message,4*++i +1);
//					client.getLevel().update(x, y, imageIndex);
//					break;
//				case UPDATEDIRECTION:
//					System.out.println("error, client doesnt get direction updates");
//					break;
//				case SERVERFULL:
//					System.out.println("Could not connect, server is full");
//					break;
//				default:
//					//System.out.println("error");
//					break;
//				}
//			}
			client.getLevel().repaint();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NegativeArraySizeException ne) {
			System.out.println("Somehow messagSize variable is negative... NegativeArraySizeException caught");
		}

	}

	public void clientWrite(int direction) {
		
//		byte[] message = new byte[2];
//		message[0] = (byte) UPDATEDIRECTION;
//		message[1] = (byte) direction;
		try {
			dos.writeInt(UPDATEDIRECTION);
			dos.writeInt(direction);
//			os.write(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}