package com.springboot.app.entity;

import com.opencsv.bean.CsvBindByName;

public class Employee {

    @CsvBindByName
    private long id;
    @CsvBindByName
    private String firstName;
    @CsvBindByName
    private String lastName;

    public Employee() {

    }

    public Employee(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
