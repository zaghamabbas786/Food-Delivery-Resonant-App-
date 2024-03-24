package com.example.fooddeliverysystem;

public class UsersSignupModel {


    private String name,username,email,shipingAddress,permentAddress,pasword,status;
    public UsersSignupModel(String name, String username, String email, String shipingAddress, String permentAddress, String pasword, String status) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.shipingAddress = shipingAddress;
        this.permentAddress = permentAddress;
        this.pasword = pasword;
        this.status = status;
    }

    public UsersSignupModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getShipingAddress() {
        return shipingAddress;
    }

    public void setShipingAddress(String shipingAddress) {
        this.shipingAddress = shipingAddress;
    }

    public String getPermentAddress() {
        return permentAddress;
    }

    public void setPermentAddress(String permentAddress) {
        this.permentAddress = permentAddress;
    }

    public String getPasword() {
        return pasword;
    }

    public void setPasword(String pasword) {
        this.pasword = pasword;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
