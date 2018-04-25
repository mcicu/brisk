package ro.orange.brisk.controllers;

import groovy.util.Eval;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.orange.brisk.beans.Case;
import ro.orange.brisk.rules.PackageCodeSpecification;
import ro.orange.brisk.rules.Spec;
import ro.orange.brisk.rules.Specification;

import java.util.Arrays;
import java.util.List;

@RestController
public class DemoController {

    @RequestMapping(path = "/preview", method = RequestMethod.POST, produces = "application/json")
    public Boolean evaluate(@RequestBody Case input) {
        Specification s = new PackageCodeSpecification();
        s.setAgainst("ME01E");

        Spec<Case> packageCodeSpec = new Spec<Case>(c -> "ME01E".equals(c.getPackageCode()));
        Spec<Case> oacodeSpec = new Spec<Case>(c -> "HDS-123".equals(c.getOacode()));

        Spec<Case> specsCapture = Arrays.asList(packageCodeSpec, oacodeSpec).stream().reduce((s1, s2) -> s1.and(s2)).get();

        Boolean eval = true;
        eval = eval && specsCapture.isSatisfiedBy(input);
        return eval;
    }

    @RequestMapping(path = "/groovy", method = RequestMethod.POST, produces = "application/json")
    public Object groovy(@RequestBody Case input) {
        Object result = Eval.me("cc", input, "cc.packageCode == \"ME01E\"");
        return result;
    }

}
