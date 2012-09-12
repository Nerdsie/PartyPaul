package PartyPaul;

import java.awt.Graphics;

public class Enemy extends Entity{
	public double speed = 1;
	PartyPaul game;
	
	public Enemy(PartyPaul g){
		game = g;
		x = game.width - 50;
		y = game.height - 50;
	}
	
	public void render(Graphics g){
		g.drawImage(Images.Enemy, x, y, w, h, null);
	}
	
	public void tick(){
		if (Math.abs(x - PartyPaul.player.x) < speed){
			if(x - PartyPaul.player.x < 0){
				x += x - PartyPaul.player.x;
			}else{
				x -= x - PartyPaul.player.x;
			}
		}else{
			if(x - PartyPaul.player.x < 0){
				x += speed;
			}else{
				x -= speed;
			}
		}

		if (Math.abs(y - PartyPaul.player.y) < speed){
			if(y - PartyPaul.player.y < 0){
				y += y - PartyPaul.player.y;
			}else{
				y -= y - PartyPaul.player.y;
			}
		}else{
			if(y - PartyPaul.player.y < 0){
				y += speed;
			}else{
				y -= speed;
			}
		}
	}
	
	public void reset(){
		x = game.width - 70;
		y = 20;
	}
}
