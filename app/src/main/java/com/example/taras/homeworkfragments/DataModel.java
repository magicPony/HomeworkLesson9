package com.example.taras.homeworkfragments;

import java.io.Serializable;

/**
 * Created by taras on 14.03.16.
 */
final public class DataModel implements Serializable {
    private String login, password, firstName, lastName, gender;

    public DataModel(String login, String password, String firstName, String lastName, String gender) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }
}
