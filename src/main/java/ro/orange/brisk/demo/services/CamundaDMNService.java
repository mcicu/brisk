package ro.orange.brisk.demo.services;

import org.camunda.bpm.dmn.engine.DmnDecisionResult;
import org.camunda.bpm.engine.DecisionService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.orange.brisk.demo.beans.Case;


@Service
public class CamundaDMNService {

    @Autowired
    ProcessEngine processEngine;

    public String camunda(Case input) {
        DecisionService decisionService = processEngine.getDecisionService();
        VariableMap variables = Variables
                .putValue("caseinput", input);

        DmnDecisionResult decisionResult = decisionService
                .evaluateDecisionByKey("booltostring")
                .variables(variables)
                .evaluate();

        return decisionResult.getSingleResult().getEntry("approvalString");
    }
}
