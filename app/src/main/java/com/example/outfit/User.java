package com.example.outfit;

public class User {
    public String firstName;
    public String lastName;
    public String userName;
    public String email;
    public String password;

    public User(){

    }

    public User(String firstName, String lastName, String userName, String email, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
}
