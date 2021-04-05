import java.util.Scanner;

/**
 * Operations - class takes in instructions and manipulates the Robot in the warehouse
 */
public class Operations {

    Scanner scanner = new Scanner(System.in);
    RobotWarehouse robotWarehouse = new RobotWarehouse();

    public void getUserInput() {
        Scanner scanner = new Scanner(System.in);

        boolean quit = false;

        while (!quit) {
            userInstructions();
            System.out.println("Please enter your choice");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> moveInTheGrid();
                case 2 -> workWithCrate();
                case 3 -> {
                    quit = true;
                    System.out.println("Turning off the robot");
                    scanner.close();
                }
                default -> System.out.println("Invalid option");
            }
        }
    }

    private void userInstructions() {
        System.out.println(
                "Robot Warehouse" + "\n" +
                        "press 1 -> move " + "\n" +
                        "press 2 -> pickup/Drop the crate -> Allowed options are G/D" + "\n" +
                        "press 3 -> to quit");
    }

    private void moveInTheGrid() {
        System.out.println("Please enter the direction in which robot must move : ");
        String directions = scanner.nextLine();

        try {
            System.out.println(robotWarehouse.moveRobot(directions));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void workWithCrate() {
        System.out.println("To pick a crate enter G or to drop a crate enter D");
        String command = scanner.nextLine();
        robotWarehouse.crateOperations(command);
    }
}
