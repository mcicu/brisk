package ro.orange.brisk.demo;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.dmn.api.core.*;
import org.springframework.stereotype.Service;
import ro.orange.brisk.demo.beans.Case;

import java.util.List;

@Service
public class DMNService {

    KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
    DMNRuntime dmnRuntime = kieContainer.newKieSession("ConsumptionSession").getKieRuntime(DMNRuntime.class);

    DMNModel dmnModel = dmnRuntime.getModel(
            "http://camunda.org/schema/1.0/dmn",  // model namespace
            "DRD"                       // model name
    );

    public List<DMNDecisionResult> evaluate(Case input) {
        DMNContext dmnContext = dmnRuntime.newContext();
        dmnContext.set("cico", input);

        DMNResult dmnResult = dmnRuntime.evaluateAll(dmnModel, dmnContext);

        return dmnResult.getDecisionResults();
    }
}
