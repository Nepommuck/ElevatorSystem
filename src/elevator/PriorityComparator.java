package elevator;

public class PriorityComparator {
    private final Elevator elevator;

    public PriorityComparator(Elevator elevator) {
        this.elevator = elevator;
    }

    private int sign(MoveDirection direction) {
        return (direction == MoveDirection.UPWARD) ? 1 : -1;
    }
    private int sign() {
        return sign(elevator.getCurrentDirection());
    }

    // In comparison, of two calls we first need take a "tier" into consideration - that is whether we are able to catch
    // given floor without changing elevator direction, or maybe with only one direction changing.
    //
    // If the tiers are the same, then in case of travelling opposite to that of a call we prefer bigger first for
    // travelling upward and smaller first for travelling downward.
    // In case of traveling in the same direction to the call we do the opposite to that.
    public int compare(ElevatorCall call1, ElevatorCall call2) {
        int tier1 = getTier(call1);
        int tier2 = getTier(call2);

        if (tier1 - tier2 != 0)
            return tier1 - tier2;

        // In case of
        if (elevator.getCurrentDirection().opposite() == call1.direction)
            return -sign() * (call1.floor - call2.floor);
        return sign() * (call1.floor - call2.floor);
    }

    private int getTier(ElevatorCall call) {
        if (
            (call.direction == elevator.getCurrentDirection() || call.direction == null)
                    && call.floor > sign() * elevator.getCurrentFloor()
        )   return 1;

        else if (call.direction == elevator.getCurrentDirection().opposite())
            return 2;

        else
            return 3;
    }

    public boolean overshadow(ElevatorCall call1, ElevatorCall call2) {
        return call1.floor == call2.floor;
    }

    public ElevatorCall getOvershadowed(ElevatorCall call) {
        return new ElevatorCall(call.floor, null);
    }
}
