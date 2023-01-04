package elevator;

import java.util.ArrayList;

public class Elevator {
    private int currentFloor;
    private MoveDirection currentDirection;
    private int goalFloor;
    private final ArrayList<ElevatorCall> calls = new ArrayList<>();

    public Elevator() {
        this(0);
    }
    public Elevator(int floor) {
        currentFloor = floor;
        goalFloor = currentFloor;
        currentDirection = MoveDirection.STATIONARY;
    }

    void addCall(ElevatorCall call) {
        if (call.floor == currentFloor && currentDirection == MoveDirection.STATIONARY)
            return;

        calls.add(call);
        if (calls.size() == 1)
            setGoal(call);
    }

    void updatePosition() {
        if (currentDirection == MoveDirection.STATIONARY)
            return;

        switch (currentDirection) {
            case UPWARD -> currentFloor++;
            case DOWNWARD -> currentFloor--;
        }

        // Reached destination
        if (currentFloor == goalFloor)
            removeFulfilledCall();
    }

    private void removeFulfilledCall() {
        calls.remove(0);
        if (calls.size() == 0)
            setGoal(null);

        else
            setGoal(calls.get(0));
    }

    // null call sets elevator into waiting state
    private void setGoal(ElevatorCall call) {
        currentDirection = MoveDirection.STATIONARY;
        if (call == null) {
            goalFloor = currentFloor;
            return;
        }

        goalFloor = call.floor;
        if (goalFloor != currentFloor)
            currentDirection = (call.floor > currentFloor) ? MoveDirection.UPWARD : MoveDirection.DOWNWARD;
    }

    @Override
    public String toString() {
        return currentFloor + " " + currentDirection;
    }
}
