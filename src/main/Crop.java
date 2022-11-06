package main;

/**
 * A crop is an object that can be planted on a tile.
 * 
 * @version 1.0
 */
public class Crop {

    // organic attributes
    private String cropType;
    private String cropName;
    private Seed source;
    private int harvestCountdown;
    private double marketPrice;
    private double expGain;

    private HarvestRange harvestRange;

    // water attributes
    private int waterLevel;

    // fertilizer attributes
    private int fertilizerLevel;

    // FLAGS
    boolean isWithered = false; // flag for whether the crop has been withered or not
    boolean isHarvestable = false; // flag for whether the crop is ready for harvest or not

    /**
     * Creates a new crop object depending on the supplied seed
     * 
     * @param seed the seed to be planted
     */
    public Crop(Seed seed) {

        this.source = seed;
        this.harvestCountdown = seed.getTimeToHarvest();
        this.cropName = seed.getName();
        this.marketPrice = seed.getBaseSellPrice();
        this.expGain = seed.getExpGain();
        this.harvestRange = new HarvestRange(seed.getHarvestMin(), seed.getHarvestMax());

        if (seed.getName() == "Turnip" ||
                seed.getName() == "Carrot" ||
                seed.getName() == "Potato") {
            this.cropType = "Root Crop";
        } else if (seed.getName() == "Rose" ||
                seed.getName() == "Tulips" ||
                seed.getName() == "Sunflower") {
            this.cropType = "Flower";
        } else if (seed.getName() == "Mango" || seed.getName() == "Apple") {
            this.cropType = "Fruit Tree";
        }
    }

    public String getCropType() {
        return cropType;
    }

    public int getWaterLevel() {
        return waterLevel;
    }

    public double getMarketPrice() {

        return this.marketPrice;
    }

    public double getWaterMax() {

        return this.source.getWaterMax();
    }

    /**
     * @return the amount of exp the crop provides the farmer when harvested
     */
    public double getExpGain() {

        return this.expGain;
    }

    /**
     * @return a random number between the min and max harvest amount
     */
    public int getYield() {
        return harvestRange.getRandom();
    }

    /**
     * @return the time (in days) until the crop is ready for harvest
     */
    public int getHarvestCountdown() {
        return harvestCountdown;
    }

    /**
     * @return whether the crop has been withered or not
     */
    public boolean isWithered() {
        return isWithered;
    }

    /**
     * @return whether the crop can be harvested or not
     */
    public boolean isHarvestable() {
        return isHarvestable;
    }

    /**
     * @return the amount of fertilizer the crop currently has
     */
    public int getFertilizerLevel() {
        return fertilizerLevel;
    }

    /**
     * @return the amount of fertilizer the crop is able to hold
     */
    public int getFertilizerMax() {
        return this.source.getFertilizerMax();
    }

    /**
     * Withers a plant
     * 
     * @return whether the crop was successfully withered or not
     */
    public Report witherPlant() {

        this.isWithered = true;
        this.isHarvestable = false;

        return new Report(true, this.cropName + "plant has withered");
    }

    /**
     * Increases the water level of the crop
     * 
     * @param modifier changes the maximum amount of water a plant can hold
     * @return whether the plant was successfully watered or not
     */
    public Report addWater(int modifier) {

        if (this.waterLevel < this.source.getWaterMax() + modifier) {

            this.waterLevel++;
            if (this.fertilizerLevel == this.source.getWaterMax() + modifier) {
                return new Report(true, "Water bonus met!");
            }

            return new Report(true, "that plant is even wetter now!");
        }

        return new Report(false, "Water level is already at max");
    }

    /**
     * Increases the amount of fertilizer on the crop
     * 
     * @param modifier changes the maximum amount of fertilizer a plant can hold
     * @return whether the plant was successfully fertilized or not
     */
    public Report addFertilizer(int modifier) {

        if (this.fertilizerLevel < this.source.getFertilizerMax() + modifier) {

            this.fertilizerLevel++;
            if (this.fertilizerLevel == this.source.getFertilizerMax() + modifier)
                return new Report(true, "Fertilizer bonus met!");

            return new Report(true, "added fertilizer");
        }

        return new Report(false, "fertilizer level is already maxed");
    }

    /**
     * Advances the crop's growth by a day
     * 
     * @return whether the plant was successfully harvested or not
     */
    public Report growCrop() {

        // decrement harvestDay
        this.harvestCountdown--;

        if (this.isHarvestable && this.harvestCountdown < 0) {
            this.witherPlant();
            return new Report(false, "Crop has withered");
        }

        String witherReason = "";
        String witherWater = "";
        String witherFertilizer = "";

        // check if water needs are met on harvestDay
        if (this.waterLevel < this.source.getWaterNeeds() && this.harvestCountdown <= 0) {
            if (this.harvestCountdown <= 0) {
                this.witherPlant();

                witherWater = "water needs not met on harvest day";
            }
        }

        // check if fertilizer needs are met on harvestDay
        if (this.fertilizerLevel < this.source.getFertilizerNeeds()) {
            if (this.harvestCountdown <= 0) {
                this.witherPlant();

                witherFertilizer = "fertilizer needs not met on harvest day";
            }
        }

        if (!witherWater.equals("") && !witherFertilizer.equals(""))
            witherWater += " and ";

        witherReason = witherWater + witherFertilizer;

        if (this.isWithered) {
            return new Report(false, this.cropName + " plant has withered because " + witherReason);
        }

        // check if harvestDay
        if (this.harvestCountdown == 0) {
            this.isHarvestable = true;
            return new Report(true, "Crop is ready to harvest");
        }

        return new Report(true, "Crop has grown");
    }

}