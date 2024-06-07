//package consoleUI;
//
//import java.util.InputMismatchException;
//import java.util.Scanner;
//
//import dao.BookDAO;
//import dao.TransactionDAO;
//import entity.Book;
//import entity.User;
//
//public class CheckoutBookMenu {
//
//    public static void run(Scanner sc, User user) {
//
//        System.out.println("Enter title of the book:");
//        try {
//            String input = sc.nextLine().trim();
//            Book book = BookDAO.getBookByTitle(input);
//
//            if (book == null)
//                return;  // Return to main menu
//
//            if (TransactionDAO.addTransaction(user.getUserID(), book.getBookTitle()))
//                System.out.println("Successfully checked out: " + book.getBookTitle());
//            else
//                System.out.println("Check out failed");
//
//
//        } catch (InputMismatchException e) {
//            System.out.println("Invalid input");
//            sc.nextLine();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }
//}
