package tools;

import main.Report;
import main.Tile;

/**
 * The pickaxe class encapsulates the pickaxe tool used by the farmer
 * 
 * @version 1.0
 */
public class Pickaxe extends Tool {

    /**
     * Picaxe constructor
     */
    public Pickaxe() {
        this.cost = 50;
        this.expGain = 15;
    }

    /**
     * @param tile the tile whose rocks are to be removed
     * @return Report the report of the pickaxe process
     *
     */
    public Report mineRocks(Tile tile) {
        // check if tile has rocks
        if (!tile.hasRocks())
            return new Report(false, "There are no rocks to mine.");

        return tile.clearRocks();
    }
}
