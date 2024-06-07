package consoleUI;

import dao.BookDAO;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AddBookAdmin {
    public static void run(Scanner sc){
        while(true){
            System.out.println("""
                    1. Add a book
                    2. Exit
                    """);
            try {
                int input = sc.nextInt();
                sc.nextLine();

                switch (input) {
                    case 1:
                        AddBook(sc);
                        break;
                    case 2:
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

    private static void AddBook(Scanner sc){
        System.out.println("Enter Book's Title");
        String title = sc.nextLine();
        System.out.println("Enter Book's Author");
        String author = sc.nextLine();
        System.out.println("Enter Book's Description");
        String desc = sc.nextLine();
        System.out.println("Enter Book's genre(s) separated by space");
        String genre= sc.nextLine();
        BookDAO.addBook(title,author,desc, Arrays.asList(genre.split(" ")));
        System.out.println("Successfully added " + title);
    }
}
