package ro.orange.brisk.core;

import ro.orange.brisk.core.ASpec;
import ro.orange.brisk.core.ISpec;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

public class SpecFactory {

    public static <I, R> ISpec<I> equals(R configuration, Function<I, R> supplier) {
        ISpec<I> spec = new ASpec<>(c -> configuration.equals(supplier.apply(c)));
        return spec;
    }

    public static <I, R> ISpec<I> inList(List<R> configuration, Function<I, R> supplier) {
        ISpec<I> spec = new ASpec<>(c -> configuration.contains(supplier.apply(c)));
        return spec;
    }

    public static ISpec<LocalDateTime> greaterThanOrEqual(LocalDateTime configuration) {
        ISpec<LocalDateTime> spec = new ASpec<>(c -> configuration.isBefore(c));
        return spec;
    }
}
