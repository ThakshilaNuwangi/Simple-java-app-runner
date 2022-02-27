package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePageController {
    public Button btnStart;

    public void btnStartOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane root = FXMLLoader.load(this.getClass().getResource("/view/MainForm.fxml"));
        Stage primaryStage = new Stage();
        Scene homeScene = new Scene(root);
        primaryStage.setScene(homeScene);
        primaryStage.setTitle("Simple Java App Runner");
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.show();
        ((Stage)(btnStart.getScene().getWindow())).close();
    }
}
