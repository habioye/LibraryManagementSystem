package menus;

import java.util.InputMismatchException;
import java.util.Scanner;

public class LoginMenu {

    private static boolean validateUsername(String username) {
        username = username.trim();
        if (username.contains(" ")) {
            System.out.println("Error: Username contains whitespace");
            return false;
        }

        if (username == null) {
            System.out.println("Error: Invalid username");
            return false;
        }

        return true;
    }

    private static boolean validatePassword(String password) {
        password = password.trim();
        if (password.contains(" ")) {
            System.out.println("Error: Password contains whitespace");
            return false;
        }

        if (password == null) {
            System.out.println("Error: Invalid password");
            return false;
        }

        return true;
    }

    // Prompt user for registration details (username and password)
    private static void registerMenu(Scanner sc) {

        try {
            System.out.println("Username:");
            String username = sc.nextLine();

            System.out.println("Password:");
            String password = sc.nextLine();

            // Check if username and password are valid
            if (!validateUsername(username))
                return;
            if (!validatePassword(password))
                return;

            /* TODO
            if (UserDAO.addUser(username, password))
                System.out.println("Successfully registered");
            else
                System.out.println("Failed to register");
            */
        } catch (InputMismatchException e) {
            System.out.println("Invalid input");
            sc.nextLine();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Prompt user for login credentials
    private static void loginMenu(Scanner sc) {

        try {
            System.out.println("Username:");
            String username = sc.nextLine();

            System.out.println("Password:");
            String password = sc.nextLine();

            // Authenticate and log in
            /* TODO
            if (UserDAO.authenticateUser(username, password)) {
                User user = UserDAO.getUser(username);
                if (user != null) {
                    MainMenu.run(sc, user);
                }

            } else
                System.out.println("Login failed");
             */

        } catch (InputMismatchException e) {
            System.out.println("Invalid input");
            sc.nextLine();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void run() {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("~~~Super Mega Library~~~");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");

            try {
                int input = sc.nextInt();
                sc.nextLine();

                switch (input) {
                    case 1:
                        loginMenu(sc);
                        break;
                    case 2:
                        registerMenu(sc);
                        break;
                    case 3:
                        System.exit(0);
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
