package com.example.taras.homeworkfragments;

import java.util.ArrayList;

/**
 * Created by taras on 27.03.16.
 */
public interface EventHandler {
    String loadPreferences();

    void updatePreferences(String orderTag);

    void showEmptyFieldMessage();

    void showNoGenderMessage();

    void updateUsersList();

    ArrayList<DataModel> loadData();

    boolean isLoginUnique(String login);

    DataModel findUserByLogin(String login);

    void removePerson(DataModel person);

    void registerPerson(DataModel person);

    void updateContent(DataModel person);
}
