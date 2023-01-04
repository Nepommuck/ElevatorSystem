package elevator;

import java.util.ArrayList;

public class Elevator {
    private int currentFloor;
    private MoveDirection currentDirection;
    private int goalFloor;
    private final ArrayList<ElevatorCall> calls = new ArrayList<>();
    private final AbstractPriorityComparison priorityComparator = new minimalDirectionChangingAlgorithm(this);

    public Elevator() {
        this(0);
    }
    public Elevator(int floor) {
        currentFloor = floor;
        goalFloor = currentFloor;
        currentDirection = MoveDirection.STATIONARY;
    }

    void addCall(ElevatorCall newCall) {
        if (newCall.floor == currentFloor && currentDirection == MoveDirection.STATIONARY)
            return;

        int index = 0;
        for (ElevatorCall call : calls) {
            if (call.equals(newCall))
                return;

            if (priorityComparator.doOvershadow(newCall, call))
                break;
            index += 1;
        }

        // Found an overshadowing call
        if (index < calls.size()) {
            newCall = priorityComparator.getOvershadowed(newCall);

            System.out.println("Removed: " + calls.get(index));
            calls.remove(index);
        }

        index = 0;
        for (ElevatorCall call : calls) {
            if (priorityComparator.compare(newCall, call) < 0)
                break;
            index += 1;
        }
        calls.add(index, newCall);
        System.out.println("Added: " + newCall);
        if (index == 0)
            setGoal(newCall);
        System.out.println(currentDirection);
        System.out.println(calls);
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
        System.out.println("Reached: " + calls.get(0));

        calls.remove(0);
        if (calls.size() == 0)
            setGoal(null);

        else
            setGoal(calls.get(0));
    }

    // null call sets elevator into waiting state
    private void setGoal(ElevatorCall call) {
        System.out.println("Set goal: " + call);
        currentDirection = MoveDirection.STATIONARY;
        if (call == null) {
            goalFloor = currentFloor;
            return;
        }

        goalFloor = call.floor;
        if (goalFloor != currentFloor)
            currentDirection = (call.floor > currentFloor) ? MoveDirection.UPWARD : MoveDirection.DOWNWARD;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }
    public MoveDirection getCurrentDirection() {
        return currentDirection;
    }

    @Override
    public String toString() {
        return currentFloor + " " + currentDirection;
    }
}
