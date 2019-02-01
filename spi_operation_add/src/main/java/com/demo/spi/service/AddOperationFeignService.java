package com.demo.spi.service;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface AddOperationFeignService {


    @RequestLine("POST /add?a={a}&b={b}")
    @Headers("Content-Type: application/json")
    public Object add(@Param("a") int a, @Param("b") int b);


}
