package elevator;

import javafx.application.Platform;

import java.util.ArrayList;

public class Elevator {
    private int currentFloor;
    private MoveDirection currentDirection;
    private int goalFloor;
    private DoorState doorState = DoorState.OPEN;
    private final ArrayList<ElevatorCall> calls = new ArrayList<>();
    private final AbstractPriorityComparison priorityComparator = new MinimalDirectionChangingAlgorithm(this);
    private final IPositionChangeObserver observer;
    public final int id;

    public Elevator(int id) {
        this(0, id, null);
    }
    public Elevator(int floor, int id, IPositionChangeObserver observer) {
        currentFloor = floor;
        goalFloor = currentFloor;
        currentDirection = MoveDirection.STATIONARY;
        this.observer = observer;
        this.id = id;
    }

    void addCall(ElevatorCall newCall) {
        // Adding this call makes no sense
        if (newCall.floor == currentFloor && (doorState == DoorState.OPEN || doorState == DoorState.OPEANING))
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
        // Doors are currently moving
        if (doorState.areMoving()) {
            doorState = doorState.moveFurther();
            Platform.runLater(() -> observer.positionChanged(this));
            return;
        }

        // No calls to do - let's open the elevator
        if (currentDirection == MoveDirection.STATIONARY) {
            if (doorState != DoorState.OPEN) {
                doorState = doorState.openFurther();
                Platform.runLater(() -> observer.positionChanged(this));
            }
            return;
        }

        // There is a call but also a need to close the elevator
        if (doorState != DoorState.CLOSED) {
            doorState = doorState.closeFurther();
            Platform.runLater(() -> observer.positionChanged(this));
            return;
        }

        switch (currentDirection) {
            case UPWARD -> currentFloor++;
            case DOWNWARD -> currentFloor--;
        }

        // Reached destination
        if (currentFloor == goalFloor) {
            removeFulfilledCall();
            doorState = doorState.openFurther();
        }
        if (observer != null)
            Platform.runLater(() -> observer.positionChanged(this));
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

    public boolean hasSpecificCall(ElevatorCall call) {
        return calls.contains(call);
    }

    public int getCallsNumber() {
        return calls.size();
    }

    public int getCurrentFloor() {
        return currentFloor;
    }
    public MoveDirection getCurrentDirection() {
        return currentDirection;
    }
    public DoorState getDoorState() {
        return doorState;
    }

    @Override
    public String toString() {
        return currentFloor + " " + currentDirection;
    }
}
