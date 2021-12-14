package com.aminnorouzi.atm;

import com.aminnorouzi.atm.database.Database;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main extends Application {

    private static final Database database = new Database("jdbc:sqlite:ATM-DB.db");

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/SplashView.fxml")));

        primaryStage.getIcons().add(new Image("/images/Main-Icon.png"));
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        Scene scene = new Scene(root);
        scene.getStylesheets().add("styles/SplashPage.css");

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        loadFonts();
        launch(args);
    }

    @Override
    public void stop() {
        if (database.close()) {
            Platform.exit();
        }
    }

    @Override
    public void init() {
        if (!database.open()) {
            Platform.exit();
        }
    }

    public static void loadFonts() {
        ArrayList<String> fonts = new ArrayList<>(getFonts());
        for (String font : fonts) {
            Font.loadFont(Main.class.getResourceAsStream("/fonts/" + font), 16);
        }
    }

    public static List<String> getFonts() {
        ArrayList<String> fonts = new ArrayList<>();
        fonts.add("OpenSans-Bold.ttf");
        fonts.add("OpenSans-BoldItalic.ttf");
        fonts.add("OpenSans-ExtraBold.ttf");
        fonts.add("OpenSans-ExtraBoldItalic.ttf");
        fonts.add("OpenSans-Italic.ttf");
        fonts.add("OpenSans-Light.ttf");
        fonts.add("OpenSans-LightItalic.ttf");
        fonts.add("OpenSans-Medium.ttf");
        fonts.add("OpenSans-MediumItalic.ttf");
        fonts.add("OpenSans-Regular.ttf");
        fonts.add("OpenSans-SemiBold.ttf");
        fonts.add("OpenSans-SemiBoldItalic.ttf");

        return fonts;
    }

    public static Connection getConnection() {
        return database.getConnection();
    }
}

