/**
 * Custom exception for when activity deletion or movement fails
 * because the given activity is not present at the expected date in the model.
 *
 * @author Ian Ludden (luddenig)
 */
public class ActivityNotFoundException extends IllegalArgumentException {

  private Activity activity;

  public ActivityNotFoundException(Activity activity, String message) {
    super(message);
    this.activity = activity;
  }
}