package main;

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

    public Crop(Seed seed) {

        this.source = seed;
        this.harvestCountdown = seed.getTimeToHarvest();
        this.cropName = seed.getName();
        this.marketPrice = seed.getBaseSellPrice();
        this.expGain = seed.getExpGain();
        this.harvestRange = new HarvestRange(seed.getHarvestMin(), seed.getHarvestMax());
    }


    public double getMarketPrice(){

        return this.marketPrice;
    }

    public double getWaterMax(){

        return this.source.getWaterMax();
    }

    public double getExpGain(){

        return this.expGain;
    }

    public Report witherPlant() {

        this.isWithered = true;

        return new Report(true, this.cropName + "plant has withered");
    }

    public int getWaterLevel() {
        return waterLevel;
    }

    public Report addWater(int modifier) {

        if(this.waterLevel < this.source.getWaterMax() + modifier){
                
            this.waterLevel++;
            return new Report(true, "Water level increased");
        }

        return new Report(false, "Water level is already at max");
    }

    public Report addFertilizer(int modifier){

        if(this.fertilizerLevel < this.source.getFertilizerMax() + modifier){

            this.fertilizerLevel++;
            return new Report(true, "added fertilizer");
        }

        return new Report(false, "fertilizer level is already maxed");
    }

    public String getCropType() {
        return cropType;
    }

    public int getYield() {
        return harvestRange.getRandom();
    }

    public int getHarvestCountdown() {
        return harvestCountdown;
    }

    public boolean isWithered() {
        return isWithered;
    }

    public boolean isHarvestable() {
        return isHarvestable;
    }

    public int getFertilizerLevel() {
        return fertilizerLevel;
    }

    public int getFertilizerMax() {
        return this.source.getFertilizerMax();
    }


    // advances the growth of the crop by one day
    // if the crop is ready to harvest, sets the isHarvestable flag to true
    // if the crop is withered, sets the isWithered flag to true
    public Report growCrop() {

        // decrement harvestDay
        this.harvestCountdown--;

        if(this.isHarvestable && this.harvestCountdown < 0){
            this.witherPlant();
            return new Report(false, "Crop has withered");
        }

        String witherReason = "";
        String witherWater = "";
        String witherFertilizer = "";


        // check if water needs are met on harvestDay
        if (this.waterLevel < this.source.getWaterNeeds()) {
            if(this.harvestCountdown <= 0) {
                this.witherPlant();

                witherWater = "water needs not met on harvest day";
            }
        }

        // check if fertilizer needs are met on harvestDay
        if (this.fertilizerLevel < this.source.getFertilizerNeeds()) {
            if(this.harvestCountdown <= 0) {
                this.witherPlant();

                witherFertilizer = "fertilizer needs not met on harvest day";
            }
        }

        if(witherWater != "" && witherFertilizer != "")
            witherWater += " and ";

        witherReason = witherWater + witherFertilizer;

        if(this.isWithered)
            return new Report(false, this.cropName + " plant has withered because " + witherReason);

        // check if harvestDay
        if (this.harvestCountdown == 0) {
            this.isHarvestable = true;
            return new Report(true, "Crop is ready to harvest");
        }


        return new Report(true, "Crop has grown");

    }
    
}