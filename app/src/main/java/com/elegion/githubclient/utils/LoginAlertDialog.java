package com.elegion.githubclient.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;


import com.elegion.githubclient.R;

/**
 * Created by vigursky on 30.09.2015.
 */
public class LoginAlertDialog extends DialogFragment{

    OnClickDialogListener callback;

    public LoginAlertDialog(OnClickDialogListener callback){
        this.callback = callback;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dlg_login_alert_msg)
                .setPositiveButton(R.string.dlg_login_retry, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        callback.OnDialogClick(0);
                    }
                });
        return builder.create();
    }

}
