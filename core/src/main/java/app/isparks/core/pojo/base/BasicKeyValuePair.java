package app.isparks.core.pojo.base;

import app.isparks.core.exception.InvalidValueException;

/**
 *
 * @author： chenghd
 * @date： 2021/3/10
 */
public class BasicKeyValuePair implements IKeyValuePair<String,Object>{

    private String key;
    private Object value;

    public BasicKeyValuePair(String key,Object value){
        this.key = key ;
        this.value = value;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {

        if(!( obj instanceof BasicKeyValuePair)){
            return false;
        }

        if(key == null || obj == null){
            throw new InvalidValueException("null value exception");
        }

        BasicKeyValuePair pair = ((BasicKeyValuePair) obj);

        if(!key.equals(pair.getKey())){
            return false;
        }

        return value == pair.getValue();
    }
}
