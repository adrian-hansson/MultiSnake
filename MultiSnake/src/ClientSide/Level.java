package ClientSide;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

//This will probably EXTEND a Swing panel or something. Will have draw() methods, etc
//Might or might not hold the info about sockets,etc...
//..OR rename to Server and move 

public class Level extends JPanel{

	private ArrayList<Snake> snakes; //list of snakes (receives updates from Server);
	//private ArrayList<Player> players; //Not use at local level.. or?
	private Snake snake; //the player snake
	
	public Level(){
		
	}
	
	public void updateSnakes(){
		for(int i = 0; i < snakes.size(); i++){
			snakes.get(i); //do something here..
		}
	}
	
	public void draw(Graphics g){
		//not sure if this method should be here.. :P
		paintComponents(g);
		for(int i = 0; i < snakes.size(); i++){
			snakes.get(i).draw(g);
		}
	}
	
}
