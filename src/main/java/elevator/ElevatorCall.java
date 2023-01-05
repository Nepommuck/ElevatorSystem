package elevator;

import java.util.Objects;

public class ElevatorCall{
    final int floor;
    final MoveDirection direction;

    public ElevatorCall(int floor, MoveDirection direction) {
        this.floor = floor;
        this.direction = direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ElevatorCall that = (ElevatorCall) o;
        return floor == that.floor && direction == that.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(floor, direction);
    }

    @Override
    public String toString() {
        return "(floor: " + this.floor + ", dir: " + this.direction + ")";
    }
}