package consoleUI;

import dao.BookDAO;
import entity.Book;
import entity.User;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ViewCheckedOutBookAdmin {

    public static void run(Scanner sc, User user){
        while(true){
            System.out.println("""
                    1. View all checkouts books
                    2. Check in books by title
                    """);
            try {
                int input = sc.nextInt();
                sc.nextLine();

                switch (input) {
                    case 1:
                        viewAllCheckOutsBooksMenu(sc, user);
                        break;
                    case 2:
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
    private static void viewAllCheckOutsBooksMenu(Scanner sc, User user) {
        List<Book> books =  BookDAO.viewAllCheckedOutBook();
        for (Book b : books) {
            System.out.println(b);
        }

    }
    private static void viewCheckOutsBookByFilter(Scanner sc, User user) {
        System.out.println("Enter book title you want to check in:");
        String title = sc.nextLine();
        List<Book> books =  BookDAO.viewCheckOutsBookByTitle(title);
        if (!books.isEmpty()){
            for (Book b : books) {
                System.out.println(b);
            }
            System.out.println("Enter a book number to check in book:");
            while (true){
                try {
                    int input = Integer.parseInt(sc.nextLine());
                    BookDAO.checkInBook();
                } catch (Exception e){
                    System.out.println("try a number from 1");
                }
            }

        }

    }
}
