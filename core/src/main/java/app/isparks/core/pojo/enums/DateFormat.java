package app.isparks.core.pojo.enums;

/**
 * 时间格式
 *
 * @author chenghd
 * @date 2020/7/24
 */
public enum DateFormat {

    DEFAULT_DATE_TIME("yyyy-MM-dd HH:mm:ss"),
    DEFAULT_DATE("yyyy-MM-dd"),
    DEFAULT_TIME("HH:mm:ss");

    DateFormat(String format) {
        this.format = format;
    }

    private String format;

    public String getFormat() {
        return format;
    }
}
