package app.isparks.core.pojo.entity;

/**
 * 字典项，用于映射一个数组的含义，如：
 * -1 -> 过期/无效数据
 *  0 -> 正常
 *  1 -> 文档
 *  2 -> 博客
 * ....
 */
public class Dictionary {

    public Dictionary(String name,int value){
        this.name = name;
        this.value = value;
    }

    private String name;

    private int value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
