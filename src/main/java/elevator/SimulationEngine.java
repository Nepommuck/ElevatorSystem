package elevator;

import java.util.ArrayList;

public class SimulationEngine implements Runnable {
    private final ElevatorSystem system;
    private final ArrayList<ElevatorCall> calls;
    private final IPositionChangeObserver observer;
    private boolean running = true;


    public SimulationEngine(int numberOfElevators, ArrayList<ElevatorCall> calls, IPositionChangeObserver observer) {
        system = new ElevatorSystem(numberOfElevators, observer);
        this.calls = (calls != null) ? calls : new ArrayList<>();
        this.observer = observer;
    }

    @Override
    public void run() {
        for(ElevatorCall call : calls)
            system.addCall(call);

        int day = 0;
        while (running) {
            System.out.println("Day " + day);
            System.out.println(system);

            system.update();

            try {
                java.lang.Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Simulation interrupted");
                running = false;
            }
        }
    }

    public void passCall(ElevatorCall call) {
        system.addCall(call);
    }
}
