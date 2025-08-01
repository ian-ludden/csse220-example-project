import javax.swing.JFrame;

/**
 * Class containing the main method for the Endurance Sports Training Tracker Java Swing application.
 *
 * @author Ian Ludden (luddenig)
 */
public class TrainingTrackerMain {
    private TrainingTrackerFrame frame;

    /**
     * Constructs and displays the TrainingTrackerFrame.
     */
    public TrainingTrackerMain() {
        this.frame = new TrainingTrackerFrame();
        this.frame.setVisible(true);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.promptLoadActivityFile();
    }

    /**
     * Constructs and displays the TrainingTrackerFrame.
     *
     * @param args command-line arguments, ignored
     */
    public static void main(String[] args) {
        TrainingTrackerMain mainApp = new TrainingTrackerMain();
    }
}
