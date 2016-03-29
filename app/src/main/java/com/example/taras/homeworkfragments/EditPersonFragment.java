package com.example.taras.homeworkfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;

/**
 * Created by taras on 28.03.16.
 */
public class EditPersonFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private DataModel person;
    private RadioButton rbMale, rbFemale;
    private EditText etLogin, etPassword, etFirstName, etLastName;
    private EventHandler eventHandler;

    public EditPersonFragment(DataModel person, EventHandler eventHandler) {
        this.eventHandler = eventHandler;
        this.person = person;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_person_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etLogin = (EditText) view.findViewById(R.id.et_login_EPFL);
        etPassword = (EditText) view.findViewById(R.id.et_password_EPFL);
        etFirstName = (EditText) view.findViewById(R.id.et_first_name_EPFL);
        etLastName = (EditText) view.findViewById(R.id.et_last_name_EPFL);
        rbMale = (RadioButton) view.findViewById(R.id.rb_male_EPFL);
        rbFemale = (RadioButton) view.findViewById(R.id.rb_female_EPFL);

        view.findViewById(R.id.btn_save_EPFL).setOnClickListener(this);
        rbMale.setOnCheckedChangeListener(this);
        rbFemale.setOnCheckedChangeListener(this);

        etLogin.setText(person.getLogin());
        etPassword.setText(person.getPassword());
        etFirstName.setText(person.getFirstName());
        etLastName.setText(person.getLastName());

        (person.getGender().equals(Constants.MALE_TAG) ? rbMale : rbFemale)
                .setChecked(true);
    }

    @Override
    public void onClick(View v) {
        if (etLogin.getText().length() == 0 || etPassword.getText().length() == 0 || etFirstName.getText().length() == 0 || etLastName.getText().length() == 0) {
            eventHandler.showEmptyFieldMessage();
            return;
        }

        if (!rbFemale.isChecked() && !rbMale.isChecked()) {
            eventHandler.showNoGenderMessage();
            return;
        }

        String login, password, firstName, lastName, gender;
        login = etLogin.getText().toString();
        password = etPassword.getText().toString();
        firstName = etFirstName.getText().toString();
        lastName = etLastName.getText().toString();
        gender = rbMale.isChecked() ? Constants.MALE_TAG : Constants.FEMALE_TAG;

        if (!eventHandler.isLoginUnique(login)) {
            AlertDialog dialog = new AlertDialog
                    .Builder(getActivity())
                    .setMessage(getString(R.string.used_login_message))
                    .create();
            dialog.show();
            return;
        }

        DataModel person = new DataModel(login, password, firstName, lastName, gender);

        MyDialogFragment myDialogFragment = new MyDialogFragment();
        String message = MessageGenerator.generateRegistrationMessage(firstName, lastName);
        myDialogFragment.setMessage(message);
        myDialogFragment.show(getActivity().getSupportFragmentManager(), Constants.DIALOG_TAG);

        eventHandler.removePerson(this.person);
        eventHandler.registerPerson(person);
        eventHandler.updateContent(person);
        eventHandler.updateUsersList();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!isChecked) {
            return;
        }

        (buttonView.getId() == R.id.rb_male_EPFL ? rbFemale : rbMale)
                .setChecked(false);
    }
}
