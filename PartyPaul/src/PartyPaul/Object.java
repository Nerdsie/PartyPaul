package PartyPaul;

import java.awt.Graphics;

public class Object extends Entity{
	
	public Object(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void render(Graphics g){
		g.drawImage(Images.Token, x, y, w, h, null);
	}
}
