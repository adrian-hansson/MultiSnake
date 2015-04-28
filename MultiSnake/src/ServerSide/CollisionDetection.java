package ServerSide;

import java.util.ArrayList;

import ClientSide.Level;
import ClientSide.Utilities;

public class CollisionDetection extends Thread{
	
	Level level;
	
	public CollisionDetection(Level level){
		this.level = level;
	}
	
	public void run(){
		while(true){
		ArrayList<Snake> snakes = level.getSnakes();
		//Check for collision:
		for(int i = 0; i < snakes.size(); i++){ //start snake
			for(int j = 0; j < snakes.size(); j++){ //compare to this snake
				for(int k = 0; k < snakes.get(i).getSize(); k++){ //segment of start snake
					for(int z = 0; z < snakes.get(j).getSize(); z++){ //segment of compare snake
						if(i == j && k == z){
							//do nothing. Cannot collide with its own head
							
						}else{
							if(snakes.get(i).getPositions()[k][0] == snakes.get(j).getPositions()[z][0] && snakes.get(i).getPositions()[k][1] == snakes.get(j).getPositions()[z][1]){
								//stuff
//								System.out.println("COLLISION!!!");
//								System.out.println("Original: ["+i+"][0] = " + snakes.get(i).getPositions()[k][0]);
//								System.out.println("Original: ["+i+"][1] = " + snakes.get(i).getPositions()[k][1]);
//								System.out.println("Compare: ["+j+"][0] = " + snakes.get(j).getPositions()[z][0]);
//								System.out.println("Compare: ["+j+"][1] = " + snakes.get(j).getPositions()[z][1]);
								Utilities.playSound("/resources/coin10.wav");
							}
							else if(snakes.get(i).getPositions()[k][0] < 0
									|| snakes.get(i).getPositions()[k][1] < 0
									|| snakes.get(i).getPositions()[k][0]  > 500
									|| snakes.get(i).getPositions()[k][1]  > 500){
								//System.out.println("Outside map");
							}
						}
					}
				}
			}
		}
		try {
			Thread.sleep(150);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}
	}
}
