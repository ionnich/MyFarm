package main;

/**
 * FarmerType represents the different types of farmers in the game.
 * 
 * @version 1.0
 */
public class FarmerType {

    // name for the type of farmer
    private String type;

    private int levelRequirement;
    private double bonusEarnings;
    private double seedCostReduction;
    private int waterMaxIncrease;
    private int fertilizerMaxIncrease;
    private double registrationFee;

    /**
     * Creates a new unregistered farmer type object
     */
    public FarmerType() {

        this.type = "Unregistered";
        this.levelRequirement = 0;
        this.bonusEarnings = 0;
        this.seedCostReduction = 0;
        this.waterMaxIncrease = 0;
        this.registrationFee = 0;
    }

    /**
     * Creates a new farmer type object based on the supplied type
     * 
     * @param type the name of the farmer type
     */
    public FarmerType(String type) {

        if (type == "Registered") {

            this.type = "Registered";
            this.levelRequirement = 5;
            this.bonusEarnings = 1;
            this.seedCostReduction = -1;
            this.waterMaxIncrease = 0;
            this.fertilizerMaxIncrease = 0;
            this.registrationFee = 200;
        }

        else if (type == "Distinguished") {

            this.type = "Distinguished";
            this.levelRequirement = 10;
            this.bonusEarnings = 2;
            this.seedCostReduction = -2;
            this.waterMaxIncrease = 1;
            this.fertilizerMaxIncrease = 0;
            this.registrationFee = 300;
        }

        else if (type == "Legendary") {

            this.type = "Legendary";
            this.levelRequirement = 15;
            this.bonusEarnings = 3;
            this.seedCostReduction = -3;
            this.waterMaxIncrease = 2;
            this.fertilizerMaxIncrease = 1;
            this.registrationFee = 400;
        }

        else
            System.out.println(type + " is not a valid FarmerType");
    }

    public String getType() {
        return type;
    }

    public int getLevelRequirement() {
        return levelRequirement;
    }

    public double getBonusEarnings() {
        return bonusEarnings;
    }

    public double getSeedCostReduction() {
        return seedCostReduction;
    }

    public int getWaterMaxIncrease() {
        return waterMaxIncrease;
    }

    public int getFertilizerMaxIncrease() {
        return fertilizerMaxIncrease;
    }

    public double getRegistrationFee() {
        return registrationFee;
    }
}