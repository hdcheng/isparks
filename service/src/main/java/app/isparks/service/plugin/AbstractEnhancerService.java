package app.isparks.service.plugin;

import app.isparks.plugin.enhance.IDecorator;
import app.isparks.core.pojo.base.BaseDTO;
import app.isparks.core.pojo.base.BaseEntity;
import app.isparks.dao.template.AbstractCurd;
import app.isparks.plugin.enhance.AbstractEnhancer;
import app.isparks.service.base.AbstractService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractEnhancerService<DOMAIN extends BaseEntity,DTO extends BaseDTO> extends AbstractService<DOMAIN> implements IDecorator<DTO> {

    public AbstractEnhancerService(AbstractCurd<DOMAIN> abstractCurd) {
        super(abstractCurd);
    }

    private final static AbstractEnhancer<BaseDTO> enhancer = AbstractEnhancer.build();

    @Override
    public void decorate(IDecorator<DTO> decorator) {
        enhancer.decorate(decorator);
    }

    @Override
    public void unload(IDecorator<DTO> decorator) {
        enhancer.unload(decorator);
    }

    @Override
    public void execute(DTO o) {
        enhancer.execute(o);
    }

    /**
     * entity 转 dto 方法
     * @param domain 参数不会为 null
     */
    protected abstract DTO toDTO(DOMAIN domain);

    protected List<DTO> toDTO(List<DOMAIN> domains){
        List<DTO> dtos = new ArrayList<>(domains.size());
        for(DOMAIN domain : domains){
            dtos.add(converter(domain));
        }
        return dtos;
    }

    protected final List<DTO> converter(List<DOMAIN> domains){
        if(domains == null || domains.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        return toDTO(domains);
    }

    protected final DTO converter(DOMAIN domain){
        if(domain == null){
            return null;
        }
        DTO dto = toDTO(domain);

        if(dto != null){
            execute(dto);
        }
        return dto;
    }

}
