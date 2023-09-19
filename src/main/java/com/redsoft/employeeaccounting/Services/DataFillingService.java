package com.redsoft.employeeaccounting.Services;

import com.redsoft.employeeaccounting.Controllers.Controller;
import com.redsoft.employeeaccounting.models.Department;
import com.redsoft.employeeaccounting.models.Employee;
import com.redsoft.employeeaccounting.models.Post;
import javafx.scene.control.ListCell;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DataFillingService {
    Controller controller;

    public DataFillingService(Controller controller) {
        this.controller = controller;
    }

    public void fillDataDep() {
        controller.dataDepartment.clear();
        ResultSet depSet = controller.dbHandler.getDepartments();
        while (true) {
            try {
                if (!depSet.next()) break;
                Department department = new Department(depSet.getInt(1), depSet.getString(2),
                        depSet.getString(3), depSet.getString(4), depSet.getString(5),
                        depSet.getInt(6));
                if (department.getDepartmentHead() == null) department.setDepartmentHead("");
                controller.dataDepartment.add(department);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        controller.columnDepId.setCellValueFactory(new PropertyValueFactory<>("id"));
        controller.columnDepName.setCellValueFactory(new PropertyValueFactory<>("name"));
        controller.columnDepPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        controller.columnDepEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        controller.columnDepHead.setCellValueFactory(new PropertyValueFactory<>("departmentHead"));
        controller.columnDepEmpCount.setCellValueFactory(new PropertyValueFactory<>("employeeCount"));
        controller.tableDepartment.setItems(controller.dataDepartment);
    }

    public void fillDataPost() {
        controller.dataPost.clear();
        ResultSet postSet = controller.dbHandler.getPosts();
        while (true) {
            try {
                if (!postSet.next()) break;
                Post post = new Post(postSet.getInt(1), postSet.getString(2), postSet.getInt(3));
                controller.dataPost.add(post);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        controller.columnPostId.setCellValueFactory(new PropertyValueFactory<>("id"));
        controller.columnPostName.setCellValueFactory(new PropertyValueFactory<>("name"));
        controller.columnPostSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        controller.tablePost.setItems(controller.dataPost);
    }

    public void fillDataEmployee() {
        controller.dataEmployee.clear();
        ResultSet employeeSet = controller.dbHandler.getEmployees();
        fillComboBoxes();
        while (true) {
            try {
                if (!employeeSet.next()) break;
                Employee employee = new Employee(employeeSet.getInt(1), employeeSet.getString(2),
                        employeeSet.getInt(3), employeeSet.getInt(4), employeeSet.getString(5),
                        employeeSet.getString(6), employeeSet.getInt(7));
                controller.dataEmployee.add(employee);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        controller.columnEmpId.setCellValueFactory(new PropertyValueFactory<>("id"));
        controller.columnEmpName.setCellValueFactory(new PropertyValueFactory<>("name"));
        controller.columnEmpDep.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
        controller.columnEmpPost.setCellValueFactory(new PropertyValueFactory<>("postName"));
        controller.columnEmpSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        controller.tableEmployee.setItems(controller.dataEmployee);
    }

    public void fillEditFields(int dataIndex) {
        switch (controller.view) {
            case DEPARTMENT -> {
                Department departmentToEdit = controller.dataDepartment.get(dataIndex);
                controller.textFieldDepName.setText(departmentToEdit.getName());
                controller.textFieldDepPhone.setText(departmentToEdit.getPhone());
                controller.textFieldDepEmail.setText(departmentToEdit.getEmail());
            }
            case POST -> {
                Post postToEdit = controller.dataPost.get(dataIndex);
                controller.textFieldPostName.setText(postToEdit.getName());
                controller.textFieldPostSalary.setText(String.valueOf(postToEdit.getSalary()));
            }
            case EMPLOYEE -> {
                Employee employeeToEdit = controller.dataEmployee.get(dataIndex);
                controller.textFieldEmpName.setText(employeeToEdit.getName());
                Department department = controller.dataDepartment.stream().filter(dep ->
                        dep.getId() == employeeToEdit.getDepId()).findAny().orElse(null);
                Post post = controller.dataPost.stream().filter(p ->
                        p.getId() == employeeToEdit.getPostId()).findAny().orElse(null);
                controller.comboBoxDep.setValue(department);
                controller.comboBoxPost.setValue(post);
            }
        }
    }

    public void fillComboBoxes() {
        controller.comboBoxDep.setItems(controller.dataDepartment);
        controller.comboBoxPost.setItems(controller.dataPost);
        controller.comboBoxDep.setCellFactory(p -> new ListCell<>() {
            @Override
            protected void updateItem(Department department, boolean empty) {
                super.updateItem(department, empty);
                if (department != null && !empty) {
                    setText(department.getName());
                } else {
                    setText(null);
                }
            }
        });
        controller.comboBoxPost.setCellFactory(p -> new ListCell<>() {
            @Override
            protected void updateItem(Post post, boolean empty) {
                super.updateItem(post, empty);
                if (post != null && !empty) {
                    setText(post.getName());
                } else {
                    setText(null);
                }
            }
        });
    }
}
