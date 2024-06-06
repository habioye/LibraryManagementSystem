package menus;

import doas.BookDAO;
import documents.Book;
import documents.User;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ViewBookMenu {

    private static final String COLLECTION_NAME = "BookTest";

    // TODO
    private static void viewAllBooksMenu(Scanner sc, User user) {

        ArrayList<Book> books = (ArrayList<Book>) BookDAO.getBooks();

        for (Book b : books) {
            System.out.println(b);
        }

    }

    private static void viewBookMenu(Scanner sc, int menuInput) {

        int mode = 0;
        switch (menuInput) {
            case 2:
                System.out.println("Enter the book title you're searching for:");
                mode = 1;
                break;
            case 3:
                System.out.println("Enter the author you're searching for:");
                mode = 2;
                break;
            case 4:
                System.out.println("Enter the genres you're searching for, separated by spaces:");
                mode = 3;
                break;
        }

        String input = sc.nextLine().trim();
        ArrayList<Book> books = (ArrayList<Book>) BookDAO.getBookUsingFilter(mode, input);

        for (Book b : books) {
            System.out.println(b);
        }
    }

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
