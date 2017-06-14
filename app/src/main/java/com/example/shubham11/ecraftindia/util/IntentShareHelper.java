package com.example.shubham11.ecraftindia.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.example.shubham11.ecraftindia.R;

/**
 * Created by shubhamjuneja11 on 14/6/17.
 */

public class IntentShareHelper {

    public static void shareOnWhatsapp(AppCompatActivity appCompatActivity, String textBody, Uri fileUri) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.setPackage("com.whatsapp");
        intent.putExtra(Intent.EXTRA_TEXT,!TextUtils.isEmpty(textBody) ? textBody : "");

        if (fileUri != null) {
            intent.putExtra(Intent.EXTRA_STREAM, fileUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType("image/*");
        }

        try {
            appCompatActivity.startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex) {
            ex.printStackTrace();
            showWarningDialog(appCompatActivity, appCompatActivity.getString(R.string.error_activity_not_found));
        }
    }


    private static void showWarningDialog(Context context, String message) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setNegativeButton(context.getString(R.string.close), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(true)
                .create().show();
    }
}