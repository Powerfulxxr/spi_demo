package com.demo.spi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class RemoteOperationController {


    @RequestMapping(value="/add")
    public Object add(Integer a,Integer b){
        int result = a + b;
        System.out.println("remote add---> args[a = "+a+",b = "+b+"],result: a + b = "+result );

        return result;

    }

    @RequestMapping(value="/divide")
    public Object divide(Integer a,Integer b){
        if( b == 0){
            System.out.println("被除数不能是0");
            return 0;
        }


        BigDecimal result = new BigDecimal(a).divide(new BigDecimal(b));
        System.out.println("remote divide---> args[a = "+a+",b = "+b+"],result: a / b = "+result.toPlainString());
        return result.toPlainString();

    }

    @RequestMapping(value="/minus")
    public Object minus(Integer a,Integer b){
        int result = a - b;
        System.out.println("remote minus---> args[a = "+a+",b = "+b+"],result: a - b = "+result );
        return result;

    }

    @RequestMapping(value="/multply")
    public Object multply(Integer a,Integer b){
        int result = a * b;
        System.out.println("remote multply---> args[a = "+a+",b = "+b+"],result: a * b = "+result );
        return result;

    }
}
