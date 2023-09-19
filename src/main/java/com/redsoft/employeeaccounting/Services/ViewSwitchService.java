package com.redsoft.employeeaccounting.Services;

import com.redsoft.employeeaccounting.Controllers.Controller;

public class ViewSwitchService {
    private Controller controller;

    public ViewSwitchService(Controller controller) {
        this.controller = controller;
    }
    public void switchEditRead() {
        if (!controller.buttonCancel.isVisible()) {
            controller.buttonPost.setDisable(true);
            controller.buttonEmployee.setDisable(true);
            controller.buttonDep.setDisable(true);
            controller.tableDepartment.setDisable(true);
            controller.tablePost.setDisable(true);
            controller.tableEmployee.setDisable(true);
            controller.buttonAdd.setVisible(false);
            controller.buttonEdit.setVisible(false);
            controller.buttonDelete.setVisible(false);
            controller.buttonConfirm.setVisible(true);
            controller.buttonCancel.setVisible(true);
        } else {
            controller.buttonPost.setDisable(false);
            controller.buttonEmployee.setDisable(false);
            controller.buttonDep.setDisable(false);
            controller.tableDepartment.setDisable(false);
            controller.tablePost.setDisable(false);
            controller.tableEmployee.setDisable(false);
            controller.buttonAdd.setVisible(true);
            controller.buttonEdit.setVisible(true);
            controller.buttonDelete.setVisible(true);
            controller.buttonConfirm.setVisible(false);
            controller.buttonCancel.setVisible(false);
        }
    }

    public void switchWorkTable() {
        switch (controller.view) {
            case DEPARTMENT -> {
                controller.tableDepartment.setVisible(true);
                controller.textFieldDepName.setVisible(true);
                controller.textFieldDepPhone.setVisible(true);
                controller.textFieldDepEmail.setVisible(true);
                controller.tablePost.setVisible(false);
                controller.textFieldPostName.setVisible(false);
                controller.textFieldPostSalary.setVisible(false);
                controller.tableEmployee.setVisible(false);
                controller.textFieldEmpName.setVisible(false);
                controller.comboBoxDep.setVisible(false);
                controller.comboBoxPost.setVisible(false);
            }
            case POST -> {
                controller.tableDepartment.setVisible(false);
                controller.textFieldDepName.setVisible(false);
                controller.textFieldDepPhone.setVisible(false);
                controller.textFieldDepEmail.setVisible(false);
                controller.tablePost.setVisible(true);
                controller.textFieldPostName.setVisible(true);
                controller.textFieldPostSalary.setVisible(true);
                controller.tableEmployee.setVisible(false);
                controller.textFieldEmpName.setVisible(false);
                controller.comboBoxDep.setVisible(false);
                controller.comboBoxPost.setVisible(false);
            }
            case EMPLOYEE -> {
                controller.tableDepartment.setVisible(false);
                controller.textFieldDepName.setVisible(false);
                controller.textFieldDepPhone.setVisible(false);
                controller.textFieldDepEmail.setVisible(false);
                controller.tablePost.setVisible(false);
                controller.textFieldPostName.setVisible(false);
                controller.textFieldPostSalary.setVisible(false);
                controller.tableEmployee.setVisible(true);
                controller.textFieldEmpName.setVisible(true);
                controller.comboBoxDep.setVisible(true);
                controller.comboBoxPost.setVisible(true);
            }
        }
    }
}
