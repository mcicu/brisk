package ro.orange.brisk.controllers;

import groovy.util.Eval;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.orange.brisk.beans.Agent;
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

        Spec<Case> packageCodeSpec = new Spec<>(c -> "ME01E".equals(c.getPackageCode()));
        Spec<Case> oacodeSpec = new Spec<>(c -> "HDS-123".equals(c.getOacode()));

        Spec<Agent> posCodeSpec = new Spec<>(a -> "POS01".equals(a.getPosCode()));

        Spec<Case> compositeSpec = new Spec<>(c -> packageCodeSpec.and(oacodeSpec).isSatisfiedBy(c) && posCodeSpec.isSatisfiedBy(c.getAgent()));



        Boolean eval = true;
        eval = eval && compositeSpec.isSatisfiedBy(input);
        return eval;
    }

    @RequestMapping(path = "/groovy", method = RequestMethod.POST, produces = "application/json")
    public Object groovy(@RequestBody Case input) {
        Object result = Eval.me("cc", input, "cc.packageCode == \"ME01E\"");
        return result;
    }

}
