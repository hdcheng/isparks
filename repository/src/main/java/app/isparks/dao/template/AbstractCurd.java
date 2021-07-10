package app.isparks.dao.template;

import app.isparks.core.pojo.base.BaseEntity;
import app.isparks.core.pojo.page.PageData;
import app.isparks.core.pojo.page.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * curd template
 *
 * @author chenghd
 * @date 2020/7/22
 */
public abstract class AbstractCurd<E extends BaseEntity> extends PageableCurd<E,String> {

    private static Logger log = LoggerFactory.getLogger(AbstractCurd.class);

    /**
     * 获取一个实例
     */
    public abstract E newEntity();

    /**
     * 分页查询所有数据。
     */
    public PageData<E> pageAll(PageInfo pageInfo) {

        return pageByCond(pageInfo, newEntity());
    }


    @Override
    public long count() {
        return countBy(newEntity());
    }

    @Override
    public E select(String id) {
        return selectByCond((E)newEntity().withId(id)).stream().findFirst().orElse(null);
    }

    @Override
    public List<E> select() {
        List<E> list = selectByCond(newEntity());
        return list != null ? list : new ArrayList<>();
    }

    @Override
    public E delete(String id) {
        E e = select(id);
        return e != null && deleteBy((E)newEntity().withId(id)) == 1 ? e : null;
    }

    @Override
    public boolean exists(E e) {
        return countBy(e) > 0;
    }


    protected abstract int deleteBy(E e);

}
