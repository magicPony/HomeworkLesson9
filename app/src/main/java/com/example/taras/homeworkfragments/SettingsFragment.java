package com.example.taras.homeworkfragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by taras on 27.03.16.
 */
final public class SettingsFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {
    private RadioButton rbLoginOrder, rbFirstNameOrder, rbLastNameOrder;
    private RadioGroup rgOrder;
    private EventHandler eventHandler;

    public SettingsFragment(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.clear();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.settings_fragment_layout, container, false);
        setRetainInstance(true);

        initViews(view);
        setListeners();

        return  view;
    }

    private void setListeners() {
        rgOrder.setOnCheckedChangeListener(this);

        switch (eventHandler.loadPreferences()) {
            case Constants.LOGIN_TAG :
                rbLoginOrder.setChecked(true);
                break;

            case Constants.FIRST_NAME_TAG :
                rbFirstNameOrder.setChecked(true);
                break;

            case Constants.LAST_NAME_TAG :
                rbLastNameOrder.setChecked(true);
                break;
        }
    }

    private void initViews(View view) {
        rgOrder = (RadioGroup) view.findViewById(R.id.rg_order);
        rbLoginOrder = (RadioButton) view.findViewById(R.id.rb_by_login_SFL);
        rbFirstNameOrder = (RadioButton) view.findViewById(R.id.rb_by_first_name_SFL);
        rbLastNameOrder = (RadioButton) view.findViewById(R.id.rb_by_last_name_SFL);
    }

    /*public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }*/

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_by_login_SFL :
                eventHandler.updatePreferences(Constants.LOGIN_TAG);
                break;

            case R.id.rb_by_first_name_SFL :
                eventHandler.updatePreferences(Constants.FIRST_NAME_TAG);
                break;

            case R.id.rb_by_last_name_SFL :
                eventHandler.updatePreferences(Constants.LAST_NAME_TAG);
                break;
        }
    }
}
