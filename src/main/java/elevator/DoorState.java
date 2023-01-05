package elevator;

public enum DoorState {
    OPEN,
    CLOSING,
    CLOSED,
    OPEANING;

    public boolean areMoving() {
        return switch (this) {
            case OPEN, CLOSED -> false;
            case CLOSING, OPEANING -> true;
        };
    }
    public DoorState moveFurther() {
        return switch (this) {
            case OPEN, OPEANING -> OPEN;
            case CLOSED, CLOSING -> CLOSED;
        };
    }
    public DoorState openFurther() {
        return switch (this) {
            case OPEN, OPEANING -> OPEN;
            case CLOSED -> OPEANING;
            case CLOSING -> CLOSED;
        };
    }
    public DoorState closeFurther() {
        return switch (this) {
            case CLOSED, CLOSING -> CLOSED;
            case OPEN -> CLOSING;
            case OPEANING -> OPEN;
        };
    }
}
