import java.util.Scanner;

public class App {
    public void run() {
        while (true) {
            System.out.print("> ");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            System.out.println(input);
        }
    }
}
