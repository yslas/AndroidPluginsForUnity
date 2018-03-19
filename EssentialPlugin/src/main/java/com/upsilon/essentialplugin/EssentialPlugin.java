package com.upsilon.essentialplugin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Build;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.os.Vibrator;

import com.unity3d.player.UnityPlayer;

/**
 * Created by yslas on 29/09/2017.
 */

public class EssentialPlugin {

    private static ProgressDialog ringProgressDialog = null;

    @SuppressLint("InlinedApi")
    private static int getTheme(){

        int theme = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            theme = android.R.style.Theme_Material_Light_Dialog;
        } else {
            theme = android.R.style.Theme_Holo_Dialog;
        }
        return theme;
    }

    private static DialogInterface.OnKeyListener KeyListener;

    static {
        KeyListener = new OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    //Log.d("AndroidNative", "AndroidPopUp");
                    UnityPlayer.UnitySendMessage("Message", "OnMessageCallBack", "0");
                    UnityPlayer.UnitySendMessage("Dialog", "OnDialogCallBack", "1");
                    UnityPlayer.UnitySendMessage("RateUs", "OnRateUsCallBack", "2");
                    dialog.dismiss();
                }
                return false;
            }
        };
    }

    public static void showLoading(String title, String message) {

        ringProgressDialog = ProgressDialog.show(new ContextThemeWrapper(UnityPlayer.currentActivity, getTheme()), title, message, true);
        ringProgressDialog.setCancelable(false);
    }

    public static void closeLoading() {

        if (ringProgressDialog.isShowing())
            ringProgressDialog.dismiss();
    }

    public static void showMessage(String title, String message, String okButtonText) {

        AlertDialog.Builder messageAlert = new AlertDialog.Builder(new ContextThemeWrapper(UnityPlayer.currentActivity, getTheme()));
        messageAlert.setTitle(title);
        messageAlert.setMessage(message);
        messageAlert.setPositiveButton(okButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UnityPlayer.UnitySendMessage("Message", "OnMessageCallBack", "0");
            }
        });
        messageAlert.setOnKeyListener(KeyListener);
        messageAlert.setCancelable(false);
        messageAlert.show();
    }

    public static void showDialog(String title, String message, String yesButtonText, String noButtonText) {

        AlertDialog.Builder dialogPopupBuilder = new AlertDialog.Builder(new ContextThemeWrapper(UnityPlayer.currentActivity, getTheme()));
        dialogPopupBuilder.setTitle(title);
        dialogPopupBuilder.setMessage(message);
        dialogPopupBuilder.setPositiveButton(yesButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UnityPlayer.UnitySendMessage("Dialog", "OnDialogCallBack", "0");
            }
        });
        dialogPopupBuilder.setNegativeButton(noButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UnityPlayer.UnitySendMessage("Dialog", "OnDialogCallBack", "1");
            }
        });
        dialogPopupBuilder.setOnKeyListener(KeyListener);
        dialogPopupBuilder.setCancelable(false);
        dialogPopupBuilder.show();
    }

    public static void showRateUs(String title, String message, String yesButtonText, String laterButtonText, String noButtonText) {

        AlertDialog.Builder ratePopupBuilder = new AlertDialog.Builder(new ContextThemeWrapper(UnityPlayer.currentActivity, getTheme()));
        ratePopupBuilder.setTitle(title);
        ratePopupBuilder.setMessage(message);
        ratePopupBuilder.setPositiveButton(yesButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UnityPlayer.UnitySendMessage("RateUs", "OnRateUsCallBack", "0");
            }
        });
        ratePopupBuilder.setNeutralButton(laterButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UnityPlayer.UnitySendMessage("RateUs", "OnRateUsCallBack", "1");
            }
        });
        ratePopupBuilder.setNegativeButton(noButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UnityPlayer.UnitySendMessage("RateUs", "OnRateUsCallBack", "2");
            }
        });
        ratePopupBuilder.setOnKeyListener(KeyListener);
        ratePopupBuilder.setCancelable(false);
        ratePopupBuilder.show();
    }

    public static void vibrate(int milliseconds) {
        Vibrator v = (Vibrator) new ContextThemeWrapper(UnityPlayer.currentActivity, getTheme()).getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(milliseconds);
    }
}