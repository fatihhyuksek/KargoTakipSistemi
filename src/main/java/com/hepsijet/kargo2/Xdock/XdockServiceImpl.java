package com.hepsijet.kargo2.Xdock;

import com.hepsijet.kargo2.Xdock.Models.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class XdockServiceImpl implements XdockService {

    @Autowired
    CacheManager cacheManager;

    @Autowired
    CacheManager cacheManager2;

    @Autowired
    private XdockRepo xdockRepo;

    public Map<String,City> cityZipCodeMap;

    XdockServiceImpl(){
        cityZipCodeMap=new HashMap<>();
        cityZipCodeMap.put("İstanbul", City.builder().cityName("İstanbul").zipCode("1111111").build());
        cityZipCodeMap.put("Ankara", City.builder().cityName("Ankara").zipCode("2222222").build());
    }
    @Cacheable(value = " getZipCode city-zip-cache" )
    public City getZipCode(String cityName){
        System.out.println("Method Called");
        return cityZipCodeMap.get(cityName);
    }
    public Cache getCacheByName(String cacheName){
        return cacheManager.getCache(cacheName);
    }
    @Cacheable(value = "xdock")
    public ResponseEntity<?> gelAllXdock(){
        System.out.println("get all xdock Method Called");
        return ResponseEntity.ok(xdockRepo.findAll());
    }



    public String getXdockWithDistrict(String district){
        return xdockRepo.getXdockWithDistrict(district);

    }

    public  String getXdockTransferCenterWithDistrict(String district){
        System.out.println("Getting book by DISTRICT" + district + "(from the method)");
        return xdockRepo.getXdockTransferCenterWithDistrict(district);
    }


}
