package connection;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
public class DBConnection {
    private final String CONNECTIONSTRING = "mongodb://localhost:27017";
    private final String DBNAME = "LibraryManagement";
    private MongoClient mongoClient;
    private MongoDatabase database;

    // return a connection for other classes to utilize
    public DBConnection() {
        mongoClient = MongoClients.create(CONNECTIONSTRING);
        database = mongoClient.getDatabase(DBNAME);

    }

    public MongoCollection<Document> getCollection(String DBCOLLECTION) {
        return database.getCollection(DBCOLLECTION);
    }

    public void close(){
        mongoClient.close();
    }


}


