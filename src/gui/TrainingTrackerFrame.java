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
        // TODO: Delete these placeholder activities once file I/O has been implemented
        
    }

}