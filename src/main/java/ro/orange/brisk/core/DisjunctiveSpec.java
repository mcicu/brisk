package ro.orange.brisk.core;

import java.util.ArrayList;
import java.util.List;

//OR separated specs
public class DisjunctiveSpec<T> implements ISpec<T> {

    private List<ISpec<T>> specs = new ArrayList<>();

    public DisjunctiveSpec<T> add(ISpec<T> spec) {
        specs.add(spec);
        return this;
    }

    public Boolean isSatisfiedBy(T input) {
        if (null == input) return false;
        Boolean eval = true;
        for (ISpec<T> spec : specs)
            eval = eval || spec.isSatisfiedBy(input);
        return eval;
    }
}
