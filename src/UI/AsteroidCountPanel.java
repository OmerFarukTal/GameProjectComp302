package UI;

import javax.swing.*;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import Database.MongoJava;
import Domain.Alien.Alien;
import Domain.Alien.CooperativeAlien;
import Domain.Alien.ProtectingAlien;
import Domain.Alien.RepairingAlien;
import Domain.Alien.SuprisingAlien;
import Domain.Alien.TimeWastingAlien;
import Domain.Asteroid.Asteroid;
import Domain.Asteroid.CircularMove;
import Domain.Asteroid.SimpleMove;
import Domain.Asteroid.ExplosiveAsteroid.ExplosiveAsteroid;
import Domain.Asteroid.FirmAsteroid.FirmAsteroid;
import Domain.Asteroid.GiftAsteroid.GiftAsteroid;
import Domain.Asteroid.SimpleAsteroid.SimpleAsteroid;
import Domain.Ball.Ball;
import Domain.Paddle.Paddle;
import Domain.PowerUp.ChancePowerUp;
import Domain.PowerUp.MagnetPowerUp;
import Domain.PowerUp.PowerUp;
import Domain.PowerUp.TallerPaddlePowerUp;
import Domain.PowerUp.WrapPowerUp;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import GameManager.GameManager;

public class AsteroidCountPanel extends JPanel implements ActionListener{

    private JLabel simpleLabel;
    private JLabel firmLabel;
    private JLabel explosiveLabel;
    private JLabel giftLabel;
    private JTextField simpleTextField;
    private JTextField firmTextField;
    private JTextField explosiveTextField;
    private JTextField giftTextField;
    private JButton submitButton;
    private JLabel emptyLabel;
    private JButton loadButton;
    private JComboBox saveGameIdField;
    
    private MongoDatabase database = MongoJava.getDatabase();
    private MongoCursor cursor;
    
    private List<SimpleAsteroid> simpleAsteroidList;
    private List<FirmAsteroid> firmAsteroidList;
    private List<ExplosiveAsteroid> explosiveAsteroidList;
    private List<GiftAsteroid> giftAsteroidList;
    private List<Alien> alienList;
    private List<PowerUp> powerUpList;
    
    private ObjectId userId;
    private int lives;
    private boolean coopBeenHit;
    private int score;
    private int total_time;

    public AsteroidCountPanel(ObjectId userId) {
    	
    	this.userId = userId;
    	
    	MongoCollection coll1 = database.getCollection("Savegame");
    	MongoCollection coll2 = database.getCollection("Asteroids");
    	
        GridLayout gl = new GridLayout(6,2,20,20);
        this.setLayout(gl);

        simpleLabel = new JLabel("Simple Asteroids: ", SwingConstants.CENTER);
        firmLabel = new JLabel("Firm Asteroids: ",SwingConstants.CENTER);
        explosiveLabel = new JLabel("Explosive Asteroids: ",SwingConstants.CENTER);
        giftLabel = new JLabel("Gift Asteroids: ",SwingConstants.CENTER);
        simpleTextField = new JTextField();
        firmTextField = new JTextField();
        explosiveTextField = new JTextField();
        giftTextField = new JTextField();
        submitButton = new JButton("Submit");
        emptyLabel = new JLabel();
        loadButton = new JButton("Load");
        saveGameIdField = new JComboBox();
        
        FindIterable<Document> iterable = coll1.find();

        MongoCursor<Document> cursor = iterable.iterator();
        
        while(cursor.hasNext()) {
        	Document o = cursor.next();
        	if(o.get("UserId").equals(userId)) {
        		saveGameIdField.addItem(o.get("_id"));
        	}
        }

        add(simpleLabel);
        add(simpleTextField);
        add(firmLabel);
        add(firmTextField);
        add(explosiveLabel);
        add(explosiveTextField);
        add(giftLabel);
        add(giftTextField);
        add(submitButton);
        add(emptyLabel);
        add(loadButton);
        add(saveGameIdField);

        submitButton.addActionListener(this);
        loadButton.addActionListener(this);
    }

    public void setUserId(ObjectId e) {
    	this.userId = e;
    }
    
    public ObjectId getUserId() {
    	return this.userId;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {

    	if(e.getSource() == submitButton) {
    		
    		int simpleAmount = Integer.parseInt(simpleTextField.getText());
            int firmAmount = Integer.parseInt(firmTextField.getText());
            int explosiveAmount = Integer.parseInt(explosiveTextField.getText());
            int giftAmount = Integer.parseInt(giftTextField.getText());
            
            SwingUtilities.getWindowAncestor(this).dispose();

            Ball ball = new Ball();
            Paddle paddle = new Paddle();
            GameManager gameManager = new GameManager(ball,paddle,simpleAmount,firmAmount,explosiveAmount,giftAmount,userId);
    		GameWindow gameWindow = new GameWindow(gameManager,ball,paddle);
    		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		gameWindow.setFocusable(false);
    		gameWindow.setResizable(false);
    		gameWindow.setSize(1820, 1040);

            gameWindow.setVisible(true);
            
    	} else if(e.getSource() == loadButton) {
    		
    		this.simpleAsteroidList = new ArrayList<SimpleAsteroid>();
    	    this.firmAsteroidList = new ArrayList<FirmAsteroid>();
    	    this.explosiveAsteroidList = new ArrayList<ExplosiveAsteroid>();
    	    this.giftAsteroidList = new ArrayList<GiftAsteroid>();
    	    this.alienList = new ArrayList<Alien>();
    	    this.powerUpList = new ArrayList<PowerUp>();
    		
    		MongoCollection coll1 = database.getCollection("Savegame");
        	MongoCollection coll2 = database.getCollection("Asteroids");
        	MongoCollection coll3 = database.getCollection("ActiveGifts");
        	
    		Object chosenSavegameId = saveGameIdField.getSelectedItem();
    		
            FindIterable<Document> iterable2 = coll1.find();
            MongoCursor<Document> cursor2 = iterable2.iterator();
            
            Document chosenSavegame = cursor2.next();
            while(cursor2.hasNext() && !chosenSavegame.get("_id").equals(chosenSavegameId)) {
            	chosenSavegame = cursor2.next();
            }
            
            FindIterable<Document> iterable3 = coll2.find();
            MongoCursor<Document> cursor3 = iterable3.iterator();
                        
            while(cursor3.hasNext()) {
            	Document currentAsteroid = cursor3.next();
            	if(currentAsteroid.get("AsteroidType").equals("Simple") && currentAsteroid.get("Savegame").equals(chosenSavegameId)) {
            		SimpleMove sm = new SimpleMove(0);
            		sm.setXLocAct(currentAsteroid.getInteger("xLocationNoMove"));
            		simpleAsteroidList.add(new SimpleAsteroid((int) currentAsteroid.get("xLocation"), (int) currentAsteroid.get("yLocation"), currentAsteroid.getBoolean("FreezeStatus"), sm, currentAsteroid.getBoolean("HasMove")));
            	} else if (currentAsteroid.get("AsteroidType").equals("Firm") && currentAsteroid.get("Savegame").equals(chosenSavegameId)) {
            		SimpleMove sm = new SimpleMove(0);
            		sm.setXLocAct(currentAsteroid.getInteger("xLocationNoMove"));
            		firmAsteroidList.add(new FirmAsteroid((int) currentAsteroid.get("xLocation"), (int) currentAsteroid.get("yLocation"), currentAsteroid.getBoolean("FreezeStatus"), currentAsteroid.getInteger("Radius"), sm, currentAsteroid.getBoolean("HasMove")));
            	} else if (currentAsteroid.get("AsteroidType").equals("Explosive") && currentAsteroid.get("Savegame").equals(chosenSavegameId)) {
            		CircularMove cm = new CircularMove(0,0);
            		cm.setXLocAct(currentAsteroid.getInteger("xLocationNoMove"));
            		cm.setYLocAct(currentAsteroid.getInteger("yLocationNoMove"));
            		explosiveAsteroidList.add(new ExplosiveAsteroid((int) currentAsteroid.get("xLocation"), (int) currentAsteroid.get("yLocation"), currentAsteroid.getBoolean("FreezeStatus"), (int) currentAsteroid.get("xLocation")+30, (int) currentAsteroid.get("yLocation")+30, new CircularMove((int) currentAsteroid.get("xLocation")+30, (int) currentAsteroid.get("yLocation")+30), currentAsteroid.getBoolean("HasMove")));
            	} else if (currentAsteroid.get("AsteroidType").equals("Gift") && currentAsteroid.get("Savegame").equals(chosenSavegameId)) {
            		giftAsteroidList.add(new GiftAsteroid((int) currentAsteroid.get("xLocation"), (int) currentAsteroid.get("yLocation"), currentAsteroid.getBoolean("FreezeStatus"), currentAsteroid.getString("Gifts")));
            	}
            }
            
            FindIterable<Document> iterable4 = coll3.find();
            MongoCursor<Document> cursor4 = iterable4.iterator();
            
            while(cursor4.hasNext()) {
            	Document currentGift = cursor4.next();
            	if(currentGift.get("Type").equals("Alien")) {
            		if(currentGift.get("AlienType").equals("CooperativeAlien") && currentGift.get("Savegame").equals(chosenSavegameId)) {
                		alienList.add(new CooperativeAlien(currentGift.getInteger("yLoc"), new Asteroid[0], new SimpleAsteroid[0], new FirmAsteroid[0], new ExplosiveAsteroid[0], new GiftAsteroid[0]));
                	} else if(currentGift.get("AlienType").equals("ProtectingAlien") && currentGift.get("Savegame").equals(chosenSavegameId)) {
                		alienList.add(new ProtectingAlien(currentGift.getInteger("xLoc"), currentGift.getInteger("yLoc")));
                	} else if(currentGift.get("AlienType").equals("RepairingAlien") && currentGift.get("Savegame").equals(chosenSavegameId)) {
                		alienList.add(new RepairingAlien(currentGift.getInteger("xLoc"), currentGift.getInteger("yLoc"), new Asteroid[0], new SimpleAsteroid[0], new FirmAsteroid[0], new ExplosiveAsteroid[0], new GiftAsteroid[0], new int[0][0]));
                	} else if(currentGift.get("AlienType").equals("TimeWastingAlien") && currentGift.get("Savegame").equals(chosenSavegameId)) {
                		alienList.add(new TimeWastingAlien(currentGift.getInteger("xLoc"), currentGift.getInteger("yLoc"), new Asteroid[0]));
                	} else if(currentGift.get("AlienType").equals("SurprisingAlien") && currentGift.get("Savegame").equals(chosenSavegameId)) {
                		alienList.add(new SuprisingAlien(new Asteroid[0], new SimpleAsteroid[0], new FirmAsteroid[0], new ExplosiveAsteroid[0], new GiftAsteroid[0], new int[0][0]));
                	}
            	} else if(currentGift.get("Type").equals("PowerUp")) {
            		if(currentGift.get("PowerUpType").equals("ChancePowerUp") && currentGift.get("Savegame").equals(chosenSavegameId)) {
                		powerUpList.add(new ChancePowerUp(currentGift.getInteger("xLoc"),currentGift.getInteger("yLoc")));
                	} else if(currentGift.get("PowerUpType").equals("MagnetPowerUp") && currentGift.get("Savegame").equals(chosenSavegameId)) {
                		if(currentGift.getInteger("yLoc") >= 790) {
                			powerUpList.add(new MagnetPowerUp(chosenSavegame.getInteger("PaddleLocation"), 796));
                		} else {
                			powerUpList.add(new MagnetPowerUp(currentGift.getInteger("xLoc"),currentGift.getInteger("yLoc")));
                		}
                	} else if(currentGift.get("PowerUpType").equals("TallerPaddlePowerUp") && currentGift.get("Savegame").equals(chosenSavegameId)) {
                		if(currentGift.getInteger("yLoc") >= 790) {
                			powerUpList.add(new TallerPaddlePowerUp(chosenSavegame.getInteger("PaddleLocation"), 796));
                		} else {
                			powerUpList.add(new TallerPaddlePowerUp(currentGift.getInteger("xLoc"),currentGift.getInteger("yLoc")));
                		}
                	} else if(currentGift.get("PowerUpType").equals("WrapPowerUp") && currentGift.get("Savegame").equals(chosenSavegameId)) {
                		if(currentGift.getInteger("yLoc") >= 790) {
                			powerUpList.add(new WrapPowerUp(chosenSavegame.getInteger("PaddleLocation"), 796));
                		} else {
                			powerUpList.add(new WrapPowerUp(currentGift.getInteger("xLoc"),currentGift.getInteger("yLoc")));
                		}
                	}
            	}	
            }

    		Ball ball = new Ball();
    		Paddle paddle = new Paddle();
    		ball.setXLocation((int) chosenSavegame.get("BallXLocation"));
    		ball.setYLocation((int) chosenSavegame.get("BallYLocation"));
    		paddle.setLocation((int) chosenSavegame.get("PaddleLocation"));
    		ball.setxVelocity((double)chosenSavegame.get("BallXVel"));
    		ball.setyVelocity((double)chosenSavegame.get("BallYVel"));
    		this.lives = chosenSavegame.getInteger("Lives");
    		this.coopBeenHit = chosenSavegame.getBoolean("CoopBeenHit");
    		this.score = chosenSavegame.getInteger("Score");
    		this.total_time = chosenSavegame.getInteger("Timer");
    		SwingUtilities.getWindowAncestor(this).dispose();
    
    		GameManager gameManager = new GameManager(ball,paddle,simpleAsteroidList,firmAsteroidList,explosiveAsteroidList,giftAsteroidList,userId, alienList, lives, coopBeenHit, powerUpList, score, total_time);
    		GameWindow gameWindow = new GameWindow(gameManager,ball,paddle);
    		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		gameWindow.setFocusable(false);
    		gameWindow.setResizable(false);
    		gameWindow.setSize(1820, 1040);
    		
    		gameWindow.setVisible(true);
    	}
    }
}
