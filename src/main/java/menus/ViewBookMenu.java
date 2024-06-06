package menus;

import documents.User;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ViewBookMenu {
    public static void run(Scanner sc, User user) {
        while (true) {
            System.out.println("1. View all books");
            System.out.println("2. View by genre");
            System.out.println("3. View by author");
            System.out.println("4. Return");

            try {
                int input = sc.nextInt();
                sc.nextLine();

                switch (input) {
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:
                        break;
                    case 4:
                        return;
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
