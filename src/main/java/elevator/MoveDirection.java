package elevator;

public enum MoveDirection {
    UPWARD,
    DOWNWARD,
    STATIONARY;

    public MoveDirection opposite() {
        return switch (this) {
            case UPWARD -> DOWNWARD;
            case DOWNWARD -> UPWARD;
            case STATIONARY -> STATIONARY;
        };
    }

    public int asSign() {
        return switch (this) {
            case UPWARD -> 1;
            case DOWNWARD -> -1;
            case STATIONARY -> 0;
        };
    }
}
