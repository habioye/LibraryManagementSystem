package consoleUI;

import dao.BookDAO;
import dao.TransactionDAO;
import entity.Book;
import entity.Transaction;
import entity.User;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import dao.TransactionDAO;
import entity.Transaction;
import entity.User;

public class MainMenu {

    // Menu for an admin
    public static void adminMenu(Scanner sc, User user) {

        // TODO implement all menu features
        System.out.println("===ADMIN MODE===");
        System.out.println("Welcome " + user.getUsername());
        while (true) {
            System.out.println("1. View books");
            System.out.println("2. View and Check in Book(s)");
            System.out.println("3. View and Check out Book(s)");
            System.out.println("4. Add Book(s)");
            System.out.println("5. Delete Book(s)");
            System.out.println("6. Transaction History");
            System.out.println("7. Logout");

            try {
                int input = sc.nextInt();
                sc.nextLine();

                switch (input) {
                    case 1:
                        ViewBookMenu.run(sc, user);
                        break;
                    case 2:
                        ViewCheckedOutBookAdmin.run(sc, user);
                        break;
                    case 3:
                        ViewCheckedInBookAdmin.run(sc,user);
                        break;
                    case 4:
                        AddBookAdmin.run(sc);
                        break;
                    case 5:
                        DeleteBookAdmin.run(sc);
                        break;
                    case 6:
                        for (Transaction t: TransactionDAO.getAllTransactions()){
                            System.out.println(t);
                        }
                        break;
                    case 7:
                        return;  // Return to login menu
                    default:
                        System.out.println("Invalid input");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
                sc.nextLine(); // Clear invalid input
            } catch (Exception e) {
                e.printStackTrace();

            }

        }
    }

    // Menu for a user
    public static void userMenu(Scanner sc, User user) {

        System.out.println("Welcome " + user.getUsername());
        while (true) {
            System.out.println("1. View books");
            System.out.println("2. View your checkout history");
            System.out.println("3. View your overdue books");
            System.out.println("4. Check out a book");
            System.out.println("5. Check in a book");
            System.out.println("6. Logout");

            try {
                int input = sc.nextInt();
                sc.nextLine();

                switch (input) {
                    case 1:
                        ViewBookMenu.run(sc, user);
                        break;
                    case 2:
                        ViewCheckoutMenu.run(sc, user);
                        break;
                    case 3:
                        ViewOverdueMenu.run(sc, user);
                        break;
                    case 4:
                        CheckoutBookMenu.run(sc, user);
                        break;
                    case 5:
                        CheckinBookMenu.run(sc, user);
                        break;
                    case 6:
                        return; // Return to login menu
                    default:
                        System.out.println("Invalid input");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
                sc.nextLine(); // Clear invalid input
            } catch (Exception e) {
                e.printStackTrace();

            }

        }
    }

    // switches between the admin and the user.
    public static void run(Scanner sc, User user) {

        if (user.getRole().equals("admin"))
            adminMenu(sc, user);
        else
            userMenu(sc, user);

    }
}
