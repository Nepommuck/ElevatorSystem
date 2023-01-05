package elevator;

import java.util.ArrayList;
import java.util.Collections;

public class ElevatorSystem {
    private final ArrayList<Elevator> elevators = new ArrayList<>();

    public ElevatorSystem(int numberOfElevators, IPositionChangeObserver observer) {
        this(numberOfElevators, 0, observer);
    }
    public ElevatorSystem(int numberOfElevators, int baseFloor, IPositionChangeObserver observer) {
        this(new ArrayList<>(Collections.nCopies(numberOfElevators, baseFloor)), observer);
    }
    public ElevatorSystem(ArrayList<Integer> baseFloors, IPositionChangeObserver observer) {
        if (baseFloors.size() == 0 || baseFloors.size() > 12)
            throw new IllegalArgumentException("Number of elevators must be between 1 and 12.");

        for (int i = 0; i < baseFloors.size(); i++)
            elevators.add(
                    new Elevator(baseFloors.get(i), i, observer)
            );
    }

    public void update() {
        for (Elevator elevator : elevators)
            elevator.updatePosition();
    }

    // First we check if any elevator doesn't already have given call. If not, then we choose the elevator with the
    // smallest number of tasks. If there is more than one, than we take one that is about to finish closest
    // to the target.
    public void addCall(ElevatorCall call) {
        Elevator bestCandidate = elevators.get(0);
        int smallestNumberOfCalls = Integer.MAX_VALUE;
        int floorDifference = Integer.MAX_VALUE;

        for (Elevator elevator : elevators) {
            if (elevator.hasSpecificCall(call))
                return;
            if (elevator.getCallsNumber() < smallestNumberOfCalls) {
                smallestNumberOfCalls = elevator.getCallsNumber();
                floorDifference = Math.abs(elevator.getLastCallFloor() - call.floor);
                bestCandidate = elevator;
            }
            else if (elevator.getCallsNumber() == smallestNumberOfCalls &&
                    Math.abs(elevator.getLastCallFloor() - call.floor) < floorDifference) {
                floorDifference = Math.abs(elevator.getLastCallFloor() - call.floor);
                bestCandidate = elevator;
            }
        }
        bestCandidate.addCall(call);
    }
    public void addInnerElevatorCall(int index, ElevatorCall call) {
        elevators.get(index).addCall(call);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Elevator elevator : elevators)
            result.append(elevator.toString()).append("\n");
        return result.toString();
    }
}
