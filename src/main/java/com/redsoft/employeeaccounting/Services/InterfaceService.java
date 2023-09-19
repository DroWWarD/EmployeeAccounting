package com.redsoft.employeeaccounting.Services;

import com.redsoft.employeeaccounting.Controllers.Controller;
import javafx.scene.control.Alert;

public class InterfaceService {
    Controller controller;

    public InterfaceService(Controller controller) {
        this.controller = controller;
    }

    public void clearTextFieldsAndComboBoxes() {
        controller.textFieldDepName.setText("");
        controller.textFieldDepPhone.setText("");
        controller.textFieldDepEmail.setText("");
        controller.textFieldPostName.setText("");
        controller.textFieldPostSalary.setText("");
        controller.textFieldEmpName.setText("");
        controller.comboBoxDep.valueProperty().set(null);
        controller.comboBoxPost.valueProperty().set(null);
    }

    public void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(alertMessage);
        alert.showAndWait();
    }

    public void refreshAllTables() {
        controller.tableDepartment.refresh();
        controller.tablePost.refresh();
        controller.tableEmployee.refresh();
    }

    public void cancelSelecting() {
        controller.tableDepartment.getSelectionModel().clearSelection();
        controller.tablePost.getSelectionModel().clearSelection();
        controller.tableEmployee.getSelectionModel().clearSelection();
    }

    public boolean fieldsDataIsCorrect() {
        return switch (controller.view) {
            case DEPARTMENT ->
                    !controller.textFieldDepName.getText().isBlank() && !controller.textFieldDepPhone.getText().isBlank() && !controller.textFieldDepEmail.getText().isBlank();
            case POST -> !controller.textFieldPostName.getText().isBlank() && !controller.textFieldPostSalary.getText().isBlank();
            case EMPLOYEE -> !controller.textFieldEmpName.getText().isBlank();
        };
    }
    public boolean isEditable(int index) {
        return controller.buttonEdit.getText().equals("Изменить") && index != -1;
    }
}
