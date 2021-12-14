package com.aminnorouzi.atm.util;

import com.aminnorouzi.atm.controller.*;
import com.aminnorouzi.atm.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class PageUtils {

    private static final String LOGIN_VIEW = "/views/LoginView.fxml";
    private static final String LOGIN_PAGE = "styles/LoginPage.css";

    public static void switchToLoginView(BorderPane borderPane) throws IOException {
        ((Stage) borderPane.getScene().getWindow()).close();

        Parent root = FXMLLoader.load(Objects.requireNonNull(PageUtils.class.getResource(LOGIN_VIEW)));

        Stage stage = new Stage();
        stage.getIcons().add(new Image("/images/Main-Icon.png"));
        stage.setTitle("Advanced Java ATM");
        stage.setResizable(false);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(LOGIN_PAGE);

        stage.setScene(scene);
        stage.show();
    }

    public static void switchToLoginView(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(PageUtils.class.getResource(LOGIN_VIEW)));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(LOGIN_PAGE);

        stage.setScene(scene);
        stage.show();
    }

    public static void switchToLoginView(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(PageUtils.class.getResource(LOGIN_VIEW)));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(LOGIN_PAGE);

        stage.setScene(scene);
        stage.show();
    }

    public static void switchToSignUpView(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(PageUtils.class.getResource("/views/ForgotPasswordView.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);
        scene.getStylesheets().add("styles/ForgotPasswordPage.css");

        stage.setScene(scene);
        stage.show();
    }

    public static void switchToForgotPasswordView(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(PageUtils.class.getResource("/views/SignUpView.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);
        scene.getStylesheets().add("styles/SignUpPage.css");

        stage.setScene(scene);
        stage.show();
    }

    public static void switchToAtmHomeView(ActionEvent event, User user) throws IOException {
        AtmHomeController atmHomeController = new AtmHomeController(user);

        FXMLLoader loader = new FXMLLoader(PageUtils.class.getResource("/views/AtmHomeView.fxml"));
        loader.setController(atmHomeController);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        atmHomeController.setStage(stage);

        Parent root = loader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add("styles/AtmHomePage.css");

        stage.setScene(scene);
        stage.show();
    }

    public static void switchToDepositView(ActionEvent event, User user) throws IOException {
        DepositController depositController = new DepositController(user);

        FXMLLoader loader = new FXMLLoader(PageUtils.class.getResource("/views/DepositView.fxml"));
        loader.setController(depositController);

        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);
        scene.getStylesheets().add("styles/DepositPage.css");

        stage.setScene(scene);
        stage.show();
    }

    public static void switchToWithdrawalView(ActionEvent event, User user) throws IOException {
        WithdrawalController withdrawalController = new WithdrawalController(user);

        FXMLLoader loader = new FXMLLoader(PageUtils.class.getResource("/views/WithdrawalView.fxml"));
        loader.setController(withdrawalController);

        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);
        scene.getStylesheets().add("styles/WithdrawalPage.css");

        stage.setScene(scene);
        stage.show();
    }

    public static void switchToTransferView(ActionEvent event, User user) throws IOException {
        TransferController transferController = new TransferController(user);

        FXMLLoader loader = new FXMLLoader(PageUtils.class.getResource("/views/TransferView.fxml"));
        loader.setController(transferController);

        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);
        scene.getStylesheets().add("styles/TransferPage.css");

        stage.setScene(scene);
        stage.show();
    }

    public static void switchToBalanceView(ActionEvent event, User user) throws IOException {
        BalanceController balanceController = new BalanceController(user);

        FXMLLoader loader = new FXMLLoader(PageUtils.class.getResource("/views/BalanceView.fxml"));
        loader.setController(balanceController);

        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);
        scene.getStylesheets().add("styles/BalancePage.css");

        stage.setScene(scene);
        stage.show();
    }

    public static void switchToHistoryView(ActionEvent event, User user) throws IOException {
        HistoryController historyController = new HistoryController(user);

        FXMLLoader loader = new FXMLLoader(PageUtils.class.getResource("/views/HistoryView.fxml"));
        loader.setController(historyController);

        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);
        scene.getStylesheets().add("styles/HistoryPage.css");

        stage.setScene(scene);
        stage.show();
    }

    public static void switchToSettingsView(ActionEvent event, User user) throws IOException {
        SettingsController settingsController = new SettingsController(user);

        FXMLLoader loader = new FXMLLoader(PageUtils.class.getResource("/views/SettingsView.fxml"));
        loader.setController(settingsController);

        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);
        scene.getStylesheets().add("styles/SettingsPage.css");

        stage.setScene(scene);
        stage.show();
    }

    public static void switchToAccountInfoView(MouseEvent event, User user) throws IOException {
        AccountInfoController accountInfoController = new AccountInfoController(user);

        FXMLLoader loader = new FXMLLoader(PageUtils.class.getResource("/views/AccountInfoView.fxml"));
        loader.setController(accountInfoController);

        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);
        scene.getStylesheets().add("styles/AccountInfoPage.css");

        stage.setScene(scene);
        stage.show();
    }

    public static void switchToUserPasswordView(MouseEvent event, User user) throws IOException {
        UserPasswordController userPasswordController = new UserPasswordController(user);

        FXMLLoader loader = new FXMLLoader(PageUtils.class.getResource("/views/UserPasswordView.fxml"));
        loader.setController(userPasswordController);

        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);
        scene.getStylesheets().add("styles/UserPasswordPage.css");

        stage.setScene(scene);
        stage.show();
    }

    public static void switchToCardPasswordView(MouseEvent event, User user) throws IOException {
        CardPasswordController cardPasswordController = new CardPasswordController(user);

        FXMLLoader loader = new FXMLLoader(PageUtils.class.getResource("/views/CardPasswordView.fxml"));
        loader.setController(cardPasswordController);

        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);
        scene.getStylesheets().add("styles/CardPasswordPage.css");

        stage.setScene(scene);
        stage.show();
    }

    public static void switchToDeleteAccountView(MouseEvent event, User user) throws IOException {
        DeleteAccountController deleteAccountController = new DeleteAccountController(user);

        FXMLLoader loader = new FXMLLoader(PageUtils.class.getResource("/views/DeleteAccountView.fxml"));
        loader.setController(deleteAccountController);

        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);
        scene.getStylesheets().add("styles/DeleteAccountPage.css");

        stage.setScene(scene);
        stage.show();
    }

    public static void switchToAboutView(BorderPane borderPane) throws IOException {
        AboutController aboutController = new AboutController(borderPane);

        FXMLLoader loader = new FXMLLoader(PageUtils.class.getResource("/views/AboutView.fxml"));
        loader.setController(aboutController);

        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);

        aboutController.setStage(stage);

        Parent root = loader.load();

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add("styles/AboutPage.css");

        stage.setScene(scene);
        stage.showAndWait();
    }
}
