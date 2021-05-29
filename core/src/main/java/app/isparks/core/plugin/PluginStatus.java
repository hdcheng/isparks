package app.isparks.core.plugin;

public enum  PluginStatus {

    // loaded 加载
    LOADED(0),

    // disable 无效
    DISABLED(1),

    // started 开始
    STARTED(2),

    // stopped 停止
    STOPPED(3);

    PluginStatus(int code){
        this.code = code;
    }

    private int code ;

    public int code(){
        return code;
    }

}
