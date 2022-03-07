package app.isparks.dao.template;

import java.util.List;

/**
 * @author chenghd
 * @date 2020/8/11
 */
public interface ICurd<E, ID> {

    /**
     * 插入数据并返回插入的数据
     *
     * @param e
     * @return E
     */
    E insert(final E e);

    /**
     * 根据ID删除数量，返回值只能为1/0
     *
     * @param id
     * @return 1/0
     */
    E delete(ID id);

    /**
     * 根据条件查询结果
     *
     * @param e
     * @return List<E>
     */
    List<E> selectByCond(E e);

    /**
     * 查找所有数据
     *
     * @return List<E>
     */
    List<E> select();

    /**
     * 根据 id 查找数据
     *
     * @param id
     * @return
     */
    E select(ID id);


    /**
     * 根据ID更新数据，e的id不能为NULL
     *
     * @param e
     * @return 更新后的对象
     */
    E updateById(final E e);


    /**
     * 统计
     *
     * @return long
     */
    long count();

    /**
     * 根据条件统计数量
     *
     * @param e
     * @return
     */
    long countBy(E e);

    /**
     * 检测数据是否存在
     *
     * @param e
     * @return
     */
    boolean exists(E e);

}
