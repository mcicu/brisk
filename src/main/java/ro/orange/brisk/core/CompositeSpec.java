package ro.orange.brisk.core;

import groovy.util.Eval;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.List;

public class CompositeSpec<T> implements ISpec<T> {

    List<ISpec<?>> specs = new ArrayList<>();

    public void add(ISpec<?> spec) {
        specs.add(spec);
    }

    @Override
    public Boolean isSatisfiedBy(T input) {
        Boolean eval = true;
        for (ISpec spec : specs) {
            String scope = spec.getClass().getAnnotation(Scope.class).value();
            eval = eval && spec.isSatisfiedBy(Eval.x(input, "x." + scope));
        }
        return eval;
    }
}
