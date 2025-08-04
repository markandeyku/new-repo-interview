package elevator;

import java.util.List;

public class ElevatorController {

    private List<Elevator> elevators;

    ElevatorController(int totalElevators) {
        elevators = new java.util.ArrayList<>();
        for (int i = 0; i < totalElevators; i++) {
            elevators.add(new Elevator(i));
        }
    }


    public void assignToElevator(int floor, Direction direction) {
        Elevator bestElevator = findBestElevator(floor, direction);
        bestElevator.addRequest(floor);
        System.out.println("Request assigned to elevator at floor: " + bestElevator.getCurrentFloor() +"  with eleveator id "+ bestElevator.getElevatorId());
    }

    private Elevator findBestElevator(int floor, Direction direction) {
        Elevator bestElevator = null;
        int minDistance = Integer.MAX_VALUE;
        for (Elevator elevator : elevators) {
            if(elevator.state == ElevatorState.STOPPED || elevator.direction == direction && ( direction == Direction.UP && floor > elevator.currentFloor ||  direction == Direction.DOWN && floor < elevator.currentFloor)) {
                if(minDistance > Math.abs(elevator.currentFloor - floor)) {
                    minDistance = Math.abs(elevator.currentFloor - floor);
                    bestElevator = elevator;
                }
            }
        }
        return bestElevator == null ? elevators.get(0) : bestElevator;
    }

    public void step() {
        for (Elevator elevator : elevators) {
            elevator.move();
        }
    }

    // elevator needs to move


}
