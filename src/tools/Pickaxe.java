package tools;

import main.Report;
import main.Tile;

public class Pickaxe extends Tool{

    public Pickaxe() {
        this.cost = 50;
        this.expGain = 15;
    }

    public Report mineRocks(Tile tile){
        // check if tile has rocks
        if (!tile.hasRocks())
            return new Report(false, "There are no rocks to mine.");

        return tile.clearRocks();
    }
}
