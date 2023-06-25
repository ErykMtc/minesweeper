package com.example.temp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerOfSetAttribute {

    @FXML
    private TextField bomb;
    @FXML
    private TextField width;
    @FXML
    private TextField height;
    @FXML
    private Label warning;

    // previous stage
    private Stage previousStage;

    /**
     * This method set game parameters and close previous window when parameters are correct
     * @param actionEvent
     * @throws IOException
     */
    public void displayNewScene(ActionEvent actionEvent) throws IOException {

        if (isNumeric(bomb.getText()) && isNumeric(height.getText()) && isNumeric(width.getText())) {
            int numberOfBombs = Integer.parseInt(bomb.getText());
            int numberHeight = Integer.parseInt(height.getText());
            int numberWidth = Integer.parseInt(width.getText());

            int maxNumberOfBombs = numberHeight * numberWidth - 1;

            if (numberHeight > 20 || numberHeight < 10 || numberWidth > 30 || numberWidth < 10 || numberOfBombs < 1 || numberOfBombs > maxNumberOfBombs) {
                warning.setText("Wpisz poprawna ilosc, max bomb: " + maxNumberOfBombs);
            } else {
                //calculation about height and width scene
                int sceneHeight = numberHeight * 30 + 50;
                int sceneWidth = numberWidth * 30;

                //rest
                FXMLLoader loader = new FXMLLoader(getClass().getResource("GameScene.fxml"));
                Parent root = loader.load();
                Controller scene2Controller = loader.getController();
                scene2Controller.ownScene(sceneHeight, sceneWidth, numberOfBombs);

                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Saper");
                stage.show();
                getPreviousStage().close();
            }
        } else {
            warning.setText("Brak wpisanych wartości");
        }
    }

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public void setPreviousStage(Stage previousStage) {
        this.previousStage = previousStage;
    }

    public Stage getPreviousStage() {
        return previousStage;
    }
}
