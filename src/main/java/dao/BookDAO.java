package dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import entity.Book;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class BookDAO {
    static private MongoCollection<Document> collection;
    // Initiate the collection based on collection passed
    public static void BookDAOInit(MongoCollection<Document> collect){
        collection = collect;
    }

    // Add book to database
    public static void addBook(String title, String author, String description, List<String> genres){
        if (collection != null){
            Document book = new Document("title",title)
                    .append("author", author)
                    .append("description", description)
                    .append("genres", genres)
                    .append("checkedOut", false);
            collection.insertOne(book);
        } else {
            System.out.println("Initialize Database");
        }
    }

    // Delete book from database
    public static void deleteBook(String id){
        if (collection != null){
            collection.deleteOne(new Document("_id", new ObjectId(id)));
        } else {
            System.out.println("Initialize Database");
        }
    }
    // checkout book by id
    public static void checkOutBook(String id){
        if (collection != null){
            collection.updateOne(eq("_id", new ObjectId(id)), set("checkedOut", true));
        }else {
            System.out.println("Initialize Database");
        }
    }
    // check in book by id
    public static void checkInBook(String id){
        if (collection != null){
            collection.updateOne(eq("_id", new ObjectId(id)), set("checkedOut", false));
        }else {
            System.out.println("Initialize Database");
        }
    }
    // Returns book(s) based on filter or title, author, or genre(s)
    public static List<Book> getBookUsingFilter(int mode, String name){
        if (collection != null){
            List<Book> books = new ArrayList<>();
            String queryType = switch (mode) {
                case 1 -> "title";
                case 2 -> "author";
                case 3 -> "genres";
                default -> "no";
            };
            Document query;
            if (queryType.equals("genres")) {
                List<Pattern> regexPatterns = new ArrayList<>();
                for (String genre : name.split(" ")) {
                    // Create a case-insensitive regex pattern for each genre
                    Pattern pattern = Pattern.compile(genre, Pattern.CASE_INSENSITIVE);
                    regexPatterns.add(pattern);
                }

                // Construct the MongoDB query using the $all operator and the regex patterns
                query = new Document("genres", new Document("$all", regexPatterns));
            } else {
                // use optional case-insensitive on query
                query = new Document(queryType, new Document("$regex", name).append("$options", "i"));
            }

            try (MongoCursor<Document> cursor = collection.find(query).iterator()){
                // get values from document and pass it into book
                while (cursor.hasNext()){
                    Document doc = cursor.next();
                    ObjectId id = doc.getObjectId("_id");
                    String title = doc.getString("title");
                    String author = doc.getString("author");
                    String description = doc.getString("description");
                    List<String> genres = doc.getList("genres", String.class);
                    Boolean checkedOut = doc.getBoolean("checkedOut");
                    books.add(new Book(id.toHexString(), title, author, description, genres,checkedOut));
                }
            }
            return books;
        } else {
            System.out.println("Initialize Database");
            return null;
        }
    }

    // Return all books
    public static List<Book>  getBooks(){
        if (collection != null){
            List<Book> books = new ArrayList<>();
            // Integrate through the database and get all blogs
            try (MongoCursor<Document> cursor = collection.find().iterator()) {
                while (cursor.hasNext()) {
                    Document doc = cursor.next();
                        ObjectId id = doc.getObjectId("_id");
                        String title = doc.getString("title");
                        String author = doc.getString("author");
                        String description = doc.getString("description");
                        List<String> genres = doc.getList("genres", String.class);
                        Boolean checkedOut = doc.getBoolean("checkedOut");
                        books.add(new Book(id.toHexString(), title, author, description, genres,checkedOut));
                }
            }
            return books;
        } else {
            System.out.println("Initialize Database");
            return null;
        }
    }

    // View all current checkedOut books if true, else view all current Available books
    public static List<Book> viewChecksBook(boolean bool){
        if (collection != null){
            List<Book> books = new ArrayList<>();
            Document query = new Document("checkedOut", bool);
            // Integrate through the database and get all books that are checked out
            try (MongoCursor<Document> cursor = collection.find(query).iterator()){
                while (cursor.hasNext()){
                    Document doc = cursor.next();
                    ObjectId id = doc.getObjectId("_id");
                    String title = doc.getString("title");
                    String author = doc.getString("author");
                    String description = doc.getString("description");
                    List<String> genres = doc.getList("genres", String.class);
                    Boolean checkedOut = doc.getBoolean("checkedOut");
                    books.add(new Book(id.toHexString(), title, author, description, genres,checkedOut));

                }
            }
            return books;
        }
        else {
            System.out.println("Initialize Database");
            return null;
        }
    }

    // Gives a list of books based on the title.
    public static List<Book> viewChecksBookUsingFilter(String titleFilter){
        if (collection != null){
            Document query = new Document("checkedOut", false);
            FindIterable<Document> result = collection.find(query);
            ArrayList<Book> books = new ArrayList<>();
            // Integrate through the database and get all books that are checked out
            try (MongoCursor<Document> cursor = collection.find(query).iterator()){
                while (cursor.hasNext()){
                    Document doc = cursor.next();
                    String title = doc.getString("title");
                    if (title.equals(titleFilter)){
                        ObjectId id = doc.getObjectId("_id");
                        String author = doc.getString("author");
                        String description = doc.getString("description");
                        List<String> genres = doc.getList("genres", String.class);
                        Boolean checkedOut = doc.getBoolean("checkedOut");
                        books.add(new Book(id.toHexString(), title, author, description, genres,checkedOut));
                    }

                }
            }
            return books;
        }
        else {
            System.out.println("Initialize Database");
            return null;
        }
    }

    // Return books by the id ( Useful for looking for books current user checked out )
    public static Book getBookById(String id){
        if (collection != null){
            Document query = new Document("_id", new ObjectId(id));
            Document doc = collection.find(query).first();
            if (doc == null) {
                System.out.println("No Book of that Id");
                return null;
            }
            String title = doc.getString("title");
            String author = doc.getString("author");
            String description = doc.getString("description");
            List<String> genres = doc.getList("genres", String.class);
            Boolean checkedOut = doc.getBoolean("checkedOut");
            return new Book(id, title, author, description, genres,checkedOut);
        }
        else {
            System.out.println("Initialize Database");
            return null;
        }
    }

}
