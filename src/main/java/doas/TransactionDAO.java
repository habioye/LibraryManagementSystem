package doas;

import com.mongodb.client.MongoCollection;
import database.DBConnection;
import documents.Book;
import org.bson.Document;

public class BookDAO {
    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
    MongoDatabase database = mongoClient.getDatabase("LibraryManagement");
    MOngoCollection<Doucment> collection = dtabase.getCollections("Transaction")
    MongoCollection<Document> collection;

    //    public BookDAO(String collection){
//        this.collection = new DBConnection().getCollection(collection);
//    }
//
//    public void addBook(){
//
//    }
//    public Book getBook(){
//        return null;
//    }
    public static void getTransaction(MongoCollection<Document> collection, String username) { // TODO: make sure that the most recent one comes first.
        Document filter = new Document("username", username);
        FindIterable<Document> result = collection.find(filter);
        String transactionID, userID, bookID, checkoutDate, dueDate;
        boolean checkedOut;

        for (var doc : result) {
            transactionID = doc.getString("_id");
            userID = doc.getString("transactionID");
            bookID = doc.getString("transactionID");
            checkedoutDate = doc.getString("transactionID");
            dueDate = doc.getString("dueDate");
            checkedOut = doc.getBoolean("checkOUt")

            Transaction trans = new Transaction(transactionID,userID,bookID,checkoutDate,dueDate,checkedOut);
            System.out.println(trans.toString());

        }

    }
    public static void adminGetTransaction(MongoCollection<Document> collection, String )

}
