package main;

import java.util.ArrayList;
import tools.*;

public class Farmer {

    FarmerType type;
    ArrayList<Seed> seedInventory;

    String name;

    int objectCoins;
    int currentExp = 0;
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

    public int getCurrentExp(){
        return this.currentExp;
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

    public Report buySeed(String name, ArrayList<Seed> seedList){

        // check if seed exists
        for (Seed seed : seedList){
            if (seed.seedName.equals(name)){
                // check if farmer has enough money
                if (this.objectCoins >= seed.cost){
                    // add seed to inventory
                    this.seedInventory.add(seed);
                    // subtract cost from farmer's money
                    this.objectCoins -= seed.cost;
                    return new Report(true, "Seed bought successfully");
            }
                else{
                    return new Report(false, "Not enough money");
                }
        }

     }

    return new Report(false, "Seed not found!");
    }

    public Report plantSeed(String seedName, Tile tile){

        // TODO: REFACTOR THIS CODE

        // // check if seed exists in inventory
        // for (Seed seed : this.seedInventory){
        //     if (seed.seedName.equals(seedName)){
        //         // check if tile is plowed
        //         if (tile.isPlowed()){
        //             // check if tile is empty
        //             if (tile.currentCrop == null){
        //                 // plant seed
        //                 tile.plantSeed(seed);
        //                 // remove seed from inventory
        //                 this.seedInventory.remove(seed);
        //                 return new Report(true, "Seed planted successfully");
        //             }
        //             else{
        //                 return new Report(false, "Tile is not empty");
        //             }
        //         }
        //         else{
        //             return new Report(false, "Tile is not plowed");
        //         }
        //     }
        // }

        return new Report(false, "Seed not found in inventory");
    }
}

