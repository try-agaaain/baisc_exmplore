package com.example.redissondemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Autowired
    private CacheService cacheService;

    @GetMapping("/set/{key}/{value}")
    public String setCache(@PathVariable String key, @PathVariable String value) {
        cacheService.setCacheValue(key, value);
        return "Value set";
    }

    @GetMapping("/get/{key}")
    public String getCache(@PathVariable String key) {
        return cacheService.getCacheValue(key);
    }
}