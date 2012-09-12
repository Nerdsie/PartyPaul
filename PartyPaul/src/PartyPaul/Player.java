package PartyPaul;

import java.awt.Graphics2D;

public class Player extends Entity{
	int speed = 4;
	
	public void render(Graphics2D g){
		g.drawImage(Images.Player, x, y, w, h, null);
	}
	
	public void tick(){
		
	}
	
	public void reset(){
		x = 20; y = 410;
		PartyPaul.xD = 1;
		PartyPaul.yD = 0;
	}
}
