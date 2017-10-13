package com.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class StringUtils {
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YEAR_TO_DAY = "YEAR_DAY";
    public static final String YEAR_TO_MINUTE = "YEAR_MINUTE";
    public static final String YEAR_TO_SECOND = "YEAR_SECOND";

    /**
     * convert string to boolean
     *
     * @param String
     *            str yes or no
     * @return boolean true or false
     */
    public static boolean strToBoolean(String str) {
        if (str.equalsIgnoreCase("YES"))
            return true;
        return false;
    }

    /**
     * convert boolean to string
     *
     * @param boolean bResult true or false
     * @return String yes or no
     */
    public static String booleanToString(boolean bResult) {
        if (bResult == true)
            return "YES";
        return "NO";
    }

    /**
     * str2DateConvert
     *
     * @param String
     *            str,String scope
     * @return date
     */
    public static Date str2DateConvert(String str, String scope)
            throws ParseException {
        String STR_FORMAT = null;
        Date date = null;
        if (str == null || str.trim().equals(""))
            return null;
        if (scope.equals(YEAR_TO_DAY))
            STR_FORMAT = YYYY_MM_DD;
        else if (scope.equals(YEAR_TO_MINUTE))
            STR_FORMAT = YYYY_MM_DD_HH_MM;
        else
            STR_FORMAT = YYYY_MM_DD_HH_MM_SS;
        SimpleDateFormat sdf = new SimpleDateFormat(STR_FORMAT);
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            throw new ParseException(
                    "You should pass the String like this:2005-05-25 12:12", 1);
        }
        return date;
    }

    /**
     * str2DateConvert
     *
     * @param Calendar
     *            cal,int minutes
     * @return date
     */
    public static Date makeDateFromCalAndMin(Calendar cal, int minutes)
            throws ParseException {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        cal.add(cal.DATE, +minutes / (24 * 60));
        cal.add(cal.HOUR, +minutes % (24 * 60) / 60);
        cal.add(cal.MINUTE, +minutes % (24 * 60) % 60);
        try {
            date = sdf.parse(sdf.format(cal.getTime()));
        } catch (Exception exp) {
            // do nothing
        }
        cal.add(cal.DATE, -minutes / (24 * 60));
        cal.add(cal.HOUR, -minutes % (24 * 60) / 60);
        cal.add(cal.MINUTE, -minutes % (24 * 60) % 60);
        return date;
    }

    /**
     * getTimeStr
     *
     * @param date
     * @return date
     */
    public static String getTimeStr(Date date) {
        String str = null;
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            str = sdf.format(date);
        }
        return str;
    }

    /**
     * getTimeStr
     *
     * @param date
     * @return date
     */
    public static boolean isValidEmailAddress(String email) {
        boolean valid = false;
        if (email != null) {
            email = org.apache.commons.lang.StringUtils.trim(email);
            int atIndex = email.indexOf("@");
            int lastPointIndex = email.lastIndexOf(".");

            if (atIndex > 0 && (email.length() - lastPointIndex) > 1
                    && (lastPointIndex - atIndex) > 1)
                valid = true;
        }
        return valid;
    }

    /**
     * 验证字符串是否是数字
     * @param str
     * @return
     */
    public static boolean isNum(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
}