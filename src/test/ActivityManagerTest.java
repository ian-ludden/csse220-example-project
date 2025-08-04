import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test cases for the ActivityManager class.
 *
 * @author Ian Ludden (luddenig)
 */
public class ActivityManagerTest {

    private static ActivityManager manager = ActivityManager.getInstance();
    /**
     * Initial activities to add to manager.
     */
    private static final Activity[] INIT_ACTIVITIES = {
            new RunActivity("Morning Run", 2400, 10, LocalDate.parse("1999-08-01")),
            new BikeActivity("Morning Ride", 2400, 30, LocalDate.parse("1999-08-02")),
            new SwimActivity("Evening Swim", 2400, 2, LocalDate.parse("1999-08-02")),
            new RunActivity("Afternoon Run", 15.4),
            new RunActivity("Lunch Run", 2400, 10, LocalDate.parse("1999-08-05")),
            new BikeActivity("Evening Ride", 2400, 30, LocalDate.parse("1999-08-05")),
            new SwimActivity("Morning Swim", 1500),
            new RunActivity("Evening Run", 8.1)
    };

    public static void loadInitialActivities() {
        manager = ActivityManager.getInstance();
        manager.clear();
        for (Activity act : INIT_ACTIVITIES) {
            manager.addActivity(act);
        }
    }

    @Test
    public void testGetNumActivities() {
        assertEquals(0, manager.getNumActivities(false));
        assertEquals(0, manager.getNumActivities(true));

        loadInitialActivities();

        assertEquals(8, manager.getNumActivities(false));
        assertEquals(5, manager.getNumActivities(true));
    }

    @Test
    public void testGetActivitiesInRange() {
        // NOTE: These test cases assumes that today (i.e., LocalDate.now()) is not in the year 1999.
        loadInitialActivities();

        ArrayList<Activity> activitiesInFirstTwoDaysAug1999 = manager.getActivitiesInRange(
                LocalDate.parse("1999-08-01"), LocalDate.parse("1999-08-02"));
        assertEquals(3, activitiesInFirstTwoDaysAug1999.size());

        ArrayList<Activity> activitiesIn1999 = manager.getActivitiesInRange(
                LocalDate.parse("1999-01-01"), LocalDate.parse("1999-12-31"));
        assertEquals(5, activitiesIn1999.size());

        manager.moveActivity(INIT_ACTIVITIES[0], LocalDate.now()); // assumed to be NOT in 1999
        activitiesIn1999 = manager.getActivitiesInRange(
                LocalDate.parse("1999-01-01"), LocalDate.parse("1999-12-31"));
        assertEquals(4, activitiesIn1999.size());

        ArrayList<Activity> activitiesInLateAug1999 = manager.getActivitiesInRange(
                LocalDate.parse("1999-08-16"), LocalDate.parse("1999-08-31"));
        assertEquals(0, activitiesInLateAug1999.size());
    }

}
