package com.hepsijet.kargo2.Xdock;

import com.hepsijet.kargo2.Xdock.Models.City;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/xdock")
@Hidden
public class XdockController {
    private final XdockService xdockService;


    public XdockController(XdockService xdockService) {
        this.xdockService = xdockService;
    }
    @GetMapping("/city")
    public City getCityByName(@RequestParam("name") String name){
        return xdockService.getZipCode(name);
    }

    @GetMapping("/all")
    public ResponseEntity<?> gelAllXdock(){
        return xdockService.gelAllXdock();
    }

    @CacheEvict(value = "xdocks")
    @GetMapping("/evict")
    public void evictMovies() {

    }
    @GetMapping(value = "/get")
    public String getXdockWithDistrict(String district){
            return xdockService.getXdockWithDistrict(district);
    }

    @GetMapping(value = "/get/transfer_center")
    public String getXdockTransferCenterByBarcode(@RequestParam("district") String district){
        System.out.println("Getting book by DISTRICT" + district + "(from the controller)");
        return xdockService.getXdockTransferCenterWithDistrict(district);
    }

    @GetMapping("/cache")
    public Cache getCacheDetails(@RequestParam("name") String name){
        return xdockService.getCacheByName(name);
    }
}
