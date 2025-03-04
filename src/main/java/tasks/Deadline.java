package tasks;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    private final LocalDateTime by;
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
    private static final DateTimeFormatter SAVE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // Constructor that parses user input
    public Deadline(String description, String by) throws SXException {
        super(description);
        try {
            this.by = LocalDateTime.parse(by, INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new SXException("Invalid date format! Use: d/M/yyyy HHmm (e.g., 2/12/2019 1800)");
        }
    }

    // Constructor that takes a LocalDateTime
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    // Returns date
    public String getBy() {
        return by.format(OUTPUT_FORMATTER);
    }

    // Returns raw date in "yyyy-MM-dd HH:mm" format
    public String getRawBy() {
        return by.format(SAVE_FORMATTER);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + getBy() + ")";
    }
}
