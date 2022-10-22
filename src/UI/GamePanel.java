package UI;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import Domain.Alien.CooperativeAlien;
import Domain.Alien.ProtectingAlien;
import Domain.Alien.RepairingAlien;
import Domain.Alien.SuprisingAlien;
import Domain.Alien.TimeWastingAlien;
import Domain.Asteroid.CircularMove;
import Domain.Asteroid.SimpleMove;
import Domain.Asteroid.Explosion.Explosion;
import Domain.Asteroid.FirmAsteroid.FirmAsteroid;
import Domain.Asteroid.GiftAsteroid.GiftAsteroid;
import Domain.Asteroid.SimpleAsteroid.SimpleAsteroid;
import Domain.Asteroid.ExplosiveAsteroid.ExplosiveAsteroid;
import Domain.Ball.*;
import Domain.Paddle.*;
import Domain.PowerUp.ChancePowerUp;
import Domain.PowerUp.MagnetPowerUp;
import Domain.PowerUp.TallerPaddlePowerUp;
import Domain.PowerUp.WrapPowerUp;
import GameManager.GameManager;

public class GamePanel extends JPanel {
	private Ball ball;
	private Paddle paddle;
	private int screenWidth = 1820;
	private int screenHeight = 880;
	private GameManager gameManager;

	private Image image;

	private Point prevPt;
	private int currentAsteroid = -1;
	private Point[] asteroidPoints;
	private SimpleAsteroid[] simpleAsteroids;
	private FirmAsteroid[] firmAsteroids;
	private ExplosiveAsteroid[] explosiveAsteroids;
	private GiftAsteroid[] giftAsteroids;
	
	private int explosionTimer = 0;

	private ProtectingAlien protectingAlien;
	private CooperativeAlien cooperativeAlien;
	private RepairingAlien repairingAlien;
	private TimeWastingAlien timeWastingAlien;
	private SuprisingAlien suprisingAlien;

	private ChancePowerUp chancePowerUp;
	private TallerPaddlePowerUp tallerPaddlePowerUp;
	private MagnetPowerUp magnetPowerUp;
	private WrapPowerUp wrapPowerUp;
	
	KeyListenerHandler kHandler = new KeyListenerHandler();
	
	public GamePanel(GameManager gameManager, Ball ball, Paddle paddle) {
		this.gameManager = gameManager;
		this.ball = ball;
		this.paddle = paddle;
		setBackground(new Color(209, 251, 255));
		super.addKeyListener(kHandler);
		ClickListener clickListener = new ClickListener();
		DragListener dragListener= new DragListener();
		this.addMouseListener(clickListener);
		this.addMouseMotionListener(dragListener);

		this.asteroidPoints = new Point[gameManager.getFirmAsteroidNumber()+gameManager.getSimpleAsteroidNumber()+gameManager.getExplosiveAsteroidNumber()+gameManager.getGiftAsteroidNumber()];
	}

	private class ClickListener extends MouseAdapter{
		public void mousePressed(MouseEvent e) {
			if(!gameManager.isHasBeenPlay()) {
				for(int i = 0; i<asteroidPoints.length; i++) {
					if((e.getX()>=(int)asteroidPoints[i].x && e.getX()<=(int)asteroidPoints[i].x+50) && (e.getY()>=(int)asteroidPoints[i].y && e.getY()<=(int)asteroidPoints[i].y+50)) {
						prevPt = asteroidPoints[i];
						currentAsteroid = i;
					}
				}
			}
			
		}
	}

	private class DragListener extends MouseMotionAdapter{
		public void mouseDragged(MouseEvent e) {
			if(prevPt != null && currentAsteroid>=simpleAsteroids.length && currentAsteroid<simpleAsteroids.length+firmAsteroids.length) {
				Point currentPt = e.getPoint();
				Point currentPtFixed = new Point(e.getPoint().x - e.getPoint().x%90+25, e.getPoint().y - e.getPoint().y%80+20);
				if(!gameManager.asteroidThere(currentPtFixed)) {
					asteroidPoints[currentAsteroid].setLocation(currentPtFixed);
					firmAsteroids[currentAsteroid-simpleAsteroids.length].setXLoc(currentPtFixed.x);
					firmAsteroids[currentAsteroid-simpleAsteroids.length].setYLoc(currentPtFixed.y);
					SimpleMove simpleMove = new SimpleMove(currentPtFixed.x);
					firmAsteroids[currentAsteroid-simpleAsteroids.length].setSimpleMove(simpleMove);
					gameManager.getAsteroids()[currentAsteroid].setXLoc(currentPtFixed.x);
					gameManager.getAsteroids()[currentAsteroid].setYLoc(currentPtFixed.y);
					prevPt = currentPt;
				}
				repaint();
			}

			if(prevPt != null && currentAsteroid<simpleAsteroids.length && currentAsteroid>=0) {
				Point currentPt = e.getPoint();
				Point currentPtFixed = new Point(e.getPoint().x - e.getPoint().x%90+25, e.getPoint().y - e.getPoint().y%80+20);
				if(!gameManager.asteroidThere(currentPtFixed)) {
					asteroidPoints[currentAsteroid].setLocation(currentPtFixed);
					simpleAsteroids[currentAsteroid].setXLoc(currentPtFixed.x);
					simpleAsteroids[currentAsteroid].setYLoc(currentPtFixed.y);
					SimpleMove simpleMove = new SimpleMove(currentPtFixed.x);
					simpleAsteroids[currentAsteroid].setSimpleMove(simpleMove);
					gameManager.getAsteroids()[currentAsteroid].setXLoc(currentPtFixed.x);
					gameManager.getAsteroids()[currentAsteroid].setYLoc(currentPtFixed.y);
					prevPt = currentPt;
				}
				repaint();
			}

			if(prevPt != null && currentAsteroid>=simpleAsteroids.length+firmAsteroids.length && currentAsteroid<simpleAsteroids.length+firmAsteroids.length+explosiveAsteroids.length) {
				Point currentPt = e.getPoint();
				Point currentPtFixed = new Point(e.getPoint().x - e.getPoint().x%90, e.getPoint().y - e.getPoint().y%80);
				if(!gameManager.asteroidThere(currentPtFixed)) {
					asteroidPoints[currentAsteroid].setLocation(currentPtFixed);
					explosiveAsteroids[currentAsteroid-simpleAsteroids.length-firmAsteroids.length].setXLoc(currentPtFixed.x);
					explosiveAsteroids[currentAsteroid-simpleAsteroids.length-firmAsteroids.length].setYLoc(currentPtFixed.y);
					CircularMove circularMove = new CircularMove(currentPtFixed.x , currentPtFixed.y);
					explosiveAsteroids[currentAsteroid-simpleAsteroids.length-firmAsteroids.length].setCircularMove(circularMove);
					gameManager.getAsteroids()[currentAsteroid].setXLoc(currentPtFixed.x);
					gameManager.getAsteroids()[currentAsteroid].setYLoc(currentPtFixed.y);
					prevPt = currentPt;
				}
				repaint();
			}

			if(prevPt != null && currentAsteroid>=simpleAsteroids.length+firmAsteroids.length+explosiveAsteroids.length) {
				Point currentPt = e.getPoint();
				Point currentPtFixed = new Point(e.getPoint().x - e.getPoint().x%90+25, e.getPoint().y - e.getPoint().y%80+20);
				if(!gameManager.asteroidThere(currentPtFixed)) {
					asteroidPoints[currentAsteroid].setLocation(currentPtFixed);
					giftAsteroids[currentAsteroid-simpleAsteroids.length-firmAsteroids.length-explosiveAsteroids.length].setXLoc(currentPtFixed.x);
					giftAsteroids[currentAsteroid-simpleAsteroids.length-firmAsteroids.length-explosiveAsteroids.length].setYLoc(currentPtFixed.y);
					gameManager.getAsteroids()[currentAsteroid].setXLoc(currentPtFixed.x);
					gameManager.getAsteroids()[currentAsteroid].setYLoc(currentPtFixed.y);
					prevPt = currentPt;
				}
				repaint();
			}
			
			
		}
	}

	private class KeyListenerHandler implements KeyListener{
		@Override
		public void keyPressed(KeyEvent e) {
			int c = e.getKeyCode();

			if (c == KeyEvent.VK_W) {
				if (gameManager.getLives() > 0) {
					gameManager.setPlay(true);
					gameManager.setHasBeenPlay(true);
					currentAsteroid = -1;
				}
			}

			if (gameManager.isPlay()) {
				if (c == KeyEvent.VK_RIGHT) {
					gameManager.movePaddleRight();
				} else if (c == KeyEvent.VK_LEFT) {
					gameManager.movePaddleLeft();
				}
			}

			if (gameManager.isPlay()) {
				if (c == KeyEvent.VK_P) {
					gameManager.setPlay(false);
				}
			}
			if (gameManager.isPlay() == false) {
				if (c == KeyEvent.VK_W) {
					if (gameManager.getLives() > 0) {
						gameManager.setPlay(true);
					}
				}
			}

			if (gameManager.isPlay()) {
				if (c == KeyEvent.VK_D) {
					gameManager.rotatePaddleRight();
				} else if (c == KeyEvent.VK_A) {
					gameManager.rotatePaddleLeft();
				}
			}
			
			// PowerUps
			
			if (gameManager.isPlay()) {
				if (c== KeyEvent.VK_1  && tallerPaddlePowerUp != null && tallerPaddlePowerUp.getImage() == null) {
					gameManager.performTallerPaddlePowerUp(true);
				}
				else if (c== KeyEvent.VK_2 && magnetPowerUp != null && magnetPowerUp.getImage() == null) {
					gameManager.performMagnetPowerUp(true);
				}
				else if (c == KeyEvent.VK_W && gameManager.getMagnetMessage()) {
					gameManager.setReleaseBallMessage();
				}
				else if (c == KeyEvent.VK_3 && wrapPowerUp != null && wrapPowerUp.getImage() == null) {
					gameManager.performWrapPowerUp(true);
				}
			}

		}

		@Override
		public void keyReleased(KeyEvent e) {

			int c = e.getKeyCode();

			if (gameManager.isPlay()) {
				if (c == KeyEvent.VK_D) {
					while(gameManager.getPaddle().getAngle() != 0) {
						gameManager.rotatePaddleLeft();
					}
				} else if (c == KeyEvent.VK_A) {
					while(gameManager.getPaddle().getAngle() != 0) {
						gameManager.rotatePaddleRight();
					}
				}
			}

		}
		@Override
		public void keyTyped(KeyEvent e) {}
	}

	private int iterateLength = 0;
	private int iterateLength2 = 0;
	private int iterateLength3 = 0;

	private void drawSimpleAsteroids(Graphics g) {
		this.simpleAsteroids = gameManager.getSimpleAsteroids();

		int length = simpleAsteroids.length;
		for (int i = 0; i < length ; i++,iterateLength++,iterateLength2++,iterateLength3++) {
			if(simpleAsteroids[i] != null) {
				asteroidPoints[i] = new Point(simpleAsteroids[i].getXLoc(),simpleAsteroids[i].getYLoc());
				g.drawImage(simpleAsteroids[i].getImage(), asteroidPoints[i].x, asteroidPoints[i].y, simpleAsteroids[i].getLength(), simpleAsteroids[i].getWidth(), this);
			}
		}
	}

	private void drawFirmAsteroids(Graphics g) {
		this.firmAsteroids = gameManager.getFirmAsteroids();
		int length = firmAsteroids.length;
		for (int i = 0; i < length; i++,iterateLength++,iterateLength2++,iterateLength3++) {
			if(firmAsteroids[i] != null) {
				asteroidPoints[iterateLength] = new Point(firmAsteroids[i].getXLoc(),firmAsteroids[i].getYLoc());
				g.drawImage(firmAsteroids[i].getImage(), asteroidPoints[iterateLength].x, asteroidPoints[iterateLength].y, firmAsteroids[i].getRadius(), firmAsteroids[i].getRadius(), this);
			}
		}
		iterateLength = 0;
	}

	private void drawExplosiveAsteroids(Graphics g) {
		this.explosiveAsteroids = gameManager.getExplosiveAsteroids();
		int length = explosiveAsteroids.length;
		for (int i = 0; i< length; i++,iterateLength2++,iterateLength3++) {
			if (explosiveAsteroids[i] != null) {
				asteroidPoints[iterateLength2] = new Point(explosiveAsteroids[i].getXLoc(),explosiveAsteroids[i].getYLoc());
				g.drawImage(explosiveAsteroids[i].getImage(), asteroidPoints[iterateLength2].x-25, asteroidPoints[iterateLength2].y-25, explosiveAsteroids[i].getLength(), explosiveAsteroids[i].getWidth(), this);
			}
		}
		iterateLength2 = 0;
	}

	private void drawGiftAsteroids(Graphics g) {
		this.giftAsteroids = gameManager.getGiftAsteroids();
		int length = giftAsteroids.length;
		for (int i = 0; i< length; i++,iterateLength3++) {
			if (giftAsteroids[i] != null) {
				asteroidPoints[iterateLength3] = new Point(giftAsteroids[i].getXLoc(),giftAsteroids[i].getYLoc());
				g.drawImage(giftAsteroids[i].getImage(), asteroidPoints[iterateLength3].x, asteroidPoints[iterateLength3].y, giftAsteroids[i].getLength(), giftAsteroids[i].getWidth(), this);
			}
		}
		iterateLength3 = 0;
	}

	private void drawExplosion(Graphics g) {
		Explosion explosion = gameManager.getExplosion();
		if (explosion != null && explosionTimer != 100) {
			g.drawImage(explosion.getImage(), explosion.getXLoc(), explosion.getYLoc(), explosion.getRadius(), explosion.getRadius(), this);
			explosionTimer += 5;
			if (explosionTimer == 100) {
				gameManager.makeExplosionNull();
			}
			explosionTimer = explosionTimer %100;
		}
	}

	private void drawProtectingAlien(Graphics g) {
		this.protectingAlien = gameManager.getProtectingAlien();
		if (protectingAlien != null) {
			g.drawImage(protectingAlien.getImage(), protectingAlien.getXLoc(), protectingAlien.getYLoc(), protectingAlien.getLength(), protectingAlien.getWidth(), this);
		}
	}

	private void drawCooperativeAlien(Graphics g) {
		this.cooperativeAlien = gameManager.getCooperativeAlien();
		if (cooperativeAlien != null) {
			g.drawImage(cooperativeAlien.getImage(), cooperativeAlien.getXLoc(), cooperativeAlien.getYLoc(), cooperativeAlien.getLength(), cooperativeAlien.getWidth(), this);
		}
	}

	private void drawRepairingAlien(Graphics g) {
		this.repairingAlien = gameManager.getRepairingAlien();
		if (repairingAlien != null) {
			g.drawImage(repairingAlien .getImage(), repairingAlien .getXLoc(), repairingAlien .getYLoc(), repairingAlien .getLength(), repairingAlien .getWidth(), this);
		}
	}

	private void drawTimeWastingAlien(Graphics g) {
		this.timeWastingAlien = gameManager.getTimeWastingAlien();
		if (timeWastingAlien != null) {
			g.drawImage(timeWastingAlien.getImage(), timeWastingAlien.getXLoc(), timeWastingAlien.getYLoc(), timeWastingAlien.getLength(), timeWastingAlien.getWidth(), this);
		}
	}

	private void drawSuprisingAlien(Graphics g) {
		this.suprisingAlien = gameManager.getSuprisingAlien();
		if (suprisingAlien != null) {
			g.drawImage(suprisingAlien.getImage(), suprisingAlien.getXLoc(), suprisingAlien.getYLoc(), suprisingAlien.getLength(), suprisingAlien.getWidth(), this);
		}
	}
	
	private void drawChancePowerUp(Graphics g) {
		this.chancePowerUp = gameManager.getChancePowerUp();
		if (chancePowerUp != null) {
			g.drawImage(chancePowerUp.getImage(), chancePowerUp.getxLoc(), chancePowerUp.getyLoc(), chancePowerUp.getLength(), chancePowerUp.getWidth(), this);
		}
	}

	private void drawTallerPaddlePowerUp(Graphics g) {
		this.tallerPaddlePowerUp =gameManager.getTallerPaddlePowerUp();
		if (tallerPaddlePowerUp != null && tallerPaddlePowerUp.getImage() != null) {
			g.drawImage(tallerPaddlePowerUp.getImage(), tallerPaddlePowerUp.getxLoc(), tallerPaddlePowerUp.getyLoc(), tallerPaddlePowerUp.getLength(), tallerPaddlePowerUp.getWidth(), this);
		}
	}

	private void drawMagnetPowerUp(Graphics g) {
		this.magnetPowerUp =gameManager.getMagnetPowerUp();
		if (magnetPowerUp != null && magnetPowerUp.getImage() != null) {
			g.drawImage(magnetPowerUp.getImage(), magnetPowerUp.getxLoc(), magnetPowerUp.getyLoc(), magnetPowerUp.getLength(), magnetPowerUp.getWidth(), this);
		}
	}
	
	public void drawWrapPowerUp(Graphics g) {
		this.wrapPowerUp = gameManager.getWrapPowerUp();
		if(wrapPowerUp != null && wrapPowerUp.getImage() != null) {
			g.drawImage(wrapPowerUp.getImage(), wrapPowerUp.getxLoc(), wrapPowerUp.getyLoc(), wrapPowerUp.getLength(), wrapPowerUp.getWidth(), this);
		}
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(new Color(50, 50, 50));
		g.fillRect(0,0,screenWidth,10);
		g.fillRect(0,0,10, screenHeight);
		g.fillRect(screenWidth - 10, 0, 10 ,screenHeight);

//		g.setColor(new Color(255, 255, 255));
//		g.fillRect(10, screenHeight - 10, screenWidth - 20, 10);

		ball.draw(g);
		this.drawSimpleAsteroids(g);
		this.drawFirmAsteroids(g);
		this.drawExplosiveAsteroids(g);
		this.drawExplosion(g);
		this.drawGiftAsteroids(g);
		this.drawProtectingAlien(g);
		this.drawCooperativeAlien(g);
		this.drawRepairingAlien(g);
		this.drawTimeWastingAlien(g);
		this.drawSuprisingAlien(g);
		this.drawChancePowerUp(g);
		this.drawTallerPaddlePowerUp(g);
		this.drawMagnetPowerUp(g);
		this.drawWrapPowerUp(g);
		paddle.draw(g);
	}

	public GameManager getGameManager() {
		return this.gameManager;
	}
}
