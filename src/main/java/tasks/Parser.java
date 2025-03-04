package tasks;

public class Parser {
    public boolean processCommand(String userInput, TaskList tasks, Ui ui, Storage storage) {
        try {
            String[] words = userInput.split(" ", 2);
            String command = words[0];

            switch (command) {
                case "bye":
                    return false;

                case "list":
                    ui.showMessage(tasks.listTasks());
                    break;

                case "mark":
                    handleMark(words, tasks, ui, storage);
                    break;

                case "unmark":
                    handleUnmark(words, tasks, ui, storage);
                    break;

                case "todo":
                    handleTodo(words, tasks, storage);
                    break;

                case "deadline":
                    handleDeadline(words, tasks, storage);
                    break;

                case "event":
                    handleEvent(words, tasks, storage);
                    break;

                case "delete":
                    handleDelete(words, tasks,ui,storage);
                    break;

                default:
                    throw new SXException("Invalid command.");
            }
        } catch (NumberFormatException e) {
            ui.showMessage("Error: Please enter a valid task number.");
        } catch (SXException e) {
            ui.showMessage("Error: " + e.getMessage());
        }
        return true;
    }

    // Handles marking tasks as done
    private void handleMark(String[] words, TaskList tasks, Ui ui, Storage storage) throws SXException {
        if (words.length < 2) {
            throw new SXException("Please specify a task number to mark.");
        }
        int index = Integer.parseInt(words[1]) - 1;
        ui.showMessage(tasks.markTask(index));
        storage.save(tasks.getTasks());
    }

    // Handles unmarking tasks
    private void handleUnmark(String[] words, TaskList tasks, Ui ui, Storage storage) throws SXException {
        if (words.length < 2) {
            throw new SXException("Please specify a task number to unmark.");
        }
        int index = Integer.parseInt(words[1]) - 1;
        ui.showMessage(tasks.unmarkTask(index));
        storage.save(tasks.getTasks());
    }

    // Handles adding Todo tasks
    private void handleTodo(String[] words, TaskList tasks, Storage storage) throws SXException {
        if (words.length < 2 || words[1].trim().isEmpty()) {
            throw new SXException("Todo description cannot be empty.");
        }
        Task task = new ToDo(words[1].trim());
        tasks.addTask(task);
        storage.save(tasks.getTasks());
    }


    // Handles adding Deadlines
    private void handleDeadline(String[] words, TaskList tasks, Storage storage) throws SXException {
        if (words.length < 2 || !words[1].contains(" /by ")) {
            throw new SXException("Invalid deadline format! Use: deadline <task> /by <date>");
        }

        String[] parts = words[1].split(" /by ", 2);
        String description = parts[0].trim();
        String deadlineDate = parts[1].trim();

        if (description.isEmpty() || deadlineDate.isEmpty()) {
            throw new SXException("Deadline description and date cannot be empty.");
        }
        Task task = new Deadline(description, deadlineDate);
        tasks.addTask(task);
        storage.save(tasks.getTasks());

    }


    //  Handles adding Events
    private void handleEvent(String[] words, TaskList tasks, Storage storage) throws SXException {
        if (words.length < 2 || !words[1].contains(" /from ") || !words[1].contains(" /to ")) {
            throw new SXException("Invalid event format! Use: event <task> /from <start> /to <end>");
        }
        String[] parts = words[1].split(" /from ", 2);
        String[] timeParts = parts[1].split(" /to ", 2);
        tasks.addTask(new Event(parts[0], timeParts[0], timeParts[1]));
        storage.save(tasks.getTasks());

    }

    // Handles deleting tasks
    private void handleDelete(String[] words, TaskList tasks,Ui ui, Storage storage) throws SXException {
        if (words.length < 2) {
            throw new SXException("Please specify a task number to delete.");
        }
        int index = Integer.parseInt(words[1]) - 1;
        ui.showMessage(tasks.deleteTask(index));
        storage.save(tasks.getTasks());
    }
}
