import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Custom JFrame for displaying the Endurance Sports Training Tracker Java Swing application.
 *
 * @author Ian Ludden (luddenig)
 */
public class TrainingTrackerFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 1024;
    private static final int DEFAULT_HEIGHT = 768;

    private ArrayList<Activity> activityList;


    public TrainingTrackerFrame() {
        super("Endurance Sports Training Tracker");
        this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        this.add(new JLabel("Placeholder for CardLayout with CalendarPanel, ActivityPanel, StatsPanel"), BorderLayout.CENTER);
        this.add(new JLabel("Placeholder for BannerPanel"), BorderLayout.NORTH);

        this.activityList = new ArrayList<>();

        // *****************************************************************************************
        // TODO: Delete these placeholder activities once file I/O has been implemented
        SwimActivity swim = new SwimActivity("Easy 20min Swim");
        swim.setDate(LocalDate.parse("2025-08-04", DateTimeFormatter.ISO_LOCAL_DATE));
        this.activityList.add(swim);

        BikeActivity ride = new BikeActivity("90min Evening Ride");
        ride.setDate(LocalDate.parse("2025-08-05", DateTimeFormatter.ISO_LOCAL_DATE));
        this.activityList.add(ride);

        RunActivity run = new RunActivity("Tempo Run");
        run.setDate(LocalDate.parse("2025-08-06", DateTimeFormatter.ISO_LOCAL_DATE));
        this.activityList.add(run);
        // *****************************************************************************************

        try {
            ActivityCsvUtil.saveToCSV(this.activityList, "example_activities.csv");
        } catch (IOException e) {
            System.err.println("Failed to save example activities to CSV.\n" + e.getMessage());
        }


        StringBuilder activityString = new StringBuilder("Activities:<br>");
        for (Activity activity : this.activityList) {
            activityString.append("&nbsp;&nbsp;&nbsp;&nbsp;").append(activity).append("<br>");
        }

        JLabel bottomLabel = new JLabel("<html>" + activityString + "</html>");
        this.add(bottomLabel, BorderLayout.SOUTH);
    }

    /**
     * Prompts the user to load activities from a file.
     */
    public void promptLoadActivityFile() {
        File workingDirectory = new File(System.getProperty("user.dir"));
        JFileChooser chooser = new JFileChooser(workingDirectory);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            String filename = chooser.getSelectedFile().getName();
            System.out.println("You chose to open this file: " + filename);
            try {
                List<Activity> loadedActivities = ActivityCsvUtil.loadFromCSV(filename);
                this.activityList.clear();
                this.activityList.addAll(loadedActivities);
            } catch (IOException e) {
                String warningMessage = "Failed to read Activity file: " + filename + ". Are you sure this file exists?";
                System.err.println(warningMessage);
                JOptionPane.showMessageDialog(this, warningMessage, "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

}