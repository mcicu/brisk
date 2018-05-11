package ro.orange.brisk.demo.controllers;

import org.kie.dmn.api.core.DMNDecisionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.orange.brisk.demo.DMNService;
import ro.orange.brisk.demo.beans.Case;

import java.util.List;

@RestController
@RequestMapping("/dmn")
public class DMNController {

    @Autowired
    DMNService dmnService;

    @RequestMapping(path = "/evaluate", method = RequestMethod.POST, produces = "application/json")
    public List<DMNDecisionResult> groovy(@RequestBody Case input) {
        return dmnService.evaluate(input);
    }
}
