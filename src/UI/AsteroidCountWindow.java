package UI;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.bson.types.ObjectId;

public class AsteroidCountWindow extends JFrame{
    
    private AsteroidCountPanel acp;
    private LoginPanel lp;
    private ObjectId userId;
    
    public AsteroidCountWindow() {

    	lp = new LoginPanel();
    	lp.setSize(800,800);
    	lp.requestFocus();
		lp.setFocusable(true);
		lp.setFocusTraversalKeysEnabled(false);
		add(lp);
    }
}
