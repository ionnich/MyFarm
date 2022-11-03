package main;
public class Tile {

    private Crop currentCrop;

    private boolean isPlowed;
    private boolean isOccupied;
    private boolean hasRocks;

    public Tile() {
        this.currentCrop = null;
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

        this.isOccupied = status;

        return new Report(true, "Tile occupation set to " + status);

    }

    public Crop getCurrentCrop() {
        return currentCrop;
    }

    public void setCurrentCrop(Crop currentCrop) {
        this.currentCrop = currentCrop;
    }

    public boolean isPlowed() {
        return isPlowed;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public boolean hasRocks() {
        return hasRocks;
    }

    public String[] getStatus() {


        String[] status = new String[2];

        // first status column
        if (this.isPlowed)
            status[0] = "🟫";
        else if (this.isOccupied)
            status[0] = "🚧";
        else if (this.hasRocks)
            status[0] = "🪨";
        else
            status[0] = "🟩";

        // second status column
        if(this.currentCrop != null){
            if(this.currentCrop.getWaterLevel() > 0){
                status[0] = "💧";
            }
            if (this.currentCrop.isWithered())
                status[1] = "🍂";
            else if (this.currentCrop.isHarvestable())
                status[1] = "🍀";
            else
                status[1] = "🌱";
        }
        else 
            status[1] = "🟩";

        return status;
    }

    public boolean isPlowable(){

        // check if tile is plowable
        if(this.isPlowed)
            return false;

        if(this.isOccupied)
            return false;
        
        if(this.hasRocks)
            return false;

        if(this.currentCrop != null)
            return false;

        return true;
    }
}
