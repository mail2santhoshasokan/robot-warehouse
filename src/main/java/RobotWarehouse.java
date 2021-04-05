import exception.InvalidDataException;
import exception.MovingOutOfGridException;

/**
 * RobotWarehouse - performs all the functions related to moving of the robot and
 * picking up the crate
 */
public class RobotWarehouse {

    int xAxis = 9;
    int yAxis = 0;
    boolean holdingCrate = false;

    int x = xAxis;
    int y = yAxis;

    boolean[][] crateArray = new boolean[][]{
            {false, false, false, false, false, false, false, false, false, true},
            {false, false, false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false, false, false},
            {false, false, false, false, true, false, false, false, false, false},
            {false, false, false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false, false, false}
    };

    /**
     * moveRobot method - takes in the direction from the user and moves accordingly
     * @param move - directions in which the robot must move
     * @return returns string message based on the condition
     * @throws MovingOutOfGridException
     */
    String moveRobot(String move) throws Exception {
        char[] directions = getDirectionsFromInput(move);

        for (char direction : directions) {
            switch (direction) {
                case RobotConstants.NORTH -> xAxis = decrementAxis(x, y, xAxis);
                case RobotConstants.EAST -> yAxis = incrementAxis(x, y, yAxis);
                case RobotConstants.SOUTH -> xAxis = incrementAxis(x, y, xAxis);
                case RobotConstants.WEST -> yAxis = decrementAxis(x, y, yAxis);
                default -> throw new InvalidDataException("Invalid Input");
            }
        }

        if (xAxis == x && yAxis == y) {
            return RobotConstants.ROBOT_IS_BACK_AT_ORIGIN + xAxis + "," + yAxis + ")";
        } else if (xAxis == 5 && yAxis == 4) {
            return RobotConstants.ROBOT_IS_IN_THE_MIDDLE + xAxis + "," + yAxis + ")";
        } else {
            return RobotConstants.ROBOT_IS_AT_THE_POSITION + xAxis + "," + yAxis + ")";
        }
    }

    /**
     * Increment Axis function increments the axis when robot moves towards south and East
     * @param x  - retains the original value of xAxis
     * @param y - retains the original value of yAxis
     * @param axis
     * @return returns incremented axis
     * @throws Exception
     */
    public int incrementAxis(int x, int y, int axis) throws Exception {
        axis++;
        if (axis > 9) {
            xAxis = x;
            yAxis = y;
            throw new MovingOutOfGridException(RobotConstants.MOVING_OUT_OF_THE_GRID + x + "," + y + ")");
        }
        return axis;
    }

    /**
     * Decrement Axis function decrements the axis when the robot moves towards north and West
     * @param x - retains the original value of xAxis
     * @param y - retains the original value of yAxis
     * @param axis
     * @return - returns decremented axis
     * @throws Exception
     */
    public int decrementAxis(int x, int y, int axis) throws Exception {
        axis--;
        if (axis < 0) {
            xAxis = x;
            yAxis = y;
            throw new MovingOutOfGridException(RobotConstants.MOVING_OUT_OF_THE_GRID + x + "," + y + ")");
        }
        return axis;
    }

    /**
     *
     * @param directions - string provided by the user
     * @return - char array with the directions obtained from splitting the string
     * can be optimised if we use string.split()
     */
    public char[] getDirectionsFromInput(String directions) {
        String newDirections = directions.replace(" ", "");
        char[] directionsArray = new char[newDirections.length()];
        for (int i = 0; i < newDirections.length(); i++) {
            directionsArray[i] = newDirections.charAt(i);
        }
        return directionsArray;
    }

    /**
     * CrateOperations chooses to pick or drop the crate based on the user input
     * @param command
     */
    public void crateOperations(String command) {
        switch (command) {
            case RobotConstants.PICKUP -> pickTheCrate();
            case RobotConstants.DROP -> dropTheCrate();
            default -> System.out.println("Invalid option");
        }
    }

    /**
     * PickTheCrate method picks up the crate if the crate is present and
     * if the robot is not holding any crate
     */
    public void pickTheCrate() {
        boolean isCratePresent = crateArray[xAxis][yAxis];
        if (isCratePresent && !holdingCrate) {
            System.out.println("picking up the crate");
            holdingCrate = true;
            crateArray[xAxis][yAxis] = false;
            System.out.println("Picked up the crate");
        } else {
            System.out.println("Crate is not present / my arms are occupied");
        }
    }

    /**
     * DropTheCrate method drops the crate if there is no other crates present in the position
     * and also only if its holding the crate
     */
    public void dropTheCrate() {
        boolean isCratePresent = crateArray[xAxis][yAxis];
        if (!isCratePresent && holdingCrate) {
            System.out.println(RobotConstants.CRATE_IS_NOT_PRESENT + xAxis + "," + yAxis + ") dropping the crate gently");
            holdingCrate = false;
            crateArray[xAxis][yAxis] = true;
            System.out.println("Dropped the crate");
        } else {
            System.out.println(RobotConstants.CRATE_IS_PRESENT + xAxis + "," + yAxis + ") please drop in different location");
        }
    }
}
