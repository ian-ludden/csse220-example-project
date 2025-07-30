/**
 * Class representing a piece of training equipment, such as a pair of shoes or bicycle.
 *
 * @author Ian Ludden (luddenig)
 */
public class Equipment {

    private String name;
    private double totalDistanceKM;
    private int totalTimeSec;

    /**
     *
     * @param name the user-provided name of this piece of equipment
     */
    public Equipment(String name) {
        this.name = name;
        this.totalDistanceKM = 0.0;
        this.totalTimeSec = 0;
    }

    /**
     * Adds the time and/or distance of the given activity to the total(s) for this piece of equipment.
     *
     * @param newActivity the new activity logged using this piece of equipment
     */
    public void addActivity(Activity newActivity) {
        double newActivityDistance = newActivity.getDistanceKM();
        if (newActivityDistance != Activity.EMPTY_DISTANCE) {
            this.totalDistanceKM += newActivityDistance;
        }
        int newActivityTime = newActivity.getTimeSec();
        if (newActivityTime != Activity.EMPTY_TIME) {
            this.totalTimeSec += newActivityTime;
        }
    }

    /**
     * Removes the time and/or distance of the given activity from the total(s) for this piece of equipment.
     *
     * @param oldActivity the activity previously associated with this piece of equipment, to be removed
     */
    public void removeActivity(Activity oldActivity) {

    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ": " +
                "name='" + name + '\'' +
                ", totalDistanceKM=" + totalDistanceKM +
                ", totalTimeSec=" + totalTimeSec;
    }

    public double getTotalDistanceKM() {
        return totalDistanceKM;
    }
}
