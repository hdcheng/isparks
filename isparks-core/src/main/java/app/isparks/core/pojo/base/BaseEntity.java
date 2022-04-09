package app.isparks.core.pojo.base;

import app.isparks.core.pojo.enums.DataStatus;

import java.io.Serializable;
import java.util.Objects;

/**
 * Base Entity
 * 所有实体类的基础类
 *
 * @author chenghd
 * @date 2020/7/22
 */
public class BaseEntity extends MetaEntity {

    /**
     * unix time
     */
    private Long createTime;

    /**
     * unix time
     */
    private Long modifyTime;

    private Integer status;

    // constructor
    public BaseEntity() { }
    public BaseEntity(String id) { super(id); }

    // getter and setter
    public Long getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
    public Long getModifyTime() {
        return modifyTime;
    }
    public void setModifyTime(Long modifyTime) {
        this.modifyTime = modifyTime;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public void setStatus(DataStatus status){ setStatus(status.getCode()); }

    // with
    public BaseEntity withId(String id) { setId(id);return this; }
    public BaseEntity withCreateTime(Long createTime) { setCreateTime(createTime);return this; }
    public BaseEntity withModifyTime(Long modifyTime) { setModifyTime(modifyTime);return this; }
    public BaseEntity withStatus(Integer status) { setStatus(status);return this; }
    public BaseEntity withStatus(DataStatus status) { return withStatus(status.getCode()); }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), createTime);
    }
}
