import java.util.Scanner;

public class ShiXian {
    private static final Task[] tasks = new Task[100];
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
        int k =0;
        while (true) {
            String userInput = scanner.nextLine();
            if (userInput.equals("bye")) {
                System.out.println("Goodbye! See you next time.");
                System.out.println("---------------------------");
                break;
            }
            else if (userInput.equals("list")){
                for(int i = 0; i < k; i++){
                    System.out.println((i+1)+":"+tasks[i]);

                }
            }
            else if (userInput.startsWith("mark ")) {
                int taskIndex = Integer.parseInt(userInput.substring(5)) - 1;
                if (taskIndex >= 0 && taskIndex < k ) {
                    tasks[taskIndex].markAsDone();
                    System.out.println("marked");
                }
                System.out.println("---------------------------");
            }
            else if (userInput.startsWith("unmark ")) {
                int taskIndex = Integer.parseInt(userInput.substring(7)) - 1;
                if (taskIndex >= 0 && taskIndex < k + 1) {
                    tasks[taskIndex].markAsNotDone();
                    System.out.println("unmarked");
                }
                System.out.println("---------------------------");
            }
            else {
                tasks[k] = new Task(userInput);
                k++;
                System.out.println("---------------------------");
                System.out.println("added: "+userInput);
            }
        }
        System.out.println("Bye. Hope to see you again soon!");
    }
}
