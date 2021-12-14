package com.aminnorouzi.atm.util;

import com.aminnorouzi.atm.controller.CustomAlertController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class AlertUtils {
    private static BorderPane root;
    private static Stage stage;
    private static Scene scene;
    private static Parent parent;

    private static final String CUSTOM_ALERT_VIEW = "/views/CustomAlertView.fxml";
    private static final String CUSTOM_ALERT_CSS = "/styles/CustomAlertPage.css";

    private static int showDialog(String button, Image image, String header, String body) {
        CustomAlertController alert = new CustomAlertController(root, button, image, header, body);

        FXMLLoader loader = new FXMLLoader(AlertUtils.class.getResource(CUSTOM_ALERT_VIEW));
        loader.setController(alert);

        initStage();
        alert.setStage(stage);

        try {
            parent = loader.load();
        } catch (IOException exception) {}

        initScene();
        stage.setScene(scene);

        alert.show();
        return alert.getCode();
    }

    private static void initStage() {
        stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
    }

    private static void initScene() {
        scene = new Scene(parent);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(CUSTOM_ALERT_CSS);
    }

    public static int showSuccessDialog(String header, String body) {
        return showDialog("Done", new Image("/images/Success.png"), header, body);
    }

    public static int showErrorDialog(String header, String body) {
        return showDialog("Try again", new Image("/images/Error.png"), header, body);
    }

    public static int showWarningDialog(String header, String body) {
        return showDialog("Done", new Image("/images/Warning.png"), header, body);
    }

    public static int showInformationDialog(String header, String body) {
        return showDialog("Save", new Image("/images/Information.png"), header, body);
    }

    public static void setRoot(BorderPane currentRoot) {
        root = currentRoot;
    }
}
