package com.mytech.salesvisit.ui;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.mytech.salesvisit.R;


public class NoticeDialogFragment extends DialogFragment {


    private NoticeDialogListener listener;
    private String message;

    public NoticeDialogFragment(NoticeDialogListener listener, String message) {
        this.listener = listener;
        this.message = message;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Build the dialog and set up the button click handlers
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
                .setPositiveButton(R.string.app_name, (dialog, id) -> {
                    listener.onDialogPositiveClick(NoticeDialogFragment.this);
                });
        return builder.create();
    }

    public interface NoticeDialogListener {
        void onDialogPositiveClick(DialogFragment noticeDialogFragment);

    }
}