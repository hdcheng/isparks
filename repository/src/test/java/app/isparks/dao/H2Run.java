package app.isparks.dao;

import org.h2.tools.Console;
import org.h2.util.Utils;

/**
 * h2数据库启动.
 *
 * @author： chenghd
 * @date： 2021/1/5
 */
public class H2Run {

    public static void main(String[] args) throws Exception{
        Console console;
        try {
            console = (Console) Utils.newInstance("org.h2.tools.GUIConsole");
        } catch (Exception | NoClassDefFoundError e) {
            console = new Console();
        }
        console.runTool(args);
    }

}
