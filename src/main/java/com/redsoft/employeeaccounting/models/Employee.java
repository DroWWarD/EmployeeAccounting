package com.redsoft.employeeaccounting.models;

public class Employee {
    private int id;
    private String name;
    private int depId;
    private int postId;
    private String departmentName;
    private String postName;
    private int salary;

    public Employee(int id, String name, int depId, int postId, String departmentName, String postName, int salary) {
        this.id = id;
        this.name = name;
        this.depId = depId;
        this.postId = postId;
        this.departmentName = departmentName;
        this.postName = postName;
        this.salary = salary;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDepId() {
        return depId;
    }

    public void setDepId(int depId) {
        this.depId = depId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
}
