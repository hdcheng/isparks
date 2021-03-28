package app.isparks.dao.template.support;

import app.isparks.core.exception.InvalidValueException;
import app.isparks.core.pojo.base.BaseEntity;
import app.isparks.core.pojo.enums.DataStatus;
import app.isparks.core.util.DateUtils;
import app.isparks.core.util.IdUtils;
import app.isparks.core.util.StringUtils;
import org.springframework.util.Assert;

/**
 * @author chenghd
 * @date 2020/8/21
 */
public abstract class AbstractCurdSupport<E extends BaseEntity> {


    public void beforeInsert(final E e){

        if(StringUtils.isEmpty(e.getId())){
            e.setId(IdUtils.id());
        }

        if (e.getStatus() == null){
            e.setStatus(DataStatus.VALID);
        }

        long cm = System.currentTimeMillis();
        e.withCreateTime(cm).withModifyTime(cm);
    }

    public void beforeUpdate(final E e){
        e.withModifyTime(System.currentTimeMillis());
    }


    /**
     * 检测数据
     */
    public void ensure(final E e) {
        Assert.notNull(e, "数据不能为null");

        ensureId(e);
        ensureTime(e);
        ensureStatus(e);
    }

    /**
     * 检测 Id 不能为空
     */
    public void checkId(E e) {
        Assert.notNull(e, "数据不能为null");
        if (StringUtils.isEmpty(e.getId()))
            throw new InvalidValueException("id不能为空");
        hasLength(e.getId(), "id must not be null");
    }

    /**
     * id 不能为空
     */
    private void ensureId(final E e) {
        if (StringUtils.isEmpty(e.getId()))
            e.setId(IdUtils.id());
    }

    /**
     * modify time 不能为空
     */
    public void ensureModifyTime(final E e) {
        e.setModifyTime(DateUtils.getTimestamp());
    }

    /**
     * modify time 和 create time 不能为空
     */
    public void ensureTime(final E e) {
        ensureModifyTime(e);
        if (e.getCreateTime() == null || e.getCreateTime() == 0)
            e.setCreateTime(DateUtils.getTimestamp());
    }

    /**
     * status 不能为空
     */
    private void ensureStatus(final E e) {
        if (e.getStatus() == null)
            e.setStatus(DataStatus.VALID.getCode());
    }

    /**
     * 非空验证
     */
    public void notNull(Object o, String msg) {
        Assert.notNull(o, msg);
    }

    /**
     * 字符串非空
     **/
    public void hasLength(String text, String msg) {
        Assert.hasLength(text, msg);
    }

}
