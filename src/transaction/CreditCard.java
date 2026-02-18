package transaction;

import utils.Colors;
import utils.InputReader;

import java.util.Random;

public class CreditCard implements PaymentMethod {
    public double budget() {
        InputReader reader = InputReader.getInstance();
        System.out.println(Colors.primary("Enter credit card infos:"));
        String cardNumber = reader.readString("Card number: ");
        String expirationDate = reader.readString("Expiration date (MM/YY): ");
        String cvv = reader.readString("CVV: ");

        if (validateCardInfos(cardNumber, expirationDate, cvv)) {
            double budget = 500.0 + (Math.random() * (5000.0 - 500.0));
            System.out.printf(Colors.primary("Available budget: $") + Colors.data("%.2f"), budget);
            System.out.println();
            return budget;
        } else {
            return -1;
        }
    }

    private boolean validateCardInfos(String cardNumber, String expirationDate, String cvv) {
        if (cardNumber.length() != 16 || !cardNumber.matches("\\d+")) {
            System.out.println(Colors.error("Invalid card number."));
            return false;
        }
        if (!expirationDate.matches("(0[1-9]|1[0-2])/\\d{2}")) {
            System.out.println(Colors.error("Invalid expiration date format."));
            return false;
        }
        if (cvv.length() != 3 || !cvv.matches("\\d+")) {
            System.out.println(Colors.error("Invalid CVV."));
            return false;
        }
        return true;
    }
}
