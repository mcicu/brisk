package ro.orange.brisk.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ro.orange.brisk.core.EnumOperator;
import ro.orange.brisk.core.IWebComponent;
import ro.orange.brisk.demo.rules.PosCodeSpec;

@Controller
@RequestMapping("/web")
public class DemoWebComponentController {

    @RequestMapping("/test")
    public ModelAndView test() {
        IWebComponent poscodespec = new PosCodeSpec("POS123", EnumOperator.EQUALS);
        return poscodespec.displayComponent();
    }
}
