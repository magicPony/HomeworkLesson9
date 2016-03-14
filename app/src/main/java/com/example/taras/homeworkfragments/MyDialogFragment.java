package com.example.taras.homeworkfragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by taras on 14.03.16.
 */
public class MyDialogFragment extends DialogFragment {
    private static String message;

    public void setMessage(String message) {
        this.message = message;
    }

    @Nullable
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog
            .Builder(getActivity())
            .setMessage(message)
            .create();
    }
}
