package app.isparks.web.util;

import app.isparks.core.pojo.entity.Log;
import app.isparks.core.pojo.enums.LogType;
import app.isparks.core.util.DateUtils;
import app.isparks.core.util.IdUtils;
import app.isparks.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chenghd
 * @date 2020/8/19
 */
@Slf4j
public class LogBuilder {

    /**
     * 构建日志对象
     */
    public static Log build(LogType[] ts, String content) {
        StringBuilder types = new StringBuilder();
        types.append("[");
        for (int i = 0, len = ts.length; i < len; ++i) {
            types.append(ts[i].getDescription());
            if (i != len - 1) {
                types.append(",");
            }
        }
        types.append("]");
        long time = DateUtils.getTimestamp();
        return (Log) new Log()
                .withType(types.toString())
                .withContent(content)
                .withDate(DateUtils.getDefaultDateTime())
                .withIp(getIp())
                .withCreateTime(time)
                .withModifyTime(time)
                .withId(IdUtils.id());
    }

    /**
     * 构建日志对象
     */
    public static Log build(LogType type, String content) {
        return build(new LogType[]{type}, content);
    }

    private static String getIp() {
        String ip = "";
        try {
            HttpServletRequest request = HttpUtils.getHttpServletRequest();
            ip = IpUtils.getIp(request);
        } catch (Exception e) {
            log.error("获取ip异常", e);
        } finally {
            if (StringUtils.isEmpty(ip)) ip = "unknown";
        }
        return ip;
    }

}
