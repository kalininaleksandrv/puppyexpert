package com.eyeslessdev.needmypuppyapi.entity;

import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.eyeslessdev.needmypuppyapi.entity.BreedRequest.builder;

@Component
public class BreedRequestFactory {

    public BreedRequest getBreedRequest (Map<String, String> allparam){


        Map<String, Optional<Integer>> outcomemap = new HashMap<>();

        Map<String, Optional<String>> outcomemapstr = new HashMap<>();

        for (Map.Entry<String, String> item : allparam.entrySet()) {
            //check if created map contains fetched value
            for (String compareditem : getSelectorArray()) {
                if (item.getKey().contains(compareditem)) {
                    outcomemap.put(item.getKey(), Optional.of(Integer.parseInt(item.getValue())));
                } else {
                    outcomemapstr.put(item.getKey(), Optional.of(item.getValue()));
                }
            }
        }

        return builder()
                .time(Optional.ofNullable(outcomemap.get("time")).orElse(Optional.of(1)).get())
                .exp(Optional.ofNullable(outcomemap.get("exp")).orElse(Optional.of(1)).get())
                .age(Optional.ofNullable(outcomemap.get("age")).orElse(Optional.of(1)).get())
                .athlet(Optional.ofNullable(outcomemap.get("athlet")).orElse(Optional.of(2)).get())
                .cynologist(Optional.ofNullable(outcomemap.get("cyno")).orElse(Optional.of(1)).get())
                .walk(Optional.ofNullable(outcomemap.get("walk")).orElse(Optional.of(1)).get())
                .family(Optional.ofNullable(outcomemap.get("cyno")).orElse(Optional.of(1)).get())
                .grummer(Optional.ofNullable(outcomemap.get("fam")).orElse(Optional.of(1)).get())
                .foragility(Optional.ofNullable(outcomemap.get("foragility")).orElse(Optional.of(0)).get())
                .forchild(Optional.ofNullable(outcomemap.get("forchild")).orElse(Optional.of(0)).get())
                .forcompany(Optional.ofNullable(outcomemap.get("forcompany")).orElse(Optional.of(1)).get())
                .forguardter(Optional.ofNullable(outcomemap.get("forguardter")).orElse(Optional.of(1)).get())
                .forhunt(Optional.ofNullable(outcomemap.get("forhunt")).orElse(Optional.of(0)).get())
                .forobidience(Optional.ofNullable(outcomemap.get("forobidience")).orElse(Optional.of(0)).get())
                .forruning(Optional.ofNullable(outcomemap.get("forruning")).orElse(Optional.of(0)).get())
                .forzks(Optional.ofNullable(outcomemap.get("forzks")).orElse(Optional.of(1)).get())
                .hairsize(Optional.ofNullable(outcomemapstr.get("hairsize")).orElse(Optional.of("any")).get())
                .blackorwhite(Optional.ofNullable(outcomemapstr.get("blackorwhite")).orElse(Optional.of("any")).get())
                .sizeconstraintmin(Optional.ofNullable(outcomemap.get("sizeconstraintmin")).orElse(Optional.of(0)).get())
                .sizeconstraintmax(Optional.ofNullable(outcomemap.get("sizeconstraintmax")).orElse(Optional.of(5)).get())
                .rare(Optional.ofNullable(outcomemapstr.get("rare")).orElse(Optional.of("yes")).get())
                .build();
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
