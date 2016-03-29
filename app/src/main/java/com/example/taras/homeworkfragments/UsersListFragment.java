package com.example.taras.homeworkfragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by taras on 27.03.16.
 */
final public class UsersListFragment extends Fragment implements View.OnClickListener {
    private LinearLayout linearLayout;
    private LayoutInflater inflater;
    private ArrayList<DataModel> data;
    private TextView tvEmptyListMessage;
    private EventHandler eventHandler;

    public UsersListFragment(EventHandler eventHandler, ArrayList<DataModel> data) {
        this.eventHandler = eventHandler;
        this.data = data;
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
        ScrollView view = (ScrollView) inflater.inflate(R.layout.users_list_fragment_layout, container, false);
        linearLayout = (LinearLayout) view.findViewById(R.id.ll_container_USFL);
        tvEmptyListMessage = (TextView) view.findViewById(R.id.tv_empty_list_message_USFL);
        this.inflater = inflater;
        return view;
    }

    public void addPersonToList(DataModel person) {
        RelativeLayout personCard;
        TextView tvUsername, tvName;

        personCard = (RelativeLayout) inflater.inflate(R.layout.person_card_layout, linearLayout, false);
        personCard.setTag(person.getLogin());
        tvUsername = (TextView) personCard.findViewById(R.id.tv_username_PCL);
        tvName = (TextView) personCard.findViewById(R.id.tv_name_PCL);

        tvUsername.setText(person.getLogin());
        tvName.setText(person.getName());

        linearLayout.addView(personCard);
        linearLayout.invalidate();
        personCard.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    public void initData() {
        linearLayout.removeAllViews();
        linearLayout.invalidate();

        for (DataModel person : data) {
            addPersonToList(person);
        }

        tvEmptyListMessage.setVisibility(data.isEmpty() ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        DataModel person = eventHandler.findUserByLogin((String) v.getTag());
        eventHandler.removePerson(person);

        EditPersonFragment editPersonFragment;
        editPersonFragment = new EditPersonFragment(person, (EventHandler) getActivity());

        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, editPersonFragment, Constants.EDIT_PERSON_FRAGMENT_TAG)
                .addToBackStack(Constants.ABOUT_FRAGMENT_TAG)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
}
