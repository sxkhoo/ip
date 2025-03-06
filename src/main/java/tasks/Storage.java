package tasks;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Storage {
    private final String filePath;
    private static final DateTimeFormatter SAVE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // Constructor
    public Storage(String filePath) {
        this.filePath = filePath; //set filepath
    }
    //Loads tasks from the specified file.
    public List<Task> load() throws IOException {
        List<Task> tasks = new ArrayList<>();
        File file = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            try {
                Task task = parseTaskFromString(line);
                if (task != null) {
                    tasks.add(task);
                }
            } catch (Exception e) {
                System.out.println("Skipping invalid task entry: " + line);
            }
        }
        reader.close();
        return tasks;
    }
    //Saves the list of tasks to the specified file.
    public void save(List<Task> tasks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Task task : tasks) {
                writer.write(formatTaskForStorage(task) + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks to file.");
        }
    }
    // Formats a task as a string to be stored in a file.
    private String formatTaskForStorage(Task task) {
        String type = (task instanceof ToDo) ? "T"
                : (task instanceof Deadline) ? "D"
                : (task instanceof Event) ? "E"
                : "?";
        String status = task.getStatusIcon().equals("[X]") ? "1" : "0";

        if (task instanceof Deadline) {
            return type + " | " + status + " | " + task.description + " | " + ((Deadline) task).getRawBy();
        } else if (task instanceof Event) {
            return type + " | " + status + " | " + task.description + " | "
                    + ((Event) task).getRawFrom() + " | " + ((Event) task).getRawTo();
        }
        return type + " | " + status + " | " + task.description;
    }
    //Parses a task from a formatted string.
    private Task parseTaskFromString(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid task format: " + line);
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        try {
            if (type.equals("T")) {
                return createToDo(description, isDone);
            } else if (type.equals("D") && parts.length == 4) {
                return createDeadline(description, parts[3], isDone);
            } else if (type.equals("E") && parts.length == 5) {
                return createEvent(description, parts[3], parts[4], isDone);
            } else {
                throw new IllegalArgumentException("Unknown task type: " + type);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid task data: " + line);
        }
    }

    // Create a To Do task
    private Task createToDo(String description, boolean isDone) {
        Task task = new ToDo(description);
        if (isDone) {
            task.markAsDone();
        }
        return task;
    }

    // Create a Deadline task
    private Task createDeadline(String description, String by, boolean isDone) {
        try {
            LocalDateTime dateTime = LocalDateTime.parse(by, SAVE_FORMATTER);
            Task task = new Deadline(description, dateTime);
            if (isDone) {
                task.markAsDone();
            }
            return task;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid deadline date format: " + by);
        }
    }

    // Create an Event task
    private Task createEvent(String description, String from, String to, boolean isDone) {
        try {
            LocalDateTime fromDateTime = LocalDateTime.parse(from, SAVE_FORMATTER);
            LocalDateTime toDateTime = LocalDateTime.parse(to, SAVE_FORMATTER);
            Task task = new Event(description, fromDateTime, toDateTime);
            if (isDone) {
                task.markAsDone();
            }
            return task;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid event date format: from " + from + ", to " + to);
        }
    }
}
