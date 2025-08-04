import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for the Activity abstract class and its subclasses.
 *
 * @author Ian Ludden (luddenig)
 */
public class ActivityTest {

    @Test
    public void testActivityTitle() {
        String title = "Morning Run";
        Activity activity = new RunActivity(title);
        assertEquals(title, activity.getTitle());
    }

    @Test
    public void testActivityDefaultDate() {
        Activity activity = new RunActivity("Morning Run");
        assertEquals(activity.getDate(), LocalDate.now());
    }

    @Test
    public void testGetPace() {
        Activity a = new RunActivity("Run", 2400, 10, LocalDate.now());
        assertEquals(240, a.getPace(DistanceUnit.KILOMETERS));
        assertEquals(386, a.getPace(DistanceUnit.MILES));
    }

    @Test
    public void testIsComplete() {
        Activity a = new RunActivity("Run", 2400, 10, LocalDate.now());
        assertTrue(a.isComplete());

        Activity b = new BikeActivity("Ride", 400);
        assertFalse(b.isComplete());

        Activity c = new SwimActivity("Swim", 3.5);
        assertFalse(c.isComplete());
        c.setTimeSec(1000);
        assertTrue(c.isComplete());
    }

}
