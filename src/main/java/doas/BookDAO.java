package doas;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import database.DBConnection;
import documents.Book;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookDAO {
    MongoCollection<Document> collection;
    public BookDAO(String collection){
        this.collection = new DBConnection().getCollection(collection);
    }

    public void addBook(String title, String author, String description, List<String> genres){
        Document book = new Document("title",title)
                .append("author", author)
                .append("description", description)
                .append("genres", genres)
                .append("checkedOut", false)
                .append("currentTransactionId", "");
        collection.insertOne(book);
    }
    public List<Book> getBookUsingFilter(int mode, String name){
        List<Book> books = new ArrayList<>();
        String queryType = switch(mode){
            case 1 -> "title";
            case 2 -> "author";
            case 3 -> "genres";
            default -> "no";
        };
        Document query;
        if (queryType.equals("genres")){
            query = new Document(queryType, new Document("$all", Arrays.asList(name.split(" "))));
        } else {
            query = new Document(queryType, name);
        }

        try (MongoCursor<Document> cursor = collection.find(query).iterator()){
            while (cursor.hasNext()){
                Document doc = cursor.next();
                ObjectId id = doc.getObjectId("_id");
                String title = doc.getString("title");
                String author = doc.getString("author");
                String description = doc.getString("description");
                List<String> genres = doc.getList("genres", String.class);
                Boolean checkedOut = doc.getBoolean("checkedOut");
                String currentTransactionId = doc.getString("currentTransactionId");
                books.add(new Book(id.toHexString(), title, author, description, genres,checkedOut,currentTransactionId));
            }
        }
        return books;
    }

}
