package com.student.todolist.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryInfo {
    private Long id;
    private String name;
    private String icon;
    private int itemCount;


    public CategoryInfo(Long id, String name, String icon, int itemCount) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.itemCount = itemCount;
    }
}
