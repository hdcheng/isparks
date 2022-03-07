package app.isparks.plugin.enhance;

public interface IDecorator<O> {

    void decorate(IDecorator<O> decorator);

    void unload(IDecorator<O> decorator);

    void execute(O o);

}
