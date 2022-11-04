package tools;

import main.FarmerType;
import main.Report;
import main.Tile;

public class Fertilizer extends Tool {

    public Fertilizer() {
        this.cost = 10;
        this.expGain = 4;
    }

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
