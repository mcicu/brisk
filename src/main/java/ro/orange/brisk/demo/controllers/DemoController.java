package ro.orange.brisk.demo.controllers;

import groovy.util.Eval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.orange.brisk.demo.beans.Case;
import ro.orange.brisk.core.*;
import ro.orange.brisk.demo.rules.*;
import ro.orange.brisk.core.utils.LambdaUtils;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.function.Function;

@RestController
public class DemoController {

    Logger logger = LoggerFactory.getLogger(DemoController.class);

    @RequestMapping(path = "/preview", method = RequestMethod.POST, produces = "application/json")
    public Boolean evaluate(@RequestBody Case input) {

        ISpec<Case> packageCodeSpec = SpecFactory.inList(Arrays.asList("ME01E", "ME02E"), c -> c.getPackageCode());
        //ISpec<Case> oacodeSpec = new HelloSpec("HDS-123", EnumOperator.EQUALS);

        ISpec<Case> posCodeSpec = new PosCodeSpec("POS123", EnumOperator.EQUALS);

        ISpec<LocalDateTime> dateSpec = SpecFactory.greaterThanOrEqual(LocalDateTime.of(2018, Month.APRIL, 1, 0, 0));

        //could eval input fields, input subfields, and static specs
        ISpec<Case> compositeSpec = new ASpec<>(c -> packageCodeSpec.isSatisfiedBy(c) && posCodeSpec.isSatisfiedBy(c) && dateSpec.isSatisfiedBy(LocalDateTime.now()));

        Boolean eval = true;

        /* AndSpec class demo */
        ISpec<Case> andSpec1 = new AndSpec<>(packageCodeSpec, packageCodeSpec);
        Boolean andSpecTest = andSpec1.isSatisfiedBy(input);
        System.out.println("AndSpec1 isSatisfiedBy: " + andSpecTest);

        ISpec<Case> andSpec2 = new AndSpec<>(andSpec1, posCodeSpec);
        Boolean andSpecTest2 = andSpec2.isSatisfiedBy(input);
        System.out.println("AndSpec2 isSatisfiedBy: " + andSpecTest2);

        eval = eval && andSpecTest2;

        return eval;
    }


    @RequestMapping(path = "/groovy", method = RequestMethod.POST, produces = "application/json")
    public Boolean groovy(@RequestBody Case input) {
        Boolean result = (Boolean) Eval.x(input, "Arrays.asList(\"ME01E\", \"ME02E\").contains(x.packageCode) && x.oacode == \"HDS-123\" && x.agent.posCode == \"POS01\"");
        return result;
    }


    //TODO lambda serialization ?
    @RequestMapping(path = "/lambda", method = RequestMethod.POST, produces = "application/json")
    public Boolean lambda(@RequestBody Case input) {

        String method = "getPackageCode";
        Function<Case, String> reflectedLambda = LambdaUtils.runtimeLambda(Case.class, method, String.class);
        ISpec<Case> packageCodeSpec = SpecFactory.inList(Arrays.asList("ME01E", "ME02E"), reflectedLambda);

        Boolean eval = true;
        eval = eval && packageCodeSpec.isSatisfiedBy(input);
        return eval;
    }

}
