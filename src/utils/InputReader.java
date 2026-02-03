package utils;

import java.util.Scanner;

public class InputReader {
    private Scanner scanner;

    public InputReader() {
        this.scanner = new Scanner(System.in);
    }

    public String readCommand(String label) {
        System.out.print(label);
        return scanner.nextLine();
    }
}
