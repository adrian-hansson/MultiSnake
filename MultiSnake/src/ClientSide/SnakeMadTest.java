package ClientSide;

import java.util.Random;

public class SnakeMadTest extends Snake{
	
	
	//TEST CLASS
	
	public SnakeMadTest(Level level) {
		super(level);
		setSize(1);
	}
	
	public void move(){
		Random rand = new Random();
		int newDir = rand.nextInt(4);
		while( (newDir==0 && getDirection()==1) || (newDir==1 && getDirection()==0) || (newDir==2 && getDirection()==3) || (newDir==3 && getDirection()==2)){
			newDir = rand.nextInt(4);
			if(newDir != getDirection()){
				newDir = rand.nextInt(10);
				if(newDir < 9){
					newDir = getDirection();
				}
				else{
					newDir = rand.nextInt(4);
				}
			}
		}
		setDirection(rand.nextInt(4));
		super.move();
	}
	
	public void pressUp(){
	}
	public void pressDown(){
	}
	public void pressLeft(){
	}
	public void pressRight(){
	}

}
