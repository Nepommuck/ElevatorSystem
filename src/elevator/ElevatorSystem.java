package elevator;

import java.util.ArrayList;
import java.util.Collections;

public class ElevatorSystem {
    private final ArrayList<Elevator> elevators = new ArrayList<>();

    public ElevatorSystem(int numberOfElevators) {
        this(new ArrayList<>(Collections.nCopies(numberOfElevators, 0)));
    }
    public ElevatorSystem(ArrayList<Integer> baseFloors) {
        for (Integer floor : baseFloors)
            elevators.add(
                    new Elevator(floor)
            );
    }

    public void update() {
        for (Elevator elevator : elevators)
            elevator.updatePosition();
    }

    // Temporary
    public void addCall(ElevatorCall call) {
        elevators.get(0).addCall(call);
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
