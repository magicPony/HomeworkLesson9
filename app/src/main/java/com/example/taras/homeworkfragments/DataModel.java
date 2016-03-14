package com.example.taras.homeworkfragments;

import java.io.Serializable;

/**
 * Created by taras on 14.03.16.
 */
public class DataModel implements Serializable {
    String login, password, firstName, lastName, gender;

    public DataModel(String login, String password, String firstName, String lastName, String gender) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
