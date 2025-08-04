import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that organizes Activity objects in a calendar data model to support date-based activity retrieval.
 *
 * @author Ian Ludden (luddenig)
 */
public class ActivityManager {

    private HashMap<LocalDate, ArrayList<Activity>> dateToActivitiesMap;

    /**
     * Constructs a new ActivityManager with an empty map of dates to activities.
     */
    public ActivityManager() {
        this.dateToActivitiesMap = new HashMap<>();
    }

    /**
     * Adds the given activity to its specified date.
     *
     * @param activity the new Activity to add
     */
    public void addActivity(Activity activity) {
        LocalDate date = activity.getDate();
        if (!this.dateToActivitiesMap.containsKey(date)) {
            ArrayList<Activity> emptyActList = new ArrayList<>();
            this.dateToActivitiesMap.put(date, emptyActList);
        }

        this.dateToActivitiesMap.get(date).add(activity);
    }

    /**
     * Removes the given activity from its date's list.
     *
     * @param activity the Activity to remove
     * @throws ActivityNotFoundException if the date's list has no activities.
     */
    public void deleteActivity(Activity activity) throws ActivityNotFoundException {
        LocalDate date = activity.getDate();

        if (!this.dateToActivitiesMap.containsKey(date)) {
            throw new ActivityNotFoundException(activity, "Date " + date + " has no known activities, cannot delete " + activity);
        }

        ArrayList<Activity> dateActs = this.dateToActivitiesMap.get(date);
        boolean isRemoved = dateActs.remove(activity);
        if (!isRemoved) {
            throw new ActivityNotFoundException(activity, "Date " + date + " does not contain " + activity + ". Perhaps it was already removed?");
        }
    }

    /**
     * Moves the given activity from its current date to the given new date.
     * <br>
     * NOTE: This method mutates the given Activity object, changing its date.
     *
     * @param activity the Activity to move
     * @param newDate the activity's updated date
     * @throws ActivityNotFoundException if the activity is not present for its current date
     */
    public void moveActivity(Activity activity, LocalDate newDate) throws ActivityNotFoundException {
        deleteActivity(activity);
        activity.setDate(newDate);
        addActivity(activity);
    }

    /**
     * Computes the total number of activities,
     * optionally filtered to remove those planned but not yet completed.
     *
     * @param completedOnly if true, planned activities are not counted;
     *                      if false, all activities (both planned and completed) are included
     * @return the number of (completed) activities
     */
    public int getNumActivities(boolean completedOnly) {
        int numActivities = 0;
        for (LocalDate date : this.dateToActivitiesMap.keySet()) {
            for (Activity activity : this.dateToActivitiesMap.get(date)) {
                if (!completedOnly || activity.isComplete()) {
                    numActivities++;
                }
            }
        }

        return numActivities;
    }

    /**
     * Retrieves a list of all activities between the given dates (inclusive).
     * <br>
     * If startDate and endDate match, returns the list of activities for that date.
     * <br>
     * If endDate is <em>before</em> startDate, silently returns an empty list.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return a list of all activities between startDate and endDate, inclusive
     */
    public ArrayList<Activity> getActivitiesInRange(LocalDate startDate, LocalDate endDate) {
        LocalDate stopDate = endDate.plusDays(1);
        ArrayList<Activity> activities = new ArrayList<>();

        // Loop structure adapted from https://www.baeldung.com/java-iterate-date-range
        for (LocalDate date = startDate; date.isBefore(stopDate); date = date.plusDays(1)) {
            activities.addAll(this.dateToActivitiesMap.getOrDefault(date, new ArrayList<>()));
        }

        return activities;
    }

}
