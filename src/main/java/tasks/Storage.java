package tasks;
import java.io.*;
import java.util.*;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }
    public List<Task> load() throws IOException {
        List<Task> tasks = new ArrayList<>();
        File file = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            Task task = parseTaskFromString(line);
            if (task != null) {
                tasks.add(task);
            }
        }
        reader.close();
        return tasks;
    }

    public void save(List<Task> tasks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Task task : tasks) {
                writer.write(formatTaskForStorage(task) + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks to file.");
        }
    }

    private String formatTaskForStorage(Task task) {
        String type = (task instanceof ToDo) ? "T"
                : (task instanceof Deadline) ? "D"
                : (task instanceof Event) ? "E"
                : "?";
        String status = task.getStatusIcon().equals("[X]") ? "1" : "0";

        if (task instanceof Deadline) {
            return type + " | " + status + " | " + task.description + " | " + ((Deadline) task).getBy();
        } else if (task instanceof Event) {
            return type + " | " + status + " | " + task.description + " | " + ((Event) task).getFrom() + " | " + ((Event) task).getTo();
        }
        return type + " | " + status + " | " + task.description;
    }

    // Convert a stored line back into a Task object
    private Task parseTaskFromString(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) return null;

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task;
        if (type.equals("T")) {
            task = new ToDo(description);
        } else if (type.equals("D") && parts.length == 4) {
            task = new Deadline(description, parts[3]);
        } else if (type.equals("E") && parts.length == 5) {
            task = new Event(description, parts[3], parts[4]);
        } else {
            return null;
        }

        if (isDone) {
            task.markAsDone();
        }
        return task;
    }
}
