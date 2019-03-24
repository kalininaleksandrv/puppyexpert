package com.eyeslessdev.needmypuppyapi.service;

import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

@Service
public class BreedRequestParsingService {

    public BreedRequestParsingService() {
    }

    Map<String, Integer> incomeToSelectorReadyMap(Map<String, String> incomemap) {

        Map<String, Integer> selectorreadymap = new HashMap<>();

        //extract values from map
        for (Map.Entry<String, String> item : incomemap.entrySet()) {
            //check if created map contains fetched value
            for (String compareditem : getSelectorArray()) {
                if (item.getKey().contains(compareditem))
                selectorreadymap.put(item.getKey(), stringtoint(item.getValue()));
            }
        }
        return selectorreadymap;
    }

    Map<String, Integer> incomeToConstraintMap(Map<String, String> incomemap) {

        Map<String, Integer> constraintmap = new HashMap<>();

        //extract values from map
        for (Map.Entry<String, String> item : incomemap.entrySet()) {
            //check if created map contains fetched value
            for (String compareditem : getConstraintArray()) {
                if (item.getKey().contains(compareditem))
                    constraintmap.put(item.getKey(), stringtoint(item.getValue()));
            }
        }
        return constraintmap;
    }

    Map<String, String> incomeToExterierMap(Map<String, String> incomemap) {

        Map<String, String> exteriermap = new HashMap<>();

        //extract values from map
        for (Map.Entry<String, String> item : incomemap.entrySet()) {
            //check if created map contains fetched value
            for (String compareditem : getExterierArray()) {
                if (item.getKey().contains(compareditem))
                    exteriermap.put(item.getKey(), item.getValue());
            }
        }
        return exteriermap;
    }

    private String[] getSelectorArray () {

        return new String[]{"time",
                      "exp",
                      "age",
                      "athlet",
                      "cyno",
                      "walk",
                      "fam",
                      "grum" };
    }

    private String[] getConstraintArray () {

        return new String[]{"foragility",
                      "forchild",
                      "forcompany",
                      "forguardter",
                      "forhunt",
                      "forobidience",
                      "forruning",
                      "forzks",
                      "sizeconstraintmin",
                      "sizeconstraintmax" };
    }

    private String[] getExterierArray () {

        return new String[]{"hairsize",
                      "blackorwhite",
                      "rare" };
    }


    private Integer stringtoint(String value) {
        return Integer.parseInt(value);
    }
}