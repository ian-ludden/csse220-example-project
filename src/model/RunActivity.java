import java.time.LocalDate;

/**
 * Class representing a running training session.
 *
 * @author Ian Ludden (luddenig)
 */
public class RunActivity extends Activity {
    private Equipment shoePair = null;

    public RunActivity(String title) {
        super(title);
    }

    public RunActivity(String title, int timeSec, double distanceKM, LocalDate date) {
        super(title, timeSec, distanceKM, date);
    }

    public RunActivity(String title, int timeSec) {
        super(title, timeSec);
    }

    public RunActivity(String title, double distanceKM) {
        super(title, distanceKM);
    }

    @Override
    public String toString() {
        return super.toString() + ", shoePair=" + this.shoePair;
    }

    public void setShoePair(Equipment shoePair) {
        this.shoePair = shoePair;
    }

    @Override
    public Equipment getEquipment() {
        return this.shoePair;
    }
}
