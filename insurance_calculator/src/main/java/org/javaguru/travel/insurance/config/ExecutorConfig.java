package org.javaguru.travel.insurance.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ExecutorConfig {

    private final int xmlExportExecutorPoolSize;

    public ExecutorConfig(@Value("${agreement.xml.exporter.job.thread.count:1}") int xmlExportExecutorPoolSize) {
        this.xmlExportExecutorPoolSize = xmlExportExecutorPoolSize;
    }

    @Bean(name = "agreementXmlExportExecutor")
    public TaskExecutor agreementXmlExportExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(xmlExportExecutorPoolSize);
        executor.setMaxPoolSize(xmlExportExecutorPoolSize);
        executor.setQueueCapacity(xmlExportExecutorPoolSize * 5);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("agreement-xml-export-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setAllowCoreThreadTimeOut(true);
        executor.initialize();
        return executor;
    }

}
