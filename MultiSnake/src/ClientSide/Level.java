package ClientSide;

import java.util.ArrayList;

import ServerSide.Snake;

//This will probably EXTEND a Swing canvas or something. Will have draw() methods, etc
//Might or might not hold the info about sockets,etc...
//..OR rename to Server and move 

public class Level {

	private ArrayList<Snake> snakes; //list of snakes (receives updates from Server);
	//private ArrayList<Player> players; //might use this instead of "snakes".. not use at local level.. or?
	
	
	public Level(){
		
	}
	
	public void updateSnakes(){
		for(int i = 0; i < snakes.size(); i++){
			snakes.get(i); //do something here..
		}
	}
	
}
