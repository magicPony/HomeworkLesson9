package com.example.taras.homeworkfragments;

/**
 * Created by taras on 14.03.16.
 */
public class MessageGenerator {
    public static String generateWelcomeMessage(String firstName, String lastName, String gender) {
        String res = "Welcome, ";
        res += gender.equals(Constants.MALE_TAG) ? "Mr." : "Mrs.";
        res += firstName;
        res += " ";
        res += lastName;
        return res;
    }

    public static String generateRegistrationMessage(String firstName, String lastName) {
        String res = "User ";
        res += firstName;
        res += " ";
        res += lastName;
        res += " registered";
        return res;
    }
}
