package menus;

import java.util.InputMismatchException;
import java.util.Scanner;

import documents.User;

public class MainMenu {

    // Menu for an admin
    public static void adminMenu(Scanner sc, User user) {

        // TODO implement all menu features
        System.out.println("===ADMIN MODE===");
        System.out.println("Welcome " + user.getUsername());
        while (true) {
            System.out.println("1. View books");
            System.out.println("2. View all checkouts");
            System.out.println("3. Check in a book");
            System.out.println("4. Logout");

            try {
                int input = sc.nextInt();
                sc.nextLine();

                switch (input) {
                    case 1:
                        System.out.println("To be implemented");
                        break;
                    case 2:
                        System.out.println("To be implemented");
                        break;
                    case 3:
                        System.out.println("To be implemented");
                        break;
                    case 4:
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


    // Menu for a user
    public static void userMenu(Scanner sc, User user) {

        System.out.println("Welcome " + user.getUsername());
        while (true) {
            System.out.println("1. View books");
            System.out.println("2. View your checkouts");
            System.out.println("3. Check out a book");
            System.out.println("4. Check in a book");
            System.out.println("5. Logout");

            try {
                int input = sc.nextInt();
                sc.nextLine();

                switch (input) {
                    case 1:
                        System.out.println("To be implemented");
                        break;
                    case 2:
                        System.out.println("To be implemented");
                        break;
                    case 3:
                        System.out.println("To be implemented");
                        break;
                    case 4:
                        System.out.println("To be implemented");
                        break;
                    case 5:
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

    public static void run(Scanner sc, User user) {

        if (user.getRole().equals("admin"))
            adminMenu(sc, user);
        else
            userMenu(sc, user);

    }
}
