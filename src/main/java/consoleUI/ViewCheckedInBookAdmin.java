package consoleUI;

import dao.BookDAO;
import dao.TransactionDAO;
import dao.UserDAO;
import entity.Book;
import entity.User;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ViewCheckedInBookAdmin {

    // Lets the admin view all the checkedin books and checkout books by title and user.
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
    // Checks to see that all the checked in books.
    private static void viewCheckInsBook(Scanner sc, User user) {
        List<Book> books =  BookDAO.viewChecksBook(false);
        for (Book b : books) {
            System.out.println(b);
        }

    }

    // Allows the user to checkout a book
    private static void viewCheckInsBookByFilter(Scanner sc, User user) {
        System.out.println("Enter book title you want to check Out:");
        String title = sc.nextLine();
        List<Book> books =  BookDAO.viewChecksBookUsingFilter(title);
        if (!books.isEmpty()){
            for (Book book : books) {
                System.out.println(book);
            }
            List<User> usersObj = UserDAO.getAllUsers();
            List<String> userName = new ArrayList<>();
            for (User u: usersObj){
                userName.add(u.getUsername());
            }
            System.out.println("Enter a book number to check out a book or (q) to quit:");
            while (true){
                try {
                    String in = sc.nextLine();
                    if (in.equals("q")){
                        break;
                    }
                    int input = Integer.parseInt(in);
                    System.out.println("Enter a number to choose user: " + userName);
                    in = sc.nextLine();
                    int userInput = Integer.parseInt(in);
                    if (TransactionDAO.addTransaction(usersObj.get(userInput-1).getUserID(), books.get(input-1).getBookTitle()))
                        System.out.println("Successfully checked out: " + books.get(input-1).getBookTitle());
                    else
                        System.out.println("Check out failed");
                    //TransactionDAO.checkInTransaction(books.get(input-1).getTransactionId());
                    break;
                } catch (Exception e){
                    System.out.println("try a number from 1");
                }
            }

        }
        else {
            System.out.println("That book is not currently available");
        }

    }
}
