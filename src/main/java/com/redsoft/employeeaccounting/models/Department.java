package com.redsoft.employeeaccounting.models;

public class Department {
    private int id;
    private String name;
    private String phone;
    private String email;
    private String departmentHead;
    private int employeeCount;

    public Department(int id, String name, String phone, String email, String departmentHead, int employeeCount) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.departmentHead = departmentHead;
        this.employeeCount = employeeCount;
    }
    public String getDepartmentHead() {
        return departmentHead;
    }

    public void setDepartmentHead(String departmentHead) {
        this.departmentHead = departmentHead;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return name;
    }
}
