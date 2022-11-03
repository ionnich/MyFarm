package tools;

import main.Tile;
import main.Report;

public class Shovel extends Tool {

    public Shovel() {
        this.expGain = 2;
        this.cost = 7;
    }

    public Report digWitheredCrop(Tile tile){

        if (tile.getCurrentCrop() == null) {
            return new Report(false, "There is no crop to dig up.");
        }

        if(!tile.getCurrentCrop().isWithered())
            return new Report(false, "The crop is not withered.");

        tile.setCurrentCrop(null);
        return new Report(true, "The withered crop has been removed from the tile.");
    }
}
