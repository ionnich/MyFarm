package tools;

import main.Report;
import main.Tile;

/**
 * The pickaxe class encapsulates the plow tool used by the farmer
 * 
 * @version 1.0
 */
public class Plow extends Tool {

    /**
     * Plow constructor
     */
    public Plow() {
        this.cost = 0;
        this.expGain = 0.5;
    }

    /**
     * @param tile the tile to be plowed
     * @return Report the report of the plow process
     */
    public Report plowTile(Tile tile) {
        // check if tile has crop
        if (tile.getCurrentCrop() != null)
            return new Report(false, "There is a crop on the tile.");

        // check if tile has rocks
        if (tile.hasRocks())
            return new Report(false, "There are rocks on the tile.");

        // check if tile is occupied
        if (tile.isOccupied())
            return new Report(false, "The tile is occupied.");

        return tile.setPlowed(true);
    }
}
