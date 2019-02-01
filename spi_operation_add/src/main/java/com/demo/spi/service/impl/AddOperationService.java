package com.demo.spi.service.impl;

import com.demo.spi.command.AddOperationHystrixCommand;
import com.demo.spi.service.OperationService;

public class AddOperationService implements OperationService {

    @Override
    public Object operation(int a, int b) {
        AddOperationHystrixCommand command = new AddOperationHystrixCommand(a,b);
        Object result = command.execute();
        System.out.println("add---> args[a = "+a+",b = "+b+"],result: a + b = "+result );
        return result;
    }


}
