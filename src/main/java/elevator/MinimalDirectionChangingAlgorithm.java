package elevator;

public class MinimalDirectionChangingAlgorithm extends AbstractPriorityComparison {
    public MinimalDirectionChangingAlgorithm(Elevator elevator) {
        super(elevator);
    }

    // In comparison, of two calls we first need take a "tier" into consideration - that is whether we are able to catch
    // given floor without changing elevator direction, or maybe with only one direction changing.
    //
    // If the tiers are the same, then in case of travelling opposite to that of a call we prefer bigger first for
    // travelling upward and smaller first for travelling downward.
    // In case of traveling in the same direction to the call we do the opposite to that.
    @Override
    public int compare(ElevatorCall call1, ElevatorCall call2) {
        int tier1 = getTier(call1);
        int tier2 = getTier(call2);
        int sign = elevator.getCurrentDirection().asSign();

        if (tier1 - tier2 != 0)
            return tier1 - tier2;

        // In case of
        if (elevator.getCurrentDirection().opposite() == call1.direction)
            return -sign * (call1.floor - call2.floor);
        return sign * (call1.floor - call2.floor);
    }

    private int getTier(ElevatorCall call) {
        int sign = elevator.getCurrentDirection().asSign();
        if (
            (call.direction == elevator.getCurrentDirection() || call.direction == null)
                    && sign * (call.floor - elevator.getCurrentFloor()) > 0
        )   return 1;

        else if (call.direction == elevator.getCurrentDirection().opposite())
            return 2;

        else
            return 3;
    }

    @Override
    public boolean doOvershadow(ElevatorCall call1, ElevatorCall call2) {
        return call1.floor == call2.floor;
    }

    @Override
    public ElevatorCall getOvershadowed(ElevatorCall call) {
        return new ElevatorCall(call.floor, null);
    }
}
