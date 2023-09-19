package com.redsoft.employeeaccounting.Controllers;

import com.redsoft.employeeaccounting.Services.CRUDService;
import com.redsoft.employeeaccounting.Services.DataFillingService;
import com.redsoft.employeeaccounting.Services.InterfaceService;
import com.redsoft.employeeaccounting.Services.ViewSwitchService;
import com.redsoft.employeeaccounting.models.Department;
import com.redsoft.employeeaccounting.models.Employee;
import com.redsoft.employeeaccounting.models.Post;
import com.redsoft.employeeaccounting.DAO.DatabaseHandler;
import com.redsoft.employeeaccounting.utilities.View;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

import static com.redsoft.employeeaccounting.utilities.View.*;

public class Controller implements Initializable {
    @FXML
    public Button buttonConfirm;
    @FXML
    public Button buttonCancel;
    @FXML
    public TableView<Post> tablePost;
    @FXML
    public TableColumn<Post, Integer> columnPostId;
    @FXML
    public TableColumn<Post, String> columnPostName;
    @FXML
    public TableColumn<Post, Integer> columnPostSalary;
    @FXML
    public TextField textFieldPostName;
    @FXML
    public TextField textFieldPostSalary;
    @FXML
    public TableView<Employee> tableEmployee;
    @FXML
    public TableColumn<Employee, Integer> columnEmpId;
    @FXML
    public TableColumn<Employee, String> columnEmpName;
    @FXML
    public TableColumn<Employee, Integer> columnEmpDep;
    @FXML
    public TableColumn<Employee, Integer> columnEmpPost;
    @FXML
    public TextField textFieldEmpName;
    @FXML
    public TableColumn<Department, String> columnDepHead;
    @FXML
    public TableColumn<Department, Integer> columnDepEmpCount;
    @FXML
    public TableColumn<Employee, Integer> columnEmpSalary;
    @FXML
    public ComboBox<Department> comboBoxDep;
    @FXML
    public ComboBox<Post> comboBoxPost;
    @FXML
    public Button buttonAdd;
    @FXML
    public Button buttonDelete;
    @FXML
    public Button buttonDep;
    @FXML
    public Button buttonEdit;
    @FXML
    public Button buttonEmployee;
    @FXML
    public Button buttonPost;
    @FXML
    public TableColumn<Department, String> columnDepEmail;
    @FXML
    public TableColumn<Department, Integer> columnDepId;
    @FXML
    public TableColumn<Department, String> columnDepName;
    @FXML
    public TableColumn<Department, String> columnDepPhone;
    @FXML
    public TableView<Department> tableDepartment;
    @FXML
    public TextField textFieldDepEmail;
    @FXML
    public TextField textFieldDepName;
    @FXML
    public TextField textFieldDepPhone;
    public DatabaseHandler dbHandler = new DatabaseHandler();
    public final ObservableList<Department> dataDepartment = FXCollections.observableArrayList();
    public final ObservableList<Post> dataPost = FXCollections.observableArrayList();
    public final ObservableList<Employee> dataEmployee = FXCollections.observableArrayList();
    public View view;
    public ViewSwitchService viewSwitchService;
    public InterfaceService interfaceService;
    public CRUDService crudService;
    public DataFillingService dataFillingService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewSwitchService = new ViewSwitchService(this);
        interfaceService = new InterfaceService(this);
        crudService = new CRUDService(this);
        dataFillingService = new DataFillingService(this);
        dataFillingService.fillDataDep();
        dataFillingService.fillDataPost();
        dataFillingService.fillDataEmployee();
        view = DEPARTMENT;
    }

    @FXML
    public void buttonAddAction(ActionEvent actionEvent) {
        if (interfaceService.fieldsDataIsCorrect()) {
            switch (view) {
                case DEPARTMENT -> crudService.addDepartment();
                case POST -> crudService.addPost();
                case EMPLOYEE -> crudService.addEmployee();
            }
            interfaceService.clearTextFieldsAndComboBoxes();
        }
    }

    @FXML
    public void buttonEditAction(ActionEvent actionEvent) {
        int selectedTableIndex = -1;
        switch (view) {
            case DEPARTMENT -> selectedTableIndex = tableDepartment.getSelectionModel().getSelectedIndex();
            case POST -> selectedTableIndex = tablePost.getSelectionModel().getSelectedIndex();
            case EMPLOYEE -> selectedTableIndex = tableEmployee.getSelectionModel().getSelectedIndex();
        }
        if (interfaceService.isEditable(selectedTableIndex)) {
            dataFillingService.fillEditFields(selectedTableIndex);
            viewSwitchService.switchEditRead();
        }
    }

    @FXML
    public void buttonDeleteAction(ActionEvent actionEvent) {
        crudService.buttonExecuteDelete();
        interfaceService.cancelSelecting();
    }

    @FXML
    public void buttonConfirmAction(ActionEvent actionEvent) {
        switch (view) {
            case DEPARTMENT -> crudService.editDepartment();
            case POST -> crudService.editPost();
            case EMPLOYEE -> crudService.editEmployee();
        }
        interfaceService.refreshAllTables();
        interfaceService.clearTextFieldsAndComboBoxes();
        viewSwitchService.switchEditRead();
        interfaceService.cancelSelecting();
    }

    @FXML
    public void buttonCancelAction(ActionEvent actionEvent) {
        interfaceService.clearTextFieldsAndComboBoxes();
        viewSwitchService.switchEditRead();
        interfaceService.cancelSelecting();
    }

    @FXML
    public void buttonDepAction(ActionEvent actionEvent) {
        interfaceService.clearTextFieldsAndComboBoxes();
        dataFillingService.fillDataDep();
        view = DEPARTMENT;
        viewSwitchService.switchWorkTable();
    }

    @FXML
    public void buttonPostAction(ActionEvent actionEvent) {
        interfaceService.clearTextFieldsAndComboBoxes();
        dataFillingService.fillDataPost();
        view = POST;
        viewSwitchService.switchWorkTable();
    }

    @FXML
    public void buttonEmployeeAction(ActionEvent actionEvent) {
        interfaceService.clearTextFieldsAndComboBoxes();
        dataFillingService.fillDataEmployee();
        view = EMPLOYEE;
        viewSwitchService.switchWorkTable();
    }
}