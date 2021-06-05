package app.isparks.core.framework.enhance;

import app.isparks.core.pojo.base.BaseDTO;

import java.util.List;

/**
 * service enhancer
 *
 * @author： chenghd
 * @date： 2021/3/25
 */
public abstract class AbstractServiceEnhancer<DTO extends BaseDTO> implements IEnhancer<DTO>{

    private AbstractServiceEnhancer nextEnhancer;

    @Override
    public final <E extends IEnhancer> void setNextEnhancer(E enhancer) {
        if(enhancer instanceof AbstractViewModelEnhancer){
            this.nextEnhancer = (AbstractServiceEnhancer)enhancer;
        }
    }

    @Override
    public final void execute(final DTO dto) {
        enhance(dto);
        if(nextEnhancer != null){
            this.nextEnhancer.execute(dto);
        }
    }

    @Override
    public void enhance(DTO o) {

    }

    public final void execute(final List<DTO> dtos){
        enhance(dtos);
        if(nextEnhancer != null){
            enhance(dtos);
        }
    }

    public void enhance(final List<DTO> dtos){

    }

}
