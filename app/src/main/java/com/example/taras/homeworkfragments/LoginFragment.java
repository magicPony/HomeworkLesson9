package com.example.taras.homeworkfragments;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by taras on 14.03.16.
 */
final public class LoginFragment extends Fragment {
    EventHandler eventHandler;

    public LoginFragment(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.login_fragment_layout, container, false);
        setRetainInstance(true);

        view.findViewById(R.id.tv_register_LFL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commitRegisterFragment();
            }
        });

        view.findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Enter();
            }
        });

        return view;
    }

    private void Enter() {
        EditText etLogin, etPassword;
        View view = this.getView();
        etLogin = (EditText) view.findViewById(R.id.et_username_LFL);
        etPassword = (EditText) view.findViewById(R.id.et_password_LFL);

        if (etLogin.getText().length() == 0 || etPassword.getText().length() == 0) {
            AlertDialog dialog = new AlertDialog
                    .Builder(getActivity())
                    .setMessage(getString(R.string.empty_field_message))
                    .create();
            dialog.show();
            return;
        }

        String login, password;
        login = etLogin.getText().toString();
        password = etPassword.getText().toString();
        DataModel person = eventHandler.findUserByLogin(login);

        if (person == null || !person.getPassword().equals(password)) {
            AlertDialog dialog = new AlertDialog
                    .Builder(getActivity())
                    .setMessage(getString(R.string.non_registered_user_or_wrong_password))
                    .create();
            dialog.show();
            return;
        }

        String message;
        message = MessageGenerator
                .generateWelcomeMessage(person.getFirstName(), person.getLastName(), person.getGender());

        AlertDialog dialog = new AlertDialog
                .Builder(getActivity())
                .setMessage(message)
                .create();
        dialog.show();
    }

    private void commitRegisterFragment() {
        RegisterFragment registerFragment = new RegisterFragment((EventHandler) getActivity());
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, registerFragment, Constants.REGISTER_FRAGMENT_TAG)
                .addToBackStack(Constants.REGISTER_FRAGMENT_TAG)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
}
