import java.util.Scanner;
import tasks.*;

public class ShiXian {
    private static final Task[] tasks = new Task[100];
    private static int k = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hello! I'm Shi Xian.");
        System.out.println("What can I do for you?");
        System.out.println("---------------------------------");

        while (true) {
            try {
                String userInput = scanner.nextLine();

                if (userInput.equals("bye")) {
                    System.out.println("Goodbye! See you next time.");
                    System.out.println("---------------------------");
                    break;
                } else if (userInput.equals("list")) {
                    listTasks();
                } else if (userInput.startsWith("mark ")) {
                    markTask(userInput);
                } else if (userInput.startsWith("unmark ")) {
                    unmarkTask(userInput);
                } else if (userInput.startsWith("todo ")) {
                    addTask(new ToDo(userInput.substring(5)));
                } else if (userInput.startsWith("deadline ")) {
                    handleDeadline(userInput);
                } else if (userInput.startsWith("event ")) {
                    handleEvent(userInput);
                }
                else if (userInput.startsWith("delete ")) {
                        deleteTask(userInput);
                } else {
                    throw new SXException("Invalid command");
                }
            } catch (SXException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private static void addTask(Task task) throws SXException {
        if (k >= tasks.length) {
            throw new SXException("Task storage is full!");
        }
        tasks[k++] = task;
        System.out.println("Added: " + task);
        System.out.println("Now you have " + k + " task(s).\n");
    }

    private static void listTasks() {
        if (k == 0) {
            System.out.println("No tasks available.");
        } else {
            System.out.println("Tasks:");
            for (int i = 0; i < k; i++) {
                System.out.println((i + 1) + ". " + tasks[i]);
            }
        }
    }

    private static void markTask(String userInput) throws SXException {
        try {
            int index = Integer.parseInt(userInput.substring(5)) - 1;
            if (index < 0 || index >= k) {
                throw new SXException("Invalid task number.");
            }
            tasks[index].markAsDone();
            System.out.println("Marked as done: " + tasks[index]);
        } catch (NumberFormatException e) {
            throw new SXException("Please enter a valid task number.");
        }
    }

    private static void unmarkTask(String userInput) throws SXException {
        try {
            int index = Integer.parseInt(userInput.substring(7)) - 1;
            if (index < 0 || index >= k) {
                throw new SXException("Invalid task number.");
            }
            tasks[index].markAsNotDone();
            System.out.println("Marked as not done: " + tasks[index]);
        } catch (NumberFormatException e) {
            throw new SXException("Please enter a valid task number.");
        }
    }

    private static void handleDeadline(String userInput) throws SXException {
        String[] parts = userInput.substring(9).split(" by ", 2);
        if (parts.length != 2) {
            throw new SXException("Invalid deadline format! Use: deadline <task> by <date/time>");
        }
        addTask(new Deadline(parts[0], parts[1]));
    }

    private static void handleEvent(String userInput) throws SXException {
        String[] parts = userInput.substring(6).split(" from ", 2);
        if (parts.length != 2) {
            throw new SXException("Invalid event format! Use: event <task> from <start> to <end>");
        }
        String[] timeParts = parts[1].split(" to ", 2);
        if (timeParts.length != 2) {
            throw new SXException("Invalid event format! Use: event <task> from <start> to <end>");
        }
        addTask(new Event(parts[0], timeParts[0], timeParts[1]));
    }
    private static void deleteTask(String userInput) throws SXException {
        try {
            int index = Integer.parseInt(userInput.substring(7)) - 1;
            if (index < 0 || index >= k) {
                throw new SXException("Invalid task number.");
            }
            System.out.println("Deleted: " + tasks[index]);

            for (int i = index; i < k - 1; i++) {
                tasks[i] = tasks[i + 1];
            }
            tasks[k - 1] = null;
            k--;
            System.out.println( k + " task(s) left.");
        } catch (NumberFormatException e) {
            throw new SXException("Please enter a valid task number.");
        }
    }
}