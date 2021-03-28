package app.isparks.core.service;

import app.isparks.core.pojo.dto.CategoryDTO;
import app.isparks.core.pojo.page.PageData;
import app.isparks.core.pojo.param.CategoryParam;

import java.util.List;
import java.util.Optional;

/**
 * 分类服务接口
 *
 * @author： chenghd
 * @date： 2021/2/26
 */
public interface ICategoryService {


    /**
     * 创建分类
     * 分类名不能重复
     *
     * @param param
     * @return 创建成功的分类对象
     */
    Optional<CategoryDTO> create(CategoryParam param);

    /**
     * 删除分类
     *
     * @param categoryName
     * @return 删除后的分类
     */
    Optional<CategoryDTO> delete(String categoryName);

    /**
     * 分页查找数据
     * @param page
     * @param size
     * @return PageData<CategoryDTO>
     */
    PageData<CategoryDTO> page(int page,int size);

    /**
     * 根据id查找分类
     *
     * @param id
     * @return categorydto
     */
    Optional<CategoryDTO> selectById(String id);

    /**
     * 查找所有分类
     *
     * @return list CategoryDTO
     */
    List<CategoryDTO> listAll();


    /**
     * 更新分类
     *
     * @param id category id
     * @param param
     * @return 更新后的数据
     */
    Optional<CategoryDTO> update(String id , CategoryParam param);

}
