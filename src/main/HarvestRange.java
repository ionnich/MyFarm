package main;

/**
 * HarvestRange represents the minimum and maximum harvest amount of a crop.
 * 
 * @version 1.0
 */
public class HarvestRange {

    private int max;
    private int min;

    /**
     * Creates a new harvest range object
     * 
     * @param min the minimum harvest amount
     * @param max the maximum harvest amount
     */
    public HarvestRange(int min, int max) {
        this.max = max;
        this.min = min;
    }

    /**
     * @return a random harvest amount within the range
     */
    public int getRandom() {

        return (int) (Math.random() * (max - min + 1) + min);
    }

}
