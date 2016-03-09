package com.psu.acc.list_reminder;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by aravi1 on 3/9/2016.
 */
//POJO to store time difference between two timestamps. Create this object to set, store and get differences.
// Also checks if one timestamp is a day, week or month (multiples) away from the other.
public class TimeBetweenTwoTimestamps {

    Calendar currentDate;
    Calendar listDate;
    long differenceInDays = 0;
    long differenceInHours = 0;
    long differenceInMinutes = 0;
    long differenceInSeconds = 0;

    public Calendar getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        Calendar currentDateCal = Calendar.getInstance();
        currentDateCal.setTime(currentDate);
        this.currentDate = currentDateCal;
    }

    public Calendar getListDate() {
        return listDate;
    }

    public void setListDate(Date listDate) {
        Calendar listDateCal = Calendar.getInstance();
        listDateCal.setTime(listDate);
        this.listDate = listDateCal;
    }

    public long getDifferenceInDays() {
        return differenceInDays;
    }

    public void setDifferenceInDays(long differenceInDays) {
        this.differenceInDays = differenceInDays;
    }

    public long getDifferenceInHours() {
        return differenceInHours;
    }

    public void setDifferenceInHours(long differenceInHours) {
        this.differenceInHours = differenceInHours;
    }

    public long getDifferenceInMinutes() {
        return differenceInMinutes;
    }

    public void setDifferenceInMinutes(long differenceInMinutes) {
        this.differenceInMinutes = differenceInMinutes;
    }

    public long getDifferenceInSeconds() {
        return differenceInSeconds;
    }

    public void setDifferenceInSeconds(long differenceInSeconds) {
        this.differenceInSeconds = differenceInSeconds;
    }

    public void displayCurrentTimeAndListTime() {
        System.out.println("Current time: " + currentDate + ", list time: " + listDate);
    }

    public void displayDifferenceInTime() {
        System.out.println(differenceInDays + " days, " + differenceInHours + " hours, "
                + differenceInMinutes + " minutes, " + differenceInSeconds + " seconds.");
    }

    public boolean oneOrMoreDayDifference() {
        if (differenceInSeconds == 0 && differenceInMinutes == 0 && differenceInHours == 0 && differenceInDays > 0)
            return true;
        else
            return false;
    }

    public boolean oneOrMoreMonthsDifference() {
        if (differenceInSeconds == 0 && differenceInMinutes == 0 && differenceInHours == 0 && (currentDate.get(Calendar.MONTH) > listDate.get(Calendar.MONTH))
                &&(currentDate.get(Calendar.DATE) == listDate.get(Calendar.DATE)))
            return true;
        else
            return false;
    }

    public boolean oneOrMoreWeeksDifference() {
        if (differenceInSeconds == 0 && differenceInMinutes == 0 && differenceInHours == 0 && (currentDate.get(Calendar.DAY_OF_WEEK) == listDate.get(Calendar.DAY_OF_WEEK))
                &&(currentDate.get(Calendar.DATE) > listDate.get(Calendar.DATE)))
            return true;
        else
            return false;
    }

    public boolean isListTimeGreaterThanCurrentTime() {
        if (listDate.after(currentDate) || listDate.equals(currentDate))
            return true;
        else
            return false;
    }

}
