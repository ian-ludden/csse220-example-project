import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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
        this.activityList.add(new SwimActivity("Easy 20min Swim"));
        this.activityList.add(new BikeActivity("90min Evening Ride"));
        this.activityList.add(new RunActivity("Tempo Run"));
        // *****************************************************************************************

        StringBuilder activityString = new StringBuilder("Activities:<br>");
        for (Activity activity : this.activityList) {
            activityString.append("&nbsp;&nbsp;&nbsp;&nbsp;").append(activity).append("<br>");
        }

        JLabel bottomLabel = new JLabel("<html>" + activityString + "</html>");
        this.add(bottomLabel, BorderLayout.SOUTH);
    }

}