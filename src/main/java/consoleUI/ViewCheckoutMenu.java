package consoleUI;

import dao.TransactionDAO;
import entity.Transaction;
import entity.User;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ViewCheckoutMenu {

    public static void run(Scanner sc, User user) {

        ArrayList<Transaction> transactions = TransactionDAO.getTransactionsByUserId(user.getUserID());

        if (transactions == null || transactions.isEmpty()) {
            System.out.println("No checkouts found");
        } else {
            for (Transaction t : transactions) {
                System.out.println(t);
            }
        }

        System.out.println("Hit enter to return");
        sc.nextLine();
    }

}
