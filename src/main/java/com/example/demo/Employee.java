package com.example.demo;

/**
 * Created by michaelgoode on 27/10/2017.
 */

public class Employee {

    int id;

    String name;

    String email;

    String dept;

    String location;

    public Employee(int id, String name, String email, String dept, String location) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dept = dept;
        this.location = location;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
