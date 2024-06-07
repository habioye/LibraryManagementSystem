package consoleUI;

import dao.BookDAO;
import dao.TransactionDAO;
import entity.Book;
import entity.Transaction;
import entity.User;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CheckinBookMenu {

    public static void run(Scanner sc, User user) {

        // Get and print the user's checked out books
        ArrayList<Transaction> transactions = TransactionDAO.getTransactionsByUserId(user.getUserID());
        ArrayList<Book> books = new ArrayList<>();
        for (Transaction t : transactions) {
            String bookId = t.getBookId();
            books.add(BookDAO.getBookById(bookId));
        }

        if (books == null || books.isEmpty()) {
            System.out.println("No checkouts found");
        } else {
            int index = 0;
            for (Book b : books) {
                System.out.println(index + 1 + ". ");  // Print adjusted index for user to use
                System.out.println(b);
                index++;
            }
        }

        // Prompt user for book to check in
        try {
            System.out.println("Enter book number to check in");
            int input = sc.nextInt();
            sc.nextLine();
            input -= 1;   // Adjust user's input to 0 based index

            // Get the book and its transaction
            Book book = books.get(input);
            //Transaction transaction = TransactionDAO.getTransactionsByUserId();



        } catch (InputMismatchException e) {
            System.out.println("Invalid input");
            sc.nextLine(); // Clear invalid input
        } catch (Exception e) {
            e.printStackTrace();

        }

        System.out.println("Hit enter to return");
        sc.nextLine();
    }

}
