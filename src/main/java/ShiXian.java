import java.io.IOException;
import tasks.*;
public class ShiXian {
    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;
    private final Parser parser;
    //Constructs a ShiXian instance with the specified file path for task storage.
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
    //Runs the main execution loop of the application.
    public void run() {
        ui.showWelcomeMessage();
        boolean isRunning = true;

        while (isRunning) {
            String userInput = ui.getUserInput();
            isRunning = parser.processCommand(userInput, tasks, ui, storage);
        }
        ui.showExitMessage();
    }
    //The main entry point of the ShiXian application.
    public static void main(String[] args) {
        new ShiXian("./data/tasks.txt").run();
    }
}
