package ro.orange.brisk.demo.rules;

import org.springframework.context.annotation.Scope;
import ro.orange.brisk.core.ISpec;

@Scope("msisdn")
public class MsisdnSpec implements ISpec<String> {

    String configuration = "123";

    @Override
    public Boolean isSatisfiedBy(String input) {
        return configuration.equals(input);
    }
}
