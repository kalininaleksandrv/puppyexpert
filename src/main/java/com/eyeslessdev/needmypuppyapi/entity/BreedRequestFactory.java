package com.eyeslessdev.needmypuppyapi.entity;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BreedRequestFactory {

    public BreedRequest getBreedRequest (Map<String, String> allparam){

        BreedRequest.Builder mybuilder = new BreedRequest.Builder();

        for (Map.Entry<String, String> item : allparam.entrySet()) {
            //check if created map contains fetched value
            for (String compareditem : getSelectorArray()) {
                if (item.getKey().contains(compareditem)) {

                    switch (item.getKey()) {
                        case ("time"):
                            mybuilder.time(Integer.parseInt(item.getValue()));
                            break;
                        case ("exp"):
                            mybuilder.exp(Integer.parseInt(item.getValue()));
                            break;
                        case ("age"):
                            mybuilder.age(Integer.parseInt(item.getValue()));
                            break;
                        case ("athlet"):
                            mybuilder.athlet(Integer.parseInt(item.getValue()));
                            break;
                        case ("cyno"):
                            mybuilder.cynologist(Integer.parseInt(item.getValue()));
                            break;
                        case ("fam"):
                            mybuilder.family(Integer.parseInt(item.getValue()));
                            break;
                        case ("foragility"):
                            mybuilder.foragility(Integer.parseInt(item.getValue()));
                            break;
                        case ("forchild"):
                            mybuilder.forchild(Integer.parseInt(item.getValue()));
                            break;
                        case ("forcompany"):
                            mybuilder.forcompany(Integer.parseInt(item.getValue()));
                            break;
                        case ("forguardter"):
                            mybuilder.forguardter(Integer.parseInt(item.getValue()));
                            break;
                        case ("forhunt"):
                            mybuilder.forhunt(Integer.parseInt(item.getValue()));
                            break;
                        case ("forobidience"):
                            mybuilder.forobidience(Integer.parseInt(item.getValue()));
                            break;
                        case ("forruning"):
                            mybuilder.forruning(Integer.parseInt(item.getValue()));
                            break;
                        case ("forzks"):
                            mybuilder.forzks(Integer.parseInt(item.getValue()));
                            break;
                        case ("sizeconstraintmin"):
                            mybuilder.sizeconstraintmin(Integer.parseInt(item.getValue()));
                            break;
                        case ("sizeconstraintmax"):
                            mybuilder.sizeconstraintmax(Integer.parseInt(item.getValue()));
                            break;
                    }

                } else {
                    switch (item.getKey()) {
                        case ("hairsize"):
                            mybuilder.hairsize(item.getValue());
                            break;
                        case ("blackorwhite"):
                            mybuilder.blackorwhite(item.getValue());
                            break;
                        case ("rare"):
                            mybuilder.rare(item.getValue());
                            break;
                    }
                }
            }
        }

        return mybuilder.build();
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
