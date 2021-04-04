import exception.MovingOutOfGridException;
import jdk.jfr.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RobotWarehouseTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    RobotWarehouse robotWarehouse;

    @BeforeEach
    public void RobotWarehouseTest() {
        robotWarehouse = new RobotWarehouse();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    @Description("Test to check if robot moves back to origin when the N E S W is given")
    void testIfRobotMovesBackToOrigin() throws Exception {
        String message = robotWarehouse.moveRobot("N E S W");
        Assertions.assertEquals("The robot is back at the origin (9,0)", message);
    }

    @Test
    @Description("Test to check if the robot moves to the centre of the grid when the command N E N E N E N E is given")
    public void testIfRobotMovesToCentreOfTheGrid() throws Exception {
        String message = robotWarehouse.moveRobot("N E N E N E N E");
        Assertions.assertEquals("The robot is at the centre of the grid (5,4)", message);
    }

    @Test
    @Description("Test to check if exception is thrown when robot moves out of the grid")
    public void testIfExceptionIsThrownWhenRobotMovesOutOfGrid() throws Exception {

        Exception exception = Assertions.assertThrows(MovingOutOfGridException.class, () -> robotWarehouse.incrementAxis(9, 0, 10));

        String expectedMessage = "Trying to move out of the grid, robot moved to initial place (9,0)";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testIfExceptionIsThrownForInvalidInput() {
        Exception exception = Assertions.assertThrows(Exception.class, () -> robotWarehouse.moveRobot("H"));
        Assertions.assertEquals("Invalid Input", exception.getMessage());
    }

    @Test
    public void doNotLiftCrateIfAlreadyLiftingOne() {
        robotWarehouse.holdingCrate = false;
        robotWarehouse.pickTheCrate();
        Assertions.assertEquals("Crate is not present / my arms are occupied", outputStreamCaptor.toString().trim());
    }

    @Test
    public void doNotPlaceCrateOnOneAnother() {
        robotWarehouse.xAxis = 1;
        robotWarehouse.yAxis = 1;
        robotWarehouse.crateArray = new boolean[][]{
                {true, true},
                {true, true}
        };
        robotWarehouse.holdingCrate = true;

        robotWarehouse.dropTheCrate();

        Assertions.assertEquals("Crate is present in the current position (1,1) please drop in different location", outputStreamCaptor.toString().trim());
    }

    @Test
    public void doNotLiftCrateIfNonePresent() {
        robotWarehouse.xAxis = 1;
        robotWarehouse.yAxis = 1;
        robotWarehouse.crateArray = new boolean[][]{
                {true, false},
                {true, false}
        };

        robotWarehouse.holdingCrate = false;
        robotWarehouse.pickTheCrate();

        Assertions.assertEquals("Crate is not present / my arms are occupied", outputStreamCaptor.toString().trim());
    }

}
