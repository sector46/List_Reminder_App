package com.psu.acc.list_reminder;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

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
                if(list.getReminderEnabled().equalsIgnoreCase("true")) {
                    //Get current date time for comparison
                    Date currentDateTime = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mma");
                    String currentDateTimeStr = dateFormat.format(currentDateTime);
                    StringBuilder reminderDateTime = new StringBuilder(list.getReminderDate());
                    reminderDateTime.append(" ").append(list.getReminderTime());
                    if (list.getReminderRecurrence().equals("none") && reminderDateTime.toString().equalsIgnoreCase(currentDateTimeStr)) {
                            triggerAlarm(list.getListName());
                    } else if (list.getReminderRecurrence().equals("daily")) {
                        //TO DO
                        int hours = hoursAgo(reminderDateTime.toString());
                        System.out.println(hours);
                    } else if (list.getReminderRecurrence().equals("weekly")) {
                        //To DO
                    } else if (list.getReminderRecurrence().equals("monthly")) {
                        //To DO
                    }
                }
            }
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean triggerAlarm(String listName) {
        // Time at which alarm will be scheduled, when current time equals reminder set for a list.
        Long time = new GregorianCalendar().getTimeInMillis();

        /* Create an Intent and set the class which will execute the onRecieve() method when Alarm triggers. */
        Intent intentAlarm = new Intent(this, AlarmReceiver.class);
        intentAlarm.putExtra("LIST_NAME",listName);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //Set the alarm for now
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, PendingIntent.getBroadcast(this, 1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
        return true;
    }

    public static int hoursAgo(String datetime) {
        try {
            Calendar date = Calendar.getInstance();
            date.setTime(new SimpleDateFormat("dd/MM/yyyy HH:mma", Locale.ENGLISH).parse(datetime)); // Parse into Date object
            Calendar now = Calendar.getInstance(); // Get time now
            long differenceInMillis = now.getTimeInMillis() - date.getTimeInMillis();
            long differenceInHours = (differenceInMillis) / 1000L / 60L / 60L; // Divide by millis/sec, secs/min, mins/hr
            return (int) differenceInHours;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
