package Database;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import Domain.Alien.Alien;
import Domain.Asteroid.Asteroid;
import Domain.Ball.Ball;
import Domain.Paddle.Paddle;
import Domain.PowerUp.PowerUp;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

public class DocumentManager {

	private MongoDatabase database = null;
	
	public String registerLoginData(String username, String password) {

		MongoCollection collection = database.getCollection("LoginData");
		
		FindIterable<Document> iterable = collection.find();
        MongoCursor<Document> cursor = iterable.iterator();
        
        while(cursor.hasNext()) {
        	Document currentUser = cursor.next();
        	if(currentUser.get("Username").equals(username)) {
        		return "A user with this username already exists";
        	}
        }
        
		Document loginData = new Document("Username", username)
				.append("Password", password);
		collection.insertOne(loginData);
		
		return "Registered successfully";
	}
	
	public ObjectId userLogin(String username, String password) {
		MongoCollection collection = database.getCollection("LoginData");
		
		FindIterable<Document> iterable = collection.find();
        MongoCursor<Document> cursor = iterable.iterator();
        
        while(cursor.hasNext()) {
        	Document currentUser = cursor.next();
        	if(currentUser.get("Username").equals(username)) {
        		if(currentUser.get("Password").equals(password)) {
        			return (ObjectId) currentUser.get("_id");
        		}
        	}
        }
        return null;
	}
	
	public ObjectId addSavegame(Paddle paddle, Ball ball, ObjectId userId, int lives, boolean coopBeenHit, int score, int total_time) {
		MongoCollection collection = database.getCollection("Savegame");
		Document saveGameDoc = new Document("PaddleLocation", paddle.getLocation())
				.append("BallXVel", ball.getxVelocity())
				.append("BallYVel", ball.getyVelocity())
				.append("BallXLocation", ball.getXLocation())
				.append("BallYLocation", ball.getYLocation())
				.append("UserId", userId)
				.append("Lives", lives)
				.append("CoopBeenHit", coopBeenHit)
				.append("Score", score)
				.append("Timer", total_time);
		collection.insertOne(saveGameDoc);
		
		return (ObjectId)saveGameDoc.get( "_id" );
	}
	
	public void updateSavegame(Paddle paddle, Ball ball, ObjectId saveGameId, int lives, ObjectId userId, boolean coopBeenHit, int score, int total_time) {
		MongoCollection collection = database.getCollection("Savegame");
		collection.deleteOne(Filters.eq("_id", saveGameId));
		collection.insertOne(new Document("_id", saveGameId)
				.append("PaddleLocation", paddle.getLocation())
				.append("BallXLocation", ball.getXLocation())
				.append("BallYLocation", ball.getYLocation())
				.append("BallXVel", ball.getxVelocity())
				.append("BallYVel", ball.getyVelocity())
				.append("Lives", lives)
				.append("UserId", userId)
				.append("CoopBeenHit", coopBeenHit)
				.append("Score", score)
				.append("Timer", total_time)
		);
				
	}
	
	public void addAsteroids(Asteroid[] asteroids, int firmIndex, int explosiveIndex, int giftIndex, ObjectId saveGameId, boolean[] asteroidHasMove, int[] radiiOfFirm, int[] actXLoc, int[] actYLoc, boolean[] freezeStatuses, String[] gifts) {
		if(asteroids.length > 0) {
			MongoCollection collection = database.getCollection("Asteroids");
			List documents = new ArrayList();

			for(int i = 0; i < asteroids.length; i++) {
				if(asteroids[i] != null) {
					if(i < firmIndex) {
						documents.add(new Document("xLocation", asteroids[i].getXLoc())
								.append("yLocation", asteroids[i].getYLoc())
								.append("AsteroidType", "Simple")
								.append("Savegame", saveGameId)
								.append("AsteroidIndex", i)
								.append("HasMove", asteroidHasMove[i])
								.append("xLocationNoMove", actXLoc[i])
								.append("FreezeStatus", freezeStatuses[i])
							);
					} else if (i >= firmIndex && i < explosiveIndex) {
						documents.add(new Document("xLocation", asteroids[i].getXLoc())
								.append("yLocation", asteroids[i].getYLoc())
								.append("AsteroidType", "Firm")
								.append("Savegame", saveGameId)
								.append("AsteroidIndex", i)
								.append("HasMove", asteroidHasMove[i])
								.append("Radius", radiiOfFirm[i-firmIndex])
								.append("xLocationNoMove", actXLoc[i])
								.append("FreezeStatus", freezeStatuses[i])
							);
					} else if (i >= explosiveIndex && i < giftIndex) {
						documents.add(new Document("xLocation", asteroids[i].getXLoc())
								.append("yLocation", asteroids[i].getYLoc())
								.append("AsteroidType", "Explosive")
								.append("Savegame", saveGameId)
								.append("AsteroidIndex", i)
								.append("HasMove", asteroidHasMove[i])
								.append("xLocationNoMove", actXLoc[i])
								.append("yLocationNoMove", actYLoc[i])
								.append("FreezeStatus", freezeStatuses[i])
							);
					} else {
						documents.add(new Document("xLocation", asteroids[i].getXLoc())
								.append("yLocation", asteroids[i].getYLoc())
								.append("AsteroidType", "Gift")
								.append("Savegame", saveGameId)
								.append("AsteroidIndex", i)
								.append("FreezeStatus", freezeStatuses[i])
								.append("Gifts", gifts[i-giftIndex])
							);
					}
				}
				
			}
			collection.insertMany(documents);
		}
		
	}
	
	public void updateAsteroids(Asteroid[] asteroids, ObjectId saveGameId, boolean[] asteroidHasMove) {
		MongoCollection collection = database.getCollection("Asteroids");
		
		for(int i = 0; i < asteroids.length; i++) {
			if(asteroids[i] == null) {
				collection.deleteOne(Filters.and(Filters.eq("AsteroidIndex", i), (Filters.eq("Savegame", saveGameId))));
			} else {
				Document newAsteroidParameter = new Document("xLocation", asteroids[i].getXLoc())
						.append("yLocation", asteroids[i].getYLoc());
				
				Document newAsteroid = new Document();
				newAsteroid.append("$set", newAsteroidParameter);
				
				Document oldAsteroid = new Document("AsteroidIndex", i)
						.append("Savegame", saveGameId);
				
				
			
				collection.findOneAndUpdate(oldAsteroid, newAsteroid);
			}
		}
	}
	
	public void addAliens(List<Alien> aliens, ObjectId saveGameId) {
		if(aliens.size() > 0) {
			MongoCollection collection = database.getCollection("ActiveGifts");
			List documents = new ArrayList();
			
			for(int i = 0; i < aliens.size(); i++) {
				if(aliens.get(i) != null) {
					documents.add(new Document("AlienType", aliens.get(i).getType())
						.append("xLoc", aliens.get(i).getXLoc())
						.append("yLoc", aliens.get(i).getYLoc())
						.append("Savegame", saveGameId)
						.append("AlienIndex", i)
						.append("Type", "Alien")
					);
				}
			}
			collection.insertMany(documents);
		}
	}
	
	public void updateAliens(List<Alien> aliens, ObjectId saveGameId) {
		MongoCollection collection = database.getCollection("ActiveGifts");
		
		for(int i = 0; i < aliens.size(); i++) {
			if(aliens.get(i) == null) {
				collection.deleteOne(Filters.and(Filters.eq("AlienIndex", i), (Filters.eq("Savegame", saveGameId))));
			} else {
				String type = aliens.get(i).getType();
				Document newAlienParameter = new Document("xLoc", aliens.get(i).getXLoc())
						.append("yLoc", aliens.get(i).getYLoc());
				
				Document newAsteroid = new Document();
				newAsteroid.append("$set", newAlienParameter);
				
				Document oldAsteroid = new Document("AlienIndex", i)
						.append("Savegame", saveGameId);
				
				collection.findOneAndUpdate(oldAsteroid, newAsteroid);
			}
		}
	}
	
	public void addPowerUps(List<PowerUp> powerUps, ObjectId saveGameId) {
		if(powerUps.size() > 0) {
			MongoCollection collection = database.getCollection("ActiveGifts");
			List documents = new ArrayList();
			
			for(int i = 0; i < powerUps.size(); i++) {
				if(powerUps.get(i) != null) {
					documents.add(new Document("xLoc", powerUps.get(i).getxLoc())
						.append("yLoc", powerUps.get(i).getyLoc())
						.append("Savegame", saveGameId)
						.append("PowerUpIndex", i)
						.append("PowerUpType", powerUps.get(i).getType())
						.append("Type", "PowerUp")
						);
				}
			}
			collection.insertMany(documents);
		}
	}
	
	public void updatePowerUps(List<PowerUp> powerUps, ObjectId saveGameId) {
		MongoCollection collection = database.getCollection("ActiveGifts");
		
		for(int i = 0; i < powerUps.size(); i++) {
			if(powerUps.get(i) == null) {
				collection.deleteOne(Filters.and(Filters.eq("PowerUpIndex", i), (Filters.eq("Savegame", saveGameId))));
			} else {
				Document newPowerUpParameter = new Document("xLoc", powerUps.get(i).getxLoc())
						.append("yLoc", powerUps.get(i).getyLoc());
				
				Document newPowerUp = new Document();
				newPowerUp.append("$set", newPowerUpParameter);
				
				Document oldPowerUp = new Document("PowerUpIndex", i)
						.append("Savegame", saveGameId);
				
				collection.findOneAndUpdate(oldPowerUp, newPowerUp);
			}
		}
	}
	public void setDatabase(MongoDatabase database) {
		this.database = database;
	} 
}
