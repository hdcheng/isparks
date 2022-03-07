package app.isparks.core.function;

@FunctionalInterface
public interface TowArgsFunction<O1,O2> {

    void handler(O1 o1,O2 o2);

}
