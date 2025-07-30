import java.time.LocalDate;

/**
 * Class representing a bicycle.
 *
 * @author Ian Ludden (luddenig)
 */
public class Bicycle extends Equipment {
    public static final int DEFAULT_MAINTENANCE_REMINDER_KM = 3000;
    public static final int DEFAULT_MAINTENANCE_REMINDER_SEC = 86400; // 24 hours

    private int maintenanceReminderKM;
    private int maintenanceReminderSec;
    private int remainingKM;
    private int remainingSec;

    private LocalDate lastServiceDate;


    /**
     * @param name the user-provided name of this piece of equipment
     */
    public Bicycle(String name) {
        super(name);
        this.maintenanceReminderKM = DEFAULT_MAINTENANCE_REMINDER_KM;
        this.maintenanceReminderSec = DEFAULT_MAINTENANCE_REMINDER_SEC;
        this.resetMaintenanceReminders(LocalDate.now());
    }

    public Bicycle(String name, int maintenanceReminderKM, int maintenanceReminderSec, LocalDate lastServiceDate) {
        this(name);
        this.setMaintenanceReminderKM(maintenanceReminderKM);
        this.setMaintenanceReminderSec(maintenanceReminderSec);
        this.setLastServiceDate(lastServiceDate);
        this.resetMaintenanceReminders(LocalDate.now());
    }

    /**
     * Checks whether maintenance is due, either because of distance or time.
     *
     * @return true if this bicycle needs maintenance, false otherwise
     */
    public boolean isMaintenanceDue() {
        return (this.remainingKM <= 0) || (this.remainingSec <= 0);
    }

    public void resetMaintenanceReminders(LocalDate serviceDate) {
        this.setLastServiceDate(serviceDate);
        this.remainingKM = this.maintenanceReminderKM;
        this.remainingSec = this.maintenanceReminderSec;
    }

    public int getMaintenanceReminderKM() {
        return maintenanceReminderKM;
    }

    public void setMaintenanceReminderKM(int maintenanceReminderKM) {
        this.maintenanceReminderKM = maintenanceReminderKM;
    }

    public int getMaintenanceReminderSec() {
        return maintenanceReminderSec;
    }

    public void setMaintenanceReminderSec(int maintenanceReminderSec) {
        this.maintenanceReminderSec = maintenanceReminderSec;
    }

    public LocalDate getLastServiceDate() {
        return lastServiceDate;
    }

    public void setLastServiceDate(LocalDate lastServiceDate) {
        this.lastServiceDate = lastServiceDate;
    }
}
