package com.psu.acc.list_reminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by ARavi1 on 3/7/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent)
    {
        System.out.println("Inside the on receive for Alarm Receiver");
        // TODO Auto-generated method stub


        // here you can start an activity or service depending on your need
        // for ex you can start an activity to vibrate phone or to ring the phone

        String phoneNumberReciver="9718202185";// phone number to which SMS to be send
        String message="Hi I will be there later, See You soon";// message to send
        // Show the toast  like in above screen shot
        Toast.makeText(context, "Alarm Triggered and SMS Sent", Toast.LENGTH_LONG).show();
    }
}
