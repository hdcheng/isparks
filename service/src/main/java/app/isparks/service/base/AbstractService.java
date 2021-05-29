package app.isparks.service.base;

import app.isparks.core.pojo.base.BaseEntity;
import app.isparks.core.pojo.enums.DataStatus;
import app.isparks.core.pojo.enums.IEnum;
import app.isparks.core.pojo.page.PageData;
import app.isparks.core.pojo.page.PageInfo;
import app.isparks.core.service.support.BaseService;
import app.isparks.core.util.CollectionUtils;
import app.isparks.core.util.StringUtils;
import app.isparks.dao.template.AbstractCurd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * General abstract service class
 *
 * @author： chenghd
 * @date： 2021/1/14
 */
public abstract class AbstractService<DOMAIN extends BaseEntity> extends BaseService {

    private static Logger log = LoggerFactory.getLogger(AbstractCurd.class);

    private final AbstractCurd<DOMAIN> abstractCurd;

    public AbstractService(AbstractCurd<DOMAIN> abstractCurd){
        this.abstractCurd = abstractCurd;
    }

    public Optional<DOMAIN> abstractInsert(DOMAIN domain){
        Assert.notNull(domain,"domain must not be null.");
        if (!beforeInsert(domain)){
            return Optional.empty();
        }
        return Optional.ofNullable(abstractCurd.insert(domain));
    }

    public List<DOMAIN> abstractInsertBatch(List<DOMAIN> domains){
        Assert.notNull(domains,"domains must not be null.");
        return returnList(abstractCurd.insertBatch(domains));
    }

    public boolean beforeInsert(DOMAIN domain){
        return true;
    }

    public Optional<DOMAIN> abstractDelete(String id){
        Assert.hasLength(id,"ID must not be null");
        if(!beforeDelete(id)){
            return Optional.empty();
        }
        return Optional.ofNullable(abstractCurd.delete(id));
    }
    protected boolean beforeDelete(String id){
        return true;
    }

    public List<DOMAIN> abstractListByStatus(Integer c){
        DataStatus dataStatus = IEnum.codeToEnum(DataStatus.class,c);
        return abstractListByStatus(dataStatus);
    }

    public List<DOMAIN> abstractListByStatus(String statusName){
        DataStatus dataStatus = IEnum.nameToEnum(DataStatus.class,statusName);
        return abstractListByStatus(dataStatus);
    }

    public List<DOMAIN> abstractListByStatus(DataStatus status){

        if (status == null){
            return new ArrayList<>();
        }

        switch (status){
            case VALID:
                return returnList(abstractListValidStatus());
            case REMOVE:
                return returnList(abstractListRemovedStatus());
        }

        DOMAIN domain = (DOMAIN) abstractCurd.newEntity().withStatus(status);

        return returnList(abstractCurd.selectByCond(domain));
    }

    public List<DOMAIN> abstractListAll(){
        return returnList(abstractCurd.select());
    }

    public List<DOMAIN> abstractListBy(DOMAIN domain){
        Assert.notNull(domain,"domain must not be empty.");
        return returnList(abstractCurd.selectByCond(domain));
    }

    public List<DOMAIN> abstractListByIds(List<String> ids){
        if (CollectionUtils.isEmpty(ids)){
            return new ArrayList<>();
        }
        return abstractCurd.selectBatchByIds(ids);
    }

    public PageData<DOMAIN> abstractPageValidStatus(int page, int size){
        return abstractPageValidStatus(pageInfo(page,size));
    }

    public PageData<DOMAIN> abstractPageValidStatus(PageInfo pageInfo) {
        Assert.notNull(pageInfo,"page info must not be null");
        DOMAIN domain = (DOMAIN)abstractCurd.newEntity().withStatus(DataStatus.VALID);
        return returnPageData(abstractCurd.pageByCond(pageInfo,domain));
    }

    public PageData<DOMAIN> abstractPageRemovedStatus(int page, int size){
        PageInfo pageInfo = pageInfo(page,size);
        DOMAIN domain= (DOMAIN) abstractCurd.newEntity().withStatus(DataStatus.REMOVE);
        return returnPageData(abstractCurd.pageByCond(pageInfo,domain));
    }

    public PageData<DOMAIN> abstractPageAll(int page, int size){
        return returnPageData(abstractCurd.pageAll(pageInfo(page,size)));
    }

    public PageData<DOMAIN> abstractPageValidStatusBy(PageInfo pageInfo, DOMAIN domain) {
        Assert.notNull(domain,"domain must not be null");
        Assert.notNull(pageInfo,"page info must not be null");
        domain.withStatus(DataStatus.VALID);
        return returnPageData(abstractCurd.pageByCond(pageInfo,domain));
    }

    public Optional<DOMAIN> abstractGetById(String id){
        Assert.hasLength(id,"domain id must not be empty");
        DOMAIN domain = (DOMAIN)abstractCurd.newEntity().withId(id);
        return abstractCurd.selectByCond(domain).stream().findFirst();
    }

    public List<DOMAIN> abstractListValidStatus() {
        DOMAIN domain= (DOMAIN) abstractCurd.newEntity().withStatus(DataStatus.VALID);
        return returnList(abstractCurd.selectByCond(domain));
    }

    public List<DOMAIN> abstractListRemovedStatus() {
        DOMAIN domain= (DOMAIN)abstractCurd.newEntity().withStatus(DataStatus.REMOVE);
        return returnList(abstractCurd.selectByCond(domain));
    }

    public List<DOMAIN> abstractListValidBy(DOMAIN domain) {
        Assert.notNull(domain,"domain must not be null");
        domain.withStatus(DataStatus.REMOVE);
        return abstractCurd.selectByCond(domain);
    }

    public Optional<DOMAIN> abstractRemove(DOMAIN domain) {
        Assert.notNull(domain,"domain must not be null");
        Assert.hasLength(domain.getId(),"domain id must not be empty");
        domain.setStatus(DataStatus.REMOVE.getCode());
        return Optional.ofNullable(abstractCurd.updateById(domain));
    }

    public Optional<DOMAIN> abstractRemove(String id){
        Assert.hasLength(id,"domain id must not be empty");
        DOMAIN domain = (DOMAIN)abstractCurd.newEntity().withStatus(DataStatus.REMOVE).withId(id);
        return Optional.ofNullable(abstractCurd.updateById(domain));
    }

    public Optional<DOMAIN> abstractRestore(String id){
        Assert.hasLength(id,"domain id must not be empty");
        DOMAIN domain = (DOMAIN)abstractCurd.newEntity().withStatus(DataStatus.VALID).withId(id);
        return Optional.ofNullable(abstractCurd.updateById(domain));
    }

    public Optional<DOMAIN> abstractUpdate(DOMAIN domain){
        Assert.notNull(domain,"domain must not be null");
        if(StringUtils.isEmpty(domain.getId())){
            return Optional.empty();
        }
        return Optional.ofNullable(abstractCurd.updateById(domain));
    }

    public List<DOMAIN> abstractUpdateBatch(List<DOMAIN> domains){
        Assert.notNull(domains,"domain list must not be null.");
        return returnList(abstractCurd.updateBatchById(domains));
    }

    /**
     * 检测此数据表格是否存在
     * @return
     */
    public boolean connectable(){
        try {
            abstractCount();
            return true;
        }catch (Exception e){
            log.error("表格不存在",e);
            return false;
        }
    }

    /**
     * 更新数据状态为 VALID
     *
     * @param domain
     * @return 一个 DOMAIN 类型的 Optional
     */
    public Optional<DOMAIN> abstractResume(DOMAIN domain) {
        Assert.notNull(domain,"domain must not be null");
        Assert.hasLength(domain.getId(),"domain id must not be empty");
        domain.setStatus(DataStatus.VALID.getCode());
        domain.setModifyTime(System.currentTimeMillis());
        return Optional.ofNullable(abstractCurd.updateById(domain));
    }

    /**
     * 统计所有数量
     *
     * @return 统计结果
     */
    public long abstractCount(){
        return abstractCurd.count();
    }

    /**
     * 生成 page info 对象
     *
     * @param page
     * @param size
     * @return PageInfo
     */
    private PageInfo pageInfo(int page, int size){
        if (page <= 0){
            page = 1;
        }
        if(size <= 0 ){
            size = 1;
        }
        return new PageInfo(page,size);
    }

    /**
     * 确保返回的 List 不为 null
     *
     * @param result
     * @return List
     */
    private List<DOMAIN> returnList(List<DOMAIN> result){
        return result == null ? new ArrayList<>() : result;
    }

    /**
     * 确保返回的 PageData 不为 null
     *
     * @param pageData
     * @return
     */
    private PageData<DOMAIN> returnPageData(PageData<DOMAIN> pageData){
        return pageData == null ? new PageData<>(0,0):pageData;
    }

}
