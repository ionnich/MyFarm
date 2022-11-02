package tools;

import main.Report;
import main.Tile;

public class Plow extends Tool {

    public Plow() {
        this.cost = 0;
        this.expGain = 0.5;
    }

    public Report plowTile(Tile tile){
        return tile.setPlowed(true);
    }
}
