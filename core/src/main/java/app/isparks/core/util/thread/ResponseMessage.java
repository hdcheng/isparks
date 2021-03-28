package app.isparks.core.util.thread;

import java.util.HashMap;
import java.util.Map;

/**
 * 响应信息属性
 *
 * @author chenghd
 * @date 2020/8/28
 */
public class ResponseMessage implements ThreadAttributes<String, String> {

    private final String DEFAULT_KEY = "RESPONSE_MSG_KEY";

    private final Map<String, String> properties = new HashMap();

    public ResponseMessage(String msg) {
        setAttribute(msg);
    }

    @Override
    public String getAttribute() {
        return properties.get(DEFAULT_KEY);
    }

    @Override
    public void setAttribute(String value) {
        properties.put(DEFAULT_KEY, value);
    }

    @Override
    public String getAttribute(String k) {
        return properties.get(k);
    }

    @Override
    public void setAttribute(String key, String value) {
        properties.put(key, value);
    }

}
