package elevator.gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class App extends Application {

    @Override
    public void start(Stage startStage) {
        ArrayList<Integer> acceptedMinimalFloorValues = getArray(-3, 0);
        ArrayList<Integer> acceptedMaximalFloorValues = getArray(4, 20);
        ArrayList<Integer> acceptedElevatorsNumberValues = getArray(1, 12);
        ArrayList<Integer> acceptedDelayValues = new ArrayList<>(Arrays.asList(500, 800, 1000, 2000, 3000, 5000));

        GridPane grid = new GridPane();
        ComboBox maximalFloorCombo = generateCombo(acceptedMaximalFloorValues, 4);
        grid.add(new Label("Highest floor: "), 0, 0);
        grid.add(maximalFloorCombo, 1, 0);

        ComboBox minimalFloorCombo = generateCombo(acceptedMinimalFloorValues, 3);
        grid.add(new Label("Lowest floor: "), 0, 1);
        grid.add(minimalFloorCombo, 1, 1);

        ComboBox elevatorsNumberCombo = generateCombo(acceptedElevatorsNumberValues, 1);
        grid.add(new Label("Number of elevators: "), 0, 2);
        grid.add(elevatorsNumberCombo, 1, 2);

        ComboBox delayCombo = generateCombo(acceptedDelayValues, 2);
        grid.add(new Label("Delay (ms): "), 0, 3);
        grid.add(delayCombo, 1, 3);

        CheckBox closeWindowCheckbox = new CheckBox("Close this window after starting the simulation");
//        grid.add(new Label("Close this window after starting the simulation"), 1, 4);
//        grid.add(closeWindowCheckbox, 0, 4);
//        GridPane.setHalignment(closeWindowCheckbox, HPos.RIGHT);

        Button startButton = new Button("Start");
        grid.add(startButton, 1, 5);

        grid.setHgap(5);
        grid.setVgap(20);
        grid.setPadding(new Insets(40, 10, 10, 40));

        GridPane menuPane = new GridPane();
        menuPane.add(grid, 0, 0);
        menuPane.add(closeWindowCheckbox, 0, 1);
        menuPane.add(startButton, 0, 2);

        menuPane.setHgap(5);
        menuPane.setVgap(20);
        menuPane.setPadding(new Insets(10, 10, 10, 40));

        Scene scene = new Scene(new BorderPane(menuPane, grid, null, null, null), 800, 600);
        startStage.setScene(scene);
        startStage.show();
        startStage.setTitle("Elevator Simulator - configuration");

        startButton.setOnAction(e -> {
            new SimulationStage(
                    (Integer) elevatorsNumberCombo.getValue(),
                    (Integer) minimalFloorCombo.getValue(),
                    (Integer) maximalFloorCombo.getValue(),
                    (Integer) delayCombo.getValue()
            );
            if (closeWindowCheckbox.isSelected())
                startStage.close();
        });
    }

    private ComboBox generateCombo(ArrayList<Integer> constraints, int basicIndex) {
        ComboBox combo = new ComboBox(FXCollections.observableArrayList(constraints));
        combo.getSelectionModel().select(basicIndex);
        return combo;
    }

    private ArrayList<Integer> getArray(int from, int to) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = from; i <= to; i++) {
            result.add(i);
        }
        return result;
    }
}
