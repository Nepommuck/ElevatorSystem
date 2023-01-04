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
}
