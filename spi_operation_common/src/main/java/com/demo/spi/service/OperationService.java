package com.demo.spi.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;


public interface OperationService {
    @HystrixCommand(fallbackMethod = "operationFallBack", groupKey = "operationGroupKey", commandKey = "operationCommandKey", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000") }, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "4") })
    Object operation(int a, int b);
}
