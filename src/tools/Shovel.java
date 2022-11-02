package tools;

import main.Tile;
import main.Report;

public class Shovel extends Tool {

    public Shovel() {
        this.expGain = 2;
        this.cost = 7;
    }

    public Report plowTile(Tile tile){
        return tile.setPlowed(true);
    }
}
