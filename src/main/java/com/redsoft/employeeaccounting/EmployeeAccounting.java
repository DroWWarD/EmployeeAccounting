package com.redsoft.employeeaccounting;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EmployeeAccounting extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EmployeeAccounting.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 953, 635);
        stage.setTitle("Employee accounting");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}