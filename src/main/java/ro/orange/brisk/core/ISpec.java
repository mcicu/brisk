package ro.orange.brisk.core;

public interface ISpec<I> {

    Boolean isSatisfiedBy(I input);
}