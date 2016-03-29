package com.example.taras.homeworkfragments;

/**
 * Created by taras on 27.03.16.
 */
public interface EventHandler {
    String loadPreferences();

    void updatePreferences(String orderTag);

    void showEmptyFieldMessage();

    void showNoGenderMessage();

    void addPerson(DataModel person);

    boolean isLoginUnique(String login);

    DataModel findUserByLogin(String login);

    void removePerson(DataModel person);

    void registerPerson(String login, String password, String firstName, String lastName, String gender);

    void updateContent(DataModel person);
}
