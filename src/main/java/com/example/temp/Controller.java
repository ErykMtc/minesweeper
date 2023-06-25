package com.example.temp;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import static java.lang.System.exit;

public class Controller implements Initializable {

    @FXML
    private Text bombNum;
    @FXML
    private Text timer;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private GridPane gridPane;


    Time time = new Time("0:0:0");

    private int realHeight;
    private int realWidth;
    private int realBomb;
    private Board board;
    private boolean isRead = false;


    /**
     * This method change the size of start window and logic
     * @param width
     * @param height
     */
    @FXML
    public void sizeChanger(int width, int height, int bomb){

        //bombNum.setText(String.valueOf(bomb));

        mainPane.setPrefWidth(width);
        mainPane.setPrefHeight(height);

        gridPane.setPrefHeight(height - 50);
        gridPane.setPrefWidth(width);

        int tempH = (height - 50)/30;
        System.out.println(tempH);
        int tempW = (width)/30;

        realHeight = tempH;
        realWidth = tempW;
        realBomb = bomb;

        // add row
        if(tempH > 10){
            for(int i = 0; i < tempH - 10; i++ ){
                gridPane.addRow(0);
            }
        }
        // add column in grid pane
        if (tempW > 10){
            for(int i = 0; i < tempW - 10; i++ ){
                gridPane.addColumn(0);
            }
        }

        gameLogic(tempW,tempH,bomb);
    }

    /**
     *This method generates board, sets the image and handles mouse action
     * @param tempW
     * @param tempH
     * @param bomb
     */
    public void gameLogic(int tempW, int tempH, int bomb){

        if(!isRead) {
            board = new Board(tempH, tempW, bomb);
            board.generatedBoard(bomb);
        }

        bombNum.setText(String.valueOf(bomb));

        for (int i = 0; i < tempH; i++) {
            for (int j = 0; j < tempW; j++) {
                    ImageView imageView = new ImageView(getClass().getResource("img/facingDown.png").toString());
                    imageView.setFitHeight(29);
                    imageView.setFitWidth(30);
                    imageView.setPreserveRatio(false);

                    if (isRead && board.isClicked(i, j)){
                        Image image = new Image(Objects.requireNonNull(getClass().getResource("img/" + board.getNumber(i, j) + ".png")).toString());
                        imageView.setImage(image);
                    } else if(isRead && board.isFlagged(i, j)){
                        Image image = new Image(Objects.requireNonNull(getClass().getResource("img/flagged.png")).toString());
                        imageView.setImage(image);
                        int tempNum = Integer.parseInt(bombNum.getText());
                        tempNum--;
                        bombNum.setText(String.valueOf(tempNum));
                    }

                int finalI = i;
                int finalJ = j;
                imageView.setOnMouseClicked((mouseEvent) -> {
                    // right mouse button
                    if(mouseEvent.getButton() == MouseButton.PRIMARY) {
                        if(!board.isFlagged(finalI, finalJ)){
                        // game over :(
                        if (board.isBomb(finalI, finalJ)) {
                            Image image = new Image(Objects.requireNonNull(getClass().getResource("img/bomb.png")).toString());
                            imageView.setImage(image);

                            showAllFields(board);

                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Porazka");
                            alert.setHeaderText("Własnie przegrales");
                            alert.setContentText("Chcesz rozpoczac nowa gre?");
                            timeline.stop();

                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                // ... user chose OK
                                gridPane.getChildren().clear();
                                isRead = false;
                                gameLogic(tempW, tempH, bomb);
                                time = new Time("0:0:0");
                                timeline.play();

                            } else {
                                // user chose CANCEL or closed the dialog
                                exit(0);
                            }
                        } else {
                            System.out.println(board.getFieldToWin());

                            if (board.getNumber(finalI, finalJ) == 0) {
                                floodFill(board, finalI, finalJ);
                            } else {
                                if (!board.isClicked(finalI, finalJ)) {
                                    Image image = new Image(Objects.requireNonNull(getClass().getResource("img/" + board.getNumber(finalI, finalJ) + ".png")).toString());
                                    imageView.setImage(image);
                                    board.setClicked(finalI, finalJ);
                                }
                            }
                            //win
                            if (board.winGame()) {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Zwyciestwo");
                                alert.setHeaderText("Gratulacje wygrales, dasz rade jeszcze raz?");
                                alert.setContentText("Chcesz rozpoczac nowa gre?");

                                timeline.stop();
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == ButtonType.OK) {
                                    // ... user chose OK
                                    gridPane.getChildren().clear();
                                    isRead = false;
                                    gameLogic(tempW, tempH, bomb);
                                    time = new Time("0:0:0");
                                    timeline.play();
                                } else {
                                    // user chose CANCEL or closed the dialog
                                    exit(0);
                                }
                            }
                        }
                        }
                    } else {
                        //flag block the mouse from being clicked
                        if(!board.isClicked(finalI,finalJ)){
                            if (!board.isFlagged(finalI,finalJ)) {
                                board.setFlagged(finalI, finalJ);
                                Image image = new Image(Objects.requireNonNull(getClass().getResource("img/flagged.png")).toString());
                                imageView.setImage(image);
                                int tempNum = Integer.parseInt(bombNum.getText());
                                tempNum--;
                                bombNum.setText(String.valueOf(tempNum));
                            } else {
                                Image image = new Image(Objects.requireNonNull(getClass().getResource("img/facingDown.png")).toString());
                                imageView.setImage(image);
                                board.antiSetFlagged(finalI,finalJ);
                                int tempNum = Integer.parseInt(bombNum.getText());
                                tempNum++;
                                bombNum.setText(String.valueOf(tempNum));
                            }
                        }
                    }
                });
                gridPane.add(imageView, j, i);
            }
        }
    }


    /**
     * method set attribute for every level and calls sizeChenger method
     * @param buttonName
     */
    public void sceneChanger(String buttonName){
        switch (buttonName) {
            case "Beginner" -> sizeChanger(300, 350, 12);
            case "Normal" -> sizeChanger(450, 500, 40);
            case "Hard" -> sizeChanger(600, 650, 99);
            default -> throw new IllegalStateException("Invalid name: " + buttonName);
        }

    }

    Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(1),
                    e -> {
                        time.oneSecondPassed();
                        timer.setText(time.getCurrentTime());
                    }));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timer.setText(time.getCurrentTime());

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    public void ownScene(int height, int width, int bomb){
        sizeChanger(width, height, bomb);
    }

    public void floodFill(Board board, int h, int w){

        if (h >= realHeight || w >= realWidth)
            return;
        if(h < 0 || w < 0)
            return;

        try {
            if (board.getNumber(h, w) != 0 && !board.isClicked(h, w) && !board.isBomb(h, w)) {
                Image image = new Image(Objects.requireNonNull(getClass().getResource("img/" + board.getNumber(h, w) + ".png")).toString());
                ImageView imgV = (ImageView) getNodeByRowColumnIndex(h, w);
                imgV.setImage(image);
                board.setClicked(h, w);
            }
        }catch (ArrayIndexOutOfBoundsException e){

        }

            if (!(board.isClicked(h,w)) && board.getNumber(h, w) == 0) {

                Image image = new Image(getClass().getResource("img/0.png").toString());
                ImageView imgV = (ImageView) getNodeByRowColumnIndex(h, w);
                imgV.setImage(image);

                board.setClicked(h, w);

                floodFill(board, h, (w - 1));
                floodFill(board, h + 1, w);
                floodFill(board, h, w + 1);
                floodFill(board, (h - 1), w);
            }
    }

    public Node getNodeByRowColumnIndex (final int row, final int column) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }

    public void showAllFields(Board board){
        for (int i = 0; i < realHeight; i++){
            for (int j = 0; j < realWidth; j++){
                if (board.isBomb(i, j)) {
                    Image image = new Image(Objects.requireNonNull(getClass().getResource("img/bomb.png")).toString());
                    ImageView imgV = (ImageView) getNodeByRowColumnIndex(i, j);
                    imgV.setImage(image);
                } else {
                    Image image = new Image(Objects.requireNonNull(getClass().getResource("img/" + board.getNumber(i, j) + ".png")).toString());
                    ImageView imgV = (ImageView) getNodeByRowColumnIndex(i, j);
                    imgV.setImage(image);
                }
            }
        }
    }

    public void saveAction(ActionEvent actionEvent) throws IOException {
        JSONSerialization c = new JSONSerialization();
        c.board2JSON(board);
    }

    public void readAction(ActionEvent actionEvent) throws IOException {
        JSONSerialization c = new JSONSerialization();

        board = c.JSONFileDeserialization("Save1");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameScene.fxml"));
        Parent root = loader.load();
        Controller scene2Controller = loader.getController();
        scene2Controller.setRead(true);
        scene2Controller.setBoard(board);
        scene2Controller.ownScene(board.getHeight()*30 + 50, board.getWidth()*30, board.getBombNum());
        Stage stage = (Stage) mainPane.getScene().getWindow(); // action event dont have a node and getscene so i use mainpane
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Saper");
        stage.show();

    }

    public void closeAction(ActionEvent actionEvent) {
        exit(0);
    }

    public void beginnerAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameScene.fxml"));
        Parent root = loader.load();
        Controller scene2Controller = loader.getController();
        scene2Controller.sceneChanger(DifficultyLevel.Beginner.toString());
        Stage stage = (Stage) mainPane.getScene().getWindow(); // action event dont have a node and getscene so i use mainpane
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Saper");
        stage.show();
    }

    public void normalAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameScene.fxml"));
        Parent root = loader.load();
        Controller scene2Controller = loader.getController();
        scene2Controller.sceneChanger(DifficultyLevel.Normal.toString());
        Stage stage = (Stage) mainPane.getScene().getWindow(); // action event dont have a node and getscene so i use mainpane
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Saper");
        stage.show();
    }

    public void hardAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameScene.fxml"));
        Parent root = loader.load();
        Controller scene2Controller = loader.getController();
        scene2Controller.sceneChanger(DifficultyLevel.Hard.toString());
        Stage stage = (Stage) mainPane.getScene().getWindow(); // action event dont have a node and getscene so i use mainpane
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Saper");
        stage.show();
    }

    public void ownAction(ActionEvent actionEvent) throws IOException {
        Stage previousStage = (Stage) mainPane.getScene().getWindow(); // action event dont have a node and getscene so i use mainpane
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SetAttributeScene.fxml"));
        Parent root = fxmlLoader.load();
        ControllerOfSetAttribute scene2Controller = fxmlLoader.getController();
        scene2Controller.setPreviousStage(previousStage); // set this window to be able to close in the next window
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL); // cant click on primary window
        stage.show();
    }

    public void gameRulesAction(ActionEvent actionEvent) throws IOException {
        String fileName = "GameRules.txt";
        ClassLoader classLoader = getClass().getClassLoader();

        File file = new File(Objects.requireNonNull(getClass().getResource(fileName)).getFile());

        //Read File Content
        String content = new String(Files.readAllBytes(file.toPath()));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Zasady");
        alert.setHeaderText(null);

        alert.setContentText(content);

        alert.showAndWait();
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
