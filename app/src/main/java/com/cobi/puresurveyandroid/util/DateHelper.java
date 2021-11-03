package com.cobi.puresurveyandroid.util;

import android.text.format.DateUtils;

import org.joda.time.DateTime;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.ocpsoft.prettytime.PrettyTime;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by admin on 2017/10/07.
 */

public class DateHelper {

    public static String[] shortMonths = new DateFormatSymbols().getShortMonths();
    public static String[] fullMonths = new DateFormatSymbols().getMonths();

    // String isoString = "2010-08-21T00:20:00+01:00";
    public static String dateToISO8601(Date date) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(tz);
        try {
            String dateAsIso = df.format(date);
            return dateAsIso;
        } catch (NullPointerException e) {
            return null;
        }
    }

    public static String dateToSimpleDate(Date date) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
        df.setTimeZone(tz);
        try {
            String dateAsIso = df.format(date);
            return dateAsIso;
        } catch (NullPointerException e) {
            return null;
        }
    }

    public static String dateToBirthdayFormat(Date date) {

        DateFormat df = new SimpleDateFormat("dd MMMM");
        try {
            String dateAsIso = df.format(date);
            return dateAsIso;
        } catch (NullPointerException e) {
            return null;
        }
    }

    public static String getDay(Date date) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("dd");
        df.setTimeZone(tz);
        try {
            String dateAsIso = df.format(date);
            return dateAsIso;
        } catch (NullPointerException e) {
            return null;
        }
    }

    public static String getMonth(Date date) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("MMMM");
        df.setTimeZone(tz);
        try {
            String dateAsIso = df.format(date);
            return dateAsIso;
        } catch (NullPointerException e) {
            return null;
        }
    }

    public static String getTime(Date date) {
        //        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("hh:mm a");
        //        df.setTimeZone(tz);
        try {
            String dateAsIso = df.format(date);
            return dateAsIso;
        } catch (NullPointerException e) {
            return null;
        }
    }

    public static boolean sameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    public static Date ISO8601ToDate(String dateAsString) {
        //        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        //        df.setTimeZone(tz);
        try {
            Date date = df.parse(dateAsString);
            return date;
        } catch (ParseException e) {

            DateTime datetime = DateTime.parse(dateAsString);
            Date date = datetime.toDate();
            return date;
        } catch (NullPointerException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }



    public static Date StringToDate(String dateAsString) {
        //        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        //        df.setTimeZone(tz);
        try {
            Date date = df.parse(dateAsString);
            return date;
        } catch (ParseException e) {

            DateTime datetime = DateTime.parse(dateAsString);
            Date date = datetime.toDate();
            return date;
        } catch (NullPointerException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isBirthday(int day, int month) {
        boolean isBirthday = false;
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());
        if (calendar.get(Calendar.DAY_OF_MONTH) == day && (calendar.get(Calendar.MONTH) + 1) == month) {
            isBirthday = true;
        }

        return isBirthday;
    }

    public static boolean isEventLive(String eventStart, String eventEnd) {

        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);

            LocalDateTime currentTime = LocalDateTime.now();

            LocalDateTime dateFrom = LocalDateTime.parse(eventStart, inputFormatter);
            LocalDateTime dateTo = LocalDateTime.parse(eventEnd, inputFormatter);

            if (currentTime.isAfter(dateFrom) && currentTime.isBefore(dateTo)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isEventStillActive(String start, String removalDate) {

        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);

            LocalDateTime currentTime = LocalDateTime.now();

            LocalDateTime dateStart = LocalDateTime.parse(start, inputFormatter);
            LocalDateTime dateRemove = LocalDateTime.parse(removalDate, inputFormatter);

            if (currentTime.isAfter(dateStart) && currentTime.isBefore(dateRemove)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isEventPassed(String eventEnd) {

        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);

            LocalDateTime currentTime = LocalDateTime.now();

            LocalDateTime dateTo = LocalDateTime.parse(eventEnd, inputFormatter);

            if (currentTime.isAfter(dateTo)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isEventUpcoming(String eventStart) {

        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);

            LocalDateTime currentTime = LocalDateTime.now();

            LocalDateTime dateFrom = LocalDateTime.parse(eventStart, inputFormatter);

            if (currentTime.isBefore(dateFrom)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean hasBeen2weeks(Date loggedInDate, Date currentDate){

        if (loggedInDate == null || currentDate == null) {
            return false;
        }
        Calendar cal1LoggedIn = Calendar.getInstance();
        Calendar cal2Current = Calendar.getInstance();
        cal1LoggedIn.setTime(loggedInDate);
        cal2Current.setTime(currentDate);
        return cal1LoggedIn.get(Calendar.YEAR) == cal2Current.get(Calendar.YEAR) && cal2Current.get(Calendar.DAY_OF_YEAR) >= cal1LoggedIn.get(Calendar.DAY_OF_YEAR) + 14;

    }

    public static boolean isEventDone(String input) {

        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);
            LocalTime endTime = LocalTime.parse(input, inputFormatter);
            LocalTime currentTime = LocalTime.parse(getTime(Calendar.getInstance().getTime()), inputFormatter);

            if (currentTime.isAfter(endTime)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static String getDayOfWeekDate(String dateFrom) {

        Date d = ISO8601ToDate(dateFrom);

        DateTimeFormatter inputFormatter;

        if (isToday(d)) {

            inputFormatter = DateTimeFormatter.ofPattern("dd MMMM yy", Locale.ENGLISH);

            return "Today, " + inputFormatter.format(LocalDateTime.parse(dateFrom));
        } else {
            inputFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yy", Locale.ENGLISH);

            return inputFormatter.format(LocalDateTime.parse(dateFrom));
        }
    }

    public static long getTimeinMil(String d) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        try {
            Date date = df.parse(d);
            long milliseconds = date.getTime();

            return milliseconds;
        } catch (ParseException e) {
            e.printStackTrace();
            return Long.parseLong(null);
        }
    }


    public static Date stringToDateTime(String dateAsString) {
        //        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("dd/mm/yyyy HH:mm");
        //        df.setTimeZone(tz);
        try {
            Date date = df.parse(dateAsString);
            return date;
        } catch (ParseException e) {

            DateTime datetime = DateTime.parse(dateAsString);
            Date date = datetime.toDate();
            return date;
        } catch (NullPointerException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }



    public static boolean isToday(Date d) {
        return DateUtils.isToday(d.getTime());
    }
}