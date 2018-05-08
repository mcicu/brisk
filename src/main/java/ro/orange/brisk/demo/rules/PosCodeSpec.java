package ro.orange.brisk.demo.rules;

import org.springframework.web.servlet.ModelAndView;
import ro.orange.brisk.demo.beans.Case;
import ro.orange.brisk.core.EnumOperator;
import ro.orange.brisk.core.ISpec;
import ro.orange.brisk.core.IWebComponent;
import ro.orange.brisk.core.SpecLambdaFactory;

import java.util.UUID;

//DEMO
public class PosCodeSpec implements ISpec<Case>, IWebComponent {

    String id = UUID.randomUUID().toString();
    String configuration;
    String right = "agent.poscode"; //just for tracing; replace with runtimeLambda?
    EnumOperator operator;

    public PosCodeSpec(String poscode, EnumOperator operator) {
        this.configuration = poscode;
        this.operator = operator;
    }

    @Override
    public Boolean isSatisfiedBy(Case input) {
        if (null == input || null == input.getAgent()) return false;
        return SpecLambdaFactory.resolveLambdaForOperator(configuration, operator, (Case c) -> c.getAgent().getPosCode()).apply(input);
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
        ModelAndView modelAndView = new ModelAndView("input-text-component :: afrag");
        modelAndView.addObject("label", getComponentName());
        modelAndView.addObject("operator", operator);
        modelAndView.addObject("input", configuration);
        return modelAndView;
    }
}