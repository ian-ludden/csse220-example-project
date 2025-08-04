import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.KeyEvent;
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
    private JLabel activityListLabel;

    private JTabbedPane tabbedPane;

    public TrainingTrackerFrame() {
        super("Endurance Sports Training Tracker");
        this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

//        this.add(new JLabel("Placeholder for CardLayout with CalendarPanel, ActivityPanel, StatsPanel"), BorderLayout.CENTER);

        // Use of JTabbedPane adapted from this example: https://docs.oracle.com/javase/tutorial/uiswing/components/tabbedpane.html
        this.tabbedPane = new JTabbedPane();
        JComponent calendarPanel = new CalendarPanel();
//        calendarPanel.setLayout(new GridLayout(1, 1));
//        JLabel calendarLabel = new JLabel("Placeholder for Calendar");
//        calendarLabel.setHorizontalAlignment(JLabel.CENTER);
//        calendarPanel.add(calendarLabel);
        this.tabbedPane.addTab("Calendar", null, calendarPanel, "Calendar View");
        this.tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        JComponent statsPanelPlaceholder = new JPanel();
        statsPanelPlaceholder.setLayout(new GridLayout(1, 1));
        JLabel statsLabel = new JLabel("Placeholder for Statistics");
        statsLabel.setHorizontalAlignment(JLabel.CENTER);
        statsPanelPlaceholder.add(statsLabel);
        this.tabbedPane.addTab("Statistics", null, statsPanelPlaceholder, "Stats View");
        this.tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        this.add(this.tabbedPane, BorderLayout.CENTER);

        this.add(new JLabel("Placeholder for BannerPanel"), BorderLayout.NORTH);

        this.activityList = new ArrayList<>();

        // *****************************************************************************************
        // TODO: Delete these placeholder activities once file I/O has been implemented
        SwimActivity swim = new SwimActivity("Easy 20min Swim");
        swim.setDate(LocalDate.parse("2025-08-04", DateTimeFormatter.ISO_LOCAL_DATE));
        this.activityList.add(swim);

        BikeActivity ride = new BikeActivity("90min Evening Ride");
        ride.setDate(LocalDate.parse("2025-08-05", DateTimeFormatter.ISO_LOCAL_DATE));
        ride.setBicycle(new Bicycle("Road Bike"));
        this.activityList.add(ride);

        RunActivity run = new RunActivity("Tempo Run");
        run.setDate(LocalDate.parse("2025-08-06", DateTimeFormatter.ISO_LOCAL_DATE));
        run.setShoePair(new ShoePair("Brooks Ghost 23"));
        this.activityList.add(run);
        // *****************************************************************************************

        try {
            ActivityCsvUtil.saveToCSV(this.activityList, "example_activities.csv");
        } catch (IOException e) {
            System.err.println("Failed to save example activities to CSV.\n" + e.getMessage());
        }

        this.activityListLabel = new JLabel();
        this.updateActivityListLabel();
        this.add(this.activityListLabel, BorderLayout.SOUTH);
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
                this.updateActivityListLabel();
                this.repaint();
            } catch (IOException e) {
                String warningMessage = "Failed to read Activity file: " + filename + ". Are you sure this file exists?";
                System.err.println(warningMessage);
                JOptionPane.showMessageDialog(this, warningMessage, "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void updateActivityListLabel() {
        StringBuilder activityString = new StringBuilder("Activities:<br>");
        for (Activity activity : this.activityList) {
            activityString.append("&nbsp;&nbsp;&nbsp;&nbsp;").append(activity).append("<br>");
        }
        this.activityListLabel.setText("<html>" + activityString + "</html>");
    }

}