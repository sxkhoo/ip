package tasks;
import java.util.Scanner;

public class Ui {
    private final Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }
    //Greet the user when programme starts
    public void showWelcomeMessage() {
        System.out.println("Hello! I'm Shi Xian.");
        System.out.println("What can I do for you?");
        System.out.println("---------------------------------");
    }
    //Receives input from user typing.
    public String getUserInput() {
        return scanner.nextLine().trim();
    }
    //Terminates programme with a goodbye message
    public void showExitMessage() {
        System.out.println("Goodbye! See you next time.");
        System.out.println("---------------------------");
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
    //Shows error message
    public void showLoadingError() {
        System.out.println("Error loading tasks from file.");
    }
}
