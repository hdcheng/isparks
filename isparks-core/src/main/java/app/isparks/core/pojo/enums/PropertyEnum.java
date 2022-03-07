package app.isparks.core.pojo.enums;

import app.isparks.core.exception.InvalidValueException;
import org.springframework.util.Assert;

/**
 *
 * @author： chenghd
 * @date： 2021/1/20
 */
public interface PropertyEnum extends IEnum<String>{

    String getKey();

    Class<?> getValueType();

    String getValue();

    static  <T> T getValue(String value,Class<T> tClass){
        Assert.hasLength(value,"value must not be empty.");
        Assert.notNull(tClass,"value type must not be empty.");

        if (tClass.isAssignableFrom(String.class)){
            return (T) value;
        }

        if (tClass.isAssignableFrom(Boolean.class)){
            return (T) Boolean.valueOf(value);
        }

        if (tClass.isAssignableFrom(Integer.class)){
            return (T) Integer.valueOf(value);
        }

        if (tClass.isAssignableFrom(Long.class)){
            return (T) Long.valueOf(value);
        }

        if (tClass.isAssignableFrom(Float.class)){
            return (T) Float.valueOf(value);
        }

        if (tClass.isAssignableFrom(Double.class)){
            return (T) Double.valueOf(value);
        }

        if (tClass.isAssignableFrom(Byte.class)){
            return (T) Byte.valueOf(value);
        }

        if (tClass.isAssignableFrom(Character.class)){
            return (T) Character.valueOf(value.charAt(0));
        }

        throw new InvalidValueException(" Type "+tClass.toString()+" not supported .");
    }


}
