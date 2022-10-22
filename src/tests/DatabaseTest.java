package tests;


import static org.junit.jupiter.api.Assertions.*;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mongodb.client.MongoDatabase;

import Database.DocumentManager;
import Database.MongoJava;
import Domain.Ball.Ball;
import Domain.Paddle.Paddle;

public class DatabaseTest {

	private MongoDatabase database;
	private DocumentManager documentManager;
	
	@BeforeEach
	void setup() {
		database = MongoJava.getDatabase();
		documentManager = new DocumentManager();
		documentManager.setDatabase(database);
	}
	
	@Test
	void DatabaseConnectionAndAcquisitionTest() {
		assertNotNull(MongoJava.getDatabase());
	}
	
	@Test
	void ConfirmLoginReturnsObjectId() {
		Document testLoginData = new Document("Username", "Abc")
				.append("Password", "123");
		
		database.getCollection("LoginData").insertOne(testLoginData);
		
		assertTrue(documentManager.userLogin("Abc", "123") != null && documentManager.userLogin("Abc", "123") instanceof ObjectId);
		
		database.getCollection("LoginData").deleteOne(testLoginData);
	}
	
	@Test
	void ConfirmRegisterErrorWhenDuplicate() {
		Document testLoginData = new Document("Username", "Abc")
				.append("Password", "123");
		
		database.getCollection("LoginData").insertOne(testLoginData);
		
		assertTrue(documentManager.registerLoginData("Abc", "123").equals("A user with this username already exists"));
		
		database.getCollection("LoginData").deleteOne(testLoginData);
	}
	
	@Test
	void ConfirmRegisterFunction() {
		assertTrue(documentManager.registerLoginData("Abc", "123").equals("Registered successfully"));
		
		Document testLoginData = new Document("Username", "Abc")
				.append("Password", 123);
		
		database.getCollection("LoginData").deleteOne(testLoginData);
	}
	
	@Test
	void ConfirmSaveGameFunction() {
		Paddle paddle = new Paddle();
		Ball ball = new Ball();
		int lives = 3;
		boolean cbh = false;
		int score = 0;
		int total_time = 0;
		ObjectId userId = documentManager.userLogin("Abc", "123");
		assertTrue(documentManager.addSavegame(paddle, ball, userId, lives, cbh, score, total_time) != null && documentManager.addSavegame(paddle, ball, userId, lives, cbh, score, total_time) instanceof ObjectId);
		
		Document testLoginData = new Document("Username", "Abc")
				.append("Password", "123");
		database.getCollection("LoginData").deleteOne(testLoginData);
	}
}
