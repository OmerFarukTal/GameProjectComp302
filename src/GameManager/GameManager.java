package GameManager;

import Domain.Asteroid.FirmAsteroid.FirmAsteroid;
import Domain.Asteroid.FirmAsteroid.FirmAsteroidFactory;
import Domain.Asteroid.FirmAsteroid.FirmAsteroidLoadFactory;
import Domain.Asteroid.GiftAsteroid.GiftAsteroid;
import Domain.Asteroid.GiftAsteroid.GiftAsteroidFactory;
import Domain.Asteroid.SimpleAsteroid.SimpleAsteroid;
import Domain.Asteroid.SimpleAsteroid.SimpleAsteroidFactory;
import Domain.Asteroid.SimpleAsteroid.SimpleAsteroidLoadFactory;
import Domain.Ball.Ball;
import Domain.Paddle.Paddle;
import Domain.PowerUp.ChancePowerUp;
import Domain.PowerUp.MagnetPowerUp;
import Domain.PowerUp.PowerUp;
import Domain.PowerUp.PowerUpFactory;
import Domain.PowerUp.TallerPaddlePowerUp;
import Domain.PowerUp.WrapPowerUp;
import Domain.Alien.Alien;
import Domain.Alien.CooperativeAlien;
import Domain.Alien.ProtectingAlien;
import Domain.Alien.RepairingAlien;
import Domain.Alien.SuprisingAlien;
import Domain.Alien.TimeWastingAlien;
import Domain.Asteroid.Asteroid;
import Domain.Asteroid.Explosion.Explosion;
import Domain.Asteroid.ExplosiveAsteroid.ExplosiveAsteroid;
import Domain.Asteroid.ExplosiveAsteroid.ExplosiveAsteroidFactory;
import Domain.Asteroid.ExplosiveAsteroid.ExplosiveAsteroidLoadFactory;
import Domain.Asteroid.ExplosiveAsteroid.ExplosiveAsteroidMoveable;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.bson.types.ObjectId;

import com.mongodb.client.MongoDatabase;

import Database.DocumentManager;
import Database.MongoJava;

import javax.swing.*;


public class GameManager implements Observer {
    private Ball ball;
    private Paddle paddle;
    private boolean play = false;
	private boolean hasBeenPlay = false;

    private SimpleAsteroid[] simpleAsteroids;
	private SimpleAsteroidFactory simpleAsteroidFactory;
	private SimpleAsteroidLoadFactory simpleAsteroidLoadFactory;
	private int simpleAsteroidNumber;
	private int remainingSimpleAsteroid;
	
	private FirmAsteroid[] firmAsteroids;
	private FirmAsteroidFactory firmAsteroidFactory;
	private FirmAsteroidLoadFactory firmAsteroidLoadFactory;
	private int firmAsteroidNumber;
	private int remainingFirmAsteroid;

	private ExplosiveAsteroid[] explosiveAsteroids;
	private ExplosiveAsteroidFactory explosiveAsteroidFactory;
	private ExplosiveAsteroidLoadFactory explosiveAsteroidLoadFactory;
	private int explosiveAsteroidNumber;
	private int remainingExplosiveAsteroid;
	
	private GiftAsteroid[] giftAsteroids;
	private GiftAsteroidFactory giftAsteroidFactory;
	private int giftAsteroidNumber;
	private int remainingGiftAsteroid;

	private Explosion explosion;
	
	private Asteroid[] asteroids;
	private boolean[] asteroidHasMove;
	private int[] radiiOfFirm;
	private int[] asteroidsActualXLocations;
	private int[] asteroidsActualYLocations;
	private boolean[] freezeStatuses;
	private String[] gifts;
	
	private List<SimpleAsteroid> sList;
	private List<FirmAsteroid> fList;
	private List<ExplosiveAsteroid> eList;
	private List<GiftAsteroid> gList;
	
	private List<Alien> activeAliens;
	private List<PowerUp> activePowerUps;
	
	private ObjectId saveGameId;
	private MongoDatabase database = MongoJava.getDatabase();
	private DocumentManager docManager;
	
	private int[][] locs;
	private ProtectingAlien protectingAlien;
	private CooperativeAlien cooperativeAlien;
	private RepairingAlien repairingAlien;
	private TimeWastingAlien timeWastingAlien;
	private SuprisingAlien suprisingAlien;
	
	private ChancePowerUp chancePowerUp;
	private TallerPaddlePowerUp tallerPaddlePowerUp;
	private MagnetPowerUp magnetPowerUp;
	private PowerUp destructiveLaserGunPowerUp;
	private WrapPowerUp wrapPowerUp;
	private PowerUp gangOfBallsPowerUp;
    
	private boolean hasBeenSaved = false;
	
	private ObjectId userId;
	
	private int lives = 3;
	private int score = 0;
	private int total_time = 0;
	
	private boolean coopBeenHit = false;
	
	private boolean tallerPaddleMessage = false;
	private boolean magnetMessage = false;
	private boolean releaseBallMessage = false;
	private boolean wrapMessage = false;
	
    public GameManager(Ball ball, Paddle paddle, int simpleAmount, int firmAmount, int explosiveAmount, int giftAmount, ObjectId userId) {
        this.ball = ball;
        this.paddle = paddle;
        this.userId = userId;
        ball.addObserver(this);

		this.simpleAsteroidNumber = simpleAmount;
		this.firmAsteroidNumber = firmAmount;
		this.explosiveAsteroidNumber = explosiveAmount;
		this.giftAsteroidNumber = giftAmount;

		this.remainingSimpleAsteroid = this.simpleAsteroidNumber;
		this.remainingFirmAsteroid = this.firmAsteroidNumber;
		this.remainingExplosiveAsteroid = this.explosiveAsteroidNumber;
		this.remainingGiftAsteroid = this.giftAsteroidNumber;

        this.simpleAsteroids = new SimpleAsteroid[simpleAsteroidNumber];
		this.simpleAsteroidFactory = SimpleAsteroidFactory.getInstance();
		this.initSimpleAsteroids();
		
		this.firmAsteroids = new FirmAsteroid[firmAsteroidNumber];
		this.firmAsteroidFactory = FirmAsteroidFactory.getInstance();
		this.initFirmAsteroids();

		this.explosiveAsteroids = new ExplosiveAsteroid[explosiveAsteroidNumber];
		this.explosiveAsteroidFactory = ExplosiveAsteroidFactory.getInstance();
		this.initExplosiveAsteroids();

		this.giftAsteroids = new GiftAsteroid[giftAsteroidNumber];
		this.giftAsteroidFactory = GiftAsteroidFactory.getInstance();
		this.initGiftAsteroids();
		
		this.asteroids = new Asteroid[simpleAsteroidNumber + firmAsteroidNumber + explosiveAsteroidNumber + giftAsteroidNumber];
		this.asteroidHasMove = new boolean[simpleAsteroidNumber + firmAsteroidNumber + explosiveAsteroidNumber];
		this.radiiOfFirm = new int[firmAsteroidNumber];
				
		this.asteroidsActualXLocations = new int[simpleAsteroidNumber + firmAsteroidNumber + explosiveAsteroidNumber];
		this.asteroidsActualYLocations = new int[simpleAsteroidNumber + firmAsteroidNumber + explosiveAsteroidNumber];
		this.freezeStatuses = new boolean[simpleAsteroidNumber + firmAsteroidNumber + explosiveAsteroidNumber + giftAsteroidNumber];
		this.gifts = new String[giftAsteroidNumber];
		
		this.activeAliens = new ArrayList<Alien>();
		this.activePowerUps = new ArrayList<PowerUp>();
		placeAsteroidsToRows();

		timer.start();
    }
    
    public GameManager(Ball ball, Paddle paddle, List<SimpleAsteroid> sList, List<FirmAsteroid> fList, List<ExplosiveAsteroid> eList, List<GiftAsteroid> gList, ObjectId userId, List<Alien> alienList, int lives, boolean coopBeenHit, List<PowerUp> powerUpList, int score, int total_time) {

    	this.ball = ball;
        this.paddle = paddle;
        this.userId = userId;
        this.lives = lives;
        this.coopBeenHit = coopBeenHit;
        this.score = score;
        this.total_time = total_time;
        ball.addObserver(this);
        
        this.simpleAsteroidNumber = sList.size();
        this.firmAsteroidNumber = fList.size();
		this.explosiveAsteroidNumber = eList.size();
		this.giftAsteroidNumber = gList.size();
		    
		this.simpleAsteroids = new SimpleAsteroid[simpleAsteroidNumber];
		this.firmAsteroids = new FirmAsteroid[firmAsteroidNumber];
		this.explosiveAsteroids = new ExplosiveAsteroid[explosiveAsteroidNumber];
		this.giftAsteroids = new GiftAsteroid[giftAsteroidNumber];
		
		this.sList = sList;
		this.fList = fList;
		this.eList = eList;
		this.gList = gList;
		this.activeAliens = alienList;
		this.activePowerUps = powerUpList;
		
		this.remainingSimpleAsteroid = this.simpleAsteroidNumber;
		this.remainingFirmAsteroid = this.firmAsteroidNumber;
		this.remainingExplosiveAsteroid = this.explosiveAsteroidNumber;
		this.remainingGiftAsteroid = this.giftAsteroidNumber;

		this.simpleAsteroidLoadFactory = SimpleAsteroidLoadFactory.getInstance();
		this.firmAsteroidLoadFactory = FirmAsteroidLoadFactory.getInstance();
		this.explosiveAsteroidLoadFactory = ExplosiveAsteroidLoadFactory.getInstance();
        
		for(int i = 0; i < giftAsteroidNumber; i++) {
			giftAsteroids[i] = gList.get(i);
		} 
		
		this.asteroidHasMove = new boolean[simpleAsteroidNumber + firmAsteroidNumber + explosiveAsteroidNumber];
		this.asteroids = new Asteroid[simpleAsteroidNumber + firmAsteroidNumber + explosiveAsteroidNumber + giftAsteroidNumber];
		this.radiiOfFirm = new int[firmAsteroidNumber];
		this.asteroidsActualXLocations = new int[simpleAsteroidNumber + firmAsteroidNumber + explosiveAsteroidNumber];
		this.asteroidsActualYLocations = new int[simpleAsteroidNumber + firmAsteroidNumber + explosiveAsteroidNumber];
		this.freezeStatuses = new boolean[simpleAsteroidNumber + firmAsteroidNumber + explosiveAsteroidNumber + giftAsteroidNumber];
		this.gifts = new String[giftAsteroidNumber];
		
		this.loadSimpleAsteroids();
		this.loadFirmAsteroids();
		this.loadExplosiveAsteroids();
		
		System.arraycopy(simpleAsteroids, 0, asteroids, 0, simpleAsteroidNumber);
		System.arraycopy(firmAsteroids, 0, asteroids, simpleAsteroidNumber, firmAsteroidNumber);
		System.arraycopy(explosiveAsteroids, 0, asteroids, simpleAsteroidNumber + firmAsteroidNumber, explosiveAsteroidNumber);
		System.arraycopy(giftAsteroids, 0, asteroids, simpleAsteroidNumber + firmAsteroidNumber + explosiveAsteroidNumber, giftAsteroidNumber);
		
		for(int i = 0; i < simpleAsteroidNumber + firmAsteroidNumber + explosiveAsteroidNumber + giftAsteroidNumber; i++) {
			
			if(i<simpleAsteroidNumber) {
				if(sList.get(i).getFreezeStatus()) {
					this.freezeStatuses[i] = true;
				} else {
					this.freezeStatuses[i] = false;
				}
			} else if(i>=simpleAsteroidNumber && i<simpleAsteroidNumber+firmAsteroidNumber) {
				if(fList.get(i-simpleAsteroidNumber).getFreezeStatus()) {
					this.freezeStatuses[i] = true;
				} else {
					this.freezeStatuses[i] = false;
				}
			} else if(i>=simpleAsteroidNumber+firmAsteroidNumber && i<simpleAsteroidNumber + firmAsteroidNumber + explosiveAsteroidNumber) {
				if(eList.get(i-simpleAsteroidNumber-firmAsteroidNumber).getFreezeStatus()) {
					this.freezeStatuses[i] = true;
				} else {
					this.freezeStatuses[i] = false;
				}
			} else {
				if(gList.get(i-simpleAsteroidNumber-firmAsteroidNumber-explosiveAsteroidNumber).getFreezeStatus()) {
					this.freezeStatuses[i] = true;
				} else {
					this.freezeStatuses[i] = false;
				}
			}
		}
		
		for(int i = 0; i<simpleAsteroidNumber + firmAsteroidNumber + explosiveAsteroidNumber; i++) {
			
			this.asteroidsActualXLocations[i] = asteroids[i].getXLoc();
			this.asteroidsActualYLocations[i] = asteroids[i].getYLoc();
		
			if(i<simpleAsteroidNumber) {
				if(sList.get(i).getHasMoveLoadObject()) {
					this.asteroidHasMove[i] = true;
				} else {
					this.asteroidHasMove[i] = false;
				}
			} else if(i>=simpleAsteroidNumber && i<simpleAsteroidNumber+firmAsteroidNumber) {
				if(fList.get(i-simpleAsteroidNumber).getHasMoveLoadObject()) {
					this.asteroidHasMove[i] = true;
				} else {
					this.asteroidHasMove[i] = false;
				}
			} else if(i>=simpleAsteroidNumber+firmAsteroidNumber && i<simpleAsteroidNumber + firmAsteroidNumber + explosiveAsteroidNumber) {
				if(eList.get(i-simpleAsteroidNumber-firmAsteroidNumber).getHasMoveLoadObject()) {
					this.asteroidHasMove[i] = true;
				} else {
					this.asteroidHasMove[i] = false;
				}
			}
		}
		
		for(int i = 0; i <firmAsteroidNumber; i++) {
			radiiOfFirm[i] = firmAsteroids[i].getRadius();
		}
		
		for(int i = 0; i<giftAsteroidNumber; i++) {
			gifts[i] = giftAsteroids[i].getGift();
		}
		
		this.loadAliens(activeAliens);
		this.loadPowerUps(activePowerUps);

		timer.start();
    }

    @Override
    public void update(Observable o, Object arg) {
        play = false;
        ball.reset();
        paddle.reset();
        lives--;

    }

	private Timer timer = new Timer(1000, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (play) {
				total_time++;
			}
		}
	});

    public boolean isPlay() {
        return play;
    }

    public void setPlay(boolean play) {
        this.play = play;
    }

	public boolean isHasBeenPlay() {
		return hasBeenPlay;
	}

	public void setHasBeenPlay(boolean hasBeenPlay) {
		this.hasBeenPlay = hasBeenPlay;
	}
	
	public boolean isHasBeenSaved() {
		return this.hasBeenSaved;
	}
	
	public void setHasBeenSaved(boolean hasBeenSaved) {
		this.hasBeenSaved = hasBeenSaved;
	}

	public int getScore() {
		return this.score;
	}

    /*
     * Init Asteroids
     */
	
	public void addSavegameToDatabase() {
		docManager = new DocumentManager();
		docManager.setDatabase(database);
		
		if(!this.hasBeenSaved) {
			this.saveGameId = docManager.addSavegame(paddle, ball, userId, lives, coopBeenHit, score, total_time);
		} else {
			docManager.updateSavegame(paddle, ball, saveGameId, lives, userId, coopBeenHit, score, total_time);
		}
		
	}
	
	public void saveAsteroidsToDatabase() {
		docManager = new DocumentManager();
		docManager.setDatabase(database);
		if(!this.hasBeenSaved) {
			docManager.addAsteroids(asteroids, simpleAsteroidNumber, simpleAsteroidNumber+firmAsteroidNumber, simpleAsteroidNumber+firmAsteroidNumber+explosiveAsteroidNumber, saveGameId, asteroidHasMove, radiiOfFirm, asteroidsActualXLocations, asteroidsActualYLocations, freezeStatuses, gifts);
		} else {
			docManager.updateAsteroids(asteroids, saveGameId, asteroidHasMove);
		}

	}
	
	public void saveAliensToDatabase() {
		docManager = new DocumentManager();
		docManager.setDatabase(database);
		
		if(this.protectingAlien != null) {
			activeAliens.add(protectingAlien);
		}
		if(this.cooperativeAlien != null) {
			activeAliens.add(cooperativeAlien);
		}
		if(this.repairingAlien != null) {
			activeAliens.add(repairingAlien);
		}
		if(this.timeWastingAlien != null) {
			activeAliens.add(timeWastingAlien);
		}
		if(this.suprisingAlien != null) {
			activeAliens.add(suprisingAlien);
		}
		
		if(!this.hasBeenSaved) {
			docManager.addAliens(activeAliens, saveGameId);
		} else {
			docManager.updateAliens(activeAliens, saveGameId);
		}
	}
	
	public void savePowerUpsToDatabase() {
		docManager = new DocumentManager();
		docManager.setDatabase(database);
		
		if(this.chancePowerUp != null) {
			activePowerUps.add(chancePowerUp);
		}
		if(this.magnetPowerUp != null) {
			activePowerUps.add(magnetPowerUp);
		}
		if(this.tallerPaddlePowerUp != null) {
			activePowerUps.add(tallerPaddlePowerUp);
		}
		if(this.wrapPowerUp != null) {
			activePowerUps.add(wrapPowerUp);
		}
		
		if(!this.hasBeenSaved) {
			docManager.addPowerUps(activePowerUps, saveGameId);
		} else {
			docManager.updatePowerUps(activePowerUps, saveGameId);
		}
	}
	
	public void loadAsteroidsFromDatabase() {
		
	}
	
	private void placeAsteroidsToRows() {
		int totalAsteroid = simpleAsteroidNumber + firmAsteroidNumber + explosiveAsteroidNumber + giftAsteroidNumber;
		System.arraycopy(simpleAsteroids, 0, asteroids, 0, simpleAsteroidNumber);
		System.arraycopy(firmAsteroids, 0, asteroids, simpleAsteroidNumber, firmAsteroidNumber);
		System.arraycopy(explosiveAsteroids, 0, asteroids, simpleAsteroidNumber + firmAsteroidNumber, explosiveAsteroidNumber);
		System.arraycopy(giftAsteroids, 0, asteroids, simpleAsteroidNumber + firmAsteroidNumber + explosiveAsteroidNumber, giftAsteroidNumber);
		
		for(int i = 0; i < simpleAsteroidNumber + firmAsteroidNumber + explosiveAsteroidNumber + giftAsteroidNumber; i++) {
			
			if(i<simpleAsteroidNumber) {
				if(asteroids[i].getFreezeStatus()) {
					this.freezeStatuses[i] = true;
				} else {
					this.freezeStatuses[i] = false;
				}
			} else if(i>=simpleAsteroidNumber && i<simpleAsteroidNumber+firmAsteroidNumber) {
				if(asteroids[i-simpleAsteroidNumber].getFreezeStatus()) {
					this.freezeStatuses[i] = true;
				} else {
					this.freezeStatuses[i] = false;
				}
			} else if(i>=simpleAsteroidNumber+firmAsteroidNumber && i<simpleAsteroidNumber + firmAsteroidNumber + explosiveAsteroidNumber) {
				if(asteroids[i-simpleAsteroidNumber-firmAsteroidNumber].getFreezeStatus()) {
					this.freezeStatuses[i] = true;
				} else {
					this.freezeStatuses[i] = false;
				}
			} else {
				if(asteroids[i-simpleAsteroidNumber-firmAsteroidNumber-explosiveAsteroidNumber].getFreezeStatus()) {
					this.freezeStatuses[i] = true;
				} else {
					this.freezeStatuses[i] = false;
				}
			}
		}

		for(int i = 0; i<simpleAsteroidNumber + firmAsteroidNumber + explosiveAsteroidNumber; i++) {
			
			this.asteroidsActualXLocations[i] = asteroids[i].getXLoc();
			this.asteroidsActualYLocations[i] = asteroids[i].getYLoc();
			
			if(i<simpleAsteroidNumber) {
				if(simpleAsteroids[i].getSimpleMove() != null) {
					this.asteroidHasMove[i] = true;
				} else {
					this.asteroidHasMove[i] = false;
				}
			} else if(i>=simpleAsteroidNumber && i<simpleAsteroidNumber+firmAsteroidNumber) {
				if(firmAsteroids[i-simpleAsteroidNumber].getSimpleMove() != null) {
					this.asteroidHasMove[i] = true;
				} else {
					this.asteroidHasMove[i] = false;
				}
			} else if(i>=simpleAsteroidNumber+firmAsteroidNumber && i<simpleAsteroidNumber + firmAsteroidNumber + explosiveAsteroidNumber) {
				if(explosiveAsteroids[i-simpleAsteroidNumber-firmAsteroidNumber].getHasMoveLoadObject()) {
					this.asteroidHasMove[i] = false;
				} else {
					this.asteroidHasMove[i] = true;
				}
			}
		}
		
		for(int i = 0; i < firmAsteroidNumber; i++) {
			radiiOfFirm[i] = firmAsteroids[i].getRadius();
		}
		
		for(int i = 0; i<giftAsteroidNumber; i++) {
			gifts[i] = giftAsteroids[i].getGift();
		}
		
		locs = new int[100][2];
		
		for (int i = 0; i< 5 ;i++ ) {
			for (int j = 0; j < 20; j++) {
				locs[i*20+j][0] = j*80+ 100;
				locs[i*20+j][1] = i*80+ 100;
			}
		}
		
		
		int[] randomAsteroidIndex = RandomListGenerator.generateRandom(totalAsteroid);
		
		for (int i = 0; i < asteroids.length; i++) {
			if (asteroids[randomAsteroidIndex[i]] instanceof ExplosiveAsteroidMoveable) {
				ExplosiveAsteroidMoveable curr = (ExplosiveAsteroidMoveable) asteroids[randomAsteroidIndex[i]];
				curr.setXcenterYCenter(locs[i][0], locs[i][1]);
			}
			asteroids[randomAsteroidIndex[i]].setXLoc(locs[i][0]);
			asteroids[randomAsteroidIndex[i]].setYLoc(locs[i][1]);
		}
		
	}
    
	private void initSimpleAsteroids() {
		
		for (int i = 0;i < simpleAsteroidNumber; i++) {
			simpleAsteroids[i] = (simpleAsteroidFactory.getSimpleAsteroid());
		}
		
	}
	
	private void initFirmAsteroids() {
		
		for (int i = 0;i < firmAsteroidNumber; i++) {
			firmAsteroids[i] = (firmAsteroidFactory.getFirmAsteroid());
		}
		
	}

	private void initExplosiveAsteroids() {
		for (int i = 0;i < explosiveAsteroidNumber; i++) {
			explosiveAsteroids[i] = (explosiveAsteroidFactory.getExplosiveAsteroid());
		}
	}
	
	private void initGiftAsteroids() {
		for (int i = 0; i < giftAsteroidNumber; i++) {
			giftAsteroids[i] = giftAsteroidFactory.getGiftAsteroid(i%11); // It should be i; For testing change 
		}
	}
	
	private void loadSimpleAsteroids() {
		for(int i = 0; i < simpleAsteroidNumber; i++) {
			simpleAsteroids[i] = (simpleAsteroidLoadFactory.getSimpleAsteroid(sList.get(i).getHasMoveLoadObject(), sList.get(i).getXLoc(), sList.get(i).getYLoc(), sList.get(i).getFreezeStatus(),  sList.get(i).getSimpleMove().getXLocAct()));
		}
	}
	
	private void loadFirmAsteroids() {
		for(int i = 0; i < firmAsteroidNumber; i++) {
			firmAsteroids[i] = (firmAsteroidLoadFactory.getFirmAsteroid(fList.get(i).getHasMoveLoadObject(), fList.get(i).getXLoc(), fList.get(i).getYLoc(), fList.get(i).getFreezeStatus(), fList.get(i).getRadius(), fList.get(i).getSimpleMove().getXLocAct()));
		}
	}
	
	private void loadExplosiveAsteroids() {
		for(int i = 0; i < explosiveAsteroidNumber; i++) {
			explosiveAsteroids[i] = (explosiveAsteroidLoadFactory.getExplosiveAsteroid(eList.get(i).getHasMoveLoadObject(), eList.get(i).getXLoc(), eList.get(i).getYLoc(), eList.get(i).getFreezeStatus(), eList.get(i).getCircularMove().getXLocAct(), eList.get(i).getCircularMove().getYLocAct()));
		}
	}
	
	private void loadAliens(List<Alien> alienList) {
		for(int i = 0; i < alienList.size(); i++) {
			if (alienList.get(i).getType().equals("ProtectingAlien")) {
				this.protectingAlien = new ProtectingAlien(alienList.get(i).getXLoc(), alienList.get(i).getYLoc());
			}
			if (alienList.get(i).getType().equals("CooperativeAlien")) {
				this.cooperativeAlien = new CooperativeAlien(alienList.get(i).getYLoc(), asteroids, simpleAsteroids, firmAsteroids, explosiveAsteroids, giftAsteroids);
			}
			if (alienList.get(i).getType().equals("RepairingAlien")) {
				this.repairingAlien = new RepairingAlien(alienList.get(i).getXLoc(), alienList.get(i).getYLoc(), asteroids, simpleAsteroids, firmAsteroids, explosiveAsteroids, giftAsteroids, locs);
			}
			if (alienList.get(i).getType().equals("TimeWastingAlien")) {
				this.timeWastingAlien = new TimeWastingAlien(alienList.get(i).getXLoc(), alienList.get(i).getYLoc(), asteroids);
			}
			if (alienList.get(i).getType().equals("SurprisingAlien")) {
				this.suprisingAlien = new SuprisingAlien(asteroids, simpleAsteroids, firmAsteroids, explosiveAsteroids, giftAsteroids, locs);
			}
		}
	}
	
	private void loadPowerUps(List<PowerUp> powerUpList) {
		for(int i = 0; i < powerUpList.size(); i++) {
			if (powerUpList.get(i).getType().equals("ChancePowerUp")) {
				this.chancePowerUp = new ChancePowerUp(powerUpList.get(i).getxLoc(), powerUpList.get(i).getyLoc());
			}
			if (powerUpList.get(i).getType().equals("MagnetPowerUp")) {
				this.magnetPowerUp = new MagnetPowerUp(powerUpList.get(i).getxLoc(), powerUpList.get(i).getyLoc());
				if(powerUpList.get(i).getyLoc() >= 790) {
					this.magnetPowerUp.setImage(null);
				}
			}
			if (powerUpList.get(i).getType().equals("TallerPaddlePowerUp")) {
				this.tallerPaddlePowerUp = new TallerPaddlePowerUp(powerUpList.get(i).getxLoc(), powerUpList.get(i).getyLoc());
				if(powerUpList.get(i).getyLoc() >= 790) {
					tallerPaddlePowerUp.setImage(null);
				}
			}
			if (powerUpList.get(i).getType().equals("WrapPowerUp")) {
				this.wrapPowerUp = new WrapPowerUp(powerUpList.get(i).getxLoc(), powerUpList.get(i).getyLoc());
				if(powerUpList.get(i).getyLoc() >= 790) {
					wrapPowerUp.setImage(null);
				}
			}
		}
	}

    
    /*
     * Move Objects
     */
    
    public void moveBall() {
    	ball.move(paddle.getLocation(), paddle.getAngle(), paddle.getWidth());
    }
    
    public void movePaddleRight() {
    	paddle.moveRight();
    }
    
    public void movePaddleLeft() {
    	paddle.moveLeft();
    }

	public void rotatePaddleRight() {
		paddle.rotateRight();
	}

	public void rotatePaddleLeft() {
		paddle.rotateLeft();
	}
    
    /*
     * Move Asteroids
     */
    
    public void moveSimpleAsteroids() {
		simpleAsteroidCollision();
		int length = simpleAsteroids.length;
		for (int i = 0; i < length ; i++) {
			if (simpleAsteroids[i] != null) {
				simpleAsteroids[i].move();
			}
		}
	}
    
    public void moveFirmAsteroids() {
		firmAsteroidCollision();
		int length = firmAsteroids.length;
		for (int i = 0; i < length ; i++) {
			if (firmAsteroids[i] != null) {
				firmAsteroids[i].move();
			}
		}
	}
    
	public void moveExplosiveAsteroids() {
    	explosiveAsteroidCollision();
    	int length = explosiveAsteroids.length;
    	for (int i = 0; i < length; i++) {
    		if (explosiveAsteroids[i] != null) {
    			explosiveAsteroids[i].move();
    		}
    	}
    	
    }
	
	public void moveGiftAsteroids() {
		giftAsteroidCollision();
	}

    /*
     * Collision
     */
    
    private void simpleAsteroidCollision() {
    	int length = simpleAsteroids.length;
		for (int i = 0; i < length ; i++) {
			if (simpleAsteroids[i] != null  &&  !simpleAsteroids[i].getFreezeStatus() && new Rectangle(ball.getXLocation(),  ball.getYLocation(), (int) ball.getRadius()*2, (int) ball.getRadius()*2).intersects( new Rectangle(simpleAsteroids[i].getXLoc(),simpleAsteroids[i].getYLoc(),simpleAsteroids[i].getLength(),simpleAsteroids[i].getWidth()))) {
				if(ball.getXLocation()+10 < simpleAsteroids[i].getXLoc() || ball.getXLocation() > simpleAsteroids[i].getXLoc()+23) {
					ball.reflectHorizontal();
				} else if(ball.getYLocation()+10 <= simpleAsteroids[i].getYLoc() || ball.getYLocation() >= simpleAsteroids[i].getYLoc()+23) {
					ball.reflectVertical();
				}
				simpleAsteroids[i] = null;
				asteroids[i] = null;
				remainingSimpleAsteroid--;
				try {
					score += 300 / total_time;
				} catch (ArithmeticException e) {
					continue;
				}

			}
		}
    }
    
    private void firmAsteroidCollision() {
    	int length = firmAsteroids.length;
		for (int i = 0; i < length ; i++) {
			if (firmAsteroids[i] != null  &&  !firmAsteroids[i].getFreezeStatus() && new Rectangle(ball.getXLocation(),  ball.getYLocation(), (int) ball.getRadius()*2, (int) ball.getRadius()*2).intersects( new Rectangle(firmAsteroids[i].getXLoc(),firmAsteroids[i].getYLoc(),firmAsteroids[i].getRadius(),firmAsteroids[i].getRadius()))) {
				if(ball.getXLocation()+10 < firmAsteroids[i].getXLoc() || ball.getXLocation() > firmAsteroids[i].getXLoc()+23) {
					ball.reflectHorizontal();
				} else if(ball.getYLocation()+10 <= firmAsteroids[i].getYLoc() || ball.getYLocation() >= firmAsteroids[i].getYLoc()+23) {
					ball.reflectVertical();
				}
				if (firmAsteroids[i].hit()) {
					firmAsteroids[i] = null;
					asteroids[simpleAsteroidNumber + i] =  null;
					remainingFirmAsteroid--;
				}
				try {
					score += 300 / total_time;
				} catch (ArithmeticException e) {
					continue;
				}

			}
		}
    }

	private void explosiveAsteroidCollision() {
    	int length = explosiveAsteroids.length;
    	for (int i = 0; i < length ; i++) {
			if (explosiveAsteroids[i] != null  &&  !explosiveAsteroids[i].getFreezeStatus() && new Rectangle(ball.getXLocation(),  ball.getYLocation(), (int) ball.getRadius()*2, (int) ball.getRadius()*2).intersects( new Rectangle(explosiveAsteroids[i].getXLoc(),explosiveAsteroids[i].getYLoc(),explosiveAsteroids[i].getLength(),explosiveAsteroids[i].getWidth()))) {
				if(ball.getXLocation()+10 < explosiveAsteroids[i].getXLoc() || ball.getXLocation() > explosiveAsteroids[i].getXLoc()+23) {
					ball.reflectHorizontal();
				} else if(ball.getYLocation()+10 <= explosiveAsteroids[i].getYLoc() || ball.getYLocation() >= explosiveAsteroids[i].getYLoc()+23) {
					ball.reflectVertical();
				}
				this.explosion = new Explosion(explosiveAsteroids[i].getXLoc(), explosiveAsteroids[i].getYLoc());
				explosiveAsteroids[i] = null;
				asteroids[firmAsteroidNumber + simpleAsteroidNumber + i] = null;
				explosion();
				remainingExplosiveAsteroid--;
				try {
					score += 300 / total_time;
				} catch (ArithmeticException e) {
					continue;
				}

			}
		}
    }
	
	private void giftAsteroidCollision() {
		int length = giftAsteroids.length;
		for (int i = 0; i < length ; i++) {
			if (giftAsteroids[i] != null  &&  !giftAsteroids[i].getFreezeStatus() && new Rectangle(ball.getXLocation(),  ball.getYLocation(), (int) ball.getRadius()*2, (int) ball.getRadius()*2).intersects( new Rectangle(giftAsteroids[i].getXLoc(),giftAsteroids[i].getYLoc(),giftAsteroids[i].getLength(),giftAsteroids[i].getWidth()))) {
				if(ball.getXLocation()+10 < giftAsteroids[i].getXLoc() || ball.getXLocation() > giftAsteroids[i].getXLoc()+23) {
					ball.reflectHorizontal();
				} else if(ball.getYLocation()+10 <= giftAsteroids[i].getYLoc() || ball.getYLocation() >= giftAsteroids[i].getYLoc()+23) {
					ball.reflectVertical();
				}
				callAlien(giftAsteroids[i].getGift());
				callPowerUp(giftAsteroids[i].getGift(), giftAsteroids[i].getXLoc(), giftAsteroids[i].getYLoc());
				giftAsteroids[i] = null;
				asteroids[explosiveAsteroidNumber + firmAsteroidNumber + simpleAsteroidNumber + i] = null;
				remainingGiftAsteroid--;
				try {
					score += 300 / total_time;
				} catch (ArithmeticException e) {
					continue;
				}

			}
		}
	}
	
	public boolean asteroidThere(Point e) {
		for(int i = 0; i < asteroids.length; i++) {
			if(new Rectangle(e.x,  e.y, 30, 30).intersects( new Rectangle(asteroids[i].getXLoc(),asteroids[i].getYLoc(),30,30)) || e.x < 0 || e.x > 1820 || e.y < 0 || e.y >790) {
				return true;
			}
		}
		return false;

	}
	
	/*
	 * Call Alien
	 */
	
	private void callAlien(String alienType) {
		int yOfLowest = 0;
		for(int i = 0; i < asteroids.length; i++) {
			if(asteroids[i] != null) {
				if(asteroids[i].getYLoc() > yOfLowest) {
					yOfLowest = asteroids[i].getYLoc();
				}
			}
		}
		System.out.println(alienType);
		if (protectingAlien == null && cooperativeAlien== null && repairingAlien == null && timeWastingAlien == null && suprisingAlien == null) {
			if (alienType.equals("Protecting Alien")) {
				protectingAlien = new ProtectingAlien(500, yOfLowest+100);
			}
			else if (alienType.equals("Cooperative Alien") && !coopBeenHit) {
				cooperativeAlien = new CooperativeAlien(500, asteroids, simpleAsteroids, firmAsteroids, explosiveAsteroids, giftAsteroids);
			}
			else if (alienType.equals("Repairing Alien")) {
				repairingAlien = new RepairingAlien(500, 500, asteroids, simpleAsteroids, firmAsteroids, explosiveAsteroids, giftAsteroids, locs);
			}
			else if (alienType.equals("Time-wasting Alien")) {
				timeWastingAlien = new TimeWastingAlien(500, 500, asteroids);
			}
			else if (alienType.equals("Suprising Alien")) {
				suprisingAlien = new SuprisingAlien(asteroids, simpleAsteroids, firmAsteroids, explosiveAsteroids, giftAsteroids, locs);
			}
		}
	}
	
	public void performProtectingAlien() {
		protectingAlienCollision();
		if (protectingAlien != null) {
			this.protectingAlien.perform();
		}
	}
	
	public void performCooperativeAlien() {
		cooperativeAlienCollision();
		if (cooperativeAlien != null) {
			this.cooperativeAlien.perform();
		}
	}
	
	public void performRepairingAlien() {
		repairingAlienCollision();
		if (repairingAlien != null) {
			this.repairingAlien.perform();
		}
			
	}
	
	public void performTimeWastingAlien() {
		timeWastingAlienCollision();
		if (timeWastingAlien != null) {
			this.timeWastingAlien.perform();
		}
	}
	
	public void performSuprisingAlien() {
		suprisingAlienCollision();
		if (suprisingAlien != null) {
			this.suprisingAlien.perform();
		}
	}
	
	private void protectingAlienCollision() {
		if (protectingAlien != null && ball.getYLocation() < protectingAlien.getYLoc() && 
				new Rectangle(ball.getXLocation(),  ball.getYLocation(), (int) ball.getRadius(), (int) ball.getRadius()).intersects( new Rectangle(protectingAlien.getXLoc(),protectingAlien.getYLoc(), protectingAlien.getLength(), protectingAlien.getWidth()))) {
			ball.reflectVertical();
			this.protectingAlien = null;
			for(int i = 0; i < activeAliens.size(); i++) {
				if(activeAliens.get(i).getType().equals("ProtectingAlien")) {
					activeAliens.remove(i);
				}
			}
		}
		else if (protectingAlien != null && 
				new Rectangle(ball.getXLocation(),  ball.getYLocation(), (int) ball.getRadius(), (int) ball.getRadius()).intersects( new Rectangle(protectingAlien.getXLoc(),protectingAlien.getYLoc(), protectingAlien.getLength(), protectingAlien.getWidth()))) {
			ball.reflectVertical();
			
		}
	}
	
	private void cooperativeAlienCollision() {
		if (cooperativeAlien != null && 
				new Rectangle(ball.getXLocation(),  ball.getYLocation(), (int) ball.getRadius(), (int) ball.getRadius()).intersects
				( new Rectangle(cooperativeAlien.getXLoc(),cooperativeAlien.getYLoc(), cooperativeAlien.getLength(), cooperativeAlien.getWidth()))) {
			ball.reflectVertical();
			this.cooperativeAlien = null;
			this.coopBeenHit = true;
			for(int i = 0; i < activeAliens.size(); i++) {
				if(activeAliens.get(i).getType().equals("CooperativeAlien")) {
					activeAliens.remove(i);
				}
			}
		}
		else if ( cooperativeAlien != null && cooperativeAlien.getXLoc() > cooperativeAlien.getXLocRight()) {
			this.cooperativeAlien = null;
			for(int i = 0; i < activeAliens.size(); i++) {
				if(activeAliens.get(i).getType().equals("CooperativeAlien")) {
					activeAliens.remove(i);
				}
			}
		}
	}
	
	private void repairingAlienCollision() {
		if (repairingAlien != null && 
				new Rectangle(ball.getXLocation(),  ball.getYLocation(), (int) ball.getRadius(), (int) ball.getRadius()).intersects
				( new Rectangle(repairingAlien.getXLoc(),repairingAlien.getYLoc(), repairingAlien.getLength(), repairingAlien.getWidth()))) {
			ball.reflectVertical();
			this.repairingAlien = null;
			for(int i = 0; i < activeAliens.size(); i++) {
				if(activeAliens.get(i).getType().equals("RepairingAlien")) {
					activeAliens.remove(i);
				}
			}
		}
	}
	
	private void timeWastingAlienCollision() {
		if (timeWastingAlien != null &&
				new Rectangle(ball.getXLocation(),  ball.getYLocation(), (int) ball.getRadius(), (int) ball.getRadius()).intersects
				( new Rectangle(timeWastingAlien.getXLoc(),timeWastingAlien.getYLoc(), timeWastingAlien.getLength(), timeWastingAlien.getWidth()))) {
			ball.reflectVertical();
			this.timeWastingAlien = null;
			for(int i = 0; i < activeAliens.size(); i++) {
				if(activeAliens.get(i).getType().equals("TimeWastingAlien")) {
					activeAliens.remove(i);
				}
			}
		}
	}
	
	private void suprisingAlienCollision() {
		if (suprisingAlien != null && 
				new Rectangle(ball.getXLocation(),  ball.getYLocation(), (int) ball.getRadius(), (int) ball.getRadius()).intersects
				( new Rectangle(suprisingAlien.getXLoc(), suprisingAlien.getYLoc(), suprisingAlien.getLength(), suprisingAlien.getWidth()))) {
			ball.reflectVertical();
			this.suprisingAlien = null;
			for(int i = 0; i < activeAliens.size(); i++) {
				if(activeAliens.get(i).getType().equals("SurprisingAlien")) {
					activeAliens.remove(i);
				}
			}
		}
		else if (suprisingAlien != null && suprisingAlien.getXLoc() > suprisingAlien.getXLocRight()) {
			this.suprisingAlien = null;
			for(int i = 0; i < activeAliens.size(); i++) {
				if(activeAliens.get(i).getType().equals("SurprisingAlien")) {
					activeAliens.remove(i);
				}
			}
		}
	}
	
	
	/*
	 * PowerUp
	 */
	
	public void callPowerUp(String powerUp, int xLoc, int yLoc) {
		System.out.println(powerUp);
		if (powerUp.equals("Chance")) {
			this.chancePowerUp = new ChancePowerUp(xLoc, yLoc);
		}
		else if (tallerPaddlePowerUp == null && powerUp.equals("Taller")) {
			 this.tallerPaddlePowerUp = new TallerPaddlePowerUp(xLoc, yLoc);
		}
		else if (magnetPowerUp == null && powerUp.equals("Magnet")) {
			this.magnetPowerUp = new MagnetPowerUp(xLoc, yLoc);
		}
		else if (wrapPowerUp == null && powerUp.equals("Wrap")) {
			this.wrapPowerUp = new WrapPowerUp(xLoc, yLoc);
		}
	}
	
	public void  catchChancePowerUp() {
		if (chancePowerUp != null) {
			chancePowerUp.fall();
		}
		if (chancePowerUp != null && chancePowerUp.perform(paddle.getLocation(), paddle.getAngle(), paddle.getWidth())) {
			this.chancePowerUp = null;
			for(int i = 0; i < activePowerUps.size(); i++) {
				if(activePowerUps.get(i).getType().equals("ChancePowerUp")) {
					activePowerUps.remove(i);
				}
			}
			lives++;
		}
	}
	
	public void catchTallerPaddlePowerUp() {
		if (tallerPaddlePowerUp != null) {
			tallerPaddlePowerUp.fall();
		}
		if (tallerPaddlePowerUp != null && tallerPaddlePowerUp.catchPaddle(paddle.getLocation(), paddle.getAngle(), paddle.getWidth())) {
			this.tallerPaddlePowerUp.setyVel(0);
			this.tallerPaddlePowerUp.setImage(null);
		}
	}
	
	public void performTallerPaddlePowerUp(boolean message) {
		if (tallerPaddlePowerUp != null && message) {
			tallerPaddleMessage = true;
		}
		if (tallerPaddlePowerUp != null && tallerPaddlePowerUp.getImage() == null && tallerPaddleMessage) {
			if (this.tallerPaddlePowerUp.perform()) {
				paddle.setWidth(200);
			}
			else {
				paddle.setWidth(100);
				this.tallerPaddlePowerUp = null;
				for(int i = 0; i < activePowerUps.size(); i++) {
					if(activePowerUps.get(i).getType().equals("TallerPaddlePowerUp")) {
						activePowerUps.remove(i);
					}
				}
				this.tallerPaddleMessage = false;
			}
		}
	}
	
	public void catchMagnetPowerUp() {
		if (magnetPowerUp != null) {
			magnetPowerUp.fall();
		}
		if (magnetPowerUp != null && magnetPowerUp.catchPaddle(paddle.getLocation(), paddle.getAngle(), paddle.getWidth())) {
			this.magnetPowerUp.setImage(null);
			this.magnetPowerUp.setyVel(0);
		}
	}
	
	public void performMagnetPowerUp(boolean message) {
		if (magnetPowerUp != null && message) {
			magnetMessage = true;
		}
		
		if (magnetPowerUp != null && magnetPowerUp.getHoldState()) {
			ball.setXLocation(paddle.getLocation() + paddle.getWidth()/2);
			ball.setYLocation(780);
		}
		if (magnetPowerUp != null && magnetPowerUp.getImage() == null && magnetMessage && !releaseBallMessage) {
			if (ball.intersectionWithPaddle(paddle.getLocation(), paddle.getAngle(), paddle.getWidth())) {
				magnetPowerUp.setHoldState(true);
			}				
		}
		if (magnetPowerUp != null && magnetPowerUp.getImage() == null && magnetMessage && releaseBallMessage) {
			if (magnetPowerUp.getHoldState() && ball.getYLocation() == 780) {
				this.magnetMessage = false;
				this.releaseBallMessage = false;
				this.magnetPowerUp = null;
				for(int i = 0; i < activePowerUps.size(); i++) {
					if(activePowerUps.get(i).getType().equals("MagnetPowerUp")) {
						activePowerUps.remove(i);
					}
				}
			}				
		}
	}
	
	public void catchWrapPowerUp() {
		if(wrapPowerUp != null) {
			wrapPowerUp.fall();
		}
		if (wrapPowerUp != null && wrapPowerUp.catchPaddle(paddle.getLocation(), paddle.getAngle(), paddle.getWidth())) {
			this.wrapPowerUp.setImage(null);
			this.wrapPowerUp.setyVel(0);
			for(int i = 0; i < activePowerUps.size(); i++) {
				if(activePowerUps.get(i).getType().equals("WrapPowerUp")) {
					activePowerUps.remove(i);
				}
			}
		}
	}
	
	public void performWrapPowerUp(boolean message) {
		if (wrapPowerUp != null && message) {
			wrapMessage = true;
		}
		if (wrapPowerUp != null && wrapPowerUp.getImage() == null && wrapMessage) {
			if (this.wrapPowerUp.perform()) {
				paddle.setWrapStatus(true);
			}
			else {
				paddle.setWrapStatus(false);
				this.wrapPowerUp = null;
				for(int i = 0; i < activePowerUps.size(); i++) {
					if(activePowerUps.get(i).getType().equals("WrapPowerUp")) {
						activePowerUps.remove(i);
					}
				}
				this.wrapMessage = false;
			}
		}
	}
	
    /*
     * Explosion
     */
    
    private void explosion() {
    	
    	int simpleAsteroidLength = simpleAsteroids.length;
		for (int i = 0; i < simpleAsteroidLength ; i++) {
			if (simpleAsteroids[i] != null  &&  new Rectangle(explosion.getXLoc(),  explosion.getYLoc(), (int) explosion.getRadius(), (int) explosion.getRadius()).intersects(
					new Rectangle(simpleAsteroids[i].getXLoc(),simpleAsteroids[i].getYLoc(),simpleAsteroids[i].getLength(),simpleAsteroids[i].getWidth()))) {
				simpleAsteroids[i] = null;
				asteroids[i] = null;
				remainingSimpleAsteroid--;
			}
		}

    	int firmAssteroidLength = firmAsteroids.length;
		for (int i = 0; i < firmAssteroidLength ; i++) {
			if (firmAsteroids[i] != null  &&  new Rectangle(explosion.getXLoc(),  explosion.getYLoc(), (int) explosion.getRadius(), (int) explosion.getRadius()).intersects(
					new Rectangle(firmAsteroids[i].getXLoc(),firmAsteroids[i].getYLoc(),firmAsteroids[i].getRadius(),firmAsteroids[i].getRadius()))) {
				firmAsteroids[i] = null;
				asteroids[simpleAsteroidNumber + i] = null;
				remainingFirmAsteroid--;
			}
		}
	
		int explosiveAsteroidLength = explosiveAsteroids.length;
		for (int i = 0; i < explosiveAsteroidLength; i++ ) {
			if (explosiveAsteroids[i] != null  &&  new Rectangle(explosion.getXLoc(),  explosion.getYLoc(), (int) explosion.getRadius(), (int) explosion.getRadius()).intersects( 
					new Rectangle(explosiveAsteroids[i].getXLoc(),explosiveAsteroids[i].getYLoc(),explosiveAsteroids[i].getLength(),explosiveAsteroids[i].getWidth()))) {
				explosiveAsteroids[i] = null;
				asteroids[simpleAsteroidNumber + firmAsteroidNumber + i] = null;
				remainingExplosiveAsteroid--;
			}
		}
		
		int giftAsteroidLength = giftAsteroids.length; 
		for (int i = 0; i < giftAsteroidLength; i++ ) {
			if (giftAsteroids[i] != null  &&  new Rectangle(explosion.getXLoc(),  explosion.getYLoc(), (int) explosion.getRadius(), (int) explosion.getRadius()).intersects( 
					new Rectangle(giftAsteroids[i].getXLoc(),giftAsteroids[i].getYLoc(),giftAsteroids[i].getLength(),giftAsteroids[i].getWidth()))) {
				giftAsteroids[i] = null;
				asteroids[simpleAsteroidNumber + firmAsteroidNumber + explosiveAsteroidNumber + i] = null;
				remainingGiftAsteroid--;
			}
		}
    }
    
    public void makeExplosionNull() {
    	this.explosion = null;
    }

    
    /*
     * Getter And Setter
     */
    
    public SimpleAsteroid[] getSimpleAsteroids() {
    	return this.simpleAsteroids;
    }
    public FirmAsteroid[] getFirmAsteroids() {
    	return this.firmAsteroids;
    }

	public ExplosiveAsteroid[] getExplosiveAsteroids() {
    	return this.explosiveAsteroids;
    }
	
	public GiftAsteroid[] getGiftAsteroids() {
    	return this.giftAsteroids;
    }
	
	public Explosion getExplosion() {
    	return explosion;
    }

	public int getSimpleAsteroidNumber() {
		return simpleAsteroidNumber;
	}

	public int getFirmAsteroidNumber() {
		return firmAsteroidNumber;
	}

	public int getExplosiveAsteroidNumber() {
		return explosiveAsteroidNumber;
	}

	public int getGiftAsteroidNumber() {
		return giftAsteroidNumber;
	}

	public Paddle getPaddle() {
		return this.paddle;
	}

	public Asteroid[] getAsteroids() {
		return this.asteroids;
	}
	
	public ProtectingAlien getProtectingAlien() {
		return this.protectingAlien;
	}
	
	public CooperativeAlien getCooperativeAlien() {
		return this.cooperativeAlien;
	}
	
	public RepairingAlien getRepairingAlien() {
		return this.repairingAlien;
	}
	
	public TimeWastingAlien getTimeWastingAlien() {
		return this.timeWastingAlien;
	}
	
	public SuprisingAlien getSuprisingAlien() {
		return this.suprisingAlien;
	}
	
	public int getLives() {
		return this.lives;
	}

	public int getRemainingSimpleAsteroid() {
		return remainingSimpleAsteroid;
	}

	public int getRemainingFirmAsteroid() {
		return remainingFirmAsteroid;
	}
	
	public int getRemainingExplosiveAsteroid() {
		return remainingExplosiveAsteroid;
	}

	public int getRemainingGiftAsteroid() {
		return remainingGiftAsteroid;
	}

	public ChancePowerUp getChancePowerUp() {
		return chancePowerUp;
	}

	public TallerPaddlePowerUp getTallerPaddlePowerUp() {
		return tallerPaddlePowerUp;
	}

	public MagnetPowerUp getMagnetPowerUp() {
		return magnetPowerUp;
	}

	public PowerUp getDestructiveLaserGunPowerUp() {
		return destructiveLaserGunPowerUp;
	}

	public WrapPowerUp getWrapPowerUp() {
		return wrapPowerUp;
	}

	public PowerUp getGangOfBallsPowerUp() {
		return gangOfBallsPowerUp;
	} // Bunu sor bakalım 	
	
	public boolean getTallerPaddleMessage() {
		return this.tallerPaddleMessage;
	}
	
	public boolean getMagnetMessage() {
		return this.magnetMessage;
	}
	
	public boolean getWrapMessage() {
		return this.wrapMessage;
	}
	
	public void setReleaseBallMessage() {
		this.releaseBallMessage = true;
	}
}
