package org.javaguru.travel.insurance.rest.common;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class TravelRestRequestExecutionTimeLogger {
    private static final Logger logger = LoggerFactory.getLogger(TravelRestRequestExecutionTimeLogger.class);

    public void log(Stopwatch stopwatch) {
        stopwatch.stop();
        logger.info("Request processing time (µs): {}", stopwatch.elapsed(TimeUnit.MICROSECONDS));
    }
}
