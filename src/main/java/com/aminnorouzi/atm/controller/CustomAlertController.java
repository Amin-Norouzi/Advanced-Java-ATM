package com.aminnorouzi.atm.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomAlertController implements Initializable {
    @FXML
    private ImageView alertImage;
    @FXML
    private Label bodyLabel;
    @FXML
    private Button closeButton;
    @FXML
    private Label headerLabel;
    @FXML
    private Button mainButton;
    @FXML
    private BorderPane root;

    private final BorderPane currentRoot;
    private Stage stage;
    private int code = 0;

    private final String button;
    private final Image image;
    private final String header;
    private final String body;

    public void show() {
        setEffect();
        headerLabel.setText(header);
        bodyLabel.setText(body);
        alertImage.setImage(image);
        mainButton.setText(button);
        stage.showAndWait();
    }

    private void close() {
        clearEffect();
        stage.close();
    }

    private void setEffect() {
        BoxBlur boxBlur = new BoxBlur(5, 5, 3);
        currentRoot.setEffect(boxBlur);
    }

    private void clearEffect() {
        currentRoot.setEffect(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainButton.setOnAction(e -> {
            code = 1;
            close();
        });

        closeButton.setOnAction(e -> close());
        stage.setOnCloseRequest(e -> close());
    }

    public CustomAlertController(BorderPane currentRoot, String button, Image image, String header, String body) {
        this.currentRoot = currentRoot;
        this.button = button;
        this.image = image;
        this.header = header;
        this.body = body;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public int getCode() {
        return code;
    }
}
