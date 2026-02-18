package transaction;

import utils.Colors;
import utils.InputReader;

public class Paypal implements PaymentMethod {
    public double budget() {
        InputReader reader = InputReader.getInstance();
        System.out.println(Colors.primary("Enter paypal infos:"));
        System.out.println();
        String email = reader.readString("Email: ");
        reader.readStringHidden("Password: ");
        System.out.println();

        if (validatePaypalInfos(email)) {
            double budget = 200.0 + (Math.random() * (2000.0 - 200.0));
            System.out.printf(Colors.primary("Available budget: $") + Colors.data("%.2f"), budget);
            System.out.println();
            return budget;
        }else {
            return -1;
        }

    }

    private boolean validatePaypalInfos(String email) {
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            System.out.println(Colors.warning("Invalid email format."));
            return false;
        }
        return true;
    }
}
