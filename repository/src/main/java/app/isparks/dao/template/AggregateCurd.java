package app.isparks.dao.template;


import app.isparks.dao.template.support.AbstractCurdSupport;

/**
 * @author chenghd
 * @date 2020/10/22
 */
public abstract class AggregateCurd<E,ID> extends AbstractCurdSupport implements ICurd<E,ID>, IBatchCurd<E,ID>,IPageableCurd<E>{




}
