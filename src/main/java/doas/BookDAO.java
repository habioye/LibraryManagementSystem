package doas;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import documents.Book;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookDAO {
    static private MongoCollection<Document> collection;
    public static void BookDAOInit(MongoCollection<Document> collect){
        collection = collect;
    }

    public static void addBook(String title, String author, String description, List<String> genres){
        if (collection != null){
            Document book = new Document("title",title)
                    .append("author", author)
                    .append("description", description)
                    .append("genres", genres)
                    .append("checkedOut", false)
                    .append("currentTransactionId", "");
            collection.insertOne(book);
        } else {
            System.out.println("Initialize Database");
        }
    }
    public static List<Book> getBookUsingFilter(int mode, String name){
        if (collection != null){
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
                query = new Document(queryType, new Document("$regex", name).append("$options", "i"));
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
        else {
            System.out.println("Initialize Database");
            return null;
        }
    }

    public static List<Book>  getBooks(){
        if (collection != null){
            List<Book> books = new ArrayList<>();
            // Integrate through the database and get all blogs
            try (MongoCursor<Document> cursor = collection.find().iterator()){
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
        else {
            return null;
        }
    }

}
