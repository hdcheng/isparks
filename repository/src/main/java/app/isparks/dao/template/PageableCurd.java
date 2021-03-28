package app.isparks.dao.template;

import app.isparks.core.exception.InvalidValueException;
import app.isparks.core.pojo.page.PageData;
import app.isparks.core.pojo.page.PageInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页实现
 *
 * @author chenghd
 * @date 2020/10/10
 */
public abstract class PageableCurd<E,ID> extends BatchCurd<E,ID> implements IPageableCurd<E>{

    private Logger log = LoggerFactory.getLogger(PageableCurd.class);

    @Override
    public PageData<E> pageByCond(PageInfo pageInfo, E e) {
        if(pageInfo == null || e == null){
            log.error("page info or e 为空");
            throw new InvalidValueException("page info can not be empty");
        }
        if(pageInfo.getPage() <= 0){
            pageInfo.setPage(1);
        }
        return pageHelperPageByCond(pageInfo,e);
    }

    /**
     * 使用 page helper 插件实现分页
     */
    private PageData<E> pageHelperPageByCond(PageInfo pageInfo, E e){
        PageData<E> result = new PageData();

        Page page = PageHelper.startPage(pageInfo.getPage(), pageInfo.getSize(), true);
        page.setOrderBy("create_time DESC"); // 默认排序方式

        List<E> pageData;

        try {
            pageData = selectByCond(e);
        }finally {
            PageHelper.clearPage(); // 清除分页数据
        }

        if(pageData != null){
            result.setData(pageData);//分页数据
        }else {
            result.setData(new ArrayList<>());
        }

        result.setPage(pageInfo.getPage());     //当前页码
        result.setSize(pageInfo.getSize());     //每页大小
        result.setTotalPage(page.getPages());   //共多少页
        result.setTotalData(page.getTotal());   //共有多少数据

        return result;
    }


}
