import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for the Equipment abstract class and its subclasses.
 *
 * @author Ian Ludden (luddenig)
 */
public class EquipmentTest {

    @Test
    public void testEquipmentName() {
        String testBikeName = "Cannondale";
        Bicycle bike = new Bicycle(testBikeName);
        assertTrue(bike.toString().contains(testBikeName));

        String testShoesName = "Saucony Kinvara";
        ShoePair shoes = new ShoePair(testShoesName);
        assertTrue(shoes.toString().contains(testShoesName));
    }

    @Test
    public void testAddActivity() {
        ShoePair shoes = new ShoePair("Saucony Kinvara");
        int runTime = 2416;
        double runDistance = 15.4;
        shoes.addActivity(new RunActivity("Progressive Run", runTime, runDistance, LocalDate.now()));

        assertEquals(runDistance, shoes.getTotalDistanceKM());
        assertFalse(shoes.isEndOfLife());

        runDistance = 1234.5;
        shoes.addActivity(new RunActivity("Long Run", runTime, runDistance, LocalDate.now().plusDays(2)));
        assertTrue(shoes.isEndOfLife());

        Bicycle bike = new Bicycle("Trek", 2500, 80000, LocalDate.now().minusDays(4));
        bike.addActivity(new BikeActivity("Long Ride", 987654321, 150000.1, LocalDate.now().minusDays(3)));
        assertTrue(bike.isMaintenanceDue());
        bike.resetMaintenanceReminders(LocalDate.now());
        assertFalse(bike.isMaintenanceDue());

    }

}
