package handler;

import service.LoggingService;

public class OutputHandler {
    LoggingService logger;

    public OutputHandler(LoggingService logger) {
        this.logger = logger;
    }

    public void printAndLog(String string) {
        System.out.println(string);
        logger.log(string);
    }
}
