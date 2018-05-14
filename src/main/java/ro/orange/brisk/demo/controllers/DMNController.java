package ro.orange.brisk.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.orange.brisk.demo.beans.Case;
import ro.orange.brisk.demo.services.CamundaDMNService;
import ro.orange.brisk.demo.services.DMNService;

import java.util.List;

@RestController
@RequestMapping("/dmn")
public class DMNController {

    @Autowired
    DMNService dmnService;

    @Autowired
    CamundaDMNService camundaDMNService;

    /*@RequestMapping(path = "/evaluate", method = RequestMethod.POST, produces = "application/json")
    public List<DMNDecisionResult> kiedmn(@RequestBody Case input) {
        return dmnService.evaluate(input);
    }*/

    @RequestMapping(path = "/camunda", method = RequestMethod.POST, produces = "application/json")
    public String camundadmn(@RequestBody Case input) {
        return camundaDMNService.camunda(input);
    }
}
