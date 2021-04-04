import exception.InvalidDataException;
import exception.MovingOutOfGridException;

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

    String moveRobot(String move) throws Exception {
        /*xAxis = x;
        yAxis = y;*/
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

    public int incrementAxis(int x, int y, int axis) throws Exception {
        axis++;
        if (axis > 9) {
            xAxis = x;
            yAxis = y;
            throw new MovingOutOfGridException(RobotConstants.MOVING_OUT_OF_THE_GRID + x + "," + y + ")");
        }
        return axis;
    }

    public int decrementAxis(int x, int y, int axis) throws Exception {
        axis--;
        if (axis < 0) {
            xAxis = x;
            yAxis = y;
            throw new MovingOutOfGridException(RobotConstants.MOVING_OUT_OF_THE_GRID + x + "," + y + ")");
        }
        return axis;
    }

    public char[] getDirectionsFromInput(String directions) {
        String newDirections = directions.replace(" ", "");
        char[] directionsArray = new char[newDirections.length()];
        for (int i = 0; i < newDirections.length(); i++) {
            directionsArray[i] = newDirections.charAt(i);
        }
        return directionsArray;
    }

    public void crateOperations(String command) {
        switch (command) {
            case RobotConstants.PICKUP -> pickTheCrate();
            case RobotConstants.DROP -> dropTheCrate();
            default -> System.out.println("Invalid option");
        }
    }

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
