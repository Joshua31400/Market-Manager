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
        Colors.setColor(Colors.CYAN_BOLD);
        String input = scanner.nextLine();
        Colors.resetColor();
        return input;
    }

    public String readString(String label) {
        System.out.print(Colors.inputLabel(label));
        Colors.setColor(Colors.CYAN_BOLD);
        String input = scanner.nextLine();
        Colors.resetColor();
        return input;
    }

    public String readStringHidden() {
        if (System.console() != null) {
            char[] passwordChars = System.console().readPassword();
            System.out.print("\033[1A");
            return new String(passwordChars);
        } else {
            System.out.print(Colors.warning(" (warning: input visible in IDE) "));
            return scanner.nextLine();
        }
    }

    public String readStringHidden(String label) {
        System.out.print(Colors.inputLabel(label));
        if (System.console() != null) {
            char[] passwordChars = System.console().readPassword();
            System.out.print("\033[1A");
            return new String(passwordChars);
        } else {
            System.out.print(Colors.warning(" (warning: input visible in IDE) "));
            return scanner.nextLine();
        }
    }

    public int readInt() {
        while (true) {
            try {
                Colors.setColor(Colors.CYAN_BOLD);
                String value = scanner.nextLine();
                Colors.resetColor();
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.println(Colors.warning("Please enter a valid integer."));
            }
        }
    }

    public int readInt(String label) {
        while (true) {
            System.out.print(Colors.inputLabel(label));
            try {
                Colors.setColor(Colors.CYAN_BOLD);
                String value = scanner.nextLine();
                Colors.resetColor();
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.println(Colors.warning("Please enter a valid integer."));
            }
        }
    }

    public int readInt(int min, int max) {
        while (true) {
            try {
                Colors.setColor(Colors.CYAN_BOLD);
                String value = scanner.nextLine();
                Colors.resetColor();
                int intValue = Integer.parseInt(value);
                if (intValue >= min && intValue <= max) {
                    return intValue;
                }
                System.out.println(Colors.warning("Input must be between " + min + " e " + max));
            } catch (NumberFormatException e) {
                System.out.println(Colors.warning("Please enter a valid integer."));
            }
        }
    }

    public int readInt(String label, int min, int max) {
        while (true) {
            System.out.print(Colors.inputLabel(label));
            try {
                Colors.setColor(Colors.CYAN_BOLD);
                String value = scanner.nextLine();
                Colors.resetColor();
                int intValue = Integer.parseInt(value);
                if (intValue >= min && intValue <= max) {
                    return intValue;
                }
                System.out.println(Colors.warning("Input must be between " + min + " and " + max));
                System.out.println();
            } catch (NumberFormatException e) {
                System.out.println(Colors.warning("Please enter a valid integer."));
                System.out.println();
            }
        }
    }
}
