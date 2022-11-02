package main;

public class Crop {

    // organic attributes
    private String cropType;
    private String name;
    private HarvestRange harvestRange;
    private int harvestCountdown;
    
    // flag for whether the crop has been withered or not
    boolean isWithered = false;
    // flag for whether the crop is ready for harvest or not
    boolean isHarvestable = false;

    // water attributes
    private int waterLevel;
    private int waterNeeds; // the minimum required water level for the crop to not wither
    private int waterMax;

    // fertilizer attributes
    private int fertilizerLevel;
    private int fertilizerNeeds; // the minimum required fertilizer level for the crop to not wither
    private int fertilizerMax;

    // market attributes
    private int baseSellPrice;
    private int expGain;

    public Crop(String name, String type, int waterNeeds, int fertilizerNeeds, int baseSellPrice, int expGain, int waterMax, int fertilizerMax, int harvestMin, int harvestMax, int harvestCountdown) {

        this.name = name;
        this.cropType = type;
        this.waterNeeds = waterNeeds;
        this.fertilizerNeeds = fertilizerNeeds;
        this.baseSellPrice = baseSellPrice;
        this.expGain = expGain;
        this.waterMax = waterMax;
        this.fertilizerMax = fertilizerMax;
        this.harvestRange = new HarvestRange(harvestMin, harvestMax);
        this.harvestCountdown = harvestCountdown;

        this.waterLevel = 0;
        this.fertilizerLevel = 0;
    }


    public Report witherPlant() {

        this.isWithered = true;

        return new Report(true, this.name + "plant has withered");
    }

    public int getWaterLevel() {
        return waterLevel;
    }

    public Report addWater() {

        if (this.waterLevel < this.waterMax) {
            this.waterLevel++;
        }

        return new Report(true, "added water");
    }

    public Report addFertilizer(){

        if (this.fertilizerLevel < this.fertilizerMax) {
            this.fertilizerLevel++;
        }

        return new Report(true, "added fertilizer");
    }

    public int getWaterNeeds() {
        return waterNeeds;
    }

    public int getWaterMax() {
        return waterMax;
    }

    public int getFertilizerLevel() {
        return fertilizerLevel;
    }

    public int getFertilizerNeeds() {
        return fertilizerNeeds;
    }

    public int getFertilizerMax() {
        return fertilizerMax;
    }

    public int getBaseSellPrice() {
        return baseSellPrice;
    }

    public int getExpGain() {
        return expGain;
    }

    public String getCropType() {
        return cropType;
    }

    public String getSeedName() {
        return name;
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

    
}