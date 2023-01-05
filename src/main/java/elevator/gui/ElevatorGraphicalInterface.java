package elevator.gui;

import elevator.Elevator;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ElevatorGraphicalInterface extends GridPane {
    private Label floorLabel = new Label("floor 0");
    private Label directionLabel = new Label("STATIONARY");
    private Label doorLabel = new Label("OPEN");
    public ElevatorGraphicalInterface(int index) {
        this.add(new Label("ELEVATOR " + (index+1)), 0, 0);
        this.add(floorLabel, 0, 1);
        this.add(directionLabel, 0, 2);
        this.add(doorLabel, 0, 3);
    }

    void update(Elevator elevator) {
        this.getChildren().remove(floorLabel);
        floorLabel = new Label("floor " + elevator.getCurrentFloor());
        this.add(floorLabel, 0, 1);

        this.getChildren().remove(directionLabel);
        directionLabel = new Label(elevator.getCurrentDirection().toString());
        this.add(directionLabel, 0, 2);

        this.getChildren().remove(doorLabel);
        doorLabel = new Label(elevator.getDoorState().toString());
        this.add(doorLabel, 0, 3);
    }
}
