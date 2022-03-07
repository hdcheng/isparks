package app.isparks.core.file.type;

/**
 * 文件储存位置，如：本地、七牛、百度网盘等
 *
 * @author chenghd
 * @date 2020/9/28
 */
public enum FileLocationType{

    /**
     * 本地文件
     */
    LOCAL(0);

    private int value;

    FileLocationType(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
