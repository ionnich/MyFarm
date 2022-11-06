package tools;

import main.FarmerType;
import main.Report;
import main.Tile;

/**
 * The fertilizer class encapsulates the fertilization process used by the
 * farmer
 * 
 * @version 1.0
 */
public class Fertilizer extends Tool {

    /**
     * Fertilizer constructor
     */
    public Fertilizer() {
        this.cost = 10;
        this.expGain = 4;
    }

    /**
     * @param type the type of farmer
     * @param tile the tile to be fertilized
     * @return Report the report of the fertilization process
     */
    public Report fertilizeTile(FarmerType type, Tile tile) {

        // check if tile has a crop
        if (tile.getCurrentCrop() == null)
            return new Report(false, "There is no crop to fertilize.");

        // check if tile is unplowed
        if (!tile.isPlowed())
            return new Report(false, "The tile is unplowed.");

        return tile.getCurrentCrop().addFertilizer(type.getFertilizerMaxIncrease());
    }

}
