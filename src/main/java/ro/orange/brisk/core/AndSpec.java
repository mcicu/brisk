package ro.orange.brisk.core;

import ro.orange.brisk.core.ISpec;

public class AndSpec<T> implements ISpec<T> {

    private ISpec<T> left;
    private ISpec<T> right;
    private String operator = "AND";

    public AndSpec(ISpec<T> left, ISpec<T> right) {
        this.left = left;
        this.right = right;
    }

    public Boolean isSatisfiedBy(T input) {
        //TODO check npe
        return left.isSatisfiedBy(input) && right.isSatisfiedBy(input);
    }
}
