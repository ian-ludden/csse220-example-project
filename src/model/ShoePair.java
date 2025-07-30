/**
 * Class representing a pair of running shoes.
 *
 * @author Ian Ludden (luddenig)
 */
public class ShoePair extends Equipment {
    public static final int DEFAULT_LIFE_EXPECTANCY_KM = 650;

    private int lifeExpectancyKM;

    /**
     * @param name the user-provided name of this piece of equipment
     */
    public ShoePair(String name) {
        super(name);
        this.lifeExpectancyKM = DEFAULT_LIFE_EXPECTANCY_KM;
    }

    public ShoePair(String name, int lifeExpectancyKM) {
        super(name);
        this.lifeExpectancyKM = lifeExpectancyKM;
    }

    /**
     * Checks whether it is time to replace this pair of shoes.
     *
     * @return true if this pair has reached its life expectancy, false otherwise
     */
    public boolean isEndOfLife() {
        return this.getTotalDistanceKM() >= this.lifeExpectancyKM;
    }

    public void setLifeExpectancyKM(int newLifeExpectancyKM) {
        this.lifeExpectancyKM = newLifeExpectancyKM;
    }
}
