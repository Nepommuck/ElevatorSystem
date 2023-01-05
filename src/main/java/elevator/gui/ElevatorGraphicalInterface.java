package elevator.gui;

import elevator.Elevator;
import elevator.ElevatorCall;
import elevator.SimulationEngine;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class ElevatorGraphicalInterface extends BorderPane {
    private final GridPane labelPane = new GridPane();
    private Label floorLabel = new Label("floor 0");
    private Label directionLabel = new Label("STATIONARY");
    private Label doorLabel = new Label("OPEN");

    public ElevatorGraphicalInterface(int id, int minFloor, int maxFloor, SimulationEngine engine) {
        labelPane.getColumnConstraints().add(new ColumnConstraints(100));

        Label namePane = new Label("ELEVATOR " + (id+1));
        labelPane.add(namePane, 0, 0);
        addLabels();

        GridPane.setHalignment(namePane, HPos.CENTER);
        centerLabels();
        this.setTop(labelPane);

        GridPane buttonPane = new GridPane();
        buttonPane.setHgap(3);
        buttonPane.setVgap(3);
        for (int i = 0; i <= maxFloor - minFloor; i++) {
            int floor = minFloor + i;
            Button button = new Button(""+floor);
            button.setPrefWidth(30);

            button.setOnAction(e -> {
                engine.passCall(id, new ElevatorCall(floor, null));
            });
            buttonPane.add(button, i % 3, i / 3);
        }
        this.setBottom(buttonPane);
    }

    void update(Elevator elevator) {
        labelPane.getChildren().remove(floorLabel);
        floorLabel = new Label("floor " + elevator.getCurrentFloor());

        labelPane.getChildren().remove(directionLabel);
        directionLabel = new Label(elevator.getCurrentDirection().toString());

        labelPane.getChildren().remove(doorLabel);
        doorLabel = new Label(elevator.getDoorState().toString());

        addLabels();
        centerLabels();
    }

    private void centerLabels() {
        GridPane.setHalignment(floorLabel, HPos.CENTER);
        GridPane.setHalignment(directionLabel, HPos.CENTER);
        GridPane.setHalignment(doorLabel, HPos.CENTER);
    }
    private void addLabels() {
        labelPane.add(floorLabel, 0, 1);
        labelPane.add(directionLabel, 0, 2);
        labelPane.add(doorLabel, 0, 3);
    }
}
