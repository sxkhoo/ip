import java.util.Scanner;

public class ShiXian {
    public static void main(String[] args) {
         /*
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);*/
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello! I'm Shi Xian.");
        System.out.println("What can I do for you?");
        System.out.println("---------------------------------");
        while (true) {
            String userInput = scanner.nextLine();
            if (userInput.equals("bye")) {
                System.out.println("Goodbye! See you next time.");
                System.out.println("---------------------------");
                break;
            }
            System.out.println("---------------------------");
            System.out.println(userInput);
        }
        System.out.println("Bye. Hope to see you again soon!");
    }
}
