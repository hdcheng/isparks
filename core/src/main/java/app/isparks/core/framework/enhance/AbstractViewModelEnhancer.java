package app.isparks.core.framework.enhance;

import app.isparks.core.pojo.base.BaseProperty;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author： chenghd
 * @date： 2021/3/23
 */
public abstract class AbstractViewModelEnhancer<VO extends BaseProperty> implements IEnhancer<VO> {

    private AbstractViewModelEnhancer nextEnhancer;

    private static AbstractViewModelEnhancer enhancer;

    @Override
    public final <E extends IEnhancer> void setNextEnhancer(E enhancer){
        if(enhancer instanceof AbstractViewModelEnhancer){
            this.nextEnhancer = (AbstractViewModelEnhancer)enhancer;
        }
    }

    @Override
    public final void execute(final VO vo) {
        enhance(vo);
        if(nextEnhancer != null){
            nextEnhancer.execute(vo);
        }
    }

    @Override
    public void enhance(VO o) {

    }

    public void execute(Model model){
        enhance(model);
        if(nextEnhancer != null){
            nextEnhancer.execute(model);
        }
    }

    public void enhance(Model model){

    }

    // 处理
    public final void before(HttpServletRequest request){
        beforeEnhance(request);
        if(nextEnhancer != null){
            nextEnhancer.before(request);
        }
    }

    protected void beforeEnhance(HttpServletRequest request){
        // 钩子
    }

    //
    public final void after(HttpServletResponse response){
        afterEnhance(response);
        if(nextEnhancer != null){
            nextEnhancer.after(response);
        }
    }

    protected void afterEnhance(HttpServletResponse response){
        // 钩子
    }
}
