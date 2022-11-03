package main;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class FarmLand {
    
    Tile tiles[][];

    int length;
    int height;

    ArrayList<Seed> seedList;
    Farmer farmer;
    String farmName;

    public FarmLand(int height, int length, Farmer farmer, String farmName, File rockstatus) throws Exception{

        this.height = height;
        this.length = length;
        this.farmer = farmer;
        this.farmName = farmName;

        this.tiles = new Tile[length][height];
        this.seedList = new ArrayList<Seed>();
        this.seedList.add(new Seed("Turnip"));

        // initialize tiles
        for(int i = 0; i < height; i++){
            for(int j = 0; j < length; j++){
                this.tiles[i][j] = new Tile();
            }
        }

        // TODO: Rock scattering
        // scatter rocks on tiles
        // based on rockstatus file
    }

    public void getTileStatus(){

        for(int i = 0; i < height; i++){
            for(int j = 0; j < length; j++){
                System.out.println(this.tiles[i][j].getStatus());
            }
            System.out.println();
        }
    }

    public String getActions(){

        String inputs = "";

        System.out.println("g) go to tile: ");
        System.out.println("b) buy seeds");
        inputs += "gb";

        // check if farmer's current tile is plowable
        if(this.getFarmerTile().isPlowable()){
            System.out.println("z) plow tile");
            inputs += "z";
        }

        if(this.farmer.getSeedInventory().size() > 0){
            if(this.getFarmerTile().isPlowed() &&
             !this.getFarmerTile().isOccupied() &&
              this.getFarmerTile().getCurrentCrop() == null){
                System.out.println("x) plant seed");
                inputs += "x";
            }
        }

        if(this.getFarmerTile().hasRocks()){
            System.out.println("a) use pickaxe");
            inputs += "a";
        }


        if(this.getFarmerTile().getCurrentCrop() != null){
            if(this.getFarmerTile().getCurrentCrop().isWithered()){
                System.out.println("s) use shovel");
                inputs += "s";
            }

            else if(!this.getFarmerTile().getCurrentCrop().isWithered()){
                System.out.println("c) water crop");
                System.out.println("f) fertilize crop");
                System.out.println("i) get days til harvest");
                inputs += "cfi";
                if(this.getFarmerTile().getCurrentCrop().isHarvestable()){
                    System.out.println("h) harvest crop");
                    inputs += "h";
                }
            }
        }

        System.out.println("e) end day");
        inputs += "e";

        return inputs;
    }

    public Report endDay(){

        // grow all crops
        for (Tile[] farmTile: this.tiles) {
            for (Tile tile : farmTile) {
                if(tile.getCurrentCrop() != null)
                    System.out.println(tile.getCurrentCrop().growCrop().getMessage());
            }
        }

        return new Report(true, "DAY_END");
    }

    public Report performAction(String inputs){

        Report retval = new Report(false, "Invalid input.");

        System.out.print("\nselect action: ");
        Scanner sc = new Scanner(System.in);
            // get user input
            String selection = sc.nextLine();

            while(!inputs.contains(selection)){
                System.out.print("invalid input, try again: ");
                selection = sc.nextLine();
            }

            switch(selection){
                case "g":
                    // go to specified tile
                    System.out.println("enter (x, y) coordinates of desired tile");
                    // clear buffer
                    int x = sc.nextInt();
                    int y = sc.nextInt();
                    retval = this.moveFarmer(x, y);
                    break;
                case "b":
                    // buy seeds
                    this.showAllSeeds();
                    System.out.println("select seed to buy: ");
                    int seed = sc.nextInt();
                    if(!(seed < 0 || seed >= this.getSeedList().size())){
                        retval = this.farmer.buySeed(this.getSeed(seed));
                    }
                    break;
                case "z":
                    // plow tile
                    retval = this.farmer.usePlow(this.getFarmerTile());
                    break;
                case "x":
                    // plant seed
                    this.farmer.showSeedInventory();
                    System.out.println("select seed to plant: ");
                    int seedIndex = sc.nextInt();
                    if(!(seedIndex < 0 || seedIndex >= this.farmer.getSeedInventory().size())){
                        retval = this.farmer.plantSeed(this.farmer.getSeedInventory().get(seedIndex), this.getFarmerTile());
                    }
                    break;
                case "i":
                    // get days til harvest
                    retval = new Report(true, "days til harvest: " + this.getFarmerTile().getCurrentCrop().getHarvestCountdown());
                    break;
                case "a":
                    // use pickaxe
                    retval = this.farmer.usePickaxe(this.getFarmerTile());
                    break;
                case "s":
                    // use shovel
                    retval = this.farmer.useShovel(this.getFarmerTile());
                    break;
                case "c":
                    // water crop
                    retval = this.farmer.useWateringCan(this.getFarmerTile());
                    break;
                case "f":
                    // fertilize crop
                    retval = this.farmer.useFertilizer(this.getFarmerTile());
                    break;
                case "h":
                    // harvest crop
                    retval = this.farmer.harvestCrop(this.getFarmerTile());
                    break;
                case "e":
                    // end day
                    retval = endDay();
                    break;
        }
        return retval;
    }

    public void showAllSeeds(){

        // list all available seeds in list form
        // print cost and name
        for(int i = 0; i < this.seedList.size(); i++){
            System.out.println(i + " : " + this.seedList.get(i).getName() + " " + this.seedList.get(i).getCost());
        }
    }

    public Tile[][] getTiles(){
        return this.tiles;
    }

    public ArrayList<Seed> getSeedList(){
        return this.seedList;
    }

    public Seed getSeed(int index){
        return this.seedList.get(index);
    }

    public void getFarmerProgress(){

        // print farmer's progress
        System.out.println("Name: " + this.farmer.getName());
        System.out.println("Level: " + this.farmer.getLevel());
        System.out.println("Exp: " + this.farmer.getCurrentExp());
        System.out.println("Coins: " + this.farmer.getObjectCoins());
        
        // print farmer type
        System.out.println("Type: " + this.farmer.getType());

        // print all seeds in farmer's invetory
        System.out.println("Seeds: ");
        for(int i = 0; i < this.farmer.getSeedInventory().size(); i++){
            System.out.println(this.farmer.getSeedInventory().get(i).getName());
        }
    }

    // display farm as a grid of tiles
    public void showFarm(){
        // print tile numbers
        for(int i = 0; i < this.length; i++){
            System.out.print(i + "       ");
        }
        System.out.println();
        // draw each tile as a square
        for(int i = 0; i < height; i++){
            // print a row
            System.out.print(i);
            for(int j = 0; j < length; j++){
                String tileStatus = "";
                tileStatus += this.tiles[i][j].getStatus()[0];
                tileStatus += this.tiles[i][j].getStatus()[1];
                System.out.print("| " + tileStatus + " |");
            }
            System.out.println();
        }
    }

    public Tile getFarmerTile(){

        // get the tile the farmer is currently on
        return tiles[this.farmer.getCurrentY()][this.farmer.getCurrentX()];
    }

    public Report moveFarmer(int x, int y){

        // move farmer to a new tile
        // check if tile is valid
        if(x < 0 || x >= this.length || y < 0 || y >= this.height){
            return new Report(false, "Invalid tile");
        }

        // move farmer
        this.farmer.gotoTile(x, y);
        return new Report(true, "Moved to tile " + x + " " + y);
    }
}
