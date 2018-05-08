package ro.orange.brisk.core;

//specification
public interface ISpec<I> {

    Boolean isSatisfiedBy(I input);

    default ISpec<I> and(ISpec<I> other) {
        return new AndSpec<I>(this, other);
    }

    /*default implement or */
}
