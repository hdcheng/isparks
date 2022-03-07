package app.isparks.core.config;


import app.isparks.core.util.ISparksUtils;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class ConfigTest {



    @Test
    public void test1()throws Exception{
        Map<String,Object> c = new HashMap<>();
        ISparksUtils.saveYaml(c,new File(ISparksProperties.CONFIG_FILE));

        String c2 = ISparksUtils.readYaml(new File(ISparksProperties.CONFIG_FILE));

        System.out.println(c2);
    }

}
