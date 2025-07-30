import java.time.LocalDate;

/**
 * Class representing a swimming training session.
 *
 * @author Ian Ludden (luddenig)
 */
public class SwimActivity extends Activity {
    public SwimActivity(String title) {
        super(title);
    }

    public SwimActivity(String title, int timeSec, double distanceKM, LocalDate date) {
        super(title, timeSec, distanceKM, date);
    }

    public SwimActivity(String title, int timeSec) {
        super(title, timeSec);
    }

    public SwimActivity(String title, double distanceKM) {
        super(title, distanceKM);
    }

    @Override
    public Equipment getEquipment() {
        return null; // SwimActivity has no equipment
    }
}
