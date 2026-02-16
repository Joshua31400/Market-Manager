package utils;

import java.util.Scanner;

public class InputReader {
    private static InputReader instance;

    private InputReader() {
        this.scanner = new Scanner(System.in);
    }

    public static InputReader getInstance() {
        if (instance == null) {
            instance = new InputReader();
        }
        return instance;
    }

    private final Scanner scanner;

    public String readString() {
        return scanner.nextLine();
    }

    public String readString(String label) {
        System.out.print(label);
        return scanner.nextLine();
    }

    public String readStringHidden() {
        if (System.console() != null) {
            char[] passwordChars = System.console().readPassword();
            System.out.print("\033[1A");
            return new String(passwordChars);
        } else {
            System.out.println("(warning: input visible in IDE)");
            return scanner.nextLine();
        }
    }

    public String readStringHidden(String label) {
        System.out.print(label);
        if (System.console() != null) {
            char[] passwordChars = System.console().readPassword();
            System.out.print("\033[1A");
            return new String(passwordChars);
        } else {
            System.out.println("(warning: input visible in IDE)");
            return scanner.nextLine();
        }
    }

    public int readInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    public int readInt(String label) {
        while (true) {
            System.out.print(label);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    public int readInt(int min, int max) {
        while (true) {
            try {
                int value = Integer.parseInt(scanner.nextLine());
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("Input must be between " + min + " e " + max);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    public int readInt(String label, int min, int max) {
        while (true) {
            System.out.print(label);
            try {
                int value = Integer.parseInt(scanner.nextLine());
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("Input must be between " + min + " and " + max);
                System.out.println();
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
                System.out.println();
            }
        }
    }
}
