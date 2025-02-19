import tasks.*;
import java.io.*;
import java.util.*;

public class ShiXian {
    private static final String FILE_PATH = "./data/tasks.txt";
    private static final List<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hello! I'm Shi Xian.");
        System.out.println("What can I do for you?");
        System.out.println("---------------------------------");

        while (true) {
            try {
                String userInput = scanner.nextLine().trim();

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
        tasks.add(task);
        saveTasksToFile();
        System.out.println("Added: " + task);
        System.out.println("Left: " + tasks.size() + " task(s).");
    }

    private static void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
    }

    private static void markTask(String userInput) throws SXException {
        try {
            int index = Integer.parseInt(userInput.substring(5)) - 1;
            if (index < 0 || index >= tasks.size()) {
                throw new SXException("Invalid task number.");
            }
            tasks.get(index).markAsDone();
            saveTasksToFile();
            System.out.println("Marked as done: " + tasks.get(index));
        } catch (NumberFormatException e) {
            throw new SXException("Please enter a valid task number.");
        }
    }

    private static void unmarkTask(String userInput) throws SXException {
        try {
            int index = Integer.parseInt(userInput.substring(7)) - 1;
            if (index < 0 || index >= tasks.size()) {
                throw new SXException("Invalid task number.");
            }
            tasks.get(index).markAsNotDone();
            saveTasksToFile();
            System.out.println("Marked as not done: " + tasks.get(index));
        } catch (NumberFormatException e) {
            throw new SXException("Please enter a valid task number.");
        }
    }

    private static void handleDeadline(String userInput) throws SXException {
        String[] parts = userInput.substring(9).split(" /by ", 2);
        if (parts.length != 2) {
            throw new SXException("Invalid deadline format! Use: deadline <task> /by <date/time>");
        }
        addTask(new Deadline(parts[0], parts[1]));
    }

    private static void handleEvent(String userInput) throws SXException {
        String[] parts = userInput.substring(6).split(" /from ", 2);
        if (parts.length != 2) {
            throw new SXException("Invalid event format! Use: event <task> /from <start> /to <end>");
        }
        String[] timeParts = parts[1].split(" /to ", 2);
        if (timeParts.length != 2) {
            throw new SXException("Invalid event format! Use: event <task> /from <start> /to <end>");
        }
        addTask(new Event(parts[0], timeParts[0], timeParts[1]));
    }

    private static void saveTasksToFile() {
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