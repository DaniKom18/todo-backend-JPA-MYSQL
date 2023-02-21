package com.student.todolist.repository;

import com.student.todolist.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByCategoryId(Long id);
}
