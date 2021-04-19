package com.example.MyBookShopApp.Util;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringToDateFormatterTest {

    public static String DATE = "18.03.2021";

    @Test
    public void testFormatToDate() throws ParseException {
        Date dateInspect = new SimpleDateFormat("dd.MM.yyyy").parse(DATE);
        Date dateCast = StringToDateFormatter.formatToDate(DATE);
        assertEquals(dateCast, dateInspect);

    }
}

