package com.example.MyBookShopApp.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.MyBookShopApp.Util.DatePattern.*;

/**
 * Cast  String to Date
 *
 * @author Иван Стрельцов
 */
public class StringToDateFormatter {

    public static final SimpleDateFormat DATE_SHORT_TIME_FORMAT = new SimpleDateFormat(DATE_SHORT_TIME_PATTERN);

    private StringToDateFormatter() {
    }

    public static Date formatToDate(String date) {
        try {
            return DATE_SHORT_TIME_FORMAT.parse(date);
        } catch (ParseException e) {
            return new Date();
        }
    }
}
