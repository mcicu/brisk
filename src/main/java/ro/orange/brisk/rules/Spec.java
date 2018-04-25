package ro.orange.brisk.rules;

import org.springframework.util.Assert;

import java.util.function.Function;

public class Spec<I> {

    private Function<I, Boolean> rule;

    public Spec(Function<I, Boolean> rule) {
        Assert.notNull(rule, "Rule definition required");
        this.rule = rule;
    }

    public Boolean isSatisfiedBy(I input) {
        return rule.apply(input);
    }

    public Spec<I> and(Spec<I> other) {
        return new Spec<I>(i -> this.isSatisfiedBy(i) && other.isSatisfiedBy(i));
    }

    public Spec<I> or(Spec<I> other) {
        return new Spec<I>(i -> this.isSatisfiedBy(i) || other.isSatisfiedBy(i));
    }
}
