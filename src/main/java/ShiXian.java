import java.util.Scanner;

public class ShiXian {
    private static final Task[] tasks = new Task[100];
    private static int k =0;
    public static void main(String[] args) {
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
            else if (userInput.equals("list")) {
                listTasks();
            }
            else if (userInput.startsWith("mark ")) {
                markTask(userInput);
            }
            else if (userInput.startsWith("unmark ")) {
                unmarkTask(userInput);
            }
            else if (userInput.startsWith("todo ")) {
                addTask(new ToDo(userInput.substring(5)));
                System.out.println("Now you have " + k + " task(s).");
            }
            else if (userInput.startsWith("deadline ")) {
                String[] parts = userInput.substring(9).split(" /by ", 2);
                if (parts.length == 2) {
                    addTask(new Deadline(parts[0], parts[1]));
                    System.out.println("Now you have " + k + " task(s).");
                } else {
                    System.out.println("Invalid deadline format! Use: deadline <task> /by <date/time>");
                }
            }
            else if (userInput.startsWith("event ")) {
                String[] parts = userInput.substring(6).split(" /from ", 2);
                if (parts.length == 2) {
                    String[] timeParts = parts[1].split(" /to ", 2);
                    if (timeParts.length == 2) {
                        addTask(new Event(parts[0], timeParts[0], timeParts[1]));
                        System.out.println("Now you have " + k + " task(s).");
                    } else {
                        System.out.println("Invalid event format! Use: event <task> /from <start> /to <end>");
                    }
                } else {
                    System.out.println("Invalid event format! Use: event <task> /from <start> /to <end>");
                }
            }
            else {
                System.out.println("Invalid command");
            }
        }
        scanner.close();
    }
    private static void addTask(Task task) {
        if (k < tasks.length) {
            tasks[k++] = task;
            System.out.println("Added: " + task);
        } else {
            System.out.println("Task storage is full!");
        }
    }

    private static void listTasks() {
        System.out.println("Tasks:");
        for (int i = 0; i < k; i++) {
            System.out.println((i + 1) + ". " + tasks[i]);
        }
    }

    private static void markTask(String userInput) {
        try {
            int index = Integer.parseInt(userInput.substring(5)) - 1;
            if (index >= 0 && index < k) {
                tasks[index].markAsDone();
                System.out.println("Marked as done: " + tasks[index]);
            } else {
                System.out.println("Invalid task number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid task number.");
        }
    }

    private static void unmarkTask(String userInput) {
        try {
            int index = Integer.parseInt(userInput.substring(7)) - 1;
            if (index >= 0 && index < k) {
                tasks[index].markAsNotDone();
                System.out.println("Marked as not done: " + tasks[index]);
            } else {
                System.out.println("Invalid task number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid task number.");
        }
    }
}

