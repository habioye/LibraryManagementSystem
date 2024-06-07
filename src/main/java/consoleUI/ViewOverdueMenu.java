package consoleUI;

import dao.BookDAO;
import dao.TransactionDAO;
import entity.Book;
import entity.User;

import java.util.ArrayList;
import java.util.Scanner;

public class ViewOverdueMenu {
    // Users can view the books that are overdue.
    public static void run(Scanner sc, User user) {

        ArrayList<String> overdueIds = TransactionDAO.getOverdueBooksByUserID(user.getUserID());
        ArrayList<Book> overdueBooks = new ArrayList<>();

        if (overdueIds == null || overdueIds.isEmpty()) {
            System.out.println("No overdue books found");
        } else {

            for (String id : overdueIds) {
                overdueBooks.add(BookDAO.getBookById(id));
            }

            for (Book b : overdueBooks) {
                System.out.println(b);
            }
        }

        System.out.println("Hit enter to return");
        sc.nextLine();
    }

}
