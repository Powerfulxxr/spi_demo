package com.demo.spi.service.impl;

import com.demo.spi.service.OperationService;

import java.math.BigDecimal;

public class DivideOperationService implements OperationService {
    @Override
    public Object operation(int a, int b) {
        if( b == 0){
            System.out.println("被除数不能是0");
            return 0;
        }


        BigDecimal result = new BigDecimal(a).divide(new BigDecimal(b));
        System.out.println("divide---> args[a = "+a+",b = "+b+"],result: a / b = "+result.toPlainString());
        return result.toPlainString();
    }
}
