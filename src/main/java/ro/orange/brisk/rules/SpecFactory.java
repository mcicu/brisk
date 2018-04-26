package ro.orange.brisk.rules;

import java.util.List;
import java.util.function.Function;

public class SpecFactory {

    public static <I, R> Spec<I> equals(R configuration, Function<I, R> supplier) {
        Spec<I> spec = new Spec<>(c -> configuration.equals(supplier.apply(c)));
        return spec;
    }

    public static <I, R> Spec<I> inList(List<R> configuration, Function<I, R> supplier) {
        Spec<I> spec = new Spec<>(c -> configuration.contains(supplier.apply(c)));
        return spec;
    }
}
