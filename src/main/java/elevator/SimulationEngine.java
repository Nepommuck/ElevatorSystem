package elevator;

import java.util.ArrayList;

public class SimulationEngine implements Runnable {
    private final ElevatorSystem system;
    private final ArrayList<ElevatorCall> calls;
    private boolean running = true;


    public SimulationEngine(int numberOfElevators, ArrayList<ElevatorCall> calls, IPositionChangeObserver observer) {
        system = new ElevatorSystem(numberOfElevators, observer);
        this.calls = (calls != null) ? calls : new ArrayList<>();
    }

    @Override
    public void run() {
        for(ElevatorCall call : calls)
            system.addCall(call);

        while (running) {
            system.update();

            try {
                java.lang.Thread.sleep(1000);
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
