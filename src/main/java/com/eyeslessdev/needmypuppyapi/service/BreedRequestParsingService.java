package com.eyeslessdev.needmypuppyapi.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BreedRequestParsingService {

    Map<String, String> constraintmap;

    public BreedRequestParsingService() {
    }

    Map<String, Integer> incomeToSelectorReadyMap(Map<String, String> incomemap) {

        Map<String, Integer> selectorreadymap = getSelectoRreadyMapByDefault();

        //extract values from map
        for (Map.Entry<String, String> item : incomemap.entrySet()) {
            for (Map.Entry<String, Integer> compareditem : selectorreadymap.entrySet()) {
                if (item.getKey().contains(compareditem.getKey()))
                selectorreadymap.put(item.getKey(), stringtoint(item.getValue()));
            }
        }
        return selectorreadymap;
    }

    private Map<String, Integer> getSelectoRreadyMapByDefault() {

        Map<String,Integer> mymap = new HashMap<>();
        mymap.put("time",1);
        mymap.put("exp", 1);
        mymap.put("age", 1);
        mymap.put("athlet", 1);
        mymap.put("cyno", 1);
        mymap.put("walk", 1);
        mymap.put("fam", 1);
        mymap.put("grum", 1);

        return mymap;
    }

    private Integer stringtoint(String value) {
        return Integer.parseInt(value);
    }
}