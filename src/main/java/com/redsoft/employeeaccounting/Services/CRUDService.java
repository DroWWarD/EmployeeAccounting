package com.redsoft.employeeaccounting.Services;

import com.redsoft.employeeaccounting.Controllers.Controller;
import com.redsoft.employeeaccounting.models.Department;
import com.redsoft.employeeaccounting.models.Employee;
import com.redsoft.employeeaccounting.models.Post;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.Optional;

import static com.redsoft.employeeaccounting.utilities.IntegerChecker.isInteger;

public class CRUDService {
    Controller controller;

    public CRUDService(Controller controller) {
        this.controller = controller;
    }

    public void addDepartment() {
        int maxId;
        if (controller.dataDepartment.isEmpty()) maxId = 0;
        else maxId = controller.dataDepartment.stream().max(Comparator.comparing(Department::getId)).orElseThrow().getId();
        Department departmentToAdd = new Department(maxId + 1, controller.textFieldDepName.getText().trim(),
                controller.textFieldDepPhone.getText().trim(), controller.textFieldDepEmail.getText().trim(), " ", 0);
        controller.dataDepartment.add(departmentToAdd);
        controller.dbHandler.insertDepartment(departmentToAdd);
    }

    public void addPost() {
        int maxId;
        if (controller.dataPost.isEmpty()) maxId = 0;
        else maxId = controller.dataPost.stream().max(Comparator.comparing(Post::getId)).orElseThrow().getId();
        String salary = controller.textFieldPostSalary.getText().trim();
        if (isInteger(salary)) {
            Post postToAdd = new Post(maxId + 1, controller.textFieldPostName.getText().trim(), Integer.parseInt(salary));
            controller.dataPost.add(postToAdd);
            controller.dbHandler.insertPost(postToAdd);
        }
    }

    public void addEmployee() {
        int maxId;
        if (controller.dataEmployee.isEmpty()) maxId = 0;
        else maxId = controller.dataEmployee.stream().max(Comparator.comparing(Employee::getId)).orElseThrow().getId();
        Department selectedDepartment = controller.comboBoxDep.getValue();
        Post selectedPost = controller.comboBoxPost.getValue();
        if (selectedPost.getName().equals("Начальник отдела") && !selectedDepartment.getDepartmentHead().isBlank()) {
            controller.interfaceService.showAlert("В '" + selectedDepartment.getName() + "' на должность 'Начальник отдела' уже назначен сотрудник " +
                    selectedDepartment.getDepartmentHead());
        } else {
            Employee employeeToAdd = new Employee(maxId + 1, controller.textFieldEmpName.getText().trim(),
                    selectedDepartment.getId(), selectedPost.getId(), selectedDepartment.getName(), selectedPost.getName(),
                    selectedPost.getSalary());
            if (employeeToAdd.getPostName().equals("Начальник отдела")){
                selectedDepartment.setDepartmentHead(employeeToAdd.getName());
            }
            controller.dataEmployee.add(employeeToAdd);
            controller.dbHandler.insertEmployee(employeeToAdd);
        }
    }

    public void editDepartment() {
        int selectedTableIndex = controller.tableDepartment.getSelectionModel().getSelectedIndex();
        Department departmentToEdit = controller.dataDepartment.get(selectedTableIndex);
        departmentToEdit.setName(controller.textFieldDepName.getText());
        departmentToEdit.setPhone(controller.textFieldDepPhone.getText());
        departmentToEdit.setEmail(controller.textFieldDepEmail.getText());
        controller.dbHandler.editDepartment(departmentToEdit);
    }

    public void editPost() {
        int selectedTableIndex = controller.tablePost.getSelectionModel().getSelectedIndex();
        Post postToEdit = controller.dataPost.get(selectedTableIndex);
        String salary = controller.textFieldPostSalary.getText();
        if (isInteger(salary)) {
            postToEdit.setName(controller.textFieldPostName.getText());
            postToEdit.setSalary(Integer.parseInt(salary));
            controller.dbHandler.editPost(postToEdit);
        }
    }

    public void editEmployee() {
        int selectedTableIndex = controller.tableEmployee.getSelectionModel().getSelectedIndex();
        Employee selectedEmployee = controller.dataEmployee.get(selectedTableIndex);
        Department selectedDepartment = controller.comboBoxDep.getValue();
        Post selectedPost = controller.comboBoxPost.getValue();
        if (selectedPost.getName().equals("Начальник отдела") && !selectedDepartment.getDepartmentHead().isBlank()) {
            controller.interfaceService.showAlert("В '" + selectedDepartment.getName() + "' на должность 'Начальник отдела' уже назначен сотрудник " +
                    selectedDepartment.getDepartmentHead());
        } else {
            selectedEmployee.setName(controller.textFieldEmpName.getText());
            selectedEmployee.setDepId(selectedDepartment.getId());
            selectedEmployee.setPostId(selectedPost.getId());
            selectedEmployee.setDepartmentName(selectedDepartment.getName());
            selectedEmployee.setPostName(selectedPost.getName());
            if (selectedEmployee.getPostName().equals("Начальник отдела")){
                selectedDepartment.setDepartmentHead(selectedEmployee.getName());
            }
            selectedEmployee.setSalary(selectedPost.getSalary());
            controller.dbHandler.editEmployee(selectedEmployee);
        }
    }

    public void buttonExecuteDelete() {
        int selectedTableIndex = -1;
        int idToDelete = -1;
        Object objectToDelete = null;
        try {
            switch (controller.view) {
                case DEPARTMENT -> {
                    selectedTableIndex = controller.tableDepartment.getSelectionModel().getSelectedIndex();
                    objectToDelete = controller.tableDepartment.getItems().get(selectedTableIndex);
                    idToDelete = controller.tableDepartment.getItems().get(selectedTableIndex).getId();
                }
                case POST -> {
                    selectedTableIndex = controller.tablePost.getSelectionModel().getSelectedIndex();
                    objectToDelete = controller.tablePost.getItems().get(selectedTableIndex);
                    idToDelete = controller.tablePost.getItems().get(selectedTableIndex).getId();
                }
                case EMPLOYEE -> {
                    selectedTableIndex = controller.tableEmployee.getSelectionModel().getSelectedIndex();
                    objectToDelete = controller.tableEmployee.getItems().get(selectedTableIndex);
                    idToDelete = controller.tableEmployee.getItems().get(selectedTableIndex).getId();
                }
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Удаление записи из БД");
            alert.setHeaderText("Вы действительно хотите удалить запись?");
            alert.setContentText("Нажмите ОК для удаления и Cancel для отмены");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                try {
                    controller.dbHandler.delete(idToDelete, objectToDelete);
                    switch (controller.view) {
                        case DEPARTMENT -> controller.dataDepartment.remove(selectedTableIndex);
                        case POST -> controller.dataPost.remove(selectedTableIndex);
                        case EMPLOYEE -> controller.dataEmployee.remove(selectedTableIndex);
                    }
                } catch (SQLException e) {
                    controller.interfaceService.showAlert("Невозможно удалить отдел или должность, к которым назначены сотрудники");
                }
            }
        } catch (IndexOutOfBoundsException e) {
            controller.interfaceService.showAlert("Элемент для удаления не выбран");
        }
    }
}
