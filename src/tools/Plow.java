package tools;

import main.Report;
import main.Tile;

public class Plow extends Tool {

    public Plow() {
        this.cost = 0;
        this.expGain = 0.5;
    }

    public Report plowTile(Tile tile){
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
