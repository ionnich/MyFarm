package tools;

import main.FarmerType;
import main.Report;
import main.Tile;

public class WateringCan extends Tool {

    public WateringCan() {
        this.expGain = 0.5;
        this.cost = 0;
    }

    public Report waterTile(FarmerType type, Tile tile){
        
        // check if tile has a crop
        if (tile.getCurrentCrop() == null) {
            return new Report(false, "There is no crop to water.");
        }

        if(tile.getCurrentCrop().isWithered())
            return new Report(false, "The crop is withered.");

        // check if tile is unplowed
        if (!tile.isPlowed())
            return new Report(false, "The tile is unplowed.");

        return tile.getCurrentCrop().addWater(type.getWaterMaxIncrease());
    }
    
}
