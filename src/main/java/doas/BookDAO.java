package doas;

import com.mongodb.client.MongoCollection;
import database.DBConnection;
import documents.Book;
import org.bson.Document;

public class BookDAO {
    MongoCollection<Document> collection;
    public BookDAO(String collection){
        this.collection = new DBConnection().getCollection(collection);
    }

    public void addBook(){

    }
    public Book getBook(){
        return null;
    }

}
