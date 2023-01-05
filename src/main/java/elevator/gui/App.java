package elevator.gui;

import elevator.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;

public class App extends Application implements IPositionChangeObserver {
    private Scene scene;
    private Stage stage;
    private GridPane floorButtonsGrid;
    private Label elevatorStateLabel;
    private final ArrayList<ElevatorGraphicalInterface> graphicalElevators = new ArrayList<>();
    private final ArrayList<VBox> elevatorIcons = new ArrayList<>();
    int numberOfElevators = 1;
    int minFloor = -1;
    int maxFloor = 10;
    int basicFloor = 0;

    public App() {
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        SimulationEngine engine = new SimulationEngine(1, null, this);
        Thread engineThread = new Thread(engine);


        stage = primaryStage;
        primaryStage.setTitle("Lab 7");

        floorButtonsGrid = new GridPane();

        for (int i = minFloor; i <= maxFloor; i++) {
            final int floor = i;
            floorButtonsGrid.add(new Label(""+floor), 0, maxFloor-i);

            if (floor < maxFloor) {
                Button UpButton = new Button("^");
                UpButton.setOnAction(e -> {
                    engine.passCall(new ElevatorCall(floor, MoveDirection.UPWARD));
                });
                floorButtonsGrid.add(UpButton, 1, floorRowIndex(i));
            }
            if (floor > minFloor) {
                Button UpButton = new Button("v");
                UpButton.setOnAction(e -> {
                    engine.passCall(new ElevatorCall(floor, MoveDirection.DOWNWARD));
                });
                floorButtonsGrid.add(UpButton, 2, floorRowIndex(i));
            }
        }

        for (int i = 0; i < numberOfElevators; i++) {
            graphicalElevators.add(new ElevatorGraphicalInterface(i));
            floorButtonsGrid.add(graphicalElevators.get(i), i + 3, 16);

            elevatorIcons.add(new VBox());
            updateElevatorIcon(i, DoorState.OPEN, basicFloor);
        }

        scene = new Scene(floorButtonsGrid, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
        engineThread.start();
        stage.setOnCloseRequest(e -> engineThread.interrupt());
    }

    public ArrayList<ElevatorCall> getCalls(){
        ArrayList<ElevatorCall> calls = new ArrayList<>();
        calls.add(new ElevatorCall(4, null));
//        calls.add(new ElevatorCall(3, MoveDirection.D));
        calls.add(new ElevatorCall(0, null));
        calls.add(new ElevatorCall(6, MoveDirection.DOWNWARD));
        calls.add(new ElevatorCall(1, MoveDirection.UPWARD));
        calls.add(new ElevatorCall(5, MoveDirection.UPWARD));
        calls.add(new ElevatorCall(0, MoveDirection.UPWARD));
        calls.add(new ElevatorCall(9, null));

        return calls;
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
        floorButtonsGrid.getChildren().remove(elevatorIcons.get(index));
        Color color = switch (doorState) {
            case OPEN -> Color.GREEN;
            case CLOSING, OPEANING -> Color.GRAY;
            case CLOSED -> Color.BLACK;
        };
        elevatorIcons.get(index).setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
        
        floorButtonsGrid.add(elevatorIcons.get(index), index + 3,
                floorRowIndex(currentFloor));
    }
    
    private int floorRowIndex(int floor) {
        return maxFloor - floor;
    }
}
