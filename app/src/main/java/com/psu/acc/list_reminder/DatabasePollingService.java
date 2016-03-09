package com.psu.acc.list_reminder;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by ARavi1 on 3/7/2016.
 */
public class DatabasePollingService extends IntentService{

    DatabaseHelper databaseHelper;
   /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public DatabasePollingService() {
        super(DatabasePollingService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        System.out.println("Starting Database Polling");
        pollDataBase();
    }

    private void pollDataBase(){
        while(true){
            databaseHelper = DatabaseHelper.getInstance(DatabasePollingService.this);
            SQLiteDatabase db = databaseHelper.getWritableDatabase();

            List<String> storedListIds = databaseHelper.getAllListIDs();
            for(String listId: storedListIds)
            {
                ListObject list = databaseHelper.getList(listId);
                if(Boolean.getBoolean(list.getReminderEnabled())){
                    String reminderDate = list.getReminderDate();
                    String reminderTime = list.getReminderTime();
                    Date currentDateTime = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                    String dateToStr = dateFormat.format(currentDateTime);
                    System.out.println(dateToStr);


                    // TODO: check if current minute is the same as the reminder time

                    triggerAlarm();
                }
            }
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean triggerAlarm() {
        // time at which alarm will be scheduled here alarm is scheduled at 1 day from current time,
        // we fetch  the current time in milliseconds and added 1 day time
        // i.e. 24*60*60*1000= 86,400,000   milliseconds in a day
        Long time = new GregorianCalendar().getTimeInMillis()+30*1000;

        System.out.println("Time in long " + time);

        // create an Intent and set the class which will execute when Alarm triggers, here we have
        // given AlarmReceiver in the Intent, the onRecieve() method of this class will execute when
        // alarm triggers and
        //we will write the code to send SMS inside onRecieve() method pf Alarmreciever class
        Intent intentAlarm = new Intent(this, AlarmReceiver.class);

        // create the object
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //set the alarm for particular time
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, PendingIntent.getBroadcast(this, 1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
        Toast.makeText(this, "Alarm Scheduled for Tomorrow", Toast.LENGTH_LONG).show();
        System.out.println("Set the alarm ");
        return true;
    }
}
