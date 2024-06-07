package consoleUI;

import dao.BookDAO;
import dao.TransactionDAO;
import entity.Book;
import entity.Transaction;
import entity.User;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ViewCheckedInBookAdmin {

    public static void run(Scanner sc, User user){
        while(true){
            System.out.println("""
                    1. View all checkins books
                    2. Check out books by title and user
                    3. Exit
                    """);
            try {
                int input = sc.nextInt();
                sc.nextLine();

                switch (input) {
                    case 1:
                        viewCheckInsBook(sc, user);
                        break;
                    case 2:
                        viewCheckInsBookByFilter(sc,user);
                        break;
                    case 3:
                        return; // Return to main menu
                    default:
                        System.out.println("Invalid input");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
                sc.nextLine();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private static void viewCheckInsBook(Scanner sc, User user) {
        List<Book> books =  BookDAO.viewChecksBook(false);
        for (Book b : books) {
            System.out.println(b);
        }

    }
    private static void viewCheckInsBookByFilter(Scanner sc, User user) {
        System.out.println("Enter book title you want to check Out:");
        String title = sc.nextLine();
        List<Book> books =  BookDAO.viewChecksBookUsingFilter(title);
        if (!books.isEmpty()){
            for (Book book : books) {
                System.out.println(book);
            }

            System.out.println("Enter a book number to check in book or (q) to quit:");
            while (true){
                try {
                    String in = sc.nextLine();
                    if (in.equals("q")){
                        break;
                    }
                    int input = Integer.parseInt(in);
                    BookDAO.checkInBook(books.get(input-1).getBookId());
                    TransactionDAO.checkInTransaction(books.get(input-1).getTransactionId());
                    System.out.println("Book checked in");
                    break;
                } catch (Exception e){
                    System.out.println("try a number from 1");
                }
            }

        }

    }
}
