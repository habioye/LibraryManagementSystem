
package consoleUI;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import dao.BookDAO;
import dao.TransactionDAO;
import entity.Book;
import entity.User;

public class CheckoutBookMenu {

    public static void run(Scanner sc, User user) {

        System.out.println("Enter title of the book:");
        try {
            String input = sc.nextLine().trim();
            Book book = BookDAO.getBookUsingFilter(1, input).getFirst();

            if (book == null)
                return;  // Return to main menu

            if (TransactionDAO.addTransaction(user.getUserID(), book.getBookTitle()))
                System.out.println("Successfully checked out: " + book.getBookTitle());
            else
                System.out.println("Check out failed");

            System.out.println("Hit enter to return");
            sc.nextLine();

        } catch (InputMismatchException e) {
            System.out.println("Invalid input");
            sc.nextLine();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

