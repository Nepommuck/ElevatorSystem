package elevator;

import java.util.ArrayList;

public class SimulationEngine implements Runnable {
    private final ElevatorSystem system;
    private final ArrayList<ElevatorCall> calls;
    private boolean running = true;
    private final int delay;

    public SimulationEngine(int numberOfElevators, ArrayList<ElevatorCall> calls, IPositionChangeObserver observer,
                            int delay, int basicFloor) {
        system = new ElevatorSystem(numberOfElevators, basicFloor, observer);
        this.calls = (calls != null) ? calls : new ArrayList<>();
        this.delay = delay;
    }

    @Override
    public void run() {
        for(ElevatorCall call : calls)
            system.addCall(call);

        while (running) {
            system.update();

            try {
                java.lang.Thread.sleep(delay);
            } catch (InterruptedException e) {
                System.out.println("Simulation interrupted");
                running = false;
            }
        }
    }

    public void passCall(int id, ElevatorCall call) {
        if (id < 0)
            system.addCall(call);
        else
            system.addInnerElevatorCall(id, call);
    }
}
