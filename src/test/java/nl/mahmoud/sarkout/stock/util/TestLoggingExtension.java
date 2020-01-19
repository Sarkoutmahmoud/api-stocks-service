package nl.mahmoud.sarkout.stock.util;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TestLoggingExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {
    private final AppenderStub appenderStub = new AppenderStub();
    private final Logger logger;

    private TestLoggingExtension(Class loggerClass) {
        this.logger = (Logger) LoggerFactory.getLogger(loggerClass);
        this.logger.detachAndStopAllAppenders();
    }

    public static TestLoggingExtension create(Class loggerClass) {
        return new TestLoggingExtension(loggerClass);
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        appendAppender();
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        detachAppender();
    }


    public void assertContains(String expected, Level level) {
        for (val event : appenderStub.getEvents()) {
            if (event.getFormattedMessage().contains(expected)) {
                if (level.equals(event.getLevel())) {
                    Assertions.assertTrue(true);
                    return;
                }
            }
        }
        Assertions.fail(String.format("logging did not contain '%s'. But contained '%s'", expected, appenderStub.getEvents()));
    }


    private void appendAppender() {
        appenderStub.start();
        logger.addAppender(appenderStub);
    }

    private void detachAppender() {
        logger.detachAndStopAllAppenders();
    }

    private static final class AppenderStub extends AppenderBase<ILoggingEvent> {
        private final List<ILoggingEvent> events = new ArrayList<>();

        @Override
        protected void append(ILoggingEvent eventObject) {
            events.add(eventObject);
        }

        List<ILoggingEvent> getEvents() {
            return events;
        }
    }
}

