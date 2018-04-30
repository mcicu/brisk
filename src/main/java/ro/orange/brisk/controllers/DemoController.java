package ro.orange.brisk.controllers;

import groovy.util.Eval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.orange.brisk.beans.Agent;
import ro.orange.brisk.beans.Case;
import ro.orange.brisk.rules.Spec;
import ro.orange.brisk.rules.SpecFactory;
import ro.orange.brisk.utils.LambdaUtils;

import java.lang.invoke.*;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Function;

@RestController
public class DemoController {

    Logger logger = LoggerFactory.getLogger(DemoController.class);

    @RequestMapping(path = "/preview", method = RequestMethod.POST, produces = "application/json")
    public Boolean evaluate(@RequestBody Case input) {

        Spec<Case> packageCodeSpec = SpecFactory.inList(Arrays.asList("ME01E", "ME02E"), Case::getPackageCode);
        Spec<Case> oacodeSpec = SpecFactory.equals("HDS-123", Case::getOacode);

        Spec<Agent> posCodeSpec = SpecFactory.equals("POS01", Agent::getPosCode);

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



    //TODO lambda serialization ?
    @RequestMapping(path = "/lambda", method = RequestMethod.POST, produces = "application/json")
    public Boolean lambda(@RequestBody Case input) throws Exception {

        String method = "getPackageCode";
        Function<Case, String> reflectedLambda = LambdaUtils.runtimeLambda(Case.class, method, String.class);
        Spec<Case> packageCodeSpec = SpecFactory.inList(Arrays.asList("ME01E", "ME02E"), reflectedLambda);

        Boolean eval = true;
        eval = eval && packageCodeSpec.isSatisfiedBy(input);
        return eval;
    }

}
