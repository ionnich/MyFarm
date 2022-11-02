package tools;

import main.Report;
import main.Tile;

public class Fertilizer extends Tool {

    public Fertilizer() {
        this.cost = 10;
        this.expGain = 4;
    }

    public Report fertilizeTile(Tile tile){
        Report retval = new Report();

        // return tile.currentCrop.addFertilizer();

        return retval;
    }
    
}
