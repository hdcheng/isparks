package app.isparks.core.pojo.base;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author chenghd
 * @date 2020/8/12
 */
public class BaseDTO extends BaseProperty implements Serializable {

    /**
     * id
     */
    private String id;

    /**
     * 修改时间
     */
    private Long modifyTime;

    /**
     * 创建时间
     */
    private Long createTime;


    // getter and setter
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Long getModifyTime() {
        return modifyTime;
    }
    public void setModifyTime(Long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Long getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    // with
    public BaseDTO withId(String id) {
        this.id = id;
        return this;
    }

    public BaseDTO withModifyTime(Long modifyTime) {
        this.modifyTime = modifyTime;
        return this;
    }

    public BaseDTO withCreateTime(Long createTime) {
        this.createTime = createTime;
        return this;
    }

    public BaseDTO withProperty(String key, Object value) {
        setProperty(key,value);
        return this;
    }

    public BaseDTO withPropertyMap(Map<String, Object> properties) {
        setProperties(properties);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseDTO baseDTO = (BaseDTO) o;
        return id.equals(baseDTO.id) && createTime.equals(baseDTO.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createTime);
    }
}
