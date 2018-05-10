package ro.orange.brisk.demo.controllers;

import groovy.util.Eval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.orange.brisk.core.*;
import ro.orange.brisk.core.utils.LambdaUtils;
import ro.orange.brisk.demo.beans.Case;
import ro.orange.brisk.demo.rules.MsisdnSpec;
import ro.orange.brisk.demo.rules.PosCodeSpec;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.function.Function;

@RestController
public class DemoController {

    Logger logger = LoggerFactory.getLogger(DemoController.class);

    @RequestMapping(path = "/preview", method = RequestMethod.POST, produces = "application/json")
    public Boolean evaluate(@RequestBody Case input) {

        ISpec<Case> packageCodeSpec = SpecFactory.inList(Arrays.asList("ME01E", "ME02E"), Case::getPackageCode);
        //ISpec<Case> oacodeSpec = new HelloSpec("HDS-123", EnumOperator.EQUALS);

        ISpec<Case> posCodeSpec = new PosCodeSpec("POS123", EnumOperator.EQUALS);

        ISpec<LocalDateTime> dateSpec = SpecFactory.greaterThanOrEqual(LocalDateTime.of(2018, Month.APRIL, 1, 0, 0));

        //could eval input fields, input subfields, and static specs
        ISpec<Case> complexSpec = new ASpec<>(c -> packageCodeSpec.isSatisfiedBy(c) && posCodeSpec.isSatisfiedBy(c) && dateSpec.isSatisfiedBy(LocalDateTime.now()));

        Boolean eval = true;

        /* ConjunctiveSpec class demo */
        ISpec<Case> andSpec1 = new ConjunctiveSpec<Case>().add(packageCodeSpec).add(complexSpec);
        logger.info("AndSpec1 eval: {}", andSpec1.isSatisfiedBy(input));

        ISpec<Case> andSpec2 = new ConjunctiveSpec<Case>().add(andSpec1).add(packageCodeSpec);
        logger.info("AndSpec2 eval: {}", andSpec2.isSatisfiedBy(input));

        eval = eval && andSpec2.isSatisfiedBy(input);

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

    @RequestMapping(path = "/scoped", method = RequestMethod.POST, produces = "application/json")
    public Boolean scoped(@RequestBody Case input) {

        ISpec<String> msisdnSpec = new MsisdnSpec();
        ISpec<String> msisdnSpec2 = new MsisdnSpec();

        CompositeSpec<Case> casespec = new CompositeSpec<>();
        casespec.add(msisdnSpec);
        casespec.add(msisdnSpec2);

        return casespec.isSatisfiedBy(input);
    }

    private void recursivePath(Class clazz, String prefix) {
        if (false == clazz.getTypeName().startsWith("ro.orange"))
            return;

        for (Field f : clazz.getDeclaredFields()) {
            System.out.println(prefix + " " + f.getName());
            recursivePath(f.getType(), prefix+prefix);
        }
    }

    @RequestMapping(path = "/scope-discovery", method = RequestMethod.POST, produces = "application/json")
    public Boolean scopeDiscovery(@RequestBody Case input) throws Exception {

        recursivePath(input.getClass(), "-");
        return true;
    }

}

