package ro.orange.brisk.core;

import java.text.MessageFormat;
import java.util.List;
import java.util.function.Function;

public class SpecLambdaFactory {

    public static <I, R> Function<I, Boolean> equals(R configuration, Function<I, R> supplier) {
        return c -> configuration.equals(supplier.apply(c));
    }

    public static <I, R> Function<I, Boolean> inList(List<R> configuration, Function<I, R> supplier) {
        return c -> configuration.contains(supplier.apply(c));
    }

    public static <I, R> Function<I, Boolean> notEquals(R configuration, Function<I, R> supplier) {
        return c -> !configuration.equals(supplier.apply(c));
    }

    public static <I, R> Function<I, Boolean> notInList(List<R> configuration, Function<I, R> supplier) {
        return c -> !configuration.contains(supplier.apply(c));
    }

    public static <I, R> Function<I, Boolean> resolveLambdaForOperator(R configuration, EnumOperator operator, Function<I, R> supplier) {
        switch (operator) {
            case EQUALS:
                return equals(configuration, supplier);
            case NOT_EQUALS:
                return notEquals(configuration, supplier);
            default:
                throw new RuntimeException(MessageFormat.format("Failed to resolve lambda for operator {0}", operator));
        }
    }

    public static <I, R> Function<I, Boolean> resolveLambdaForOperator(List<R> configuration, EnumOperator operator, Function<I, R> supplier) {
        switch (operator) {
            case IN_LIST:
                return inList(configuration, supplier);
            case NOT_IN_LIST:
                return notInList(configuration, supplier);
            default:
                throw new RuntimeException(MessageFormat.format("Failed to resolve lambda for operator {0}", operator));
        }
    }
}
