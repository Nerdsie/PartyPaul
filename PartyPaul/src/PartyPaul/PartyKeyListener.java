package PartyPaul;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PartyKeyListener implements KeyListener{
	public PartyPaul game;
	
	public PartyKeyListener(PartyPaul g){
		game = g;
	}
	
	public void keyPressed(KeyEvent e) {
		if(game.state==0){
			if(e.getKeyChar() == 'w' || e.getKeyChar() == 'W'){
				PartyPaul.xD = 0;
				PartyPaul.yD = -1;
			}
			if(e.getKeyChar() == 'a' || e.getKeyChar() == 'A'){
				PartyPaul.xD = -1;
				PartyPaul.yD = 0;
			}
			if(e.getKeyChar() == 's' || e.getKeyChar() == 'S'){
				PartyPaul.xD = 0;
				PartyPaul.yD = 1;
			}
			if(e.getKeyChar() == 'd' || e.getKeyChar() == 'D'){
				PartyPaul.xD = 1;
				PartyPaul.yD = 0;
			}
		}
		
		if((e.getKeyChar() == 'r' || e.getKeyChar() == 'R') && game.state!=0){
			game.score = 0;
			game.state = 0;
		}
		if(e.getKeyChar() == 'm' || e.getKeyChar() == 'M'){
			PartyPaul.sounds = !PartyPaul.sounds;
		}
	}

	public void keyReleased(KeyEvent e) {
		
	}

	public void keyTyped(KeyEvent e) {
		
	}

}
