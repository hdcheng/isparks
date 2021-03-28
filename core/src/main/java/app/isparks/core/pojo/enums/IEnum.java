package app.isparks.core.pojo.enums;

import app.isparks.core.exception.InvalidValueException;
import org.springframework.util.Assert;

import java.util.stream.Stream;

/**
 * 所有枚举类型的接口
 *
 * @author： chenghd
 * @date： 2021/1/13
 */
public interface IEnum<C> {

    static <E extends IEnum> E nameToEnum(Class<E> enumType, String name){
        Assert.notNull(enumType, "enum type must not be null");
        Assert.notNull(name, "value must not be null");
        Assert.isTrue(enumType.isEnum(), "type must be an enum type");

        return Stream.of(enumType.getEnumConstants())
                .filter((i)->i.toString().equals(name))
                .findFirst()
                .orElseThrow(()-> new InvalidValueException("unknown enum type : " + name));
    }


    static <C,E extends IEnum<C>> E codeToEnum(Class<E> enumType, C code) {

        Assert.notNull(enumType, "enum type must not be null");
        Assert.notNull(code, "value must not be null");
        Assert.isTrue(enumType.isEnum(), "type must be an enum type");

        return Stream.of(enumType.getEnumConstants())
                .filter(item -> item.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("未找到枚举类型: " + code));
    }

    C getCode();

}
