package app.isparks.dao.template;


import app.isparks.core.pojo.page.PageData;
import app.isparks.core.pojo.page.PageInfo;

/**
 * paging interface
 * 分页接口
 *
 * @author chenghd
 * @date 2020/10/9
 */
public interface IPageableCurd<E> {

    /**
     * 返回分页结果 PageData<E>对象
     * （返回值不能为NULL）
     *
     * @param pageInfo
     * @param e
     * @return PageData
     */
    PageData<E> pageByCond(PageInfo pageInfo, E e);

}
