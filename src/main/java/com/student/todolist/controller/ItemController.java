package com.student.todolist.controller;

import com.student.todolist.entity.Item;
import com.student.todolist.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ItemController {

    @Autowired
    ItemRepository itemRepository;

    @GetMapping("/items/{id}")
    public ResponseEntity<Item> getItems(@PathVariable("id") long id){
        Optional<Item> item = itemRepository.findById(id);
        if(item.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(item.get(), HttpStatus.OK);
    }

    @GetMapping("/items")
    public ResponseEntity<List<Item>> getItems(){
        List<Item> item = itemRepository.findAll();
        if(item.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("/items/completed")
    public ResponseEntity<List<Item>> getCompletedItems(){
        // .sorted((o1, o2) -> Math.toIntExact(o1.getId() - o2.getId()))
        List<Item> item = itemRepository.findAll().stream().filter(o -> o.getDateCompleted() != null).sorted(Comparator.comparing(Item::getDateCompleted)).toList();
        if(item.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Item> deleteItem(@PathVariable("id") long id){
        Optional<Item> item = itemRepository.findById(id);
        if (item.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        itemRepository.delete(item.get());
        return new ResponseEntity<>(item.get(), HttpStatus.OK);
    }
}
