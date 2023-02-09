package com.student.todolist.repository;

import com.student.todolist.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public interface ItemRepository extends JpaRepository<Item, Long> {
}
