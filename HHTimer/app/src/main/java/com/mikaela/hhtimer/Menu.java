package com.mikaela.hhtimer;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.mikaela.hhtimer.appBlock.AppList;
import com.mikaela.hhtimer.appBlock.Why;
import com.mikaela.hhtimer.util.TopActivityUtils;

public class Menu extends Activity {

    Button about;
    Button timerbtn;
    Button calendarBtn;
    Button appBtn;
    private static final int REQUEST_SETTING = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        about = (Button) findViewById(R.id.Aboutbtn);
        about.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menu.this, About.class);
                startActivity(i);
            }


        });

        timerbtn = (Button) findViewById(R.id.TimerBtn);
        timerbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menu.this, Timer.class);
                startActivity(i);
            }


        });

        calendarBtn = (Button) findViewById(R.id.CalendarBtn);
        calendarBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menu.this, Calendar.class);
                startActivity(i);
            }
        });

        appBtn = (Button) findViewById(R.id.AppBtn);
        appBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TopActivityUtils.isStatAccessPermissionSet(Menu.this)) {
                    showDialog();
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                        intent.setClass(Menu.this, AppList.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent();
                        intent.setClass(Menu.this, AppList.class);
                        startActivity(intent);
                    }
                }
            }
        });




    }
    private void showDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_title)
                .setMessage(R.string.dialog_message)
                .setCancelable(true)
                .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        //some rom has removed the newly introduced android.settings.USAGE_ACCESS_SETTINGS
                        try {
                            startActivityForResult(new Intent("android.settings.USAGE_ACCESS_SETTINGS"), REQUEST_SETTING);
                        } catch (Exception e) {
                            dialog.dismiss();
                        }
                    }
                })
                .setNegativeButton(R.string.dialog_no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNeutralButton(R.string.why_button, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Menu.this, Why.class);
                        startActivity(intent);
                    }
                })
                .show();

    }
}
