package tools;

import main.Report;
import main.Tile;

public class Pickaxe extends Tool{

    public Pickaxe() {
        this.cost = 50;
        this.expGain = 15;
    }

    public Report mineRocks(Tile tile){

        Report retval = new Report();
        retval = tile.clearRocks();

        return retval;
    }
}
