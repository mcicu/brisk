package ro.orange.brisk.core;

import org.springframework.util.Assert;
import ro.orange.brisk.core.ISpec;

import java.util.function.Function;

public class ASpec<I> implements ISpec<I> {

    private Function<I, Boolean> rule;

    public ASpec(Function<I, Boolean> rule) {
        Assert.notNull(rule, "Rule definition required");
        this.rule = rule;
    }

    public Boolean isSatisfiedBy(I input) {
        return rule.apply(input);
    }
}
