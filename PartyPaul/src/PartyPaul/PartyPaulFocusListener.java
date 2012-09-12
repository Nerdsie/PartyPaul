package PartyPaul;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class PartyPaulFocusListener implements FocusListener {

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		PartyPaul.focus = true;
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		PartyPaul.focus = false;
		
	}

}
