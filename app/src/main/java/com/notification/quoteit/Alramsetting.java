package com.notification.quoteit;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.gb.quoteit.R;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class Alramsetting extends AppCompatActivity implements
        TimePickerDialog.OnTimeSetListener {

    TextView mtimeTextView;
    TextView mam_pmTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alramsetting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View includeView = findViewById(R.id.alramid);

        mtimeTextView = (TextView) includeView.findViewById(R.id.time);
        mam_pmTextView = (TextView) includeView.findViewById(R.id.ampm);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        Alramsetting.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        false
                );

                tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("TimePicker", "Dialog was cancelled");
                    }
                });
                tpd.show(getFragmentManager(), "Timepickerdialog");
            }
        });
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
       PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

        int temp=0;
        if (hourOfDay>12)
            temp=hourOfDay-12;

        if (minute < 10)
            mtimeTextView.setText(temp + ":0" + minute);
        else
            mtimeTextView.setText(temp + ":" + minute);

        if (temp < 10)
            mtimeTextView.setText(temp + ":0" + minute);
        else
            mtimeTextView.setText(temp + ":" + minute);

        String am_pm = (hourOfDay < 12) ? "am" : "pm";
        mam_pmTextView.setText(am_pm);

        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Calendar timeOff9 = Calendar.getInstance();
        timeOff9.set(Calendar.HOUR_OF_DAY, hourOfDay);
        timeOff9.set(Calendar.MINUTE, minute);
        timeOff9.set(Calendar.SECOND, second);
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeOff9.getTimeInMillis(), pendingIntent);

    }
}
