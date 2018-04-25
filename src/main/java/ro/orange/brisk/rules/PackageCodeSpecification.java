package ro.orange.brisk.rules;

import ro.orange.brisk.beans.Case;

public class PackageCodeSpecification extends CaseSpecification<String> {

    @Override
    public boolean evaluate(Case input) {
        return false;
    }
}
