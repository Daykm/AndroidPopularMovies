package com.example.daykm.popmovies;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class SortDialog extends DialogFragment {

    SortChangeCallback callback;

    public static SortDialog newInstance(SortChangeCallback callback) {
        SortDialog dialog = new SortDialog();
        dialog.callback = callback;
        return dialog;
    }

    @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.sort_movies)
                .setItems(R.array.sort_options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onSortChange(which);
                    }
                });
        return builder.create();
    }

    interface SortChangeCallback {
        void onSortChange(int which);
    }
}
