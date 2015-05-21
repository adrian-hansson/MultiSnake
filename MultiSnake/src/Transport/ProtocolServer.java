package Transport;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
	private DataOutputStream dos;
	private DataInputStream dis;

	public ProtocolServer(Socket socket, Snake snake){
		this.socket = socket;
		this.snake = snake;
		try {
			is = socket.getInputStream();
			os = socket.getOutputStream();
			dos = new DataOutputStream(os);
			dis = new DataInputStream(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Receives 2 ints.
	//First int = command type (only one type used presently), Second int = direction (up, down, left or right.. represented by ints 0 to 3)
	public void serverRead() {
		try {
			int command = dis.readInt();
			int direction = dis.readInt();
			
			switch (command) {
			case UPDATELEVEL:
				System.out.println("Error, cannot update level on server-side.");
				break;
			case UPDATEDIRECTION:
				snake.setDirection(direction);
				break;
			default:
				//System.out.println("error");
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

	//Sends 1 int (size of positions array) + all ints in positions array
	//Positions = array with ALL snake segments on the map + their graphics index
	public void serverWrite(int[] positions) {		
		try {
			dos.writeInt(positions.length);
			for(int i = 0; i< positions.length; ++i){
				dos.writeInt(positions[i]);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
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