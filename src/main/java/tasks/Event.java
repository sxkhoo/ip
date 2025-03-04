package tasks;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
    private static final DateTimeFormatter SAVE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // Constructor for user input parsing
    public Event(String description, String from, String to) throws SXException {
        super(description);
        try {
            this.from = LocalDateTime.parse(from, INPUT_FORMATTER);
            this.to = LocalDateTime.parse(to, INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new SXException("Invalid event format! Use: d/M/yyyy HHmm (e.g., 3/12/2019 1400 /to 3/12/2019 1600)");
        }
    }

    // Constructor for loading from Storage
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    // Get formatted start time
    public String getFrom() {
        return from.format(OUTPUT_FORMATTER);
    }

    // Get raw start time for Storage
    public String getRawFrom() {
        return from.format(SAVE_FORMATTER);
    }

    // Get formatted end time
    public String getTo() {
        return to.format(OUTPUT_FORMATTER);
    }

    // Get raw end time for Storage
    public String getRawTo() {
        return to.format(SAVE_FORMATTER);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + getFrom() + " to: " + getTo() + ")";
    }
}
