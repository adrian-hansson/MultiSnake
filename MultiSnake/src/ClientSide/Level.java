package ClientSide;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

//This will probably EXTEND a Swing panel or something. Will have draw() methods, etc
//Might or might not hold the info about sockets,etc...
//..OR rename to Server and move 

public class Level extends JPanel{

	private ArrayList<Snake> snakes; //list of snakes (receives updates from Server);
	//private ArrayList<Player> players; //Not use at local level.. or?
	private Snake snake; //the player snake
	Client client;
	
	public Level(){
		//snake = new Snake(this);
		snakes = new ArrayList<Snake>(); //Maybe the Level receives
		//this list from the server if we make Snake objects
		//serializable
		
		
		//snakes.add(0, snake);//puts player first in list of snakes
		//snakes.add(new SnakeMadTest(this));
		client = new Client(this, "", 0);
		//client.start();
		//(new CollisionDetection(this)).start();
		//Utilities.music("battleThemeA.wav");
		//startGame();
		//Utilities.playSound("/resources/battleThemeA.wav");
	}
	
	public void update(ArrayList<Snake> snakes){
		this.snakes = snakes;
		this.repaint();
	}
	
//	public void updateSnakes(){
//		for(int i = 0; i < snakes.size(); i++){
//			snakes.get(i); //do something here..
//		}
//	}
//	
	public Snake getPlayer(){
		return snake;
	}
	
	public ArrayList<Snake> getSnakes(){
		return snakes;
	}
	
	public void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);
		draw(g2d);
	}
	
	public void draw(Graphics g){
		//not sure if this method should be here.. :P
		//paintComponents(g);
		for(int i = 0; i < snakes.size(); i++){
			snakes.get(i).draw(g);
		}
	}
	
	public void pressUp(){
		client.sendToServer(0);
	}
	public void pressDown(){
		client.sendToServer(1);
	}
	public void pressLeft(){
		client.sendToServer(2);
	}
	public void pressRight(){
		client.sendToServer(3);
	}
	
}
