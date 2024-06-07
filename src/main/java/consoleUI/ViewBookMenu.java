package consoleUI;

import dao.BookDAO;
import entity.Book;
import entity.User;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ViewBookMenu {

    // Gets all the books in the library
    private static void viewAllBooksMenu(Scanner sc, User user) {

        ArrayList<Book> books = (ArrayList<Book>) BookDAO.getBooks();
        for (Book b : books) {
            System.out.println(b);
        }

    }

    // Lets you search for a specific book besed on the book title, author, or genre.
    private static void viewBookMenu(Scanner sc, int menuInput) {

        int mode = 0;
        switch (menuInput) {
            case 2:
                mode = 1;
                System.out.println("Enter the book title you're searching for:");
                break;
            case 3:
                mode = 2;
                System.out.println("Enter the author you're searching for:");
                break;
            case 4:
                mode = 3;
                System.out.println("Enter the genres you're searching for, separated by spaces:");
                break;
        }

        String input = sc.nextLine().trim();

        ArrayList<Book> books = (ArrayList<Book>) BookDAO.getBookUsingFilter(mode, input);
        for (Book b : books) {
            System.out.println(b);
        }

    }

    //  Presents the main menu for what the user wants when viewing book catalogue
    public static void run(Scanner sc, User user) {
        while (true) {
            System.out.println("1. View all books");
            System.out.println("2. Search by title");
            System.out.println("3. Search by author");
            System.out.println("4. Search by genre");
            System.out.println("5. Return");

            try {
                int input = sc.nextInt();
                sc.nextLine();

                switch (input) {
                    case 1:
                        viewAllBooksMenu(sc, user);
                        break;
                    case 2:
                    case 3:
                    case 4:
                        viewBookMenu(sc, input);
                        break;
                    case 5:
                        return;  // Return to main menu
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
}
