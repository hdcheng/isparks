package app.isparks.plugin.enhance;

import app.isparks.core.exception.PluginException;
import app.isparks.core.util.IdUtils;
import app.isparks.core.util.StringUtils;

public abstract class AbstractEnhancer<O> implements IEnhancer<O>, IDecorator<O> {

    private String id;

    public AbstractEnhancer(String id){
        if(StringUtils.isEmpty(id)){
            throw new PluginException("Decorator ID must not be empty.");
        }
        this.id = id;
    }

    private AbstractEnhancer<O> nextEnhancer;

    @Override
    public final synchronized void decorate(IDecorator enhancer){
        if(enhancer instanceof AbstractEnhancer && !exist(enhancer)){
            AbstractEnhancer pre = this;
            while (true){
                if(pre.getNext() == null){
                    break;
                }
                pre = pre.getNext();
            }
            pre.setNext((AbstractEnhancer)enhancer);
        }
    }

    @Override
    public final synchronized void unload(IDecorator decorator) {
        if(decorator != null && decorator instanceof AbstractEnhancer){
            unload(((AbstractEnhancer) decorator).getId());
        }
    }

    public final synchronized void unload(String id) {
        if(id == null || id.isEmpty()){
            return;
        }
        AbstractEnhancer pre = this;
        while (true){
            if(pre == null || pre.nextEnhancer == null){
                break;
            }
            if(id.equals(pre.nextEnhancer.getId())){
                pre.setNext(pre.nextEnhancer.getNext());
            }
            pre = pre.getNext();
        }
    }

    public AbstractEnhancer getNext(){
        return nextEnhancer;
    }

    public void setNext(AbstractEnhancer<O> enhancer){
        this.nextEnhancer = enhancer;
    }

    @Override
    public final void execute(final O o) {
        enhance(o);
        if(nextEnhancer != null){
            nextEnhancer.execute(o);
        }
    }

    /**
     * 检测增强器是否已经存在
     * @param enhancer
     */
    private boolean exist(IDecorator enhancer){
        if(enhancer instanceof AbstractEnhancer){
            AbstractEnhancer next = nextEnhancer;
            while (next != null){
                if(next.equals(enhancer)){
                    return true;
                }
                next = next.nextEnhancer;
            }
        }
        return false;
    }

    public static AbstractEnhancer build(){
        return new InterEnhancer(IdUtils.id());
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(obj == this){
            return true;
        }
        return this.getId().equals(((AbstractEnhancer)obj).getId());
    }

    static class InterEnhancer<O> extends AbstractEnhancer<O>{
        public InterEnhancer(String id) {
            super(id);
        }

        @Override
        public void enhance(O o) {

        }
    }
}
