package com.ksoft.crm.data;

import java.awt.image.BufferedImage;

public class Employee {

    private int id;

    private String name;

    private String address;

    private String telephone;

    private String role;

    private BufferedImage photo;

    private int salary = 0;

    public Employee(int id, String name, String address, String telephone, String role, BufferedImage photo, int salary) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.telephone = telephone;
        this.role = role;
        this.photo = photo;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public BufferedImage getPhoto() {
        return photo;
    }

    public void setPhoto(BufferedImage photo) {
        this.photo = photo;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }


}
