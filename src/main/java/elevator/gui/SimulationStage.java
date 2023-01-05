package elevator.gui;

import elevator.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SimulationStage extends Stage implements IPositionChangeObserver {
    private final GridPane gridPane;
    private final ArrayList<ElevatorGraphicalInterface> graphicalElevators = new ArrayList<>();
    private final ArrayList<VBox> elevatorIcons = new ArrayList<>();
    private final int maxFloor;

    public SimulationStage(int numberOfElevators, int minFloor, int maxFloor, int delay) {
        this.maxFloor = maxFloor;
        final int basicFloor = 0;
        SimulationEngine engine = new SimulationEngine(numberOfElevators, null, this, delay, basicFloor);
        Thread engineThread = new Thread(engine);

        this.setTitle("Elevator Simulator");
        gridPane = new GridPane();

        // Floor labels
        for (int i = minFloor; i <= maxFloor; i++) {
            final int floor = i;
            Label label = new Label(""+floor);
            gridPane.add(label, 2, maxFloor-i);
            GridPane.setHalignment(label, HPos.CENTER);

            if (floor < maxFloor) {
                Button upButton = new Button("^");
                upButton.setPrefWidth(25);
                upButton.setOnAction(e -> {
                    engine.passCall(-1, new ElevatorCall(floor, MoveDirection.UPWARD));
                });
                gridPane.add(upButton, 0, floorRowIndex(i));
            }
            if (floor > minFloor) {
                Button downButton = new Button("v");
                downButton.setPrefWidth(25);
                downButton.setOnAction(e -> {
                    engine.passCall(-1, new ElevatorCall(floor, MoveDirection.DOWNWARD));
                });
                gridPane.add(downButton, 1, floorRowIndex(i));
            }
        }

        for (int i = 0; i < numberOfElevators; i++) {
            graphicalElevators.add(new ElevatorGraphicalInterface(i, minFloor, maxFloor, engine));
            gridPane.add(graphicalElevators.get(i), i + 3, 16);

            elevatorIcons.add(new VBox());
            updateElevatorIcon(i, DoorState.OPEN, basicFloor);
        }

        gridPane.setHgap(3);
        gridPane.setVgap(3);
        gridPane.setPadding(new Insets(40, 10, 10, 40));

        Scene scene = new Scene(gridPane,
            Math.max(800, 100 + 110 * numberOfElevators),
            Math.max(600, 600 + 40 * (maxFloor - minFloor - 11))
        );

        this.setScene(scene);
        this.show();
        engineThread.start();
        this.setResizable(false);
        this.setOnCloseRequest(e -> engineThread.interrupt());
    }

    @Override
    public void positionChanged(Elevator elevator) {
        graphicalElevators.get(elevator.id).update(elevator);
        updateElevatorIcon(elevator);
    }
    
    private void updateElevatorIcon(Elevator elevator) {
        updateElevatorIcon(elevator.id, elevator.getDoorState(), elevator.getCurrentFloor());
    }
    private void updateElevatorIcon(int index, DoorState doorState, int currentFloor) {
        gridPane.getChildren().remove(elevatorIcons.get(index));
        Color color = switch (doorState) {
            case OPEN -> Color.GREEN;
            case CLOSING, OPEANING -> Color.GRAY;
            case CLOSED -> Color.BLACK;
        };
        elevatorIcons.get(index).setBackground(new Background(
                new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
        
        gridPane.add(elevatorIcons.get(index), index + 3,
                floorRowIndex(currentFloor));
    }
    
    private int floorRowIndex(int floor) {
        return maxFloor - floor;
    }
}
