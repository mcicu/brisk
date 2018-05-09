package ro.orange.brisk.core;

import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

public class WebConjunctiveSpec<T> extends ConjunctiveSpec<T> implements IWebComponent {

    @Override
    public String getComponentId() {
        return null;
    }

    @Override
    public String getComponentName() {
        return null;
    }

    @Override
    public ModelAndView displayComponent() {
        ModelAndView mav = new ModelAndView("fragmented");
        List<String> fragments = new ArrayList<>();
        for (ISpec<T> spec : this.specs) {
            if (spec instanceof IWebComponent) {
                fragments.add(((IWebComponent) spec).displayComponent().getViewName());
            }
        }

        mav.addObject("fragments", fragments);
        return mav;
    }
}
