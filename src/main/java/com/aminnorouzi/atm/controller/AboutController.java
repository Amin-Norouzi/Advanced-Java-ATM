package com.aminnorouzi.atm.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable {
    @FXML
    private Button closeButton;
    @FXML
    private Label contactLabel;
    @FXML
    private Label databaseLabel;
    @FXML
    private ImageView devImage;
    @FXML
    private Label emailLabel;
    @FXML
    private Label githubLabel;
    @FXML
    private Label headerLabel;
    @FXML
    private Label instagramLabel;
    @FXML
    private Label linkedinLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label technologiesLabel;
    @FXML
    private Label versionLabel;
    @FXML
    private Label copyrightLabel;

    private Stage stage;
    private final BorderPane currentRoot;

    private static final String INSTAGRAM = "https://www.instagram.com/realaminnorouzi/";
    private static final String LINKEDIN = "https://www.linkedin.com/in/amin-norouzi/";
    private static final String GITHUB = "https://github.com/Amin-Norouzi";
    private static final String EMAIL = "mailto:realaminnorouzi@gmail.com";

    @FXML
    private void handleInstagram(MouseEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI(INSTAGRAM));
    }

    @FXML
    private void handleLinkedin(MouseEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI(LINKEDIN));
    }

    @FXML
    private void handleGithub(MouseEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI(GITHUB));
    }

    @FXML
    private void handleEmail(MouseEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().mail(new URI(EMAIL));
    }

    private void roundImage() {
        devImage.setImage(new Image("/images/Developer.png"));

        Rectangle clip = new Rectangle(devImage.getFitWidth(), devImage.getFitHeight());

        clip.setArcWidth(500);
        clip.setArcHeight(500);
        devImage.setClip(clip);

        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        WritableImage image = devImage.snapshot(parameters, null);

        devImage.setClip(null);
        devImage.setEffect(new DropShadow(15, Color.BLACK));
        devImage.setImage(image);
    }

    private void setEffect() {
        BoxBlur boxBlur = new BoxBlur(5, 5, 3);
        currentRoot.setEffect(boxBlur);
    }

    private void clearEffect() {
        currentRoot.setEffect(null);
    }

    private void close() {
        clearEffect();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setEffect();
        roundImage();

        closeButton.setOnAction(e -> close());
        stage.setOnCloseRequest(e -> close());
    }

    public AboutController(BorderPane currentRoot) {
        this.currentRoot = currentRoot;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
