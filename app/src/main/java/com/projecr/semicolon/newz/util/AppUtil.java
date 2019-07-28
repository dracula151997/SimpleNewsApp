package com.projecr.semicolon.newz.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AppUtil {

    public static String getDate(String dataString){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'", Locale.ENGLISH);
            Date date = simpleDateFormat.parse(dataString);
            DateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH);
            return dateFormat.format(date);

        }catch (Exception e){
            e.printStackTrace();
            return "x";
        }

    }

    public static String getTime(String timeString){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'", Locale.ENGLISH);
            Date date = simpleDateFormat.parse(timeString);
            DateFormat dateFormat = new SimpleDateFormat("h:mm a", Locale.ENGLISH);
            return dateFormat.format(date);

        }catch (Exception e){
            e.printStackTrace();
            return "xx";
        }
    }
}
