package ro.orange.brisk.core;

import org.springframework.web.servlet.ModelAndView;

public interface IWebComponent {

    String getComponentId();

    String getComponentName();

    ModelAndView displayComponent();
}
