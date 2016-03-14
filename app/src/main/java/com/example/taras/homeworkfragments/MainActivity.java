package com.example.taras.homeworkfragments;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<DataModel> mData = new ArrayList<DataModel>();
    private static LoginFragment loginFragment;
    private static DataModel lastActivePerson = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            commitLoginFragment();
        }
    }

    public void commitLoginFragment() {
        loginFragment = new LoginFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, loginFragment, "login")
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

    public static void registerPerson(String login, String password, String firstName, String lastName, String gender) {
        mData.add(new DataModel(login, password, firstName, lastName, gender));
    }

    public static boolean isLoginUnique(String login) {
        for (DataModel i : mData)
            if (i.login.equals(login)) {
                return false;
            }

        return true;
    }

    public static void updateContent(DataModel person) {
        // fill first fragment with person's info
        View view = loginFragment.getView();
        TextView tvHello;
        EditText etLogin, etPassword;
        tvHello = (TextView) view.findViewById(R.id.tv_hello_LFL);
        etLogin = (EditText) view.findViewById(R.id.et_username_LFL);
        etPassword = (EditText) view.findViewById(R.id.et_password_LFL);
        String tmp = "Hello, ";
        tmp += person.gender == "male" ? "Mr." : "Mrs.";
        tmp += person.firstName;
        tmp += " ";
        tmp += person.lastName;

        tvHello.setText(tmp);
        etLogin.setText(person.login);
        etPassword.setText(person.password);
        lastActivePerson = person;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("data", mData);

        if (lastActivePerson != null) {
            outState.putSerializable("active", lastActivePerson);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mData = (ArrayList<DataModel>) savedInstanceState.getSerializable("data");

        if (savedInstanceState.getSerializable("active") != null) {
            lastActivePerson = (DataModel) savedInstanceState.getSerializable("active");
            updateContent(lastActivePerson);
        }
    }
}
