package com.example.taras.homeworkfragments;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

final public class MainActivity extends AppCompatActivity implements EventHandler {
    public DBHelper dBHelper;
    private LoginFragment loginFragment = new LoginFragment((EventHandler) this);
    private static DataModel lastActivePerson = null;
    private SharedPreferences preferences;
    private UsersListFragment usersListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dBHelper = new DBHelper(this);

        if (savedInstanceState == null) {
            commitLoginFragment();
        }
    }

    @Override
    public void registerPerson(DataModel person) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.LOGIN_TAG, person.getLogin());
        contentValues.put(Constants.PASSWORD_TAG, person.getPassword());
        contentValues.put(Constants.FIRST_NAME_TAG, person.getFirstName());
        contentValues.put(Constants.LAST_NAME_TAG, person.getLastName());
        contentValues.put(Constants.GENDER_TAG, person.getGender());

        SQLiteDatabase db = dBHelper.getWritableDatabase();
        db.insert(Constants.DATA_BASE_NAME, null, contentValues);
        dBHelper.close();
    }

    @Override
    public boolean isLoginUnique(String login) {
        ArrayList<DataModel> data = loadData();

        for (DataModel i : data)
            if (i.getLogin().equals(login)) {
                return false;
            }

        return true;
    }

    @Override
    public DataModel findUserByLogin(String login) {
        ArrayList<DataModel> data = loadData();

        for (DataModel i : data)
            if (i.getLogin().equals(login)) {
                return i;
            }

        return null;
    }

    @Override
    public void removePerson(DataModel person) {
        dBHelper.removePerson(person);
    }

    @Override
    public void updateContent(DataModel person) {
        View view = loginFragment.getView();
        TextView tvHello;
        EditText etLogin, etPassword;
        tvHello = (TextView) view.findViewById(R.id.tv_hello_LFL);
        etLogin = (EditText) view.findViewById(R.id.et_username_LFL);
        etPassword = (EditText) view.findViewById(R.id.et_password_LFL);
        String tmp = "Hello, ";
        tmp += person.getGender().equals(Constants.MALE_TAG) ? "Mr." : "Mrs.";
        tmp += person.getFirstName();
        tmp += " ";
        tmp += person.getLastName();

        tvHello.setText(tmp);
        etLogin.setText(person.getLogin());
        etPassword.setText(person.getPassword());
        lastActivePerson = person;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (lastActivePerson != null) {
            outState.putSerializable(Constants.ACTIVE_PERSON_TAG, lastActivePerson);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState.getSerializable(Constants.ACTIVE_PERSON_TAG) != null) {
            lastActivePerson = (DataModel) savedInstanceState.getSerializable(Constants.ACTIVE_PERSON_TAG);
            updateContent(lastActivePerson);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mi_about :
                commitAboutFragment();
                break;

            case R.id.mi_settings :
                commitSettingsFragment();
                break;

            case R.id.mi_show_users :
                commitUsersListFragment();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void commitUsersListFragment() {
        usersListFragment = new UsersListFragment((EventHandler) this);
        hideKeyboard();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, usersListFragment, Constants.LOGIN_FRAGMENT_TAG)
                .addToBackStack(Constants.USERS_LIST_FRAGMENT_TAG)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    private void commitSettingsFragment() {
        SettingsFragment settingsFragment = new SettingsFragment((EventHandler) this);
        hideKeyboard();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, settingsFragment, Constants.LOGIN_FRAGMENT_TAG)
                .addToBackStack(Constants.SETTINGS_FRAGMENT_TAG)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    private void commitAboutFragment() {
        AboutFragment aboutFragment = new AboutFragment();
        hideKeyboard();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, aboutFragment, Constants.LOGIN_FRAGMENT_TAG)
                .addToBackStack(Constants.ABOUT_FRAGMENT_TAG)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();

        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void commitLoginFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, loginFragment, Constants.LOGIN_FRAGMENT_TAG)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    @Override
    public String loadPreferences() {
        preferences = getPreferences(MODE_PRIVATE);
        return preferences.getString(Constants.PREFERENCES_ORDER_TAG, Constants.LOGIN_TAG);
    }

    @Override
    public void updatePreferences(String orderTag) {
        SharedPreferences preferences = this.getPreferences(Context.MODE_PRIVATE);
        preferences.edit()
                .putString(Constants.PREFERENCES_ORDER_TAG, orderTag)
                .commit();
    }

    @Override
    public void showEmptyFieldMessage() {
        AlertDialog dialog = new AlertDialog
                .Builder(this)
                .setMessage(R.string.empty_field_message)
                .create();

        dialog.show();
    }

    @Override
    public void showNoGenderMessage() {
        // TODO : unite messages
        AlertDialog dialog = new AlertDialog
                .Builder(this)
                .setMessage(getString(R.string.no_gender_message))
                .create();

        dialog.show();
    }

    @Override
    public void updateUsersList() {
        usersListFragment.initData();
    }

    @Override
    public ArrayList<DataModel> loadData() {
        return dBHelper.loadData(loadPreferences());
    }
}
