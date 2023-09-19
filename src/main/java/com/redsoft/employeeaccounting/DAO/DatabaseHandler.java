package com.redsoft.employeeaccounting.DAO;

import com.redsoft.employeeaccounting.models.Department;
import com.redsoft.employeeaccounting.models.Employee;
import com.redsoft.employeeaccounting.models.Post;

import java.io.File;
import java.sql.*;

public class DatabaseHandler {

    private static final File DbDirectory =new File(".");
    private static final String URL = "jdbc:firebirdsql:local:" + DbDirectory.getAbsolutePath() + "/company.fdb?lc_ctype=WIN1251";
    private static final String USER = "SYSDBA";
    private static final String PASSWORD = "masterkey";
    private static Connection connection;
    private static ResultSet resultSet = null;
    private static final String DEPARTMENT = "DEP";
    private static final String POST = "POST";
    private static final String EMPLOYEE = "EMPLOYEE";

    static {
        try {
            Class.forName("org.firebirdsql.jdbc.FBDriver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            if (connection == null) {
                System.err.println("Could not connect to database");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public ResultSet getDepartments() {
        String getDepartments = """
                SELECT DEP.ID, DEP.NAME, DEP.PHONE, DEP.EMAIL,
                
                    (SELECT EMPLOYEE.NAME
                    FROM EMPLOYEE
                    JOIN POST ON EMPLOYEE.PostId = POST.ID
                    WHERE POST.NAME = 'Начальник отдела' AND EMPLOYEE.DepId = DEP.ID)
                    AS "DEP_HEAD",
                    
                    (SELECT COUNT(*)
                    FROM EMPLOYEE WHERE EMPLOYEE.DepID = DEP.ID)
                    AS "COUNT"
                    
                FROM DEP
                """;

        PreparedStatement prSt = null;
        try {
            prSt = getConnection().prepareStatement(getDepartments);
            resultSet = prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public ResultSet getPosts() {
        String getPosts = "SELECT * FROM POST";
        PreparedStatement prSt = null;
        try {
            prSt = getConnection().prepareStatement(getPosts);
            resultSet = prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public ResultSet getEmployees() {
        String getEmployees = """
                SELECT EMPLOYEE.ID, EMPLOYEE.NAME, EMPLOYEE.DepID, EMPLOYEE.PostID, DEP.NAME, POST.NAME, POST.SALARY
                FROM EMPLOYEE
                JOIN DEP ON EMPLOYEE.DepId = DEP.ID
                JOIN POST ON EMPLOYEE.PostId = POST.ID
                """;
        PreparedStatement prSt = null;
        try {
            prSt = getConnection().prepareStatement(getEmployees);
            resultSet = prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public void insertDepartment(Department department) {
        String insertDep = "INSERT INTO DEP (ID,NAME,PHONE,EMAIL) VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getConnection().prepareStatement(insertDep);
            preparedStatement.setInt(1, department.getId());
            preparedStatement.setString(2, department.getName());
            preparedStatement.setString(3, department.getPhone());
            preparedStatement.setString(4, department.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void insertPost(Post postToAdd) {
        String insertPost = "INSERT INTO POST (ID,NAME,SALARY) VALUES (?,?,?)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getConnection().prepareStatement(insertPost);
            preparedStatement.setInt(1, postToAdd.getId());
            preparedStatement.setString(2, postToAdd.getName());
            preparedStatement.setInt(3, postToAdd.getSalary());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertEmployee(Employee employeeToAdd) {
        String insertEmployee = "INSERT INTO EMPLOYEE (ID,NAME,DepId, PostId) VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getConnection().prepareStatement(insertEmployee);
            preparedStatement.setInt(1, employeeToAdd.getId());
            preparedStatement.setString(2, employeeToAdd.getName());
            preparedStatement.setInt(3, employeeToAdd.getDepId());
            preparedStatement.setInt(4, employeeToAdd.getPostId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void editDepartment(Department department) {
        String editDepartment = "UPDATE DEP SET NAME = ?, PHONE = ?, EMAIL = ? WHERE ID = ?";
        PreparedStatement preparedStatement = null;
        String name = department.getName();
        String phone = department.getPhone();
        String email = department.getEmail();
        int id = department.getId();
        try {
            preparedStatement = getConnection().prepareStatement(editDepartment);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, phone);
            preparedStatement.setString(3, email);
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void editPost(Post postToEdit) {
        String editPost = "UPDATE POST SET NAME = ?, SALARY = ? WHERE ID = ?";
        PreparedStatement preparedStatement = null;
        int id = postToEdit.getId();
        String name = postToEdit.getName();
        int salary = postToEdit.getSalary();
        try {
            preparedStatement = getConnection().prepareStatement(editPost);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, salary);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void editEmployee(Employee employeeToEdit) {
        String editEmployee = "UPDATE EMPLOYEE SET NAME = ?, DepId = ?, PostId = ? WHERE ID = ?";
        PreparedStatement preparedStatement = null;
        int id = employeeToEdit.getId();
        String name = employeeToEdit.getName();
        int depId = employeeToEdit.getDepId();
        int postId = employeeToEdit.getPostId();
        try {
            preparedStatement = getConnection().prepareStatement(editEmployee);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, depId);
            preparedStatement.setInt(3, postId);
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int idToDelete, Object o) throws SQLException {
        String fromTableName = "";
        if (o instanceof Department) fromTableName = DEPARTMENT;
        else if (o instanceof Post)fromTableName = POST;
        else if (o instanceof Employee) fromTableName = EMPLOYEE;
        String deleteRequest = "DELETE FROM " + fromTableName + " WHERE ID = ?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = getConnection().prepareStatement(deleteRequest);
            preparedStatement.setInt(1, idToDelete);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
