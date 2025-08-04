package elevator;

import java.util.PriorityQueue;

public class Elevator {
    int elevatorId;
    int currentFloor;
    Direction direction;
    ElevatorState state;
    PriorityQueue<Integer> upPriorityQueue;
    PriorityQueue<Integer> downPriorityQueue;

    public Elevator(int elevatorId) {
        this.elevatorId = elevatorId;
        this.currentFloor = 0;
        this.direction = Direction.IDLE;
        this.state = ElevatorState.STOPPED;
        this.upPriorityQueue = new PriorityQueue<>(); // min heap
        this.downPriorityQueue = new PriorityQueue<>((a,b)-> b-a); // max heap
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    public ElevatorState getState() {
        return state;
    }

    public int getElevatorId() {
        return elevatorId;
    }

    public PriorityQueue<Integer> getUpPriorityQueue() {
        return upPriorityQueue;
    }

    public PriorityQueue<Integer> getDownPriorityQueue() {
        return downPriorityQueue;
    }

    public void addRequest(int floor) {
        if(floor > currentFloor) {
            upPriorityQueue.offer(floor);
        } else {
            downPriorityQueue.offer(floor);
        }
        updateStatus();
    }

    public void move(){
        if(this.state == ElevatorState.STOPPED) return ;
        else if (direction == Direction.UP && !upPriorityQueue.isEmpty()) {
            currentFloor = upPriorityQueue.poll();
        } else if (direction == Direction.DOWN && !downPriorityQueue.isEmpty()) {
            currentFloor = downPriorityQueue.poll();
        }

        System.out.println("Elevator at floor: " + currentFloor + " elevator id "+ elevatorId);
        updateStatus();
    }

    private void updateStatus() {
        if(!upPriorityQueue.isEmpty() || !downPriorityQueue.isEmpty()) {
            this.state = ElevatorState.MOVING;
            this.direction = upPriorityQueue.isEmpty() ? Direction.DOWN : Direction.UP;
        }
        else{
            this.state = ElevatorState.STOPPED;
            this.direction = Direction.IDLE;
        }
    }
}
