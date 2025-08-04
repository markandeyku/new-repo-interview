package elevator;

public class ElevatorSystem {

    public static void main(String[] args) {

        ElevatorController elevatorController = new ElevatorController(2);
        elevatorController.assignToElevator(5, Direction.UP);
        elevatorController.assignToElevator(2, Direction.UP);
        elevatorController.assignToElevator(7, Direction.UP);
        elevatorController.assignToElevator(7, Direction.DOWN);
        elevatorController.assignToElevator(4, Direction.DOWN);

        for (int i = 0; i < 10; i++) {
            System.out.println("Step " + (i + 1));
            elevatorController.step();
        }

    }
}
