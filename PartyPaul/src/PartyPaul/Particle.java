package PartyPaul;

import java.awt.Color;
import java.awt.Font;

public class Particle {
	public String text = "+10";
	public Color color = Color.GREEN;
	public Color back = Color.GRAY;
	public int yOff = 0;
	public int a = 255;
	
	public Particle(String s, Color c){
		text = s;
		color = c;
	}
	
	public Particle(){
	}
	
	public void tick(){
		yOff++;
	}
		
	public void render(){
		int off = 2;

		color = new Color(color.getRed(), color.getGreen(), color.getBlue(), a);
		back = new Color(back.getRed(), back.getGreen(), back.getBlue(), a);
		
		a -= 10;
		
		if(a < 0){
			a = 0;
			
			PartyPaul.toDie.add(this);
			return;
		}
		
		PartyPaul.canvas.setFont(new Font("Arial", 1, 24));
		PartyPaul.canvas.setColor(back);
		PartyPaul.canvas.drawString(text, PartyPaul.player.x + off/2 + 2, PartyPaul.player.y - 6 - yOff + off/2);
		PartyPaul.canvas.setColor(color);
		PartyPaul.canvas.drawString(text, PartyPaul.player.x - off/2 + 2, PartyPaul.player.y - 6 - yOff - off/2);
	}
}
