package com.example.taras.homeworkfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * Created by taras on 14.03.16.
 */
final public class RegisterFragment extends Fragment {
    private EventHandler eventHandler;

    public RegisterFragment(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.register_fragment_layout, container, false);
        setRetainInstance(true);

        final CheckBox cbMale, cbFemale;
        cbMale = (CheckBox) view.findViewById(R.id.cb_male_RFL);
        cbFemale = (CheckBox) view.findViewById(R.id.cb_female_RFL);

        cbFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchGender(cbFemale, cbMale);
            }
        });

        cbMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchGender(cbMale, cbFemale);
            }
        });

        view.findViewById(R.id.btn_register_RFL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etLogin, etPassword, etFirstName, etLastName;
                etLogin = (EditText) view.findViewById(R.id.et_login_RFL);
                etPassword = (EditText) view.findViewById(R.id.et_password_RFL);
                etFirstName = (EditText) view.findViewById(R.id.et_first_name_RFL);
                etLastName = (EditText) view.findViewById(R.id.et_last_name_RFL);

                if (etLogin.getText().length() == 0 || etPassword.getText().length() == 0 || etFirstName.getText().length() == 0 || etLastName.getText().length() == 0) {
                    eventHandler.showEmptyFieldMessage();
                    return;
                }

                if (!cbFemale.isChecked() && !cbMale.isChecked()) {
                    eventHandler.showNoGenderMessage();
                    return;
                }

                String login, password, firstName, lastName, gender;
                login = etLogin.getText().toString();
                password = etPassword.getText().toString();
                firstName = etFirstName.getText().toString();
                lastName = etLastName.getText().toString();
                gender = cbMale.isChecked() ? Constants.MALE_TAG : Constants.FEMALE_TAG;

                if (!eventHandler.isLoginUnique(login)) {
                    AlertDialog dialog = new AlertDialog
                            .Builder(getActivity())
                            .setMessage(getString(R.string.used_login_message))
                            .create();
                    dialog.show();
                    return;
                }

                DataModel person = new DataModel(login, password, firstName, lastName, gender);
                eventHandler.registerPerson(person);
                eventHandler.updateContent(person);

                MyDialogFragment myDialogFragment = new MyDialogFragment();
                String message = MessageGenerator.generateRegistrationMessage(firstName, lastName);
                myDialogFragment.setMessage(message);
                myDialogFragment.show(getActivity().getSupportFragmentManager(), Constants.DIALOG_TAG);
            }
        });

        return  view;
    }

    private void switchGender(CheckBox cbMale, CheckBox cbFemale) {
        /**no matter we call switchGender with (male,female) or (female,male)
         * we just have to set check in first parament if needed
        */

        if (!cbMale.isChecked()) {
            return;
        }

        cbFemale.setChecked(false);
    }
}
