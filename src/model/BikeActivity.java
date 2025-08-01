import java.time.LocalDate;

/**
 * Class representing a bicycling training session.
 *
 * @author Ian Ludden (luddenig)
 */
public class BikeActivity extends Activity {
    private Equipment bicycle = null;

    public BikeActivity(String title) {
        super(title);
        this.bicycle = null;
    }

    public BikeActivity(String title, int timeSec, double distanceKM, LocalDate date) {
        super(title, timeSec, distanceKM, date);
    }

    public BikeActivity(String title, int timeSec) {
        super(title, timeSec);
    }

    public BikeActivity(String title, double distanceKM) {
        super(title, distanceKM);
    }

    public void setBicycle(Bicycle bicycle) {
        this.bicycle = bicycle;
    }

    @Override
    public Equipment getEquipment() {
        return null;
    }

    @Override
    public String toString() {
        return super.toString() + ", bicycle=" + this.bicycle;
    }
}
