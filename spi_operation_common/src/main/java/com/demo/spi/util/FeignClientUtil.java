package com.demo.spi.util;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

public class FeignClientUtil {

    private FeignClientUtil(){

    }

    public static FeignClientUtil getInstance(){
        return Singleston.INSTANCE.getInstance();
    }

    private static enum Singleston {
        INSTANCE;
        private FeignClientUtil feignUtil;

        private Singleston() {
            feignUtil = new FeignClientUtil();
        }

        public FeignClientUtil getInstance() {
            return feignUtil;
        }


    }


    public <T> T getClient(Class<T> apiType,String url){

        T client = Feign.builder().encoder(new JacksonEncoder()).decoder(new JacksonDecoder()).target(apiType,url);

        return client;

    }
}
