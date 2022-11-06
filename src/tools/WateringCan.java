package tools;

import main.FarmerType;
import main.Report;
import main.Tile;

/**
 * The watering can class encapsulates the watering can tool used by the farmer
 * 
 * @version 1.0
 */
public class WateringCan extends Tool {

    /**
     * WateringCan constructor
     */
    public WateringCan() {
        this.expGain = 0.5;
        this.cost = 0;
    }

    /**
     * @param type the type of farmer
     * @param tile the tile whose crop is to be watered
     * @return Report the report of the watering process
     */
    public Report waterTile(FarmerType type, Tile tile) {

        // check if tile has a crop
        if (tile.getCurrentCrop() == null) {
            return new Report(false, "There is no crop to water.");
        }

        if (tile.getCurrentCrop().isWithered())
            return new Report(false, "The crop is withered.");

        // check if tile is unplowed
        if (!tile.isPlowed())
            return new Report(false, "The tile is unplowed.");

        return tile.getCurrentCrop().addWater(type.getWaterMaxIncrease());
    }

}
