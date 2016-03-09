package com.psu.acc.list_reminder;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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

    private void pollDataBase() {
        Date current = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("ss");
        long delayInSeconds = 1000 * (60 - Integer.parseInt(formatter.format(current)));
        Timer timer = new Timer();
        TimerTask everyMinuteTask = new TimerTask() {
            @Override
            public void run() {
                databaseHelper = DatabaseHelper.getInstance(DatabasePollingService.this);

                List<String> storedListIds = databaseHelper.getAllListIDs();
                for (String listId : storedListIds) {
                    ListObject list = databaseHelper.getList(listId);
                    if (list.getReminderEnabled().equalsIgnoreCase("true")) {
                        //Get current date time for comparison
                        Date currentDateTime = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy h:mma");
                        String currentDateTimeStr = dateFormat.format(currentDateTime);
                        //Build list's date and time
                        StringBuilder reminderDateTime = new StringBuilder(list.getReminderDate());
                        reminderDateTime.append(" ").append(list.getReminderTime());
                        System.out.println("Find difference between " + currentDateTimeStr + " and " + reminderDateTime + " for list " + list.getListName());
                        //Find differences between 2 timestamps
                        TimeBetweenTwoTimestamps differenceInTime = null;
                        try {
                            differenceInTime = calculateDifferenceBetweenTimeStamps(
                                    dateFormat.parse(currentDateTimeStr), dateFormat.parse(reminderDateTime.toString()));
                        } catch (ParseException e){
                            e.printStackTrace();
                        }
                        if (differenceInTime!=null) {
                            differenceInTime.displayDifferenceInTime();
                            if (reminderDateTime.toString().equalsIgnoreCase(currentDateTimeStr)) {
                                triggerAlarm(list.getListName());
                                //Disable reminder
                                list.setReminderEnabled("false");
                                databaseHelper.updateList(list);
                            } else if (list.getReminderRecurrence().equalsIgnoreCase("daily")) {
                                if (differenceInTime.oneOrMoreDayDifference())
                                    triggerAlarm(list.getListName());

                            } else if (list.getReminderRecurrence().equalsIgnoreCase("weekly")) {
                                if (differenceInTime.oneOrMoreWeeksDifference())
                                    triggerAlarm(list.getListName());
                            } else if (list.getReminderRecurrence().equalsIgnoreCase("monthly")) {
                                if (differenceInTime.oneOrMoreMonthsDifference())
                                    triggerAlarm(list.getListName());
                            }
                        }
                    }
                }
            }
        };
        timer.schedule(everyMinuteTask, delayInSeconds, 1000*60);
    }

    private boolean triggerAlarm(String listName) {
        // Time at which alarm will be scheduled, when current time equals reminder set for a list.
        Long time = new GregorianCalendar().getTimeInMillis();

        /* Create an Intent and set the class which will execute the onRecieve() method when Alarm triggers. */
        Intent intentAlarm = new Intent(this, AlarmReceiver.class);
        intentAlarm.putExtra("LIST_NAME", listName);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //Set the alarm for now
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, PendingIntent.getBroadcast(this, 1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
        return true;
    }

    public static TimeBetweenTwoTimestamps calculateDifferenceBetweenTimeStamps(Date currentTime, Date listTime) {
        TimeBetweenTwoTimestamps timeDifference = new TimeBetweenTwoTimestamps();
        timeDifference.setCurrentDate(currentTime);
        timeDifference.setListDate(listTime);
        long diff = currentTime.getTime() - listTime.getTime();

        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        timeDifference.setDifferenceInDays(diffDays);
        timeDifference.setDifferenceInHours(diffHours);
        timeDifference.setDifferenceInMinutes(diffMinutes);
        timeDifference.setDifferenceInSeconds(diffSeconds);

        return timeDifference;

    }

}
