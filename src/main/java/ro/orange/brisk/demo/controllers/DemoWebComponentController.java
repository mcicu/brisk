package ro.orange.brisk.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ro.orange.brisk.core.EnumOperator;
import ro.orange.brisk.core.WebConjunctiveSpec;
import ro.orange.brisk.demo.beans.Case;
import ro.orange.brisk.demo.infrastructure.SpecRepository;
import ro.orange.brisk.demo.rules.PosCodeSpec;

@Controller
@RequestMapping("/web")
public class DemoWebComponentController {

    @Autowired
    SpecRepository specRepository;

    @RequestMapping("/test")
    public ModelAndView test() {
        //PosCodeSpec posCodeSpec = new PosCodeSpec("POS123", EnumOperator.EQUALS);
        PosCodeSpec poscodespec = (PosCodeSpec) specRepository.findOne("f58c318f-e601-478d-9e06-795f6fe93d7b");
        return poscodespec.displayComponent();
    }

    @RequestMapping("/fragmented")
    public ModelAndView fragment() {
        PosCodeSpec spec1 = new PosCodeSpec("POS124", EnumOperator.NOT_EQUALS);
        PosCodeSpec spec2 = new PosCodeSpec("POS123", EnumOperator.EQUALS);
        PosCodeSpec spec3 = new PosCodeSpec("POS123", EnumOperator.EQUALS);
        WebConjunctiveSpec<Case> webConjunctiveSpec = new WebConjunctiveSpec<>();
        webConjunctiveSpec.add(spec1);
        webConjunctiveSpec.add(spec2);
        webConjunctiveSpec.add(spec3);

        return webConjunctiveSpec.displayComponent();
    }
}
