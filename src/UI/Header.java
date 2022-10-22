package UI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import GameManager.GameManager;

public class Header extends JPanel implements ActionListener{
	private JButton pauseButton;
	private JButton resumeButton;
	private JButton saveButton;
	private JLabel scoreLabel;
	private int score = 0;
	private JLabel livesLabel;
	private int lives = 3;
	private GameManager gameManager;

	private JLabel remainingSimpleAsteroidsLabel;
	private JLabel remainingFirmAsteroidsLabel;
	private JLabel remainingExplosiveAsteroidsLabel;
	private JLabel remainingGiftAsteroidsLabel;
	
	private boolean pause = false;

	private final Font font = new Font(Font.SERIF,  Font.BOLD, 15);
	
	public Header(GameManager gameManager) {
		this.gameManager = gameManager;
		
		setBackground(new Color(50, 50, 50));
		this.setLayout(null);

		
		this.pauseButton = new JButton("Pause");
		pauseButton.setFocusable(false);
		pauseButton.addActionListener(this);
		pauseButton.setSize(100,35);
		pauseButton.setLocation(860, 10);
		pauseButton.setFont(font);
		add(pauseButton);
		
		this.resumeButton = new JButton("Resume");
		resumeButton.setFocusable(false);
		resumeButton.addActionListener(this);
		resumeButton.setSize(100,35);
		resumeButton.setLocation(860,50);
		resumeButton.setFont(font);
		add(resumeButton);
		
		this.saveButton = new JButton("Save");
		saveButton.setFocusable(false);
		saveButton.addActionListener(this);
		saveButton.setSize(100,35);
		saveButton.setLocation(860,90);
		saveButton.setFont(font);
		add(saveButton);
		
		this.scoreLabel = new JLabel("Score = 0", SwingConstants.CENTER);
		scoreLabel.setFocusable(false);
		scoreLabel.setSize(100, 30);
		scoreLabel.setLocation(30,20);
		scoreLabel.setFont(font);
		scoreLabel.setOpaque(true);
		scoreLabel.setBackground(new Color(255, 88, 81));
		scoreLabel.setForeground(Color.BLACK);
		add(scoreLabel);
		
		this.livesLabel = new JLabel("Lives = 0", SwingConstants.CENTER);
		livesLabel.setFocusable(false);
		livesLabel.setSize(100, 30);
		livesLabel.setLocation(30,70);
		livesLabel.setFont(font);
		livesLabel.setOpaque(true);
		livesLabel.setBackground(new Color(255, 88, 81));
		livesLabel.setForeground(Color.BLACK);
		add(livesLabel);
		
		initializeAsteroidsLabel();
		
	}
	
	/*
	 * Initialize
	 */
	
	private void initializeSimpleAsteroidsLabel() {
		this.remainingSimpleAsteroidsLabel = new JLabel("Simple Asteroids = ", SwingConstants.CENTER);
		remainingSimpleAsteroidsLabel.setFocusable(false);
		remainingSimpleAsteroidsLabel.setSize(200, 30);
		remainingSimpleAsteroidsLabel.setLocation(1330,20);
		remainingSimpleAsteroidsLabel.setFont(font);
		remainingSimpleAsteroidsLabel.setOpaque(true);
		remainingSimpleAsteroidsLabel.setBackground(new Color(240, 240, 240));
		remainingSimpleAsteroidsLabel.setForeground(Color.BLACK);
		add(remainingSimpleAsteroidsLabel);
	}
	
	private void initializeFirmAsteroidsLabel() {
		this.remainingFirmAsteroidsLabel = new JLabel("Firm Asteroids = ", SwingConstants.CENTER);
		remainingFirmAsteroidsLabel.setFocusable(false);
		remainingFirmAsteroidsLabel.setSize(200, 30);
		remainingFirmAsteroidsLabel.setLocation(1330,70);
		remainingFirmAsteroidsLabel.setFont(font);
		remainingFirmAsteroidsLabel.setOpaque(true);
		remainingFirmAsteroidsLabel.setBackground(new Color(240, 240, 240));
		remainingFirmAsteroidsLabel.setForeground(Color.BLACK);
		add(remainingFirmAsteroidsLabel);
	}
	
	private void initializeExplosiveAsteroidsLabel() {
		this.remainingExplosiveAsteroidsLabel = new JLabel("Explosive Astereoids = ", SwingConstants.CENTER);
		remainingExplosiveAsteroidsLabel.setFocusable(false);
		remainingExplosiveAsteroidsLabel.setSize(200, 30);
		remainingExplosiveAsteroidsLabel.setLocation(1580,20);
		remainingExplosiveAsteroidsLabel.setFont(font);
		remainingExplosiveAsteroidsLabel.setOpaque(true);
		remainingExplosiveAsteroidsLabel.setBackground(new Color(240, 240, 240));
		remainingExplosiveAsteroidsLabel.setForeground(Color.BLACK);
		add(remainingExplosiveAsteroidsLabel);
	}
	
	private void initializeGiftAsteroidsLabel() {
		this.remainingGiftAsteroidsLabel = new JLabel("Gift Asteroids = ", SwingConstants.CENTER);
		remainingGiftAsteroidsLabel.setFocusable(false);
		remainingGiftAsteroidsLabel.setSize(200, 30);
		remainingGiftAsteroidsLabel.setLocation(1580,70);
		remainingGiftAsteroidsLabel.setFont(font);
		remainingGiftAsteroidsLabel.setOpaque(true);
		remainingGiftAsteroidsLabel.setBackground(new Color(240, 240, 240));
		remainingGiftAsteroidsLabel.setForeground(Color.BLACK);
		add(remainingGiftAsteroidsLabel);
	}
	
	private void initializeAsteroidsLabel() {
		initializeSimpleAsteroidsLabel();
		initializeFirmAsteroidsLabel();
		initializeExplosiveAsteroidsLabel();
		initializeGiftAsteroidsLabel();
	}
	
	/*
	 * Update Labels
	 */
	public void updateScoreLabel(int score) {
		scoreLabel.setText("Score = " + score);
	}
	
	public void updateLivesLabel(int live) {
		livesLabel.setText("Lives = " + live);
	}
	
	public void updateAsteroids(int simpleAsteroidNumber, int firmAsteroidNumber, int explosiveAsteroidNumber, int giftAsteroidNumber) {
		remainingSimpleAsteroidsLabel.setText("Simple Asteroids = " + simpleAsteroidNumber);
		remainingFirmAsteroidsLabel.setText("Firm Asteroids = " + firmAsteroidNumber);
		remainingExplosiveAsteroidsLabel.setText("Explosive Astereoids = " + explosiveAsteroidNumber);
		remainingGiftAsteroidsLabel.setText("Gift Asteroids = " + giftAsteroidNumber);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		if (e.getSource() == pauseButton) {
			gameManager.setPlay(false);
		}
		else if (e.getSource() == resumeButton) {
			gameManager.setPlay(true);
		}
		else if (e.getSource() == saveButton) {
			((GameWindow) SwingUtilities.getWindowAncestor(this)).getGamePanel().getGameManager().addSavegameToDatabase();
			((GameWindow) SwingUtilities.getWindowAncestor(this)).getGamePanel().getGameManager().saveAsteroidsToDatabase();
			((GameWindow) SwingUtilities.getWindowAncestor(this)).getGamePanel().getGameManager().saveAliensToDatabase();
			((GameWindow) SwingUtilities.getWindowAncestor(this)).getGamePanel().getGameManager().savePowerUpsToDatabase();
			((GameWindow) SwingUtilities.getWindowAncestor(this)).getGamePanel().getGameManager().setHasBeenSaved(true);
		}
	}
}
