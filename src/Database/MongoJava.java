package Database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class MongoJava {

    public static MongoDatabase getDatabase() {
    	
        try {
            final MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
            final MongoDatabase database = mongoClient.getDatabase("OuterSpaceTest");
            System.out.println("Successful database connection established. \n");
            return database;
        } catch (Exception exception) {
            System.err.println(exception.getClass().getName() + ": " + exception.getMessage());
            return null;
        }
    }
}