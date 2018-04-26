package ro.orange.brisk.rules;

import java.util.List;
import java.util.function.Function;

public class SpecFactory {

    public static <I> Spec<I> stringEquals(String configuration, Function<I, String> stringSupplier) {
        Spec<I> spec = new Spec<>(c -> configuration.equals(stringSupplier.apply(c)));
        return spec;
    }

    public static <I> Spec<I> stringInList(List<String> configuration, Function<I, String> stringSupplier) {
        Spec<I> spec = new Spec<>(c -> configuration.contains(stringSupplier.apply(c)));
        return spec;
    }
}
