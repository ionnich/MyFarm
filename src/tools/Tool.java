package tools;

/**
 * Tool is a wrapper class that serves to encapsulate cost and expGain
 * 
 * @version 1.0
 */
public class Tool {
    protected double cost;
    protected double expGain;

    /**
     * @return double the cost of the tool
     */
    public double getCost() {
        return cost;
    }

    /**
     * @return double the expGain of the tool
     */
    public double getExpGain() {
        return expGain;
    }

}