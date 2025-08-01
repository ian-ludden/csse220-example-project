import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * This class supports saving and loading Activity objects to and from CSV files.
 *
 * @author Ian Ludden (luddenig)
 */
public class ActivityCsvUtil {
    private static final String DELIMITER = ",";
    private static final String QUOTE = "\"";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final String HEADER = "type,title,timeSec,distanceKM,date";
    static final int NUM_FIELDS = 5;
    
    /**
     * Saves a list of activities to a CSV file.
     * 
     * @param activities List of Activity objects to save
     * @param filename Path to the CSV file
     * @throws IOException if file writing fails
     */
    public static void saveToCSV(List<Activity> activities, String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Write header
            writer.println(HEADER);
            
            // Write each activity
            for (Activity activity : activities) {
                writer.println(activityToCSVLine(activity));
            }
        }
    }
    
    /**
     * Loads activities from a CSV file.
     * Note: This requires a factory method or switch statement to create concrete Activity subclasses.
     * 
     * @param filename Path to the CSV file
     * @return List of Activity objects
     * @throws IOException if file reading fails
     */
    public static List<Activity> loadFromCSV(String filename) throws IOException {
        List<Activity> activities = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            reader.readLine(); // Skip header
            String line;
            
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    try {
                        Activity activity = parseCSVLine(line);
                        activities.add(activity);
                    } catch (Exception e) {
                        System.err.println("Failed to load activity, skipping.\n\tCSV Line: " + line + "\n" + e.getMessage());
                    }
                }
            }
        }
        
        return activities;
    }
    
    /**
     * Converts an Activity object to a CSV line.
     */
    private static String activityToCSVLine(Activity activity) {
        StringBuilder sb = new StringBuilder();

        String className = activity.getClass().getSimpleName();
        String activityType = className.replace("Activity", "").toLowerCase();
        sb.append(escapeCSVField(activityType));
        sb.append(DELIMITER);

        sb.append(escapeCSVField(activity.getTitle()));
        sb.append(DELIMITER);

        if (activity.getTimeSec() != Activity.EMPTY_TIME) {
            sb.append(activity.getTimeSec());
        }
        sb.append(DELIMITER);

        if (activity.getDistanceKM() != Activity.EMPTY_DISTANCE) {
            sb.append(activity.getDistanceKM());
        }
        sb.append(DELIMITER);

        sb.append(activity.getDate().format(DATE_FORMATTER));
        
        return sb.toString();
    }
    
    /**
     * Parses a CSV line into an Activity object.
     */
    private static Activity parseCSVLine(String line) throws CorruptedActivityFileException, InvalidActivityTypeException {
        List<String> fields = parseCSVFields(line);

        if (fields.size() != NUM_FIELDS) {
            throw new CorruptedActivityFileException(line);
        }

        String type = fields.get(0);
        String title = fields.get(1);
        String timeStr = fields.get(2);
        String distanceStr = fields.get(3);
        String dateStr = fields.get(4);

        int timeSec = timeStr.isEmpty() ? Activity.EMPTY_TIME : Integer.parseInt(timeStr);
        double distanceKM = distanceStr.isEmpty() ? Activity.EMPTY_DISTANCE : Double.parseDouble(distanceStr);
        LocalDate date = LocalDate.parse(dateStr, DATE_FORMATTER);

        // Create activity based on type
        return createActivity(type, title, timeSec, distanceKM, date);
    }

    /**
     * Constructs and returns an Activity object based on the provided data.
     *
     * @param type the type of activity ("run", "bike", or "swim")
     * @param title the activity's user-provided title
     * @param timeSec the activity's elapsed time, in seconds
     * @param distanceKM the activity's distance, in kilometers
     * @param date the activity's date
     * @return Activity object with the provided data
     * @throws InvalidActivityTypeException if provided type is not one of "run", "bike", or "swim"
     */
    private static Activity createActivity(String type, String title, int timeSec, double distanceKM, LocalDate date) throws InvalidActivityTypeException {
        switch (type.toLowerCase()) {
            case "run" -> {
                return new RunActivity(title, timeSec, distanceKM, date);
            }
            case "bike" -> {
                return new BikeActivity(title, timeSec, distanceKM, date);
            }
            case "swim" -> {
                return new SwimActivity(title, timeSec, distanceKM, date);
            }
            default -> throw new InvalidActivityTypeException(type);
        }
    }
    
    /**
     * Parses CSV fields from a line, handling quoted fields that may contain commas.
     */
    private static List<String> parseCSVFields(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        boolean inQuotes = false;
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    currentField.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                fields.add(currentField.toString());
                currentField.setLength(0);
            } else {
                currentField.append(c);
            }
        }

        fields.add(currentField.toString());
        
        return fields;
    }
    
    /**
     * Escapes a field for CSV format by adding quotes if necessary.
     */
    private static String escapeCSVField(String field) {
        if (field == null) {
            return "";
        }
        
        // Quote the field if it contains comma, quote, or newline
        if (field.contains(DELIMITER) || field.contains(QUOTE) || field.contains("\n") || field.contains("\r")) {
            // Escape quotes by doubling them
            String escaped = field.replace(QUOTE, QUOTE + QUOTE);
            return QUOTE + escaped + QUOTE;
        }
        
        return field;
    }
}

/**
 * This custom exception class indicates an invalid activity type string was read from a CSV file.
 */
class InvalidActivityTypeException extends IOException {
    public InvalidActivityTypeException(String type) {
        super("'" + type + "' is not a known activity type.");
    }
}

class CorruptedActivityFileException extends IOException {
    public CorruptedActivityFileException(String line) {
        super("Corrupted CSV line in activity file (expected " + ActivityCsvUtil.NUM_FIELDS + " fields): " + line);
    }
}