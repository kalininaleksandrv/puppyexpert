package com.eyeslessdev.needmypuppyapi.entity;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BreedRequestFactory {

    public BreedRequest getBreedReqwest (Map<String, Integer> brpselect, Map<String, Integer> brpconstraint, Map<String, String> brpexterier){

        return new BreedRequest(
                brpselect.get("time"),
                brpselect.get("exp"),
                brpselect.get("age"),
                brpselect.get("athlet"),
                brpselect.get("cyno"),
                brpselect.get("walk"),
                brpselect.get("fam"),
                brpselect.get("grum"),
                brpconstraint.get("foragility"),
                brpconstraint.get("forchild"),
                brpconstraint.get("forcompany"),
                brpconstraint.get("forguardter"),
                brpconstraint.get("forhunt"),
                brpconstraint.get("forobidience"),
                brpconstraint.get("forruning"),
                brpconstraint.get("forzks"),
                brpexterier.get("hairsize"),
                brpexterier.get("blackorwhite"),
                brpconstraint.get("sizeconstraintmin"),
                brpconstraint.get("sizeconstraintmax"),
                brpexterier.get("rare"));

    }
}
