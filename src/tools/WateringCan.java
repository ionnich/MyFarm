package tools;

import main.Report;
import main.Tile;

public class WateringCan extends Tool {

    public WateringCan() {
        this.expGain = 0.5;
        this.cost = 0;
    }

    public Report waterTile(Tile tile){

        Report retval = new Report(true, null);
        // return tile.currentCrop.addWater();

        return retval;
    }
    
}
