package consoleUI;

import dao.BookDAO;
import dao.TransactionDAO;
import entity.Book;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class DeleteBookAdmin {
    public static void run(Scanner sc){
        while(true){
            System.out.println("""
                    1.
                    2. Delete a book
                    3. Exit
                    """);
            try {
                int input = sc.nextInt();
                sc.nextLine();

                switch (input) {
                    case 1:
                        DeleteBook(sc);
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

    private static void DeleteBook(Scanner sc){
        System.out.println("Enter book title you want to check Out:");
        String title = sc.nextLine();
        List<Book> books =  BookDAO.viewChecksBookUsingFilter(title);
        if (!books.isEmpty()) {
            for (Book book : books) {
                System.out.println(book);
            }
            System.out.println("Enter a book number to delete a book or (q) to quit:");
            while (true){
                try {
                    String in = sc.nextLine();
                    if (in.equals("q")){
                        break;
                    }
                    int input = Integer.parseInt(in);
                    BookDAO.deleteBook(books.get(input-1).getBookId());
                    System.out.println("Successfully deleted a book");
                    break;
                } catch (Exception e){
                    System.out.println("try a number from 1");
                }
            }
        }
        else {
            System.out.println("That book isn't available or not checked in for deletion ");
        }
    }
}
