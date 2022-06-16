package net.juhn.roomworker.metrics;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import net.juhn.roomservice.service.ServiceRoom;

@Component
public class MetricsCollector{

	private Map<Integer, Counter> metricCounters = null;
    private MeterRegistry meterRegistry;
    
    
    ServiceRoom serviceRoom;

    @Autowired
    public MetricsCollector(MeterRegistry meterRegistry) {
    	ApplicationContext ctx = new ClassPathXmlApplicationContext("service-context.xml");
        serviceRoom = ctx.getBean(ServiceRoom.class);
        
        this.meterRegistry = meterRegistry;
        metricCounters = new HashMap<Integer, Counter>();
        Gauge.builder("roomworker.deleteddatarecords", new MetricDeletedDataRecords()).register(meterRegistry);
        Gauge.builder("roomworker.lastcleaning", new MetricLastCleaning()).register(meterRegistry);
    }


}
