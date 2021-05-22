package app.isparks.core.util;

import app.isparks.core.exception.InvalidValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

/**
 * 工具包
 *
 * @author： chenghd
 * @date： 2021/1/21
 */
public final class ISparksUtils {

    private static Logger log = LoggerFactory.getLogger(ISparksUtils.class);

    private ISparksUtils(){}

    /**
     * 将字符串转换为基本类型
     *
     * @param valueString
     * @param valueType
     * @param <V>
     * @return V
     */
    public static <V> V stringParse(String valueString,Class<V> valueType){

        if (valueString == null || ("".equals(valueString) && !valueType.isAssignableFrom(String.class))){
            throw new InvalidValueException("value string must not be empty.");
        }

        if (valueType == null){
            throw new InvalidValueException("value type must not be null.");
        }

        if (valueType.isAssignableFrom(String.class)){
            return (V) valueString;
        }

        if (valueType.isAssignableFrom(Boolean.class)){
            return (V) Boolean.valueOf(valueString);
        }

        if (valueType.isAssignableFrom(Integer.class)){
            return (V) Integer.valueOf(valueString);
        }

        if (valueType.isAssignableFrom(Long.class)){
            return (V) Long.valueOf(valueString);
        }

        if (valueType.isAssignableFrom(Float.class)){
            return (V) Float.valueOf(valueString);
        }

        if (valueType.isAssignableFrom(Double.class)){
            return (V) Double.valueOf(valueString);
        }

        if (valueType.isAssignableFrom(Byte.class)){
            return (V) Byte.valueOf(valueString);
        }

        if (valueType.isAssignableFrom(Character.class)){
            return (V) Character.valueOf(valueString.charAt(0));
        }

        throw new InvalidValueException(" Type "+valueType.toString()+" not supported .");

    }

    /**
     * 保存 yaml 格式配置文件
     *
     * @param configMap
     * @param file
     */
    public static void saveYaml(Map<String,Object> configMap, File file) {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        saveYaml(configMap,file,options);
    }

    public static void saveYaml(Object o, File file,DumperOptions options){
        Assert.notNull(o,"config map must not be null.");
        Assert.notNull(file,"file must not be null.");
        Assert.notNull(options,"option must not be null.");

        File parent = file.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }

        try (FileWriter fw = new FileWriter(file)) {

            Yaml yaml = new Yaml(options);
            String s = yaml.dumpAsMap(o);

            // 生成 yaml 文件
            fw.write(s);
            fw.flush();

        } catch (IOException e) {

            log.error("持久化yaml文件异常", e);

        }
    }



    /**
     * 读取 yaml 配置文件的内容
     *
     * @param t
     * @param file
     * @param <T>
     * @return
     */
    public static <T> Optional<T> readYaml(Class<T> t, File file) {
        T result = null;

        try (FileReader reader = new FileReader(file)) {

            Yaml yaml = new Yaml();
            result = yaml.loadAs(reader,t);

        } catch (IOException | YAMLException e) {

            log.error("读取yaml文件异常", e);

        }

        return Optional.ofNullable(result);
    }

    /**
     * 读取 yaml 文件中的内容
     *
     * @param file
     * @return yaml file content
     */
    public static String readYaml(File file) {
        if (file == null || !file.exists() || !file.isFile()) {
            return "";
        }

        String result = "";
        try (FileReader reader = new FileReader(file)) {

            result = new Yaml().loadAs(reader,String.class);

        } catch (IOException | YAMLException e) {

            log.error("读取yaml文件异常", e);

        }

        return result;
    }


}
