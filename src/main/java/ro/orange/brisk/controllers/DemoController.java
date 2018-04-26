package ro.orange.brisk.controllers;

import groovy.util.Eval;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.orange.brisk.beans.Agent;
import ro.orange.brisk.beans.Case;
import ro.orange.brisk.rules.Spec;
import ro.orange.brisk.rules.SpecFactory;

import java.util.Arrays;

@RestController
public class DemoController {

    @RequestMapping(path = "/preview", method = RequestMethod.POST, produces = "application/json")
    public Boolean evaluate(@RequestBody Case input) {

        Spec<Case> packageCodeSpec = SpecFactory.stringInList(Arrays.asList("ME01E", "ME02E"), Case::getPackageCode);
        Spec<Case> oacodeSpec = SpecFactory.stringEquals("HDS-123", Case::getOacode);

        Spec<Agent> posCodeSpec = new Spec<>(a -> "POS01".equals(a.getPosCode()));

        Spec<Case> compositeSpec = new Spec<>(c -> packageCodeSpec.and(oacodeSpec).isSatisfiedBy(c) && posCodeSpec.isSatisfiedBy(c.getAgent()));

        Boolean eval = true;
        eval = eval && compositeSpec.isSatisfiedBy(input);
        return eval;
    }

    @RequestMapping(path = "/groovy", method = RequestMethod.POST, produces = "application/json")
    public Boolean groovy(@RequestBody Case input) {
        Boolean result = (Boolean) Eval.me("cc", input, "cc.packageCode == \"ME01E\"");
        return result;
    }

}
