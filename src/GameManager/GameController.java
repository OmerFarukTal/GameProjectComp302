package GameManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import UI.GamePanel;
import UI.Header;

public class GameController {
	
	private GamePanel gamePanel;
	private Header header;
	private GameManager gameManager;

	private boolean gameOverFlag = true;
	
	private Timer timer = new Timer(10, new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (gameManager.isPlay()) {
				gameManager.moveBall();
				gameManager.moveSimpleAsteroids();
				gameManager.moveFirmAsteroids();
				gameManager.moveExplosiveAsteroids();
				gameManager.moveGiftAsteroids();
				gameManager.performProtectingAlien();
				gameManager.performCooperativeAlien();
				gameManager.performRepairingAlien();
				gameManager.performTimeWastingAlien();
				gameManager.performSuprisingAlien();
				gameManager.catchChancePowerUp();
				gameManager.catchTallerPaddlePowerUp();
				gameManager.performTallerPaddlePowerUp(gameManager.getTallerPaddleMessage());
				gameManager.catchMagnetPowerUp();
				gameManager.performMagnetPowerUp(gameManager.getMagnetMessage());
				gameManager.catchWrapPowerUp();
				gameManager.performWrapPowerUp(gameManager.getWrapMessage());

				//stopAndResumeGame();
			}
			if (gameOverFlag && gameManager.getLives() == 0) {
				JOptionPane.showMessageDialog(gamePanel, "GAME OVER!", "Game Over", JOptionPane.PLAIN_MESSAGE);
				gameOverFlag = false;
			}
			gamePanel.repaint();
			header.updateLivesLabel(gameManager.getLives());
			header.updateScoreLabel(gameManager.getScore());
			header.updateAsteroids(gameManager.getRemainingSimpleAsteroid(), gameManager.getRemainingFirmAsteroid(), gameManager.getRemainingExplosiveAsteroid(), gameManager.getRemainingGiftAsteroid());
		}
	});
	
	public GameController(GamePanel gamePanel,Header header) {
		this.gamePanel = gamePanel;
		this.header = header;
		this.gameManager = this.gamePanel.getGameManager();
	}
	
	public void startGame() {
		timer.start();
	}
	
	private void stopAndResumeGame() {
		
	}
}
