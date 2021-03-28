package app.isparks.core.util;

import app.isparks.core.pojo.enums.DateFormat;
import app.isparks.core.pojo.enums.TimeUnits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Date Utils
 *
 * @author chenghd
 * @date 2020/7/22
 */
public class DateUtils {

    private static Logger log = LoggerFactory.getLogger(DateUtils.class);

    /**
     * get current unix timestamp
     * 获取当前 unix 时间戳
     */
    public static int nowUnixTime() {
        return getSecond(System.currentTimeMillis());
    }

    public static long getTimestamp(){
        return System.currentTimeMillis();
    }


    /**
     * 时间格式化
     */
    public static String formatTime(long time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(time));
    }

    public static String formatTime(long time, DateFormat format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format.getFormat());
        return sdf.format(new Date(time));
    }

    /**
     * 获取默认格式的时间日期
     */
    public static String getDefaultDateTime() {
        return formatTime(System.currentTimeMillis(), DateFormat.DEFAULT_DATE_TIME);
    }

    /**
     * 获取毫秒值
     */
    private static long getMillis(int seconds) {
        return ((long) seconds * 1000L);
    }

    /**
     * 获取秒
     */
    private static int getSecond(long millis) {
        return (int) (millis / 1000L);
    }

    /**
     * 获取从当前到指定时间
     * <p>
     * 如果 unit是毫秒，不推荐使用这个方法
     */
    public static Long getTimeOffset(int amount, TimeUnits unit) {

        int field;
        switch (unit) {
            case DAY:
                field = Calendar.DAY_OF_YEAR;
                break;
            case HOUR:
                field = Calendar.HOUR_OF_DAY;
                break;
            case WEEK:
                field = Calendar.WEEK_OF_YEAR;
                break;
            case YEAR:
                field = Calendar.YEAR;
                break;
            case MONTH:
                field = Calendar.MONTH;
                break;
            case MINUTE:
                field = Calendar.MINUTE;
                break;
            case MILLISECOND:
                field = Calendar.SECOND;
                break;
            default:
                return System.currentTimeMillis() + (long) amount;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(field, amount);
        return calendar.getTimeInMillis();
    }

    public static Long getTimeOffset(long amount, TimeUnits units) {
        if (units == TimeUnits.MILLISECOND)
            return System.currentTimeMillis() + amount;
        return getTimeOffset((int) (amount), units);
    }

    /**
     * 获取 calendar 对象
     */
    public static Calendar getCalendar(long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar;
    }

}
