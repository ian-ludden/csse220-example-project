import java.time.LocalDate;

/**
 * This class represents an individual training activity, such as a run, bicycle ride, or swimming session. It stores a title, time, and distance, with support for computing pace per unit distance.
 *
 * @author Ian Ludden (luddenig)
 */
public abstract class Activity {
    public static int EMPTY_TIME = -1;
    public static double EMPTY_DISTANCE = -1.0;
    public static int EMPTY_PACE = 0;
    public static double KM_PER_MILE = 1.60934;

    private String title;
    private int timeSec;
    private double distanceKM;
    private LocalDate date;

    /**
     * Constructs a new activity with the given title and placeholder values for time, distance, and date (default: today).
     *
     * @param title the activity's title, e.g., "Afternoon Run"
     */
    public Activity(String title) {
        this.title = title;
        this.timeSec = EMPTY_TIME;
        this.distanceKM = EMPTY_DISTANCE;
        this.date = LocalDate.now();
    }

    /**
     * Constructs a new activity with the given title, time, and distance.
     *
     * @param title the activity's title, e.g., "Afternoon Run"
     * @param timeSec the activity's time, in seconds, e.g., 3000 for a 50-minute run
     * @param distanceKM the activity's distance, in kilometers, e.g., 5.0 for a 5k run
     * @param date the activity's date, e.g., 07/30/2025
     */
    public Activity(String title, int timeSec, double distanceKM, LocalDate date) {
        this(title);
        this.setTimeSec(timeSec);
        this.setDistanceKM(distanceKM);
        this.setDate(date);
    }

    public Activity(String title, int timeSec) {
        this(title);
        this.setTimeSec(timeSec);
    }

    public Activity(String title, double distanceKM) {
        this(title);
        this.setDistanceKM(distanceKM);
    }

    /**
     * Checks whether the activity is complete (i.e., has both a time and distance) or is only planned (i.e., is missing one or both fields).
     *
     * @return true if this activity is complete, false if it is planned
     */
    public boolean isComplete() {
        return (this.timeSec != EMPTY_TIME)
                && (this.distanceKM != EMPTY_DISTANCE);
    }

    /**
     * Computes the pace of this activity with respect to the given distance units. If the activity is only planned and has not yet been completed, this returns EMPTY_PACE instead.
     *
     * @param units distance units (mi or km)
     * @return this activity's pace, in sec/mi or sec/km, or EMPTY_PACE if the activity is only planned (not completed)
     */
    public int getPace(DistanceUnit units) {
        if (!isComplete()) {
            return EMPTY_PACE;
        }

        if (units == DistanceUnit.KILOMETERS) {
            return (int)(this.timeSec / this.distanceKM);
        }

        if (units == DistanceUnit.MILES) {
            return (int)(this.timeSec / (this.distanceKM / KM_PER_MILE));
        }

        throw new IllegalArgumentException("Received distance units " + units + ", but only miles and kilometers are supported.");
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ": " +
                "title='" + title + '\'' +
                ", timeSec=" + timeSec +
                ", distanceKM=" + distanceKM +
                ", date=" + date;
    }

    /**
     * Returns the equipment (if any) used as part of this activity.
     * @return the Equipment object for this activity, or null if none was used
     */
    public abstract Equipment getEquipment();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTimeSec() {
        return timeSec;
    }

    public void setTimeSec(int timeSec) {
        this.timeSec = timeSec;
    }

    public double getDistanceKM() {
        return distanceKM;
    }

    public void setDistanceKM(double distanceKM) {
        this.distanceKM = distanceKM;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getShortDescription() {
        return this.title + " - " + this.timeSec + " sec, " + this.distanceKM + "km";
    }
}