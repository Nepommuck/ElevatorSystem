package elevator;

public class ElevatorCall{
    final int floor;
    final MoveDirection direction;

    ElevatorCall(int floor, MoveDirection direction) {
        this.floor = floor;
        this.direction = direction;
    }
}