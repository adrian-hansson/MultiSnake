//package ServerSide;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.UnsupportedEncodingException;
//import java.net.Socket;
//
//import ClientSide.Level;
//
//public class PlayerInputReader extends Thread{
//
//	private Socket socket;
//	private BufferedReader bf;
//	private Player player;
//	
//	public PlayerInputReader(Socket socket, Player player){
//		this.player = player;
//		this.socket = socket;
//	}
//	
//	public void run(){
//		//looks for input from players
//		try {
//			bf = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		//The code below needs to change. It reads incoming strings.
//		//This may.. or may not... be good. Probably not :P
//		String str;
//		try {
//			while((str = bf.readLine() ) != null){
//				//System.out.println(bf.readLine());
//				//String str;
//				if(socket.isClosed()){
//					break;
//				}
//				if((Integer.parseInt(str)) == 0){
//					//move up
//					player.getSnake().pressUp();
//				}
//				else if((Integer.parseInt(str)) == 1){
//					//move down
//					player.getSnake().pressDown();
//				}
//				else if((Integer.parseInt(str)) == 2){
//					//move left
//					player.getSnake().pressLeft();
//				}
//				else if((Integer.parseInt(str)) == 3){
//					//move right
//					player.getSnake().pressRight();
//				}
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//}
