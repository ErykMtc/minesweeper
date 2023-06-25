package com.example.temp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * the following methods switching to next scene with information about
     * difficulty level e.g. beginner, normal ...
     */
    public void switchToNextScene1(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameScene.fxml"));
        root = loader.load();
        Controller scene2Controller = loader.getController();
        scene2Controller.sceneChanger(DifficultyLevel.Beginner.toString()); //difficulty level name

        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Saper");
        stage.show();
    }

    public void switchToNextScene2(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameScene.fxml"));
        root = loader.load();
        Controller scene2Controller = loader.getController();
        scene2Controller.sceneChanger(DifficultyLevel.Normal.toString());

        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Saper");
        stage.show();
    }

    public void switchToNextScene3(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameScene.fxml"));
        root = loader.load();
        Controller scene2Controller = loader.getController();
        scene2Controller.sceneChanger(DifficultyLevel.Hard.toString());

        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Saper");
        stage.show();
    }

    public void switchToNextScene4(javafx.event.ActionEvent actionEvent) throws IOException {

        Stage previousStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SetAttributeScene.fxml"));
        root = fxmlLoader.load();
        ControllerOfSetAttribute scene2Controller = fxmlLoader.getController();
        scene2Controller.setPreviousStage(previousStage); // set this window to be able to close in the next window
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL); // cant click on primary window
        stage.show();

    }
}