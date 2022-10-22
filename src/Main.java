import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.mongodb.client.MongoDatabase;

import Database.DocumentManager;
import Database.MongoJava;
import UI.AsteroidCountPanel;
import UI.AsteroidCountWindow;
import UI.GameWindow;
import java.awt.GridLayout;

public class Main {
	public static void main(String[] args) {
		
		AsteroidCountWindow asteroidCountWindow = new AsteroidCountWindow();
		asteroidCountWindow.setResizable(false);
		asteroidCountWindow.setFocusable(false);
		asteroidCountWindow.setSize(800,800);
		asteroidCountWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		asteroidCountWindow.setVisible(true);
		
	}
}
