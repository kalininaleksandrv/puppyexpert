package com.eyeslessdev.needmypuppyapi.controller;

import com.eyeslessdev.needmypuppyapi.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("pet")
public class PetController {

    private int counter = 4;

    private List<Map<String, String>> pets = new ArrayList<Map<String, String>>(){{
        add(new HashMap<String, String>() {{put("id", "1"); put ("title", "Лабрадор"); }});
        add(new HashMap<String, String>() {{put("id", "2"); put ("title", "Хаски"); }});
        add(new HashMap<String, String>() {{put("id", "3"); put ("title", "Ретривер"); }});
    }};

    @GetMapping
    public List<Map<String, String>> list (){
        return pets;
    }

    @GetMapping("{id}")
    private Map<String, String> getPetById(@PathVariable String id){
        return getId(id);
    }

    @PostMapping
    public Map<String,String> createNewPet (@RequestBody Map<String, String> pet){

        pet.put("id", String.valueOf(counter++));
        pets.add(pet);
        return pet;
    }

    @PutMapping("{id}")
    public Map<String, String> updatePetById(@PathVariable String id, @RequestBody Map<String, String> pet){

        Map<String, String> petFromDb = getId(id);
        petFromDb.putAll(pet);
        petFromDb.put("id", id);

        return petFromDb;
    }

    @DeleteMapping ("{id}")
    public void deleatPetById (@PathVariable String id){
        pets.remove(getPetById(id));
    }

    private Map<String, String> getId(@PathVariable String id) {
        return pets.stream()
                .filter(pet -> pet.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }
}
