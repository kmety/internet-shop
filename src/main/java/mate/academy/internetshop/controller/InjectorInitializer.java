package mate.academy.internetshop.controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import mate.academy.internetshop.lib.Injector;
import org.apache.log4j.Logger;

public class InjectorInitializer implements ServletContextListener {
    private static final Logger logger = Logger.getLogger(InjectorInitializer.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Injector.injectDependencies();
            logger.info("Dependencies injected");
        } catch (IllegalAccessException e) {
            logger.error("Dependencies injection failed", e);
            throw new RuntimeException(e);
        }
    }
}
