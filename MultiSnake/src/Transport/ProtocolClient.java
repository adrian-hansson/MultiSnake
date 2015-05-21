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
	private DataOutputStream dos;
	private DataInputStream dis;

	public ProtocolClient(Socket socket, Client client){
		this.socket = socket;
		this.client = client;
		try {
			is = socket.getInputStream();
			os = socket.getOutputStream();
			dos = new DataOutputStream(os);
			dis = new DataInputStream(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Receives many ints from the server
	//First int = messageSize (tells us how many ints to read before a new message starts), the rest of the ints are multiple iterations of posX, posY, graphicsIndex for ALL snake segments
	//This is the data used by the clients to draw the graphics
	public void clientRead() {
		try {
			int tempSize = dis.readInt();
			int[] ints = new int[tempSize];
			for(int i = 0; i < tempSize; i++){
				ints[i] = dis.readInt();
			}	
			for (int i = 0; i < ints.length-2; i++) {
				int x = ints[i];
				int y = ints[i+1];
				int imageIndex = ints[i+2];
				client.getLevel().update(x,  y,  imageIndex);
				i = i+2;
			}
			client.getLevel().repaint();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NegativeArraySizeException ne) {
			System.out.println("Somehow messagSize variable is negative... NegativeArraySizeException caught");
		}

	}

	//Sends two ints to the server.
	//First int = command type (only UPDATEDIRECTION used presently), Second int = direction (represented by ints 0 to 3)
	public void clientWrite(int direction) {
		try {
			dos.writeInt(UPDATEDIRECTION);
			dos.writeInt(direction);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//-------------------------------------------------------
	//  DISCONTINUED METHODS BELOW. THEY ARE NO LONGER USED
	//-------------------------------------------------------
	
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
	
}