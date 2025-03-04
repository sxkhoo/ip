import java.io.IOException;
import tasks.*;
public class ShiXian {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;
    private final Parser parser;

    public ShiXian(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        parser = new Parser();
        try {
            tasks = new TaskList(storage.load());
        } catch (IOException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.showWelcomeMessage();
        boolean isRunning = true;

        while (isRunning) {
            String userInput = ui.getUserInput();
            isRunning = parser.processCommand(userInput, tasks, ui, storage);
        }
        ui.showExitMessage();
    }

    public static void main(String[] args) {
        new ShiXian("./data/tasks.txt").run();
    }
}
