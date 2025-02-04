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
        String[] storedList = new String[100];
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
                    System.out.println(i+1+":"+storedList[i]);

                }
            }
            storedList[k] = userInput;
            k++;
            System.out.println("---------------------------");
            System.out.println("added: "+userInput);
        }
        System.out.println("Bye. Hope to see you again soon!");
    }
}
