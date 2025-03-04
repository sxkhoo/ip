package tasks;
import java.util.*;

public class TaskList {
    private final List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public String listTasks() {
        if (tasks.isEmpty()) {
            return "No tasks available.";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            sb.append((i + 1)).append(". ").append(tasks.get(i)).append("\n");
        }
        return sb.toString();
    }

    public void addTask(Task task) {
        tasks.add(task);
        System.out.println("Added: " + task);
        System.out.println("Now you have " + tasks.size() + " task(s).");
    }

    public String deleteTask(int index) throws SXException {
        if (index < 0 || index >= tasks.size()) {
            throw new SXException("Invalid task number.");
        }
        Task removedTask = tasks.remove(index);
        return "Deleted: " + removedTask + "\nNow you have " + tasks.size() + " task(s).";
    }

    public String markTask(int index) throws SXException {
        if (index < 0 || index >= tasks.size()) {
            throw new SXException("Invalid task number.");
        }
        tasks.get(index).markAsDone();
        return "Marked as done: " + tasks.get(index);
    }

    public String unmarkTask(int index) throws SXException {
        if (index < 0 || index >= tasks.size()) {
            throw new SXException("Invalid task number.");
        }
        tasks.get(index).markAsNotDone();
        return "Marked as not done: " + tasks.get(index);
    }
    public String findTasks(String keyword) {
        List<Task> matchingTasks = new ArrayList<>();

        for (Task task : tasks) {
            if (task.description.toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }

        if (matchingTasks.isEmpty()) {
            return "No matching tasks found.";
        }

        StringBuilder result = new StringBuilder("Here are the matching tasks in your list:\n");
        for (int i = 0; i < matchingTasks.size(); i++) {
            result.append((i + 1)).append(". ").append(matchingTasks.get(i)).append("\n");
        }
        return result.toString();
    }
}
