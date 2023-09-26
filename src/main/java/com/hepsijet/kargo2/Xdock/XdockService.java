package com.hepsijet.kargo2.Xdock;

import com.hepsijet.kargo2.Xdock.Models.City;
import org.springframework.cache.Cache;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface XdockService {

    public String getXdockWithDistrict(String district);

    public ResponseEntity<?> gelAllXdock();

    public Cache getCacheByName(String cacheName);
    public City getZipCode(String cityName);

        public  String getXdockTransferCenterWithDistrict(String district);


}

