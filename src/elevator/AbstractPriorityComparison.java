package elevator;

public abstract class AbstractPriorityComparison {
    protected final Elevator elevator;

    public AbstractPriorityComparison(Elevator elevator) {
        this.elevator = elevator;
    }

    public abstract int compare(ElevatorCall call1, ElevatorCall call2);

    public boolean doOvershadow(ElevatorCall call1, ElevatorCall call2) {
        return false;
    }

    public ElevatorCall getOvershadowed(ElevatorCall call) {
        return call;
    }
}
