package com.egeinfo.utils;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConvertEditor extends PropertyEditorSupport {
    private SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.hasText(text)) {
            try {
                if (text.indexOf(":") == -1 && text.length() == 10) {
                    setValue(this.dateFormat.parse(text));
                } else if (text.indexOf(":") > 0 && text.length() == 19) {
                    setValue(this.datetimeFormat.parse(text));
                } else if (text != null && text.length() == 13 && org.apache.commons.lang3.StringUtils.isNumeric(text)) {
                    Date date = new Date();
                    date.setTime(Long.parseLong(text));
                    setValue(date);
                } else {
                    throw new IllegalArgumentException("Could not parse date, date format is error ");
                }
            } catch (ParseException ex) {
                IllegalArgumentException iae = new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
                throw iae;
            }
        } else {
            setValue(null);
        }
    }
}
