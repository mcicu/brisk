package ro.orange.brisk.demo.rules;

import org.springframework.web.servlet.ModelAndView;
import ro.orange.brisk.core.*;
import ro.orange.brisk.demo.beans.Case;

import java.util.UUID;

//DEMO
public class PosCodeSpec implements ISpec<Case>, IWebComponent, IPersistentSpec {

    String id = "a" + UUID.randomUUID().toString().replaceAll("-", "");

    String configuration;

    String against = "posCode"; //just for tracing; replace with runtimeLambda?

    EnumOperator operator;

    public PosCodeSpec() {

    }

    public PosCodeSpec(String poscode, EnumOperator operator) {
        this.configuration = poscode;
        this.operator = operator;
    }

    @Override
    public Boolean isSatisfiedBy(Case input) {
        if (null == input || null == input.getAgent()) return false;
        return SpecLambdaFactory.resolveLambdaForOperator(configuration, operator, (Case c) -> input.getAgent().getPosCode()).apply(input);
    }

    @Override
    public String getComponentId() {
        return id;
    }

    @Override
    public String getComponentName() {
        return "Pos Code";
    }

    @Override
    public ModelAndView displayComponent() {
        ModelAndView modelAndView = new ModelAndView("input-text-component::frag(" + id + ")");
        modelAndView.addObject(id + "label", against);
        modelAndView.addObject(id + "operator", operator);
        modelAndView.addObject(id + "input", configuration);
        return modelAndView;
    }
}