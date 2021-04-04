import exception.MovingOutOfGridException;

public class RobotWarehouse {

    int xAxes;
    int yAxes;

    String moveRobot(int x, int y, String move) {
        xAxes = x;
        yAxes = y;
        char[] directions = getDirectionsFromInput(move);

        for (char direction : directions) {
            try {
                switch (direction) {
                    case 'N' -> xAxes = decrementAxes(x, y, xAxes);
                    case 'E' -> yAxes = incrementAxes(x, y, yAxes);
                    case 'S' -> xAxes = incrementAxes(x, y, xAxes);
                    case 'W' -> yAxes = decrementAxes(x, y, yAxes);
                    default -> throw new Exception("Invalid Input");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (xAxes == x && yAxes == y) {
            return "The robot is back at the origin (" + xAxes + "," + yAxes + ")";
        } else if (xAxes == 5 && yAxes == 4) {
            return "The robot is at the centre of the grid (" + xAxes + "," + yAxes + ")";
        } else {
            return "The robot is at the position (" + xAxes + "," + yAxes + ")";
        }
    }

    public int incrementAxes(int x, int y, int axes) throws Exception {
        axes++;
        if (axes > 9) {
            throw new MovingOutOfGridException("trying to move out of the grid, robot moved to initial place (" + x + "," + y + ")");
        }
        return axes;
    }

    public int decrementAxes(int x, int y, int axes) throws Exception {
        axes--;
        if (axes < 0) {
            throw new MovingOutOfGridException("trying to move out of the grid, robot moved to initial place (" + x + "," + y + ")");
        }
        return axes;
    }

    public char[] getDirectionsFromInput(String directions) {
        String newDirections = directions.replace(" ", "");
        char[] directionsArray = new char[newDirections.length()];
        for (int i = 0; i < newDirections.length(); i++) {
            directionsArray[i] = newDirections.charAt(i);
        }
        return directionsArray;
    }
}
