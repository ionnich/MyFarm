package main;

public class HarvestRange {

    private int max;
    private int min;

    public HarvestRange(int min, int max) {
        this.max = max;
        this.min = min;
    }

    public int getRandom(){

        return (int) (Math.random() * (max - min + 1) + min);
    }

}
