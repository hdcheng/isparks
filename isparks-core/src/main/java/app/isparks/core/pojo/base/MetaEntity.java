package app.isparks.core.pojo.base;

import java.io.Serializable;

public abstract class MetaEntity implements Serializable {


    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private String id;

    public MetaEntity(String id) {
        this.id = id;
    }

    public MetaEntity(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
