package com.eyeslessdev.needmypuppyapi.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BreedRequestParsingService {

    public BreedRequestParsingService() {
    }

    Map<String, Integer> incomeToSelectorReadyMap(Map<String, String> incomemap) {

        Map<String, Integer> selectorreadymap = getSelectoRreadyMapByDefault();

        //extract values from map
        for (Map.Entry<String, String> item : incomemap.entrySet()) {
            //check if created map contains fetched value
            for (Map.Entry<String, Integer> compareditem : selectorreadymap.entrySet()) {
                if (item.getKey().contains(compareditem.getKey()))
                selectorreadymap.put(item.getKey(), stringtoint(item.getValue()));
            }
        }
        return selectorreadymap;
    }

    Map<String, Integer> incomeToConstraintMap(Map<String, String> incomemap) {

        Map<String, Integer> constraintmap = getConstraintMapByDefault();

        //extract values from map
        for (Map.Entry<String, String> item : incomemap.entrySet()) {
            //check if created map contains fetched value
            for (Map.Entry<String, Integer> compareditem : constraintmap.entrySet()) {
                if (item.getKey().contains(compareditem.getKey()))
                    constraintmap.put(item.getKey(), stringtoint(item.getValue()));
            }
        }
        return constraintmap;
    }

    Map<String, String> incomeToExterierMap(Map<String, String> incomemap) {

        Map<String, String> exteriermap = getExterierMapByDefault();

        //extract values from map
        for (Map.Entry<String, String> item : incomemap.entrySet()) {
            //check if created map contains fetched value
            for (Map.Entry<String, String> compareditem : exteriermap.entrySet()) {
                if (item.getKey().contains(compareditem.getKey()))
                    exteriermap.put(item.getKey(), item.getValue());
            }
        }
        return exteriermap;
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

    private Map<String, Integer> getConstraintMapByDefault() {

        Map<String,Integer> mymap = new HashMap<>();
        mymap.put("foragility",0);
        mymap.put("forchild", 0);
        mymap.put("forcompany", 0);
        mymap.put("forguardter", 0);
        mymap.put("forhunt", 0);
        mymap.put("forobidience", 0);
        mymap.put("forruning", 0);
        mymap.put("forzks", 0);
        mymap.put("sizeconstraintmin", 0);
        mymap.put("sizeconstraintmax", 5);

        return mymap;
    }

    private Map<String, String> getExterierMapByDefault() {

        Map<String,String> mymap = new HashMap<>();
        mymap.put("hairsize", "");
        mymap.put("blackorwhite", "");
        mymap.put("rare", "");

        return mymap;
    }

    private Integer stringtoint(String value) {
        return Integer.parseInt(value);
    }
}