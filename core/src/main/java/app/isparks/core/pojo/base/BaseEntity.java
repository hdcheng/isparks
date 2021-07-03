package app.isparks.core.pojo.base;

import app.isparks.core.pojo.enums.DataStatus;

import java.io.Serializable;

/**
 * Base Entity
 * 所有实体类的基础类
 *
 * @author chenghd
 * @date 2020/7/22
 */
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private String id;

    /**
     * unix time
     */
    private Long createTime;

    /**
     * unix time
     */
    private Long modifyTime;

    /**
     * status.
     * see:com.fence.core.pojo.enums.DataStatus
     */
    private Integer status;

    // constructor
    public BaseEntity() { }
    public BaseEntity(String id) { this.id = id; }

    // getter and setter
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
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

}
