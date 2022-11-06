package main;

/**
 * A seed creates a crop and holds/dictates its corresponding attributes
 * 
 * @version 1.0
 */
public class Seed {

    // array of all possible seedNames
    private String[] seedTable = {
            "Turnip", "Carrot", "Potato", "Rose", "Tulips", "Sunflower", "Mango", "Apple"
    };

    // make hashtable of seedNames and their corresponding methods

    private String name;
    private double cost;
    private int harvestMin;
    private int harvestMax;

    private int daysToHarvest;

    // water attributes
    private int waterNeeds; // the minimum required water level for the crop to not wither
    private int waterMax;

    // fertilizer attributes
    private int fertilizerNeeds; // the minimum required fertilizer level for the crop to not wither
    private int fertilizerMax;

    // market attributes
    private double baseSellPrice;
    private double expGain;

    /**
     * Creates a new seed object depending on the supplied seedName
     * 
     * @param name the name of the desired seed
     * @throws Exception if the seedName is not found in the seedList
     */
    public Seed(String name) throws Exception {

        boolean exists = false;
        int index = -1;

        // find name in seedTable
        for (int i = 0; i < seedTable.length; i++) {
            if (seedTable[i].equals(name)) {
                index = i;
                exists = true;
                this.name = name;
                // end loop
                break;
            }
        }

        if (!exists) {
            // throw exception
            throw new Exception("Seed does not exist");
        }

        // call the corresponding method

        switch (index) {
            case 0:
                makeTurnip();
                break;
            case 1:
                makeCarrot();
                break;
            case 2:
                makePotato();
                break;
            case 3:
                makeRose();
                break;
            case 4:
                makeTulips();
                break;
            case 5:
                makeSunflower();
                break;
            case 6:
                makeMango();
                break;
            case 7:
                makeApple();
                break;
            default:
                makeDefault();
                throw new Exception("Seed does not exist");
        }
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public int getHarvestMin() {
        return harvestMin;
    }

    public int getHarvestMax() {
        return harvestMax;
    }

    public int getTimeToHarvest() {
        return daysToHarvest;
    }

    public int getWaterNeeds() {
        return waterNeeds;
    }

    public int getWaterMax() {
        return waterMax;
    }

    public int getFertilizerNeeds() {
        return fertilizerNeeds;
    }

    public int getFertilizerMax() {
        return fertilizerMax;
    }

    public double getBaseSellPrice() {
        return baseSellPrice;
    }

    public double getExpGain() {
        return expGain;
    }

    /**
     * changes the attributes of the seed to default
     */
    private void makeDefault() {

        this.name = null;
        this.cost = 0;
        this.harvestMin = 0;
        this.harvestMax = 0;
        this.daysToHarvest = 0;
        this.waterNeeds = 0;
        this.waterMax = 0;
        this.fertilizerNeeds = 0;
        this.fertilizerMax = 0;
        this.baseSellPrice = 0;
        this.expGain = 0;
    }

    /**
     * changes the attributes of the seed to a turnip
     */
    private void makeTurnip() {

        this.daysToHarvest = 2;
        this.waterNeeds = 1;
        this.waterMax = 2;
        this.fertilizerNeeds = 0;
        this.fertilizerMax = 1;
        this.harvestMin = 1;
        this.harvestMax = 2;
        this.cost = 5;
        this.baseSellPrice = 6;
        this.expGain = 5;
    }

    /**
     * changes the attributes of the seed to a carrot
     */
    private void makeCarrot() {

        this.daysToHarvest = 3;
        this.waterNeeds = 1;
        this.waterMax = 2;
        this.fertilizerNeeds = 0;
        this.fertilizerMax = 1;
        this.harvestMin = 1;
        this.harvestMax = 2;
        this.cost = 10;
        this.baseSellPrice = 9;
        this.expGain = 7.5;
    }

    /**
     * changes the attributes of the seed to a potato
     */
    private void makePotato() {

        this.daysToHarvest = 5;
        this.waterNeeds = 3;
        this.waterMax = 4;
        this.fertilizerNeeds = 1;
        this.fertilizerMax = 2;
        this.harvestMin = 1;
        this.harvestMax = 10;
        this.cost = 20;
        this.baseSellPrice = 3;
        this.expGain = 12.5;
    }

    /**
     * changes the attributes of the seed to a rose
     */
    private void makeRose() {

        this.daysToHarvest = 1;
        this.waterNeeds = 1;
        this.waterMax = 2;
        this.fertilizerNeeds = 0;
        this.fertilizerMax = 1;
        this.harvestMin = 1;
        this.harvestMax = 1;
        this.cost = 5;
        this.baseSellPrice = 5;
        this.expGain = 2.5;
    }

    /**
     * changes the attributes of the seed to a tulip
     */
    private void makeTulips() {

        this.daysToHarvest = 2;
        this.waterNeeds = 2;
        this.waterMax = 3;
        this.fertilizerNeeds = 0;
        this.fertilizerMax = 1;
        this.harvestMin = 1;
        this.harvestMax = 1;
        this.cost = 10;
        this.baseSellPrice = 9;
        this.expGain = 5;
    }

    /**
     * changes the attributes of the seed to a sunflower
     */
    private void makeSunflower() {

        this.daysToHarvest = 3;
        this.waterNeeds = 2;
        this.waterMax = 3;
        this.fertilizerNeeds = 1;
        this.fertilizerMax = 2;
        this.harvestMin = 1;
        this.harvestMax = 1;
        this.cost = 20;
        this.baseSellPrice = 19;
        this.expGain = 7.5;
    }

    /**
     * changes the attributes of the seed to a mango
     */
    private void makeMango() {

        this.daysToHarvest = 10;
        this.waterNeeds = 7;
        this.waterMax = 7;
        this.fertilizerNeeds = 4;
        this.fertilizerMax = 4;
        this.harvestMin = 5;
        this.harvestMax = 15;
        this.cost = 100;
        this.baseSellPrice = 8;
        this.expGain = 25;
    }

    /**
     * changes the attributes of the seed to an apple
     */
    private void makeApple() {

        this.daysToHarvest = 10;
        this.waterNeeds = 7;
        this.waterMax = 7;
        this.fertilizerNeeds = 5;
        this.fertilizerMax = 5;
        this.harvestMin = 10;
        this.harvestMax = 15;
        this.cost = 200;
        this.baseSellPrice = 8;
        this.expGain = 25;
    }

}
