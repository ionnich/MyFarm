package main;

import java.util.Scanner;

/**
 * The main class containing the GameLoop
 * 
 * @version 1.0
 */
public class App {

    private App() {
        // private constructor to prevent instantiation
        throw new IllegalStateException("Utility class");
    }

    private static boolean isRunning;
    private static String dayEndFlag = "DAY_END";
    private static Scanner sc = new Scanner(System.in);

    /**
     * a method that signals the beginning of the game
     * 
     * @return the name of the farm
     */
    public static String begin() {
        System.out.println("Welcome to MyFarm!");
        System.out.println();
        isRunning = true;

        // prompt user for farm name
        System.out.println("Please enter your farm name: ");
        String farmName = sc.nextLine();
        return farmName;
    }

    /**
     * canContinue checks if the game must continue running
     * 
     * @param farm the farm where the game is taking place
     * @return Report contains the status of the game and the boolean value linked
     *         to isRunning
     */
    public static Report canContinue(FarmLand farm) {

        Report retval = new Report(true, "Goodnight " + farm.farmer.getName() + ". Onto the next day.");

        boolean staleFarm = true;
        boolean brokeFarm = false;
        boolean witheredFarm = true;

        // extra contingency features
        if (farm.getSeed(0) == null)
            retval = new Report(false, "The farm has no seeds to sell");

        double cheapestSeed = farm.getSeed(0).getCost();

        // get the cheapest seed in farm
        for (Seed seed : farm.getSeedList()) {
            if (seed.getCost() < cheapestSeed) {
                cheapestSeed = seed.getCost();
            }
        }
        // check if the farmer is able to buy the cheapest seed
        if (farm.farmer.getObjectCoins() < cheapestSeed) {
            brokeFarm = true;
            retval = new Report(true, "You ain't got cash");
        }

        // check if all tiles have withered plants
        for (Tile[] tileRow : farm.getTiles()) {

            for (Tile tile : tileRow) {
                if (tile.getCurrentCrop() != null) {
                    if (!tile.getCurrentCrop().isWithered()) {
                        witheredFarm = false;
                    }
                }
            }

        }
        if (witheredFarm) {
            retval = new Report(false, "All crops have withered.");
        }

        // check if no crops are planted
        for (Tile[] tileRow : farm.getTiles()) {
            for (Tile tile : tileRow) {
                if (tile.getCurrentCrop() != null && !tile.getCurrentCrop().isWithered()) {
                    staleFarm = false;
                }
            }
        }

        if (staleFarm) {
            retval = new Report(true, "The farm has gone stale.");
        }

        // if all flags are true, end the game
        if (staleFarm && brokeFarm && witheredFarm) {
            retval = new Report(false, "Game over.");
        }

        return retval;
    }

    /**
     * GameLoop is the main method that runs the game
     * 
     * @param farm the farm where the game is taking place
     */
    public static void GameLoop(FarmLand farm) {

        int daycounter = 1;

        while (isRunning) {
            boolean dayEnd = false;

            // while the farmer has not ended the day
            while (!dayEnd) {
                // clear screen
                System.out.print("\033[H\033[2J");

                // print farmer status
                farm.farmer.printFarmerStatus();
                // print farm status
                System.out.println("Day " + daycounter);
                farm.showFarm();

                // get valid inputs
                String inputs = farm.getActions();
                // perform action
                Report report = farm.performAction(inputs, sc);

                // print action
                System.out.println(report.getMessage());

                // ask user to press enter to continue
                System.out.println("press enter to continue");
                sc.nextLine();

                if (report.getMessage().contains(dayEndFlag)) {
                    // clear the screen
                    System.out.print("\033[H\033[2J");
                    dayEnd = true;
                    daycounter++;

                    farm.farmer.levelUp();
                    if (farm.farmer.canUpgrade() != null) {
                        // prompt user to upgrade
                        System.out.println("Level and coin requirements met! Upgrade your license? (y/n)");
                        String upgrade = sc.nextLine();
                        if (upgrade.equals("Y") || upgrade.equals("y")) {
                            FarmerType next = farm.farmer.canUpgrade();
                            System.out.println(farm.farmer.payFee(next).getMessage());
                            System.out.println(farm.upgradeFarmer());
                        }
                    }

                }
            }
            // check if game can continue
            Report canContinue = canContinue(farm);
            // system out the message
            System.out.println(canContinue.getMessage());
            // ask user to press enter to continue
            System.out.println("press enter to continue");
            sc.nextLine();
            if (!canContinue.isSuccess()) {
                isRunning = false;
            }
        }

        System.out.println("LAST STATE OF THE FARM");
        System.out.println("----------------------");

        // print farmer status
        farm.farmer.printFarmerStatus();
        // print farm status
        farm.showFarm();
    }

    /**
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        boolean playAgain = true;

        String name = begin();
        while (playAgain) {
            isRunning = true;

            FarmLand farm = new FarmLand(
                    3, 3,
                    new Farmer("Beta Tester"),
                    name,
                    null);

            GameLoop(farm);

            // ask the user if they want to play again
            System.out.println("Do you want to play again? (y/n)");
            String input = sc.nextLine();
            if (input.equals("n") || input.equals("N"))
                playAgain = false;
        }

        sc.close();
    }
}
