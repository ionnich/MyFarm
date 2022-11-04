package main;

import java.util.ArrayList;

import tools.*;

/**
 * The farmer is the main actor in FarmerType
 * 
 * @version 1.0
 */
public class Farmer {

    FarmerType type;
    ArrayList<Seed> seedInventory;

    String name;

    double objectCoins;
    double currentExp = 0;
    int level = 0;

    int currentX = 0;
    int currentY = 0;

    // tools
    WateringCan wateringCan;
    Plow plow;
    Fertilizer fertilizer;
    Shovel shovel;
    Pickaxe pickaxe;

    /**
     * Creates a new farmer object
     */
    public Farmer() {

        this.name = "Adam Smith";
        this.type = new FarmerType();
        this.objectCoins = 100;

        this.seedInventory = new ArrayList<Seed>();
        this.wateringCan = new WateringCan();
        this.plow = new Plow();
        this.fertilizer = new Fertilizer();
        this.shovel = new Shovel();
        this.pickaxe = new Pickaxe();
    }

    /**
     * Creates a new farmer object with the specified name
     * 
     * @param name the name of the farmer
     */
    public Farmer(String name) {

        this.name = name;
        this.type = new FarmerType();
        this.objectCoins = 100;

        this.seedInventory = new ArrayList<Seed>();
        this.wateringCan = new WateringCan();
        this.plow = new Plow();
        this.fertilizer = new Fertilizer();
        this.shovel = new Shovel();
        this.pickaxe = new Pickaxe();
    }

    public double getCurrentExp() {
        return this.currentExp;
    }

    /**
     * @param expAmount the amount of exp to be added to the farmer's current exp
     */
    private void addExp(double expAmount) {
        this.currentExp += expAmount;
    }

    // get coordinates
    public int getCurrentX() {
        return this.currentX;
    }

    public int getCurrentY() {
        return this.currentY;
    }

    /**
     * Changes the farmer's current coordinates
     * 
     * @param x the new x coordinate
     * @param y the new y coordinate
     */
    public void gotoTile(int x, int y) {
        this.currentX = x;
        this.currentY = y;
    }

    /**
     * Purchases a seed from the farm and adds it to the farmer's inventory
     * 
     * @param seed the seed to be purchased
     * @param type the farmer's type (used to determine the price of the seed)
     * @return a rich message containing: true if the purchase was successful, false
     *         otherwise
     */
    public Report buySeed(Seed seed, FarmerType type) {

        // check if farmer has enough money
        if (this.objectCoins < seed.getCost() - type.getSeedCostReduction())
            return new Report(false, "You do not have enough money to buy this seed.");

        this.seedInventory.add(seed);
        this.objectCoins -= (seed.getCost() - type.getSeedCostReduction());
        return new Report(true, "You have bought " + seed.getName() + " for " + seed.getCost() + " coins.");
    }

    /**
     * Plants a tree on the specified tile
     * 
     * @param seed  the seed to be planted
     * @param field the farm's 2d array of tiles (used to determine whether the farm
     *              can accommodate the tree)
     * @return a rich message containing: true if the planting was successful, false
     *         otherwise
     */
    public Report plantTree(Seed seed, Tile[][] field) {

        // chcek if adjacent tiles are empty
        if (field[this.currentX][this.currentY + 1].getCurrentCrop() != null) {
            return new Report(false, "Tile is occupied");
        }
        if (field[this.currentX][this.currentY - 1].getCurrentCrop() != null) {
            return new Report(false, "Tile is occupied");
        }
        if (field[this.currentX + 1][this.currentY].getCurrentCrop() != null) {
            return new Report(false, "Tile is occupied");
        }
        if (field[this.currentX - 1][this.currentY].getCurrentCrop() != null) {
            return new Report(false, "Tile is occupied");
        }
        // check if diagonal tiles are empty
        if (field[this.currentX + 1][this.currentY + 1].getCurrentCrop() != null) {
            return new Report(false, "Tile is occupied");
        }
        if (field[this.currentX - 1][this.currentY - 1].getCurrentCrop() != null) {
            return new Report(false, "Tile is occupied");
        }
        if (field[this.currentX + 1][this.currentY - 1].getCurrentCrop() != null) {
            return new Report(false, "Tile is occupied");
        }
        if (field[this.currentX - 1][this.currentY + 1].getCurrentCrop() != null) {
            return new Report(false, "Tile is occupied");
        }

        plantSeed(seed, field[this.currentX][this.currentY + 1]);

        // set adjacent tiles as occupied
        field[this.currentX][this.currentY + 1].setOccupied(true);
        field[this.currentX][this.currentY - 1].setOccupied(true);
        field[this.currentX + 1][this.currentY].setOccupied(true);
        field[this.currentX - 1][this.currentY].setOccupied(true);

        // set diagonal tiles as occupied
        field[this.currentX + 1][this.currentY + 1].setOccupied(true);
        field[this.currentX - 1][this.currentY + 1].setOccupied(true);
        field[this.currentX + 1][this.currentY - 1].setOccupied(true);
        field[this.currentX - 1][this.currentY - 1].setOccupied(true);

        return new Report(true, "Tree planted successfully");
    }

    /**
     * Plants a seed on the specified tile
     * 
     * @param seed the seed to be planted
     * @param tile the tile on which the seed will be planted
     * @return a rich message containing: true if the planting was successful, false
     *         otherwise
     */
    public Report plantSeed(Seed seed, Tile tile) {

        // check if seed exists in inventory
        if (!seedInventory.contains(seed))
            return new Report(false, "Seed not found in inventory");

        if (!tile.isPlowed())
            return new Report(false, "Tile is not plowed");

        if (tile.isOccupied())
            return new Report(false, "Tile is occupied");

        if (tile.hasRocks())
            return new Report(false, "Tile has rocks");

        if (tile.getCurrentCrop() != null)
            return new Report(false, "Tile already has a crop");

        tile.setCurrentCrop(new Crop(seed));
        // remove seed from inventory
        seedInventory.remove(seed);

        return new Report(true, "Seed planted successfully");
    }

    /**
     * Harvests the crop on the specified tile and instantly sells it.
     * The specified tile is also emptied.
     * 
     * @param tile the tile on which the crop will be harvested
     * @return a rich message containing: true if the harvesting was successful,
     *         false otherwise
     */
    public Report harvestCrop(Tile tile) {

        if (tile.getCurrentCrop() == null)
            return new Report(false, "Tile does not have a crop");

        if (!tile.getCurrentCrop().isHarvestable())
            return new Report(false, "Crop is not mature");

        if (tile.getCurrentCrop().isWithered())
            return new Report(false, "Crop is withered");

        int yield = tile.getCurrentCrop().getYield();
        double harvestTotal = yield * (tile.getCurrentCrop().getMarketPrice() + type.getBonusEarnings());

        double waterBonus = 0;
        double fertilizerBonus = 0;
        // check if waterMax has beeen met
        if (tile.getCurrentCrop().getWaterLevel() >= tile.getCurrentCrop().getWaterMax())
            waterBonus = harvestTotal * 0.2 * (tile.getCurrentCrop().getWaterLevel() - 1);

        if (tile.getCurrentCrop().getFertilizerLevel() >= tile.getCurrentCrop().getFertilizerMax())
            fertilizerBonus = harvestTotal * 0.5 * tile.getCurrentCrop().getFertilizerLevel();

        harvestTotal += waterBonus + fertilizerBonus;

        if (tile.getCurrentCrop().getCropType() == "Flower")
            harvestTotal *= 1.1;

        // add coins to farmer
        this.objectCoins += harvestTotal;

        // remove crop from tile
        tile.setCurrentCrop(null);
        tile.setPlowed(false);
        return new Report(true, "Crop harvested successfully\nProduced: " + yield + " Sold for: " + harvestTotal);
    }

    /**
     * Performs the pickaxe's action on the specified tile
     * 
     * @param tile the tile on which the pickaxe will be used
     * @return a rich message containing: true if the pickaxe was successful, false
     *         otherwise
     */
    public Report usePickaxe(Tile tile) {
        // check if farmer has enough money
        if (this.objectCoins >= this.pickaxe.getCost()) {
            // subtract cost from farmer's money
            this.objectCoins -= this.pickaxe.getCost();

            if (pickaxe.mineRocks(tile).isSuccess()) {
                // add exp to farmer
                addExp(this.pickaxe.getExpGain());
                return new Report(true, "Rocks mined successfully");
            } else {
                return new Report(false, "Rocks not found");
            }
        } else {
            return new Report(false, "Not enough money");
        }
    }

    /**
     * Waters a crop on the specified tile
     * 
     * @param tile the tile on which the crop will be watered
     * @return a rich message containing: true if the watering was successful, false
     *         otherwise
     */
    public Report useWateringCan(Tile tile) {

        Report retval = this.wateringCan.waterTile(this.type, tile);

        if (retval.isSuccess()) {
            addExp(this.wateringCan.getExpGain());
        }

        return retval;
    }

    /**
     * Fertilizes a crop on the specified tilek
     * 
     * @param tile the tile on which the crop will be fertilized
     * @return a rich message containing: true if the fertilizing was successful,
     *         false otherwise
     */
    public Report useFertilizer(Tile tile) {

        // contingency case
        Report retval = new Report(false, "Failed to fertilize tile");
        // check if farmer has enough money
        if (this.objectCoins >= this.fertilizer.getCost()) {
            // subtract cost from farmer's money
            this.objectCoins -= this.fertilizer.getCost();

            retval = this.fertilizer.fertilizeTile(this.type, tile);

            if (retval.isSuccess()) {
                addExp(this.fertilizer.getExpGain());
            }
        } else
            retval = new Report(false, "Not enough money");

        return retval;
    }

    /**
     * Performs the shovel's action on the specified tile
     * 
     * @param tile the tile on which the shovel will be used
     * @return a rich message containing: true if the shovel was successful, false
     *         otherwise
     */
    public Report useShovel(Tile tile) {
        Report retval = new Report(false, "Failed to dig tile");

        // check if farmer has enough money
        if (this.objectCoins >= this.shovel.getCost()) {
            // subtract cost from farmer's money
            this.objectCoins -= this.shovel.getCost();

            retval = this.shovel.digWitheredCrop(tile);
            if (retval.isSuccess())
                addExp(this.shovel.getExpGain());
        } else {
            retval = new Report(false, "Not enough money");
        }

        return retval;
    }

    /**
     * Performs the plow's action on the specified tile
     * 
     * @param tile the tile on which the plow will be used
     * @return a rich message containing: true if the plow was successful, false
     *         otherwise
     */
    public Report usePlow(Tile tile) {

        Report retval = this.plow.plowTile(tile);
        if (retval.isSuccess())
            addExp(this.plow.getExpGain());

        return retval;
    }

    /**
     * Prints all seeds available to the farmer
     */
    public void showSeedInventory() {

        System.out.println("Seed Inventory:");
        for (int i = 0; i < this.seedInventory.size(); i++) {
            System.out.println(i + " : " + this.seedInventory.get(i).getName());
        }
    }

    public double getObjectCoins() {

        return objectCoins;
    }

    public double getLevel() {

        return level;
    }

    public String getName() {

        return name;
    }

    public String getType() {

        return type.getType();
    }

    public ArrayList<Seed> getSeedInventory() {

        return seedInventory;
    }

    /**
     * Prints the farmer's stats
     */
    public void printFarmerStatus() {
        System.out.println(this.type.getType() + " Farmer " + this.name);
        System.out.println("Current exp: " + this.currentExp);
        // print current position
        System.out.print("in x: " + this.currentX + " y: " + this.currentY);
        // print current balance
        System.out.println(" $ " + this.objectCoins);
        // print current exp

        System.out.println("---------------------------");
    }

}
