package com.demo.spi.command;

import com.demo.spi.constants.Constants;
import com.demo.spi.service.AddOperationFeignService;
import com.demo.spi.util.FeignClientUtil;
import com.netflix.hystrix.*;

public class AddOperationHystrixCommand extends HystrixCommand<Object> {

    private Integer a;

    private Integer b;

    public AddOperationHystrixCommand(Integer a,Integer b) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("addOperationGroupKey"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(6000))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("addOperationThreadPoolKey")).
                        andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(4)));
        this.a = a;
        this.b = b;

    }



    @Override
    protected Object run() throws Exception {
        AddOperationFeignService feignService = FeignClientUtil.getInstance().getClient(AddOperationFeignService.class, Constants.remoteUrl);
        Object result = feignService.add(a,b);
        return result;
    }


    @Override
    protected Object getFallback() {
        System.out.println("-----进入add回退----args：[a = "+a+",b = "+b+"]");
        return "999";
    }
}
