package com.ksoft.crm.data;

/**
 * Created by prasadh on 11/24/2016.
 */
public class Customer {

    private int id;
    private String name;
    private String phone;
    private String email;
    private String clientType;
    private String address;

    public Customer(int id, String name, String phone, String email,
                     String clientType, String address)
    {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.clientType = clientType;
        this.address = address;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getName(){return name;}
    public void setName(String name) {this.name = name;}

    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone = phone;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getClientType() {return clientType;}
    public void setClientType(String clientType) {this.clientType = clientType;}

    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}
}
