package ClientSide;

import java.util.ArrayList;

public class Client extends Thread{

	//listens to input from the server.. and gives it to the Level.. which will repaint.
	//also listens to the GUI and sends commands from that to the server
	//holds Socket data, etc
	
	Level level;
	Snake snake;
	
	public Client(Level level){
		snake = level.getPlayer();
		this.level = level;
	}
	
	public void run(){
		gameLoop();
	}
	
	public void gameLoop(){
		//Maybe some of the game-loop stuff should happen on serverside..
		while(true){
			ArrayList<Snake> snakes = level.getSnakes();
			for(int i = 0; i < snakes.size(); i++){
				snakes.get(i).move();
			}
			//snake.move();
			try {
				Thread.sleep(150);
			} catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}
	}
	
}
