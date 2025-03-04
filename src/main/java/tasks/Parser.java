package tasks;

public class Parser {
    public boolean processCommand(String userInput, TaskList tasks, Ui ui, Storage storage) {
        try {
            String[] words = userInput.split(" ", 2);
            String command = words[0];

            if (command.equals("bye")) {
                return false;
            } else if (command.equals("list")) {
                ui.showMessage(tasks.listTasks());
            } else if (command.equals("mark")) {
                if (words.length < 2) {
                    throw new SXException("Please specify a task number to mark.");
                }
                int index = Integer.parseInt(words[1]) - 1;
                ui.showMessage(tasks.markTask(index));
                storage.save(tasks.getTasks());
            } else if (command.equals("unmark")) {
                if (words.length < 2) {
                    throw new SXException("Please specify a task number to unmark.");
                }
                int index = Integer.parseInt(words[1]) - 1;
                ui.showMessage(tasks.unmarkTask(index));
                storage.save(tasks.getTasks());
            } else if (command.equals("todo")) {
                if (words.length < 2 || words[1].trim().isEmpty()) {
                    throw new SXException("Todo description cannot be empty.");
                }
                tasks.addTask(new ToDo(words[1]));
                storage.save(tasks.getTasks());
            } else if (command.equals("deadline")) {
                if (words.length < 2 || !words[1].contains(" /by ")) {
                    throw new SXException("Invalid deadline format! Use: deadline <task> /by <date>");
                }
                String[] parts = words[1].split(" /by ", 2);
                tasks.addTask(new Deadline(parts[0], parts[1]));
                storage.save(tasks.getTasks());
            } else if (command.equals("event")) {
                if (words.length < 2 || !words[1].contains(" /from ") || !words[1].contains(" /to ")) {
                    throw new SXException("Invalid event format! Use: event <task> /from <start> /to <end>");
                }
                String[] parts = words[1].split(" /from ", 2);
                String[] timeParts = parts[1].split(" /to ", 2);
                tasks.addTask(new Event(parts[0], timeParts[0], timeParts[1]));
                storage.save(tasks.getTasks());
            } else if (command.equals("delete")) {
                if (words.length < 2) {
                    throw new SXException("Please specify a task number to delete.");
                }
                int index = Integer.parseInt(words[1]) - 1;
                ui.showMessage(tasks.deleteTask(index));
                storage.save(tasks.getTasks());
            } else {
                throw new SXException("Invalid command.");
            }
        } catch (NumberFormatException e) {
            ui.showMessage("Error: Please enter a valid task number.");
        } catch (SXException e) {
            ui.showMessage("Error: " + e.getMessage());
        }
        return true;
    }
}
