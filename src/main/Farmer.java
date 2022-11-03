package main;

import java.util.ArrayList;

import tools.*;

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

    public Farmer(){

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

    public Farmer(String name){

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

    public double getCurrentExp(){
        return this.currentExp;
    }

    public void addExp(double expAmount) {
        this.currentExp += expAmount;
    }

    // get coordinates
    public int getCurrentX(){
        return this.currentX;
    }
    public int getCurrentY(){
        return this.currentY;
    }

    // must be used to move farmer
    // used by FarmLand to move farmer
    public void gotoTile(int x, int y){
        this.currentX = x;
        this.currentY = y;
    }

    public Report buySeed(Seed seed){

        // check if farmer has enough money
        if(this.objectCoins < seed.getCost())
            return new Report(false, "You do not have enough money to buy this seed.");

        this.seedInventory.add(seed);
        this.objectCoins -= seed.getCost();
        return new Report(true, "You have bought " + seed.getName() + " for " + seed.getCost() + " coins.");
    }

    // planting logic for fruit trees
    public Report plantTree(Seed seed, Tile[][] field){

        // chcek if adjacent tiles are empty
        if (field[this.currentX][this.currentY + 1].getCurrentCrop() != null){
            return new Report(false, "Tile is occupied");
        }
        if (field[this.currentX][this.currentY - 1].getCurrentCrop() != null){
            return new Report(false, "Tile is occupied");
        }
        if (field[this.currentX + 1][this.currentY].getCurrentCrop() != null){
            return new Report(false, "Tile is occupied");
        }
        if (field[this.currentX - 1][this.currentY].getCurrentCrop() != null){
            return new Report(false, "Tile is occupied");
        }
        // check if diagonal tiles are empty
        if (field[this.currentX + 1][this.currentY + 1].getCurrentCrop() != null){
            return new Report(false, "Tile is occupied");
        }
        if (field[this.currentX - 1][this.currentY - 1].getCurrentCrop() != null){
            return new Report(false, "Tile is occupied");
        }
        if (field[this.currentX + 1][this.currentY - 1].getCurrentCrop() != null){
            return new Report(false, "Tile is occupied");
        }
        if (field[this.currentX - 1][this.currentY + 1].getCurrentCrop() != null){
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

    public Report plantSeed(Seed seed, Tile tile){

        // TODO: REFACTOR THIS CODE

        // check if seed exists in inventory
        if(!seedInventory.contains(seed))
            return new Report(false, "Seed not found in inventory");

        if (!tile.isPlowed())
            return new Report(false, "Tile is not plowed");

        if(tile.isOccupied())
            return new Report(false, "Tile is occupied");

        if(tile.hasRocks())
            return new Report(false, "Tile has rocks");

        if(tile.getCurrentCrop() != null)
            return new Report(false, "Tile already has a crop");

        tile.setCurrentCrop(new Crop(seed));
        // remove seed from inventory
        seedInventory.remove(seed);

        return new Report(true, "Seed planted successfully");
    }

    public Report harvestCrop(Tile tile){

        if (tile.getCurrentCrop() == null)
            return new Report(false, "Tile does not have a crop");

        if (!tile.getCurrentCrop().isHarvestable())
            return new Report(false, "Crop is not mature");

        if (tile.getCurrentCrop().isWithered())
            return new Report(false, "Crop is withered");

        // TODO: ADD HARVEST LOGIC
        int yield = tile.getCurrentCrop().getYield();
        double harvestTotal = yield * (tile.getCurrentCrop().getMarketPrice() + type.getBonusEarnings());

        double waterBonus = 0;
        double fertilizerBonus = 0;
        // check if waterMax has beeen met
        if(tile.getCurrentCrop().getWaterLevel() >= tile.getCurrentCrop().getWaterMax())
            waterBonus = harvestTotal * 0.2 * (tile.getCurrentCrop().getWaterLevel() - 1);

        if(tile.getCurrentCrop().getFertilizerLevel() >= tile.getCurrentCrop().getFertilizerMax())
            fertilizerBonus = harvestTotal * 0.5 * tile.getCurrentCrop().getFertilizerLevel();

        harvestTotal += waterBonus + fertilizerBonus;

        if(tile.getCurrentCrop().getCropType() == "Flower")
            harvestTotal *= 1.1;

        // add coins to farmer
        this.objectCoins += harvestTotal;

        // remove crop from tile
        tile.setCurrentCrop(null);
        tile.setPlowed(false);
        return new Report(true, "Crop harvested successfully\nProduced: " + yield + " Sold for: " + harvestTotal);
    }

    // uses pickaxe on current tile
    public Report usePickaxe(Tile tile){
        // check if farmer has enough money
        if (this.objectCoins >= this.pickaxe.getCost()){
            // subtract cost from farmer's money
            this.objectCoins -= this.pickaxe.getCost();

            if(pickaxe.mineRocks(tile).isSuccess()){
                // add exp to farmer
                this.currentExp += this.pickaxe.getExpGain();
                return new Report(true, "Rocks mined successfully");
            }
            else{
                return new Report(false, "Rocks not found");
            }
        }
        else{
            return new Report(false, "Not enough money");
        }
    }

    public Report useWateringCan(Tile tile){

        Report retval = this.wateringCan.waterTile(this.type, tile);

        if (retval.isSuccess()){
            this.currentExp += this.wateringCan.getExpGain();
        }

        return retval;
    }

    public Report useFertilizer(Tile tile){

        // contingency case
        Report retval = new Report(false, "Failed to fertilize tile");
        // check if farmer has enough money
        if (this.objectCoins >= this.fertilizer.getCost()){
            // subtract cost from farmer's money
            this.objectCoins -= this.fertilizer.getCost();

            retval = this.fertilizer.fertilizeTile(this.type, tile);

            if(retval.isSuccess()){
                this.currentExp += this.fertilizer.getExpGain();
            }
        }
        else
            retval = new Report(false, "Not enough money");

        return retval;
    }

    public Report useShovel(Tile tile){
        Report retval = new Report(false, "Failed to dig tile");

        // check if farmer has enough money
        if (this.objectCoins >= this.shovel.getCost()){
            // subtract cost from farmer's money
            this.objectCoins -= this.shovel.getCost();

            retval = this.shovel.digWitheredCrop(tile);
            if(retval.isSuccess())
                this.currentExp += this.shovel.getExpGain();
        }
        else{
            retval = new Report(false, "Not enough money");
        }

        return retval;
    }

    public Report usePlow(Tile tile){

        Report retval = this.plow.plowTile(tile);
        if(retval.isSuccess())
            this.currentExp += this.plow.getExpGain();
        
        return retval;
    }

    public void showSeedInventory(){

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

    public String getType(){

        return type.getType();
    }

    public ArrayList<Seed> getSeedInventory() {

        return seedInventory;
    }

    public void printFarmerStatus(){
        System.out.println(this.type.getType() + " Farmer " + this.name);
        // print current position
        System.out.print(" in x: " + this.currentX + " y: " + this.currentY);
        // print current balance
        System.out.println(" $ " + this.objectCoins);
        // print current exp
        System.out.println("Current exp: " + this.currentExp);

        System.out.println("---------------------------");
    }

}

