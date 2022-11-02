package main;
public class Tile {

    // private Crop currentCrop = null;

    private boolean isPlowed;
    private boolean isOccupied;
    private boolean hasRocks;

    public Tile() {
        this.isPlowed = false;
        this.isOccupied = false;
        this.hasRocks = false;
    }


    private void setHasRocks(boolean status){
        this.hasRocks = status;
    }


    public Report addRocks(){
        if (this.hasRocks){
            return new Report(false, "Tile already has rocks");
        }
        this.setHasRocks(true);
        return new Report(true, "Rocks have been added to the tile");
    }

    public Report clearRocks(){
        if (!this.hasRocks){
            return new Report(false, "Tile does not have rocks");
        }
        this.setHasRocks(false);
        return new Report(true, "Rocks have been removed from the tile");
    }

    public Report setPlowed(boolean status){
        if (this.isPlowed == status){
            return new Report(false, "Tile is already plowed");
        }
        this.isPlowed = status;
        return new Report(true, "Tile has been plowed");
    }

    public Report setOccupied(boolean status){
        if (this.isOccupied == status){
            return new Report(false, "Tile is already occupied");
        }
        this.isOccupied = status;
        return new Report(true, "Tile has been occupied");
    }
}
