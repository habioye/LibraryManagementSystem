package dao;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import entity.Book;

public class BookDAO {
    static private MongoCollection<Document> collection;

    public static void BookDAOInit(MongoCollection<Document> collect) {
        collection = collect;
    }

    public static void addBook(String title, String author, String description, List<String> genres) {
        if (collection != null) {
            Document book = new Document("title", title)
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

    public static void deleteBook(String id) {
        if (collection != null) {
            collection.deleteOne(new Document("_id", new ObjectId(id)));
        } else {
            System.out.println("Initialize Database");
        }
    }

    public static void checkedOutBook(String id) {
        if (collection != null) {
            collection.updateOne(eq("_id", id), new Document("$set", new Document("checkedOut", true)));
        } else {
            System.out.println("Initialize Database");
        }
    }

    public static void checkedInBook(String id) {
        if (collection != null) {
            collection.updateOne(eq("_id", id), new Document("$set", new Document("checkedOut", false)));
        } else {
            System.out.println("Initialize Database");
        }
    }

    public static List<Book> getBookUsingFilter(int mode, String name) {
        if (collection != null) {
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
                query = new Document(queryType, new Document("$regex", name).append("$options", "i"));
            }

            try (MongoCursor<Document> cursor = collection.find(query).iterator()) {
                while (cursor.hasNext()) {
                    Document doc = cursor.next();
                    ObjectId id = doc.getObjectId("_id");
                    String title = doc.getString("title");
                    String author = doc.getString("author");
                    String description = doc.getString("description");
                    List<String> genres = doc.getList("genres", String.class);
                    Boolean checkedOut = doc.getBoolean("checkedOut");
                    String currentTransactionId = doc.getString("currentTransactionId");
                    books.add(new Book(id.toHexString(), title, author, description, genres, checkedOut, currentTransactionId));
                }
            }
            return books;
        } else {
            System.out.println("Initialize Database");
            return null;
        }
    }

    public static List<Book> getBooks() {
        if (collection != null) {
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
                    String currentTransactionId = doc.getString("currentTransactionId");
                    books.add(new Book(id.toHexString(), title, author, description, genres, checkedOut, currentTransactionId));
                }
            }
            return books;
        } else {
            return null;
        }
    }

    // TODO test this
    public static Book getBookByTitle(String title) {
        if (collection != null) {
            Document filter = new Document("title", title);
            Document result = collection.find(filter).first();

            if (result == null) {
                System.out.println("No results found");
                return null;
            }

            ObjectId id = result.getObjectId("_id");
            String author = result.getString("author");
            String description = result.getString("description");
            List<String> genres = result.getList("genres", String.class);
            Boolean checkedOut = result.getBoolean("checkedOut");
            String currentTransactionId = result.getString("currentTransactionId");

            return new Book(id.toHexString(), title, author, description, genres, checkedOut, currentTransactionId);
        }

        return null;
    }

}
