package com.demo.spi.controller;

import com.demo.spi.service.OperationService;
import com.demo.spi.util.ServiceUtil;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OperationController {


    @RequestMapping(value="/{type}/{a}/{b}")
    public String operation(@PathVariable("type") String type,@PathVariable("a") int a,@PathVariable("b") int b){

        ServiceUtil.getInstance().initCache();

        OperationService operationService = ServiceUtil.getInstance().getService(type);

        return operationService.operation(a,b).toString();

    }
}
