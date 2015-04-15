package ServerSide;

import ClientSide.Level;

public class PlayerInputReader extends Thread{

	private Level level;
	
	public PlayerInputReader(Level level){
		this.level = level;
	}
	
	public void run(){
		//looks for input from players
	}
}
