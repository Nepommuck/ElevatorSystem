package elevator;

import java.util.ArrayList;

public class SimulationEngine {
    private final ElevatorSystem system;
    private final ArrayList<ElevatorCall> calls;

    public SimulationEngine(int numberOfElevators, ArrayList<ElevatorCall> calls) {
        system = new ElevatorSystem(numberOfElevators, 5);
        this.calls = calls;
    }

    public void run() {
        for(ElevatorCall call : calls)
            system.addCall(call);

        for (int i = 0; i < 20; i++) {
            System.out.println("Day " + i);
            System.out.println(system);

            system.update();

            try {
                java.lang.Thread.sleep(0);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
