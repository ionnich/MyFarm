package tools;

import main.Tile;
import main.Report;

/**
 * The shovel class encapsulates the shovel tool used by the farmer
 * 
 * @version 1.0
 */
public class Shovel extends Tool {

    /**
     * Shovel constructor
     */
    public Shovel() {
        this.expGain = 2;
        this.cost = 7;
    }

    /**
     * @param tile The tile whose withered crop is to be removed
     * @return Report The report of the shovel process
     */
    public Report digWitheredCrop(Tile tile) {

        if (tile.getCurrentCrop() == null) {
            return new Report(false, "There is no crop to dig up.");
        }

        if (!tile.getCurrentCrop().isWithered())
            return new Report(false, "The crop is not withered.");

        tile.setCurrentCrop(null);
        tile.setPlowed(false);
        return new Report(true, "The withered crop has been removed from the tile.");
    }
}
