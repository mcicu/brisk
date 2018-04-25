package ro.orange.brisk.rules;

import ro.orange.brisk.beans.Case;

import java.text.MessageFormat;

public abstract class Specification<I, T> {

    String specificationName;
    String operator;
    T against;

    public String getSpecificationName() {
        return specificationName;
    }

    public void setSpecificationName(String specificationName) {
        this.specificationName = specificationName;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public T getAgainst() {
        return against;
    }

    public void setAgainst(T against) {
        this.against = against;
    }

    abstract public boolean evaluate(I input);

    public String stringify() {
        return MessageFormat.format("input {0} {1}", operator, against);
    }
}
