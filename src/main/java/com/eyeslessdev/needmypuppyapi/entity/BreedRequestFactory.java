package com.eyeslessdev.needmypuppyapi.entity;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class BreedRequestFactory {

    public BreedRequest getBreedRequest (Map<String, String> allparam){


        Map<String, Integer> outcomemap = new HashMap<>();

        Map<String, String> outcomemapstr = new HashMap<>();

        for (Map.Entry<String, String> item : allparam.entrySet()) {
            //check if created map contains fetched value
            for (String compareditem : getSelectorArray()) {
                if (item.getKey().contains(compareditem)) {
                    outcomemap.put(item.getKey(), Integer.parseInt(item.getValue()));
                } else outcomemapstr.put(item.getKey(), item.getValue());
            }
        }

        return new BreedRequest(
                outcomemap.get("time"),
                outcomemap.get("exp"),
                outcomemap.get("age"),
                outcomemap.get("athlet"),
                outcomemap.get("cyno"),
                outcomemap.get("walk"),
                outcomemap.get("fam"),
                outcomemap.get("grum"),
                outcomemap.get("foragility"),
                outcomemap.get("forchild"),
                outcomemap.get("forcompany"),
                outcomemap.get("forguardter"),
                outcomemap.get("forhunt"),
                outcomemap.get("forobidience"),
                outcomemap.get("forruning"),
                outcomemap.get("forzks"),
                outcomemapstr.get("hairsize"),
                outcomemapstr.get("blackorwhite"),
                outcomemap.get("sizeconstraintmin"),
                outcomemap.get("sizeconstraintmax"),
                outcomemapstr.get("rare"));

    }



    private String[] getSelectorArray () {

        return new String[]{"time",
                "exp",
                "age",
                "athlet",
                "cyno",
                "walk",
                "fam",
                "grum",
                "foragility",
                "forchild",
                "forcompany",
                "forguardter",
                "forhunt",
                "forobidience",
                "forruning",
                "forzks",
                "sizeconstraintmin",
                "sizeconstraintmax"
        };
    }
}
