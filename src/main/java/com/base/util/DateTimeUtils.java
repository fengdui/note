package com.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 日期和时间的常用类<br>
 * 方法都是static的,可通过类名调用<br>
 */
public class DateTimeUtils {

    private static Log log = LogFactory.getLog(DateTimeUtils.class);
    private static final String DATEPATTERN = "yyyy-MM-dd";
    private static final String TIMEPATTERN = "HH:mm";
    private static final String DATETIMEPATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String DATESTR = "yyyyMMdd";
    private static final String TIMEZONEPATTERN = "yyyyMMddHHmmss";

    // 不允许实例化该类
    private DateTimeUtils(){
    }

    /**
     * 获得系统当前日期时间
     *
     * @return 格式为 yyyy-MM-dd HH:mm:ss
     */
    public static String getNowDateTime() {
        return new SimpleDateFormat(DATETIMEPATTERN).format(new Date());
    }

    /**
     * 获得系统当前日期
     *
     * @return 格式为 yyyy-MM-dd
     */
    public static String getDate() {
        return (new SimpleDateFormat(DATEPATTERN)).format(new Date());
    }

    /**
     * 获得指定时间的标准返回格式
     *
     * @param year
     *            指定的年份
     * @param month
     *            指定的月份
     * @param day
     *            指定的日期
     * @return 格式为 yyyy-MM-dd
     */
    public static String getDate(int year, int month, int day) {
        GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
        return (new SimpleDateFormat(DATEPATTERN)).format(calendar.getTime());
    }

    /**
     * 得到"yyyy年M月d日"格式的日期
     *
     * @return "yyyy年M月d日"格式的日期
     */
    public static String getChineseDate() {
        return (new SimpleDateFormat("yyyy\u5E74M\u6708d\u65E5"))
                .format(new Date());
    }

    /**
     * 得到当前的时间,格式为"HH:mm:ss"
     *
     * @return 当前时间,格式为"HH:mm:ss"
     */
    public static String getTime() {
        return (new SimpleDateFormat("HH:mm:ss")).format(new Date());
    }

    /**
     * 得到当前时间
     *
     * @return 数组格式的当前时间,array[0]为小时,array[1]为分钟 String[]
     */
    public static String[] getTimeForHourMinute() {
        String time[] = new String[2];
        String timeStr = (new SimpleDateFormat("HH:mm:ss")).format(new Date());
        String temp[] = timeStr.split(":");
        time[0] = temp[0];
        time[1] = temp[1];
        return time;
    }

    /**
     * 得到当前日期的星期
     *
     * @return 当前日期的星期 String
     */
    public static String getWeekday() {
        return (new SimpleDateFormat("E")).format(new Date());
    }

    /**
     * 获取时间字符串
     * @return 格式为 yyyyMMdd
     */
    public static String getDateStr(){
        return (new SimpleDateFormat(DATESTR)).format(new Date());
    }

    /**
     * 将当前的时间增加指定的小时
     *
     * @param hour
     * @return
     */
    public static String getDateTimeByHour(int hour) {
        String dateTime = null;
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR_OF_DAY, hour);// 增加指定的小时
        SimpleDateFormat sdf = new SimpleDateFormat(DATETIMEPATTERN);
        dateTime = sdf.format(c.getTime());
        return dateTime;
    }

    /**
     * 得到指定日期的星期
     *
     * @param year
     *            指定的年份
     * @param month
     *            指定的月份
     * @param day
     *            指定的日期
     * @return 日期 String
     */
    public static String getWeekday(int year, int month, int day) {
        GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
        return (new SimpleDateFormat("E")).format(calendar.getTime());
    }

    /**
     * 得到指定日期前/后offset天的日期
     *
     * @param dateString 以"yyyy-MM-dd"格式指定的日期
     * @param offset     偏移量
     * @return String
     */
    public static String getDate(String dateString, int offset) {
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = (new SimpleDateFormat("yyyy-MM-dd")).parse(dateString);
            calendar.setTime(date);
        } catch (Exception e) {
            return "";
        }
        calendar.add(6, -1 * offset);
        return (new SimpleDateFormat("yyyy-MM-dd")).format(calendar.getTime());
    }

    /**
     * 得到当前日期的年份和月份
     *
     * @return 以"yyyy-MM"表示的年份和月份 String
     */
    public static String getYearAndMonth() {
        return (new SimpleDateFormat("yyyy-MM")).format(new Date());
    }

    /**
     * 得到指定日期的年份和月份
     *
     * @param year
     *            指定的年份
     * @param month
     *            指定的月份
     * @param day
     *            指定的日期
     * @return 年份和月份 String
     */
    public static String getYearAndMonth(int year, int month, int day) {
        GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
        return (new SimpleDateFormat("yyyy-MM")).format(calendar.getTime());
    }

    /**
     * 得到当前日期是一年中的第几天
     *
     * @return String
     */
    public static String getDateInYear() {
        return (new SimpleDateFormat("DDD")).format(new Date());
    }

    /**
     * 得到指定日期是一年中的第几天
     *
     * @param year
     *            指定的年份
     * @param month
     *            指定的月份
     * @param day
     *            指定的日期
     * @return 指定日期在一年中的第几天 String
     */
    public static String getDateInYear(int year, int month, int day) {
        GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
        return (new SimpleDateFormat("DDD")).format(calendar.getTime());
    }

    /**
     * 得到当前日期是一年中的第几个星期
     *
     * @return String
     */
    public static String getWeekInYear() {
        return (new SimpleDateFormat("ww")).format(new Date());
    }

    /**
     * 得到指定日期是一年中的第几个星期
     *
     * @param year
     *            指定的年份
     * @param month
     *            指定的月份
     * @param day
     *            指定的日期
     * @return 指定日期是一年中的第几个星期 String
     */
    public static String getWeekInYear(int year, int month, int day) {
        GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
        return (new SimpleDateFormat("ww")).format(calendar.getTime());
    }

    /**
     * 得到指定日期是当前月的第几个星期
     *
     * @return String
     */
    public static String getWeekInMonth() {
        return (new SimpleDateFormat("WW")).format(new Date());
    }

    /**
     * 得到指定日期是所在月份的第几个星期
     *
     * @param year
     *            指定的年份
     * @param month
     *            指定的月份
     * @param day
     *            指定的日期
     * @return 指定日期是所在月份的第几个星期 String
     */
    public static String getWeekInMonth(int year, int month, int day) {
        GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
        return (new SimpleDateFormat("WW")).format(calendar.getTime());
    }

    /**
     * 得到当前日期前beforeNum天的日期
     *
     * @param beforeNum
     *            提前量
     * @return String
     */
    public static String getDateByBefore(int beforeNum) {
        Calendar now = Calendar.getInstance();
        now.add(6, -1 * beforeNum);
        return (new SimpleDateFormat("yyyy-MM-dd")).format(now.getTime());
    }

    /**
     * 得到指定日期前beforeNum天的日期
     *
     * @param year
     *            指定的年份
     * @param month
     *            指定的月份
     * @param day
     *            指定的日期
     * @param beforeNum
     *            提前量
     * @return String
     */
    public static String getDateByBefore(int year, int month, int day,
                                         int beforeNum) {
        GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
        calendar.add(6, -1 * beforeNum);
        return (new SimpleDateFormat("yyyy-MM-dd")).format(calendar.getTime());
    }

    /**
     * 得到指定日期前beforeNum天的日期
     *
     * @param dateString
     *            以"yyyy-MM-dd"格式指定的日期
     * @param beforeNum
     *            提前量
     * @return String
     */
    public static String getDateByBefore(String dateString, int beforeNum) {
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = (new SimpleDateFormat("yyyy-MM-dd")).parse(dateString);
            calendar.setTime(date);
        } catch (Exception e) {
            return "";
        }
        calendar.add(6, -1 * beforeNum);
        return (new SimpleDateFormat("yyyy-MM-dd")).format(calendar.getTime());
    }

    /**
     * 得到指定日期后afterNum天的日期
     *
     * @param dateString
     *            以"yyyy-MM-dd"格式指定的日期
     * @param afterNum
     *            偏移量
     * @return String
     */
    public static String getDateByAfter(String dateString, int afterNum) {
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = (new SimpleDateFormat("yyyy-MM-dd")).parse(dateString);
            calendar.setTime(date);
        } catch (Exception e) {
            return "";
        }
        calendar.add(6, afterNum);
        return (new SimpleDateFormat("yyyy-MM-dd")).format(calendar.getTime());
    }

    public static String getWeekdayByBefore(int beforeNum) {
        Calendar now = Calendar.getInstance();
        now.add(6, -1 * beforeNum);
        return (new SimpleDateFormat("E")).format(now.getTime());
    }

    public static String getWeekdayByBefore(int year, int month, int day,
                                            int beforeNum) {
        GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
        calendar.add(6, -1 * beforeNum);
        return (new SimpleDateFormat("E")).format(calendar.getTime());
    }

    public static String getDateByAfter(int afterNum) {
        Calendar now = Calendar.getInstance();
        now.add(6, afterNum);
        return (new SimpleDateFormat("yyyy-MM-dd")).format(now.getTime());
    }

    public static String getDateByAfter(int year, int month, int day,
                                        int afterNum) {
        GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
        calendar.add(6, afterNum);
        return (new SimpleDateFormat("yyyy-MM-dd")).format(calendar.getTime());
    }

    public static String getWeekdayByAfter(int afterNum) {
        Calendar now = Calendar.getInstance();
        now.add(6, afterNum);
        return (new SimpleDateFormat("E")).format(now.getTime());
    }

    public static String getWeekdayByAfter(int year, int month, int day,
                                           int afterNum) {
        GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
        calendar.add(6, afterNum);
        return (new SimpleDateFormat("E")).format(calendar.getTime());
    }

    public static String getDateOfWeekend() {
        Calendar now = Calendar.getInstance();
        now.add(6, 7 - now.get(7));
        return (new SimpleDateFormat("yyyy-MM-dd")).format(now.getTime());
    }

    public static String getDateOfWeekend(int year, int month, int day) {
        GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
        calendar.add(6, 7 - calendar.get(7));
        return (new SimpleDateFormat("yyyy-MM-dd")).format(calendar.getTime());
    }

    public static String getDateOfWeekstart() {
        Calendar now = Calendar.getInstance();
        now.add(6, 1 - now.get(7));
        return (new SimpleDateFormat("yyyy-MM-dd")).format(now.getTime());
    }

    public static String getDateOfWeekstart(int year, int month, int day) {
        GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
        calendar.add(6, 1 - calendar.get(7));
        return (new SimpleDateFormat("yyyy-MM-dd")).format(calendar.getTime());
    }

    public static int getDateOfMonthend() {
        Calendar now = Calendar.getInstance();
        return now.getActualMaximum(5);
    }

    public static int getDateOfMonthend(int year, int month, int day) {
        GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
        return calendar.getActualMaximum(5);
    }

    public static String getDateBefore(String timeString, int minute) {
        long min = 0L;
        try {
            Date date = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
                    .parse(timeString);
            min = date.getTime();
        } catch (Exception exception) {
        }
        return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date(
                min - (minute * 60 * 1000)));
    }

    public static boolean IsOverTime(String timeString, int rating) {
        try {
            Date date = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
                    .parse(timeString);
            Date now = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
                    .parse((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
                            .format(new Date()));
            long min = date.getTime();
            long nowmin = now.getTime();
            return nowmin - min > (rating * 60 * 1000);
        } catch (Exception e) {
            return false;
        }
    }

    public static String switchDateFormat(String dateStr) {
        Date date = null;
        try {
            date = (new SimpleDateFormat("yyyy-MM-dd")).parse(dateStr);
        } catch (Exception exception) {
        }
        return (new SimpleDateFormat("yyyy\u5E74M\u6708d\u65E5")).format(date);
    }

    public static String switchDateFormat(String firstFormat,
                                          String secondFormat, String dateStr) {
        Date date = null;
        try {
            date = (new SimpleDateFormat(firstFormat)).parse(dateStr);
        } catch (Exception exception) {
        }
        return (new SimpleDateFormat(secondFormat)).format(date);
    }

    public static String getWeekdayByDateString(String dateString) {
        String weekday = "";
        try {
            Date date = (new SimpleDateFormat("yyyy-MM-dd")).parse(dateString);
            weekday = (new SimpleDateFormat("E")).format(date);
        } catch (Exception exception) {
        }
        return weekday;
    }

    public static String getWeekdayByDate(Date date) {
        return (new SimpleDateFormat("E")).format(date);
    }

    public static String getDateTimeZone() {
        return (new SimpleDateFormat("yyyyMMddHHmmssS")).format(new Date());
    }

    public static String getYear() {
        return (new SimpleDateFormat("yyyy")).format(new Date());
    }

    /**
     * 根据传入yyyy-MM-dd HH:mm:ss格式的日期,获得季度<br>
     * 返回String数组 分别存放当前季度的开始时间,结束时间,当前季度,当前季度的中文形式<br>
     * 例如:
     *
     * <pre>
     *  2008-10-01
     *  2008-12-31
     *  4
     *  2008年第4季度
     * </pre>
     *
     * @param date
     *            yyyy-MM-dd HH:mm:ss格式的日期
     * @return
     */
    public static String[] getQuarter(Date date) {
        String record[] = new String[4];
        String startDateStr = "";
        String endDateStr = "";
        String quarter = null;
        String year = (new SimpleDateFormat("yyyy")).format(date);
        String month = (new SimpleDateFormat("MM")).format(date);
        int monthInteger = Integer.parseInt(month);
        if (monthInteger >= 1 && monthInteger <= 3) {
            startDateStr = "-01-01";
            endDateStr = "-03-31";
            quarter = "1";
        }
        if (monthInteger >= 4 && monthInteger <= 6) {
            startDateStr = "-04-01";
            endDateStr = "-06-30";
            quarter = "2";
        }
        if (monthInteger >= 7 && monthInteger <= 9) {
            startDateStr = "-07-01";
            endDateStr = "-09-30";
            quarter = "3";
        }
        if (monthInteger >= 9 && monthInteger <= 12) {
            startDateStr = "-10-01";
            endDateStr = "-12-31";
            quarter = "4";
        }
        record[0] = year + startDateStr;
        record[1] = year + endDateStr;
        record[2] = quarter;
        record[3] = year + "\u5E74\u7B2C" + quarter + "\u5B63\u5EA6";
        return record;
    }

    /**
     * 获得当前季度的相关信息<br>
     * 返回String数组 分别存放当前季度的开始时间,结束时间,当前季度,当前季度的中文形式<br>
     * 例如:
     *
     * <pre>
     *  2008-10-01
     *  2008-12-31
     *  4
     *  2008年第4季度
     * </pre>
     *
     * @return
     */
    public static String[] getNowQuarter() {
        String record[] = new String[4];
        String startDateStr = "";
        String endDateStr = "";
        String quarter = "";
        Date date = new Date();
        String year = (new SimpleDateFormat("yyyy")).format(date);
        String month = (new SimpleDateFormat("MM")).format(date);
        int monthInteger = Integer.parseInt(month);
        if (monthInteger >= 1 && monthInteger <= 3) {
            startDateStr = "-01-01";
            endDateStr = "-03-31";
            quarter = "1";
        }
        if (monthInteger >= 4 && monthInteger <= 6) {
            startDateStr = "-04-01";
            endDateStr = "-06-30";
            quarter = "2";
        }
        if (monthInteger >= 7 && monthInteger <= 9) {
            startDateStr = "-07-01";
            endDateStr = "-09-30";
            quarter = "3";
        }
        if (monthInteger >= 9 && monthInteger <= 12) {
            startDateStr = "-10-01";
            endDateStr = "-12-31";
            quarter = "4";
        }
        record[0] = year + startDateStr;
        record[1] = year + endDateStr;
        record[2] = quarter;
        record[3] = year + "\u5E74\u7B2C" + quarter + "\u5B63\u5EA6";
        return record;
    }

    public static String[] getDownQuarter(String date, String quarter)
            throws Exception {
        String record[] = new String[4];
        String startDateStr = "";
        String endDateStr = "";
        int monthInteger = Integer.parseInt(quarter);
        String year = (new SimpleDateFormat("yyyy"))
                .format((new SimpleDateFormat("yyyy-MM-dd")).parse(date));
        int yearInteger = Integer.parseInt(year);
        if (monthInteger == 1) {
            startDateStr = year + "-04-01";
            endDateStr = year + "-06-30";
            quarter = "2";
        }
        if (monthInteger == 2) {
            startDateStr = year + "-07-01";
            endDateStr = year + "-09-30";
            quarter = "3";
        }
        if (monthInteger == 3) {
            startDateStr = year + "-10-01";
            endDateStr = year + "-12-31";
            quarter = "4";
        }
        if (monthInteger == 4) {
            startDateStr = (yearInteger + 1) + "-01-01";
            endDateStr = (yearInteger + 1) + "-03-31";
            quarter = "1";
            year = Integer.toString(yearInteger + 1);
        }
        record[0] = startDateStr;
        record[1] = endDateStr;
        record[2] = quarter;
        record[3] = year + "\u5E74\u7B2C" + quarter + "\u5B63\u5EA6";
        return record;
    }

    public static String[] getUpQuarter(String date, String quarter)
            throws Exception {
        String record[] = new String[4];
        String startDateStr = "";
        String endDateStr = "";
        int monthInteger = Integer.parseInt(quarter);
        String year = (new SimpleDateFormat("yyyy"))
                .format((new SimpleDateFormat("yyyy-MM-dd")).parse(date));
        int yearInteger = Integer.parseInt(year);
        if (monthInteger == 1) {
            startDateStr = (yearInteger - 1) + "-10-01";
            endDateStr = (yearInteger - 1) + "-12-31";
            quarter = "4";
            year = Integer.toString(yearInteger - 1);
        }
        if (monthInteger == 2) {
            startDateStr = year + "-01-01";
            endDateStr = year + "-03-31";
            quarter = "1";
        }
        if (monthInteger == 3) {
            startDateStr = year + "-04-01";
            endDateStr = year + "-06-30";
            quarter = "2";
        }
        if (monthInteger == 4) {
            startDateStr = year + "-07-01";
            endDateStr = year + "-09-30";
            quarter = "3";
        }
        record[0] = startDateStr;
        record[1] = endDateStr;
        record[2] = quarter;
        record[3] = year + "\u5E74\u7B2C" + quarter + "\u5B63\u5EA6";
        return record;
    }

    public static int getExamTime(String firsttime, String secondtime, int m) {
        long record = 0L;
        try {
            Date first = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
                    .parse(firsttime);
            Date second = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
                    .parse(secondtime);
            Date now = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
                    .parse((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
                            .format(new Date()));
            long m_first = first.getTime();
            long m_second = second.getTime();
            if (m_first == m_second) {
                record = m * 60 * 1000;
            } else {
                record = (m * 60 * 1000) - (now.getTime() - first.getTime());
            }
        } catch (Exception exception) {
        }
        return (int) record / 60000;
    }

    public static String switchDateStr(String date) {
        String record = "";
        try {
            Date first = (new SimpleDateFormat("yyyy-MM-dd HH")).parse(date);
            record = (new SimpleDateFormat("yyyyMMddHH")).format(first);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return record;
    }

    public static String switchDateStrForWorkList(String date) {
        String record = "";
        try {
            Date first = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S"))
                    .parse(date);
            record = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
                    .format(first);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return record;
    }

    public static String uniteDateTime(String date, String time) {
        return date + " " + time;
    }

    public static String getOffsetTime(String format, int field, int offset) {
        Calendar calendar = Calendar.getInstance();

        calendar.add(field, offset);
        return new SimpleDateFormat(format).format(calendar.getTime());
    }

    public static String getOffsetTime(String timeString, String format,
                                       int field, int offset) {
        Calendar calendar = Calendar.getInstance();

        calendar.add(field, offset);
        return new SimpleDateFormat(format).format(calendar.getTime());
    }

    public static String getDatePattern() {
        return DATEPATTERN;
    }

    public static final String getDate(Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate != null) {
            df = new SimpleDateFormat(DATEPATTERN);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    public static final Date convertStringToDate(String aMask, String strDate)
            throws ParseException {
        SimpleDateFormat df = null;
        Date date = null;
        df = new SimpleDateFormat(aMask);

        if (log.isDebugEnabled()) {
            log.debug("converting '" + strDate + "' to date with mask '"
                    + aMask + "'");
        }

        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            // log.error("ParseException: " + pe);
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());
        }

        return (date);
    }

    /**
     * 把传入的字符串转换为Date
     *
     * @param aMask
     *            传入的字符串的格式
     * @param strDate
     *            时间日期字符串
     * @return
     * @throws java.text.ParseException
     */
    public static final Date convertStringToDateTime(String aMask,
                                                     String strDate) throws ParseException {
        SimpleDateFormat df = null;
        Date date = null;
        df = new SimpleDateFormat(aMask);

        if (log.isDebugEnabled()) {
            log.debug("converting '" + strDate + "' to date time with mask '"
                    + aMask + "'");
        }

        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            // log.error("ParseException: " + pe);
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());
        }

        return (date);
    }

    /**
     * This method returns the current date time in the format: yyyy/MM/dd HH:MM
     * a
     *
     * @param theTime
     *            the current time
     * @return the current date/time
     */
    public static String getTimeNow(Date theTime) {
        return getDateTime(TIMEPATTERN, theTime);
    }

    /**
     * This method returns the current date in the format: yyyy-MM-dd
     *
     * @return the current date
     * @throws java.text.ParseException
     */
    public static Calendar getToday() throws ParseException {
        Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat(DATEPATTERN);

        // This seems like quite a hack (date -> string -> date),
        // but it works ;-)
        String todayAsString = df.format(today);
        Calendar cal = new GregorianCalendar();
        cal.setTime(convertStringToDate(todayAsString));

        return cal;
    }

    /**
     * This method generates a string representation of a date's date/time in
     * the format you specify on input
     *
     * @param aMask
     *            the date pattern the string is in
     * @param aDate
     *            a date object
     * @return a formatted string representation of the date
     * @see java.text.SimpleDateFormat
     */
    public static final String getDateTime(String aMask, Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate == null) {
            log.error("aDate is null!");
        } else {
            df = new SimpleDateFormat(aMask);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    /**
     *
     * @param aDate
     * @return
     */
    public static final String getDateTime(Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";
        if (aDate == null) {
            log.error("aDate is null!");
        } else {
            df = new SimpleDateFormat(DATETIMEPATTERN);
            returnValue = df.format(aDate);
        }
        return (returnValue);
    }

    public static final String convertDateToString(Date aDate) {
        return getDateTime(DATEPATTERN, aDate);
    }

    public static Date convertStringToDate(String strDate)
            throws ParseException {
        Date aDate = null;

        try {
            if (log.isDebugEnabled()) {
                log.debug("converting date with pattern: " + DATEPATTERN);
            }

            aDate = convertStringToDate(DATEPATTERN, strDate);
        } catch (ParseException pe) {
            log.error("Could not convert '" + strDate
                    + "' to a date, throwing exception");
            pe.printStackTrace();
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());

        }

        return aDate;
    }

    /**
     * 将传入的字符串转换为Date类型
     *
     * @param strDate
     *            需要转换的字符串,格式为'yyyy-MM-dd HH:mm:ss'
     * @return
     * @throws java.text.ParseException
     */
    public static Date convertStringToDateTime(String strDate)
            throws ParseException {
        Date aDate = null;

        try {
            if (log.isDebugEnabled()) {
                log.debug("converting date with pattern: " + DATETIMEPATTERN);
            }

            aDate = convertStringToDate(DATETIMEPATTERN, strDate);
        } catch (ParseException pe) {
            log.error("Could not convert '" + strDate
                    + "' to a date, throwing exception");
            pe.printStackTrace();
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());

        }

        return aDate;
    }

    /**
     * 将指定的日期减少指定的天数
     *
     * @param dateTime
     * @return
     * @throws java.text.ParseException
     */
    public static String getSubtractTime(String dateTime, int day) {
        String nowTime = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Date validToDate = sdf.parse(dateTime);
            Calendar calendar1 = Calendar.getInstance();// 日历对象
            calendar1.setTime(validToDate);// 设置该公司服务到期的日期
            calendar1.add(Calendar.DAY_OF_MONTH, day);
            nowTime = sdf.format(calendar1.getTime());
        } catch (Exception e) {
            log.error("将指定的日期减少指定的天数失败", e);
        }
        return nowTime;
    }

    /**
     * 获取2个日期相差天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long getOffset(String date1, String date2) {
        long diff = 0;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Date d1 = sdf.parse(date1);
            Date d2 = sdf.parse(date2);

            long c = d2.getTime() - d1.getTime();
            diff = c / 1000 / 3600 / 24;
        } catch (Exception e) {
            log.error("获取偏移量失败", e);
        }

        return diff;
    }

    public static void main(String[] args) {
        String d = "2011-11-03";
        String d2 = "2011-11-01";
        long date = DateTimeUtils.getOffset(d, d2);

        System.out.println(date);

    }

    public static String getTimeZone(){
        SimpleDateFormat sdf = new SimpleDateFormat(TIMEZONEPATTERN);
        return sdf.format(new Date());
    }

}