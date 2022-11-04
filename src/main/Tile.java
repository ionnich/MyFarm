package main;

/**
* A tile holds and allows a crop to be planted and grow
* @version 1.0
*/
public class Tile {

    private Crop currentCrop;

    private boolean isPlowed;
    private boolean isOccupied;
    private boolean hasRocks;

    /**
     * Constructor for a tile
     */
    public Tile() {
        this.currentCrop = null;
        this.isPlowed = false;
        this.isOccupied = false;
        this.hasRocks = false;
    }

    /**
     * @param status the desired rock status of the tile
     */
    private void setHasRocks(boolean status){
        this.hasRocks = status;
    }

    /**
     * adds rocks to the tile -- setting hasRocks to true
     * @return the context and result of the action
     */
    public Report addRocks(){

        if (this.hasRocks){
            return new Report(false, "Tile already has rocks");
        }

        this.setHasRocks(true);
        return new Report(true, "Rocks have been added to the tile");
    }

    /**
     * removes rocks from the tile -- setting hasRocks to false
     * @return the context and result of the action
     */
    public Report clearRocks(){

        if (!this.hasRocks){
            return new Report(false, "Tile does not have rocks");
        }

        this.setHasRocks(false);
        return new Report(true, "Rocks have been removed from the tile");
    }

    /**
     * changes the state of the tile's plowed status
     * @return the context and result of the action
     */
    public Report setPlowed(boolean status){

        if (this.isPlowed == status){
            return new Report(false, "Tile is already plowed");
        }

        this.isPlowed = status;
        return new Report(true, "Tile has been plowed");
    }

    /**
     * changes the state of the tile's occupied status
     * @return the context and result of the action
     */
    public Report setOccupied(boolean status){

        this.isOccupied = status;

        return new Report(true, "Tile occupation set to " + status);

    }

    /**
     * @return the crop currently planted on the tile
     */
    public Crop getCurrentCrop() {
        return currentCrop;
    }

    /**
     * changes the crop currently planted on the tile
     * @param currentCrop the desired crop
     */
    public void setCurrentCrop(Crop currentCrop) {
        this.currentCrop = currentCrop;
    }


    /**
     * @return whether the tile is plowed
     */
    public boolean isPlowed() {
        return isPlowed;
    }

    /**
     * @return whether the tile is occupied
     */
    public boolean isOccupied() {
        return isOccupied;
    }

    /**
     * @return whether the tile has rocks
     */
    public boolean hasRocks() {
        return hasRocks;
    }

    /**
     * @return a String representation of the tile's current state.
     */
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

            status[1] = "🌱";
            if(this.currentCrop.getWaterLevel() > 0){
                status[0] = "💧";
            }
            if(this.currentCrop.getFertilizerLevel() > 0){
                status[1] = "✨";
            }
            if (this.currentCrop.isWithered())
                status[1] = "🍂";
            else if (this.currentCrop.isHarvestable())
                status[1] = "🍀";
        }
        else 
            status[1] = "🟩";

        return status;
    }

    /**
     * @return whether the tile is plowable by the farmer
     */
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
