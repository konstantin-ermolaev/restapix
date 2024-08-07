package com.example.restapix.controller;

import com.example.restapix.entity.Cat;
import com.example.restapix.repository.CatRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MainController {

    @Autowired
    CatRepository catRepository;

    // private final CatRepository catRepository;     то же, что и через Autowired
    private final ObjectMapper objectMapper;

    @PostMapping("/api/add")
    public void addCat(@RequestBody Cat cat){
        log.info("New row: " + catRepository.save(cat));
    }

    @SneakyThrows
    @GetMapping("/api/all")
    public String getAll(){
        List<Cat> cats = catRepository.findAll();
        return objectMapper.writeValueAsString(cats);
    }

    @SneakyThrows
    @GetMapping("/api/all1")
    public List<Cat> getAll1(){
        List<Cat> cats = catRepository.findAll();
        return cats;
    }

    @GetMapping("/api/cat")
    public Cat getCat(@RequestParam int id){
        return catRepository.findById(id).orElseThrow();
    }

    @DeleteMapping("/api")
    public void deleteCat(@RequestParam int id){
        catRepository.deleteById(id);
    }

    @PutMapping("/api")
    public String updateCat(@RequestBody Cat cat){
        if(!catRepository.existsById(cat.getId())) {
            return "No cat with such id";
        }
        return catRepository.save(cat).toString();
    }
}
