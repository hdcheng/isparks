package app.isparks.dao.template;

import java.util.List;

/**
 * batch operation interface
 */
public interface IBatchCurd<E, ID> {

    /**
     * 批量创建数据
     *
     * @param es
     * @return
     */
    List<E> insertBatch(List<E> es);


    /**
     * 批量查找
     * @param ids
     * @return
     */
    List<E> selectBatchByIds(List<ID> ids);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int deleteBatch(List<ID> ids);

    /**
     * 批量更新
     *
     * @param es
     * @return
     */
    List<E> updateBatchById(List<E> es);

}
