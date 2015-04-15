package ServerSide;

import java.util.ArrayList;

public class Server {
	
	ArrayList<Player> players;
	
	public Server(){
		players = new ArrayList<Player>();
	}
	
	public void addPlayer(Player player){
		players.add(player);
	}

}
