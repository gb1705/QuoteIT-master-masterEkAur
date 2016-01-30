package com.notification.quoteit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.gb.quoteit.NewMessageNotification;

public class AlarmReceiver extends BroadcastReceiver {
    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        try
        { NewMessageNotification.notify(context, "Hello", 111);

        }
        catch (Exception ex)
        {

        }





    }
}
