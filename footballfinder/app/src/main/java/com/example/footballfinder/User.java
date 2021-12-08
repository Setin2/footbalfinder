package com.example.footballfinder;

/*
 * Class representing users
 */
public class User {
    int id;
    String username;
    String password;

    /*
     * Constructor
     */
    public User(int id, String name, String password){
        this.id = id;
        this.username = name;
        this.password = password;
    }

}