package UI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import Database.DocumentManager;
import Database.MongoJava;

public class LoginPanel extends JPanel implements ActionListener {

	private JLabel nameLabel;
	private JLabel passLabel;
	private JTextField nameField;
	private JTextField passField;
	private JButton submitLogin;
	private JButton submitRegister;
	private JLabel resultLabel;
	
	private MongoDatabase database = MongoJava.getDatabase();
    private MongoCursor cursor;
    
    private String username;
    private String password;
    private ObjectId userId;
    
    private DocumentManager docMan;
    
	public LoginPanel() {
		
		MongoCollection coll = database.getCollection("LoginData");
		
		GridLayout gl = new GridLayout(4,2,20,20);
        this.setLayout(gl);
        
        nameLabel = new JLabel("Username: ", SwingConstants.CENTER);
        passLabel = new JLabel("Password: ", SwingConstants.CENTER);
        nameField = new JTextField();
        passField = new JTextField();
        submitLogin = new JButton("Login");
        submitRegister = new JButton("Register");
        resultLabel = new JLabel("", SwingConstants.CENTER);
        
        add(nameLabel);
        add(nameField);
        add(passLabel);
        add(passField);
        add(submitLogin);
        add(submitRegister);
        add(resultLabel);
        
        submitLogin.addActionListener(this);
        submitRegister.addActionListener(this);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		docMan = new DocumentManager();
		docMan.setDatabase(database);
		this.username = nameField.getText();
		this.password = passField.getText();
		
		if(e.getSource() == this.submitLogin) {
			ObjectId preCheckUserId = docMan.userLogin(username, password);	
			if(preCheckUserId != null) {
				this.userId = preCheckUserId;
				AsteroidCountPanel acp = new AsteroidCountPanel(userId);
		        acp.setSize(800,1000);
		        acp.requestFocus();
				acp.setFocusable(true);
				acp.setFocusTraversalKeysEnabled(false);
				this.setVisible(false);
				SwingUtilities.getWindowAncestor(this).add(acp);
				acp.setVisible(true);
			} else {
				this.resultLabel.setText("This user doesn't exist");
			}
		} else if (e.getSource() == this.submitRegister) {
			if(password == null || username == null) {
				this.resultLabel.setText("Fields cannot be empty");
			} else {
				String response = docMan.registerLoginData(username, password);
				this.resultLabel.setText(response);
			}
		}
	}
}
