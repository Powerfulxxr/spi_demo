package com.demo.spi.util;

import com.demo.spi.service.OperationService;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class ServiceUtil {

    private Map<String ,OperationService> serviceMap = new ConcurrentHashMap<>();


    private ServiceUtil(){

    }

    public static  ServiceUtil getInstance(){
        return Singleston.INSTANCE.getInstance();
    }

    private static enum Singleston {
        INSTANCE;
        private ServiceUtil serviceUtil;

        private Singleston() {
            serviceUtil = new ServiceUtil();
        }

        public ServiceUtil getInstance() {
            return serviceUtil;
        }


    }

    private LoadingCache<String,Map<String,OperationService>> caffeineCache = Caffeine.newBuilder()
            .expireAfterAccess(1, TimeUnit.DAYS)
            .build(new CacheLoader<String,Map<String,OperationService>>() {

                @Override
                public Map<String, OperationService> load(String s) throws Exception {
                    System.out.println("加载服务缓存...");
                    ServiceLoader<OperationService> services = ServiceLoader.load(OperationService.class);
                    Iterator<OperationService> iterator = services.iterator();
                    while (iterator.hasNext()){
                        OperationService operationService = iterator.next();
                        String clzName = operationService.getClass().getSimpleName();
                        System.out.println(clzName);
                        String key = StringUtils.substring(clzName ,0,StringUtils.lastIndexOf(clzName,OperationService.class.getSimpleName()));
                        if(StringUtils.isNotBlank(key)){
                            key = key.toLowerCase();
                        }
                        System.out.println("key:"+key);

                        serviceMap.put(key,operationService);


                    }
                    return serviceMap;
                };
            });


    public void initCache(){
          this.caffeineCache.get("serviceMap");
    }


    public void refreshCache(){
        this.caffeineCache.refresh("serviceMap");
    }

    public Map<String, OperationService> getServiceMap() {
        return serviceMap;
    }


    public OperationService getService(String key) {
        if (StringUtils.isBlank(key)) {
            throw new RuntimeException("key is null");
        }

        key = key.toLowerCase();

        OperationService operationService = serviceMap.get(key);

        if (operationService == null) {
            ServiceLoader<OperationService> services = ServiceLoader.load(OperationService.class);
            Iterator<OperationService> iterator = services.iterator();
            while (iterator.hasNext()) {
                operationService = iterator.next();
                String clzName = operationService.getClass().getSimpleName();
                String type = StringUtils.substring(clzName, 0, StringUtils.lastIndexOf(clzName, OperationService.class.getSimpleName()));
                if (StringUtils.isNotBlank(type)) {
                    type = type.toLowerCase();
                }

                if (type.equals(key)) {
                    serviceMap.put(key, operationService);
                    break;
                }
            }

            operationService = serviceMap.get(key);
        }

        if(operationService == null){
            throw new RuntimeException("operationService is not found with key :"+key);
        }

        return operationService;

    }



    public static void main(String[] args) {
        ServiceUtil.getInstance().initCache();

        OperationService operationService = ServiceUtil.getInstance().getService("add");

        operationService.operation(10,2);


        operationService = ServiceUtil.getInstance().getService("divide");

        operationService.operation(10,2);


        operationService = ServiceUtil.getInstance().getService("minus");

        operationService.operation(10,2);


        operationService = ServiceUtil.getInstance().getService("multiply");

        operationService.operation(10,2);
    }


}
