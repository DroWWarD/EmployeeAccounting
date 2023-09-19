module com.redsoft.employeeaccounting {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.firebirdsql.jaybird;

    /* для запуска проекта, помимо установки RedDataBase 3.0, нужно
    подключить к проекту библиотеки из директории "Libraries" в корне проекта*/


    opens com.redsoft.employeeaccounting to javafx.fxml;
    exports com.redsoft.employeeaccounting;
    exports com.redsoft.employeeaccounting.models;
    opens com.redsoft.employeeaccounting.models to javafx.fxml;
    exports com.redsoft.employeeaccounting.utilities;
    opens com.redsoft.employeeaccounting.utilities to javafx.fxml;
    exports com.redsoft.employeeaccounting.DAO;
    opens com.redsoft.employeeaccounting.DAO to javafx.fxml;
    exports com.redsoft.employeeaccounting.Controllers;
    opens com.redsoft.employeeaccounting.Controllers to javafx.fxml;
}