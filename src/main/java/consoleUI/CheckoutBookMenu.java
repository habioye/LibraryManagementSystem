package consoleUI;

import entity.User;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CheckoutBookMenu {

    public static void run(Scanner sc, User user) {
        while (true) {
            System.out.println("Enter title of the book:");

            try {
                String input = sc.nextLine();




            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
                sc.nextLine();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
