package app.isparks.dao.template;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 通用但是效率低下的实现方法
 * 对效率要求比较高的地方建议在具体实现类中重写
 *
 * @author chenghd
 * @date 2020/10/22
 */
public abstract class BatchCurd<E, ID> extends AggregateCurd<E,ID> implements IBatchCurd<E, ID> {

    @Override
    public List<E> insertBatch(List<E> es) {
        List<E> result = new ArrayList<>(es.size());
        es.forEach((e)->{
            result.add(insert(e));
        });
        return result;
    }

    @Override
    public List<E> selectBatchByIds(List<ID> ids){
        if(ids == null || ids.size() < 1){
            return new ArrayList<>();
        }
        return selectByIdsLoop(ids);
    }


    @Override
    public int deleteBatch(List<ID> ids) {
        if(ids == null || ids.size() < 1){
            return 0;
        }
        return deleteByIdsLoop(ids);
    }

    @Override
    public List<E> updateBatchById(List<E> es) {
        List<E> result = new ArrayList<>(es.size());
        es.forEach((e)->result.add(updateById(e)));
        return result;
    }

    /**
     * todo : 修改
     * 循环遍历删除
     *
     * 这个方法一般情况下不是经常使用，而且使用需要谨慎。
     * warning : 低效但是通用，如果对效率有一定要求的操作，建议在实现类中重写此方法。
     */
    private int deleteByIdsLoop(List<ID> ids){
        int result = 0;
        for(ID id : ids){
            if(delete(id) != null){
                ++ result;
            }
        }
        return result;
    }

    private List<E> selectByIdsLoop(List<ID> ids){
        List<E> result = new ArrayList<>(ids.size());
        ids.forEach((id)->result.add(select(id)));
        return result;
    }

}
