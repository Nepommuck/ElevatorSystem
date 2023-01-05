package elevator.gui;

import elevator.ElevatorCall;
import elevator.MoveDirection;
import elevator.SimulationEngine;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class App extends Application{
    private Scene scene;
    private Stage stage;
    private GridPane gridPane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        primaryStage.setTitle("Lab 7");

        gridPane = new GridPane();

        javafx.scene.control.Button saveConfigButton = new javafx.scene.control.Button("Test");
        gridPane.add(saveConfigButton, 1, 1);

        scene = new Scene(gridPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        SimulationEngine engine = new SimulationEngine(2, getCalls());
        Thread engineThread = new Thread(engine);

        engineThread.start();
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
}
