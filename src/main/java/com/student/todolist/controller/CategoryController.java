package com.student.todolist.controller;

import com.student.todolist.entity.Category;
import com.student.todolist.entity.CategoryInfo;
import com.student.todolist.entity.Item;
import com.student.todolist.repository.CategoryRepository;
import com.student.todolist.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CategoryController {

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryInfo>> getCategoryItems(){
        List<Category> categories = categoryRepository.findAll();

        if (categories.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<CategoryInfo> categoryInfos = new ArrayList<>();

        for (Category category: categories) {
            CategoryInfo categoryInfo = new CategoryInfo(category.getId(), category.getName(), category.getIcon(), category.getItems().size());
            categoryInfos.add(categoryInfo);
        }

        return new ResponseEntity<>(categoryInfos, HttpStatus.OK);
    }

    @GetMapping("/categories/{id}/items")
    public ResponseEntity<List<Item>> getCategoryItems(@PathVariable("id") Long id){
        Optional<Category> optCategory = categoryRepository.findById(id);

        if (optCategory.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Category category = optCategory.get();
        List<Item> items = category.getItems().stream().toList();


        //sort items
        if (category.getName().equals("Completed")){
            //sorted by completed date
            items = items.stream().filter(o -> o.getDateCompleted() != null).sorted(Comparator.comparing(Item::getDateCompleted)).toList();
        }else {
            //sorted by item id
            items = items.stream()
                .sorted(Comparator.comparingLong(Item::getId)).toList();
        }

        return new ResponseEntity<>(items, HttpStatus.CREATED);
    }

    @PostMapping("/categories/{id}/items")
    public ResponseEntity<Category> createCategoryItem(@PathVariable("id") Long id, @RequestBody Item item){
        Optional<Category> optCategory = categoryRepository.findById(id);

        if (optCategory.isEmpty()){
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Category category = optCategory.get();
        itemRepository.save(new Item(item.getDescription(), category));
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @PutMapping("/categories/{id}/items")
    public ResponseEntity<Category> updateCategoryItem(@PathVariable("id") Long id, @RequestBody Item item){

        Optional<Category> optCategory = categoryRepository.findById(id);
        Optional<Item> oldItem = itemRepository.findById(item.getId());

        if (optCategory.isEmpty() || oldItem.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Category category = optCategory.get();
        Item updatedItem = oldItem.get();

        //Update old Item
        updatedItem.setCategory(category);

        if (item.getTitle() != null){
            updatedItem.setTitle(item.getTitle());
        }
        if (item.getDescription() != null){
            updatedItem.setDescription(item.getDescription());
        }
        if (item.getDateDue() != null){
            updatedItem.setDateDue(item.getDateDue());
        }
        if (item.getDateCompleted() != null){
            updatedItem.setDateCompleted(item.getDateCompleted());
        }

        //Save updated item in database
        itemRepository.save(updatedItem);

        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @DeleteMapping("/categories/{id}/items")
    public ResponseEntity<List<Item>> deleteAllItemsFromCategory(@PathVariable("id") Long id){

        Optional<Category> optCategory = categoryRepository.findById(id);

        if (optCategory.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Category category = optCategory.get();

        List<Item> items = itemRepository.findAllByCategoryId(category.getId());

        itemRepository.deleteAll(items);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
}
