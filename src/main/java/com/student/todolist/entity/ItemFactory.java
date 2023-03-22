package com.student.todolist.entity;

public class ItemFactory {
        public static Item createItem(String description, Category category){
            return new Item(description, category);
        }

}
