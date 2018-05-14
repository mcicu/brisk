package ro.orange.brisk.demo.services;

import org.springframework.stereotype.Service;

@Service
public class DMNService {

    /*KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
    DMNRuntime dmnRuntime = kieContainer.newKieSession("ConsumptionSession").getKieRuntime(DMNRuntime.class);

    DMNModel dmnModel = dmnRuntime.getModel(
            "schema/test",
            "string_test"
    );



    public List<DMNDecisionResult> evaluate(Case input) {
        DMNContext dmnContext = dmnRuntime.newContext();
        dmnContext.set("poscode", input.getAgent().getPosCode());
        dmnContext.set("msisdn", input.getMsisdn());

        DMNResult dmnResult = dmnRuntime.evaluateAll(dmnModel, dmnContext);

        return dmnResult.getDecisionResults();
    }*/
}
