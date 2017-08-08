package com.andres_k.gameToolsLib.utils.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by com.andres_k on 30/03/2016.
 */
public class DateTools {
    public static String getCurrentDate(String format) {
        Date date = new Date();
        DateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }
}
