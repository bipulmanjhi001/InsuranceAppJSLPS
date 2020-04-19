
package com.jslps.bimaseva;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;


public class CustomProgressDialog extends ProgressDialog {

    TextView msg;

    public static CustomProgressDialog create(Context context) {
        CustomProgressDialog dialog = new CustomProgressDialog(context);

        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        return dialog;
    }

    public CustomProgressDialog(Context context) {
        super(context, R.style.AppTheme);
    }


    @Override
    public void setMessage(CharSequence message) {
    }

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.custom_progress_bar);

        getWindow().setBackgroundDrawableResource(android.R.drawable.screen_background_dark_transparent);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);

    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void cancel() {
        super.cancel();
    }
}
