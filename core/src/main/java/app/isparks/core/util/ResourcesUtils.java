package app.isparks.core.util;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 资源工具包
 *
 * @author chenghd
 * @date 2020/7/24
 */
public class ResourcesUtils {

    private static Logger log = LoggerFactory.getLogger(ResourcesUtils.class);

    private final static String CHARSET = "UTF-8";

    private final static String SQL_FILE_DIR = "sql";

    /**
     * 保存文件
     */
    public static void saveYaml(Object o, File file) {
        File parent = file.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        try (FileWriter fw = new FileWriter(file)) {
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            Yaml yaml = new Yaml(options);
            String str = yaml.dumpAsMap(o);

            fw.write(str);
            fw.flush();
        } catch (IOException e) {
            log.error("持久化yaml文件异常", e);
        }
    }

    /**
     * 读取文件内容
     */
    public static <T> T readYaml(Class<T> t, File file) {
        T result = null;
        try (FileReader reader = new FileReader(file)) {
            Yaml yaml = new Yaml();
            result = yaml.loadAs(reader, t);
        } catch (IOException | YAMLException e) {
            log.error("读取yaml文件异常", e);
        }
        return result;
    }

    /**
     * 读取 yaml 配置文件
     */
    public static String readYaml(File file) {
        if (file == null || !file.exists() || !file.isFile()) {
            return "";
        }
        String result = "";
        try (FileReader reader = new FileReader(file)) {
            Yaml yaml = new Yaml();
            result = yaml.load(reader);
        } catch (IOException | YAMLException e) {
            log.error("读取yaml文件异常", e);
        }
        return result;
    }

    /**
     * 读取文本文件内容
     */
    public static String readFileContent(File file){
        if (file == null || !file.exists() || !file.isFile()) {
            return "";
        }
        String result = "";
        try {
            result = FileUtils.readFileToString(file,CHARSET);
        }catch (IOException e){
            log.error("文件读取异常",e);
        }finally {
            return result;
        }
    }

    /**
     * 读取系统Resource下的文件
     */
    public static String readResources(String fileName){
        String content = "";
        try {
            ClassPathResource resource= new ClassPathResource(fileName);
            content =  FileUtils.readFileToString(resource.getFile(),CHARSET);
        }catch (IOException e){
            log.error("读取系统资源文件出错",e);
        }finally {
            return content;
        }
    }


    /**
     * 读取 sql 文件
     */
    public static String readSqlResources(String filename){
        if(StringUtils.isEmpty(filename)){
            return "";
        }

        filename = filename.toLowerCase();

        if(!filename.endsWith(".sql")){
            filename = filename + ".sql";
        }

        return readResources(SQL_FILE_DIR + File.separator + filename);
    }

    /**
     * 转换程 uri 形式的路径
     */
    public static String parseFilePathToURI(String filePath){
        return new File(filePath).toURI().getPath();
    }

}
