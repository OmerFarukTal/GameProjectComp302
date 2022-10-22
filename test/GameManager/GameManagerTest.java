package GameManager;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Domain.Asteroid.Asteroid;
import Domain.Asteroid.ExplosiveAsteroid.ExplosiveAsteroid;
import Domain.Asteroid.FirmAsteroid.FirmAsteroid;
import Domain.Asteroid.GiftAsteroid.GiftAsteroid;
import Domain.Asteroid.SimpleAsteroid.SimpleAsteroid;
import Domain.Ball.Ball;
import Domain.Paddle.Paddle;

class GameManagerTest {
	
	private GameManager gameManager;
	private Ball ball = new Ball();
    private Paddle paddle = new Paddle();
    
	@BeforeEach
	void setUp() {
		gameManager = new GameManager(ball, paddle, 5,5,5,5, null); 
	}
	
	@Test
	void AsteroidCreationTest() { // BB Test; Checks If it return a null array @ Omer Faruk TAL
		assertNotNull(gameManager.getAsteroids());
	}
	
	@Test
	void AsteroidCreationElementTest() { // BB Test; Checks If asteroids are initialized or not; @ Omer Faruk TAL
		Asteroid[] asteroids = gameManager.getAsteroids();
		for (int i = 0; i <  asteroids.length; i++) {
			assertNotNull(asteroids[i]);
		}	
	}
	
	@Test
	void AsteroidCreationElementFromTrueClasses() {
		Asteroid[] asteroids = gameManager.getAsteroids();
		for (int i = 0; i <  asteroids.length; i++) { //BB Test; Checks If the the elements are from the true classes @ Omer Faruk TAL
			assertTrue(asteroids[i] instanceof SimpleAsteroid || asteroids[i] instanceof ExplosiveAsteroid ||
					asteroids[i] instanceof FirmAsteroid || asteroids[i] instanceof GiftAsteroid);
		}	
	}
	
	@Test
	void AsteroidCreationSimpleAsteroidRepOk() { // GB Test; Checks IF the Created Simple Asteroids are represented right @ Omer Faruk TAL
			SimpleAsteroid[] simpleAsteroid = gameManager.getSimpleAsteroids();
			for (int i =0 ; i < simpleAsteroid.length; i++) {
				assertTrue(simpleAsteroid[i].repOk());
			}
	}
	
	@Test
	void AsteroidCreationAreMovementOkay() {
		SimpleAsteroid[] simpleAsteroid = gameManager.getSimpleAsteroids();
		for (int i =0 ; i < simpleAsteroid.length; i++) {  // GB Test; Checks IF the Created Simple Asteroids are moving correctly @ Omer Faruk TAL
			int oldXLoc = simpleAsteroid[i].getXLoc();
			simpleAsteroid[i].move();
			assertTrue(simpleAsteroid[i].getXLoc() >= oldXLoc);
			assertTrue(simpleAsteroid[i].repOk());
		}
	}

}
