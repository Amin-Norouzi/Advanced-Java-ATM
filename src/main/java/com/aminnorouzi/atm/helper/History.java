package com.aminnorouzi.atm.helper;

import com.aminnorouzi.atm.model.Transaction;
import com.aminnorouzi.atm.model.User;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class History {
    private User user;

    public History(User user) {
        this.user = user;
    }

    public boolean saveTransaction(Stage stage, String trackingCode, String details) {
        String path = getFileDirectory(stage);

        if (path != null) {
            path += "\\" + trackingCode + ".txt";
            return saveTextToFile(details, new File(path));
        }

        return false;
    }

    private String getFileDirectory(Stage stage) {
        try {
            return new DirectoryChooser().showDialog(stage).getPath();
        } catch (Exception exception) {
            return null;
        }
    }

    private boolean saveTextToFile(String content, File file) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(content);
            writer.close();
            return true;
        } catch (IOException exception) {
            return false;
        }
    }

}





