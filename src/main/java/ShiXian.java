import java.util.*;
import java.io.*;
public class ShiXian {
    private static final String FILE_PATH = "./data/tasks.txt";
    private static final List<Task> tasks = new ArrayList<>();
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

            }
            else if (userInput.startsWith("deadline ")) {
                String[] parts = userInput.substring(9).split(" /by ", 2);
                if (parts.length == 2) {
                    addTask(new Deadline(parts[0], parts[1]));

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
                        System.out.println("Now you have " + tasks.size() + " task(s).");
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
        tasks.add(task);
        saveTasks();
        System.out.println("Added: " + task);
        System.out.println("Have:" + tasks.size() + " task(s).");
    }

    private static void listTasks() {
        System.out.println("Tasks:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }
    private static void markTask(String userInput) {
        try {
            int index = Integer.parseInt(userInput.substring(5)) - 1;
            if (index >= 0 && index < tasks.size()) {
                tasks.get(index).markAsDone();
                System.out.println("Marked as done: " + tasks.get(index));
                saveTasks();
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
            if (index >= 0 && index < tasks.size()) {
                tasks.get(index).markAsNotDone();
                saveTasks();
                System.out.println("Marked as not done: " + tasks.get(index));
            } else {
                System.out.println("Invalid task number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid task number.");
        }
    }

    private static void saveTasks() {
        try {
            File file = new File(FILE_PATH);

            FileWriter writer = new FileWriter(file);
            for (Task task : tasks) {
                writer.write(task + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks to file.");
        }
    }

}

