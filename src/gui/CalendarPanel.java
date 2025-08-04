import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Class representing a GUI training calendar, with a default view of a month grid.
 *
 * @author Ian Ludden (luddenig)
 */
public class CalendarPanel extends JPanel {
    private static final int DEFAULT_NUM_WEEKS = 5;
    private static final int DEFAULT_PREFERRED_WIDTH = 750;

    private ActivityManager activityManager;
    private LocalDate currentDate;
    private int currentNumWeeks;
    private ArrayList<DayPanel> dayPanels;

    public CalendarPanel() {
        this.activityManager = ActivityManager.getInstance();
        this.currentDate = LocalDate.now().minusDays(7);
        this.currentNumWeeks = DEFAULT_NUM_WEEKS;
        this.resetDayPanels();
        this.setPreferredSize(new Dimension(DEFAULT_PREFERRED_WIDTH, this.currentNumWeeks * 100));
    }

    /**
     * Resets dayPanels field for the current date and number of weeks.
     */
    private void resetDayPanels() {
        this.dayPanels = new ArrayList<>();

        this.removeAll();
        this.setLayout(new GridLayout(this.currentNumWeeks, 7));

        LocalDate current = firstSundayOnOrBefore(currentDate);
        for (int weekIndex = 0; weekIndex < this.currentNumWeeks; weekIndex++) {
            for (int dayOfWeekIndex = 0; dayOfWeekIndex < 7; dayOfWeekIndex++) {
                DayPanel day = new DayPanel(current);
                day.overwriteActivities(this.activityManager.getActivitiesInRange(current, current));
                this.dayPanels.add(day);
                current = current.plusDays(1);
            }
        }

        for (DayPanel dayPanel : this.dayPanels) {
            this.add(dayPanel);
        }

        this.repaint();
    }

    public void refreshActivities() {
        this.resetDayPanels();
    }

    /**
     * Computes the most recent Sunday on or before the given date.
     *
     * @param date the date from which to search backward for a Sunday
     * @return the most recent Sunday
     */
    private static LocalDate firstSundayOnOrBefore(LocalDate date) {
        LocalDate prev = date.minusDays(0);
        while (prev.getDayOfWeek() != DayOfWeek.SUNDAY) {
            prev = prev.minusDays(1);
        }

        return prev;
    }

}

/**
 * Class representing a single day in the calendar GUI.
 */
class DayPanel extends JPanel {
    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_HEIGHT = 100;

    private LocalDate date;
    private ArrayList<Activity> activities;
    private JPanel activityPanel;

    public DayPanel(LocalDate date) {
        super();
        this.date = date;
        this.activities = new ArrayList<>();
        this.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        this.setLayout(new FlowLayout());
        this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

        this.add(new JLabel(this.date.toString()));
        this.activityPanel = new JPanel(new FlowLayout());
        this.add(this.activityPanel);
    }

    public void overwriteActivities(ArrayList<Activity> newActivities) {
        this.activities.clear();
        this.activities.addAll(newActivities);
        this.updateActivityPanel();
    }

    private void updateActivityPanel() {
        this.activityPanel.removeAll();
        for (Activity activity : this.activities) {
            this.activityPanel.add(this.buildActivityComponent(activity));
        }
        this.repaint();
    }

    private JComponent buildActivityComponent(Activity activity) {
        JButton activityButton = new JButton(activity.getShortDescription());
        activityButton.addActionListener(null); // TODO: Set up action listeners for activity buttons
        return activityButton;
    }

}