package elevator.gui;

import elevator.*;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

public class App extends Application implements IPositionChangeObserver {
    private Scene scene;
    private Stage stage;
    private GridPane gridPane;
    private Label elevatorStateLabel;
    private final ArrayList<ElevatorGraphicalInterface> graphicalElevators = new ArrayList<>();
    private final ArrayList<VBox> elevatorIcons = new ArrayList<>();
    int numberOfElevators = 6;
    int minFloor = -1;
    int maxFloor = 10;
    int basicFloor = 0;

    public App() {
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        SimulationEngine engine = new SimulationEngine(numberOfElevators, null, this);
        Thread engineThread = new Thread(engine);


        stage = primaryStage;
        primaryStage.setTitle("Lab 7");

        gridPane = new GridPane();

        // Floor labels
        for (int i = minFloor; i <= maxFloor; i++) {
            final int floor = i;
            Label label = new Label(""+floor);
            gridPane.add(label, 2, maxFloor-i);
            GridPane.setHalignment(label, HPos.CENTER);

            if (floor < maxFloor) {
                Button UpButton = new Button("^");
                UpButton.setOnAction(e -> {
                    engine.passCall(-1, new ElevatorCall(floor, MoveDirection.UPWARD));
                });
                gridPane.add(UpButton, 0, floorRowIndex(i));
            }
            if (floor > minFloor) {
                Button UpButton = new Button("v");
                UpButton.setOnAction(e -> {
                    engine.passCall(-1, new ElevatorCall(floor, MoveDirection.DOWNWARD));
                });
                gridPane.add(UpButton, 1, floorRowIndex(i));
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

        scene = new Scene(gridPane,
                Math.max(800, 100 + 110 * numberOfElevators),
                600);

        primaryStage.setScene(scene);
        primaryStage.show();
        engineThread.start();
        stage.setResizable(false);
        stage.setOnCloseRequest(e -> engineThread.interrupt());
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
        elevatorIcons.get(index).setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
        
        gridPane.add(elevatorIcons.get(index), index + 3,
                floorRowIndex(currentFloor));
    }
    
    private int floorRowIndex(int floor) {
        return maxFloor - floor;
    }
}
