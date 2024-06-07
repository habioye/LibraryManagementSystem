package consoleUI;

import dao.BookDAO;
import dao.TransactionDAO;
import dao.UserDAO;
import entity.Transaction;
import entity.User;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ViewCheckedOutBookAdmin {

    // Allows the admin to view the checked out books and to check in books by title.
    public static void run(Scanner sc, User user){
        while(true){
            System.out.println("""
                    1. View all checkouts books
                    2. Check in books by title
                    3. Exit
                    """);
            try {
                int input = sc.nextInt();
                sc.nextLine();

                switch (input) {
                    case 1:
                        viewAllCheckOutsBooksMenu(sc, user);
                        break;
                    case 2:
                        viewCheckOutsBookByFilter(sc,user);
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
    // Allows the user to view all checkedout books.
    private static void viewAllCheckOutsBooksMenu(Scanner sc, User user) {
        List<Transaction> transactions =  TransactionDAO.getCheckOutTransactions();
        for (Transaction b : transactions) {
            System.out.println("Checked Out By: " + UserDAO.getUserByID(b.getUserId()).getUsername());
            System.out.println(BookDAO.getBookById(b.getBookId()));
        }

    }
    // Allows the user to view the checkedoutbooks.
    private static void viewCheckOutsBookByFilter(Scanner sc, User user) {
        System.out.println("Enter book title you want to check in:");
        String title = sc.nextLine();
        List<Transaction> transactions =  TransactionDAO.viewCheckOutsTransActionUsingTitle(title);
        if (!transactions.isEmpty()){
            for (Transaction transaction : transactions) {
                System.out.println("Checked Out By: " + UserDAO.getUserByID(transaction.getUserId()).getUsername());
                System.out.println(BookDAO.getBookById(transaction.getBookId()));
            }
            System.out.println("Enter a book number to check in book or (q) to quit:");
            while (true){
                try {
                    String in = sc.nextLine();
                    if (in.equals("q")){
                        break;
                    }
                    int input = Integer.parseInt(in);
                    BookDAO.checkInBook(transactions.get(input-1).getBookId());
                    TransactionDAO.checkInTransaction(transactions.get(input-1).getTransactionId());
                    System.out.println("Book checked in");
                    break;
                } catch (Exception e){
                    System.out.println("try a number from 1");
                }
            }

        }

    }
}
