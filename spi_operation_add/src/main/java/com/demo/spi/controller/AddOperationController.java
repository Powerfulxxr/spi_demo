package com.demo.spi.controller;


import com.demo.spi.constants.Constants;
import com.demo.spi.service.AddOperationFeignService;
import com.demo.spi.util.FeignClientUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddOperationController {


    @RequestMapping(value="/{a}/{b}")
    @HystrixCommand(fallbackMethod = "addFallBack", groupKey = "addGroupKey", commandKey = "addCommandKey", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000") }, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "4") })
    public String add(@PathVariable("a") int a,@PathVariable("b") int b){

        AddOperationFeignService feignService = FeignClientUtil.getInstance().getClient(AddOperationFeignService.class, Constants.remoteUrl);

        return feignService.add(a,b).toString();
    }


}
