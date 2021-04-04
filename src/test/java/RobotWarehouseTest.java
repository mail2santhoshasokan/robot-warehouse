import exception.MovingOutOfGridException;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RobotWarehouseTest {

    RobotWarehouse robotWarehouse;

    @BeforeEach
    public void RobotWarehouseTest() {
        robotWarehouse = new RobotWarehouse();
    }

    @Test
    @Description("Test to check if robot moves back to origin when the N E S W is given")
    void testIfRobotMovesBackToOrigin() {
        String message = robotWarehouse.moveRobot(9, 0, "N E S W");
        Assertions.assertEquals("The robot is back at the origin (9,0)", message);
    }

    @Test
    @Description("Test to check if the robot moves to the centre of the grid when the command N E N E N E N E is given")
    public void testIfRobotMovesToCentreOfTheGrid() {
        String message = robotWarehouse.moveRobot(9, 0, "N E N E N E N E");
        Assertions.assertEquals("The robot is at the centre of the grid (5,4)", message);
    }

    @Test
    @Description("Test to check if exception is thrown when robot moves out of the grid")
    public void testIfExceptionIsThrownWhenRobotMovesOutOfGrid() throws Exception {

        Exception exception = Assertions.assertThrows(MovingOutOfGridException.class, () -> robotWarehouse.incrementAxes(9, 0, 10));

        String expectedMessage = "trying to move out of the grid, robot moved to initial place";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void testIfExceptionIsThrownForInvalidInput() {
        Exception exception = Assertions.assertThrows(Exception.class, () -> robotWarehouse.moveRobot(9, 0, "H"));
        Assertions.assertEquals("Invalid Input", exception.getMessage());
    }

}
