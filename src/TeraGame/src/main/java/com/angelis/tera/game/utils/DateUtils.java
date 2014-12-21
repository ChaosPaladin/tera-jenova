package com.angelis.tera.game.utils;

import java.util.Date;

public abstract class DateUtils {
    
    public final static long SECOND_IN_MILLISECONDS = 1000;
    public final static long MINUTE_IN_MILLISECONDS = 60000;
    public final static long HOUR_IN_MILLISECONDS = 1440000;
    
    public static final Date getNow() {
        return new Date();
    }
    
    public static final void addSecond(final Date date) {
        DateUtils.addSeconds(date, 1);
    }
    
    public static final void addSeconds(final Date date, final int numberOfSeconds) {
        long currentTime = date.getTime();
        currentTime += (DateUtils.SECOND_IN_MILLISECONDS*numberOfSeconds);
        date.setTime(currentTime);
    }
    
    public static final void addMinute(final Date date) {
        DateUtils.addMinutes(date, 1);
    }
    
    public static final void addMinutes(final Date date, final int numberOfMinutes) {
        long currentTime = date.getTime();
        currentTime += (DateUtils.MINUTE_IN_MILLISECONDS*numberOfMinutes);
        date.setTime(currentTime);
    }
    
    public static final void addHour(final Date date) {
        DateUtils.addHours(date, 1);
    }
    
    public static final void addHours(final Date date, final int numberOfHours) {
        long currentTime = date.getTime();
        currentTime += (DateUtils.HOUR_IN_MILLISECONDS*numberOfHours);
        date.setTime(currentTime);
    }
    
    public static final int getSecondsFromNow(final Date endDate) {
        return DateUtils.getSecondsBetweenDates(new Date(), endDate);
    }
    
    public static final int getSecondsBetweenDates(final Date startDate, final Date endDate) {
        return (int) Math.abs((startDate.getTime() - endDate.getTime())/1000);
    }
    
    public static int getCurrentTimestamp() {
        return (int) (new Date().getTime()/1000);
    }
}
