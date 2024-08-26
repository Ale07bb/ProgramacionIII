package logs;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogSimple {
    private static final Logger logger = LogManager.getRootLogger();
    public static void main(String[] args) {
        logger.debug("Este no lo veo si la configuracion no dice desde debug");
        logger.info("Este no lo veo si la configuracion no dice desde info");
        logger.warn("Este no lo veo si la configuracion no dice desde warn");
        logger.error("Este no lo veo si la configuracion no dice desde error");
    }
}





