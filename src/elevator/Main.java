package elevator;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<ElevatorCall> calls = new ArrayList<>();
        calls.add(new ElevatorCall(3, MoveDirection.DOWNWARD));
        calls.add(new ElevatorCall(0, null));
        calls.add(new ElevatorCall(5, MoveDirection.DOWNWARD));
        calls.add(new ElevatorCall(1, MoveDirection.DOWNWARD));
        calls.add(new ElevatorCall(0, MoveDirection.UPWARD));
        calls.add(new ElevatorCall(9, null));

        SimulationEngine engine = new SimulationEngine(1, calls);
        engine.run();
    }
}