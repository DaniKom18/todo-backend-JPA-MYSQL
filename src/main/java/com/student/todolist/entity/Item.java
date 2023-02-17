package com.student.todolist.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="item")
@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "completed")
    private boolean completed;
    @Column(name = "date_created")
    private Date dateCreated;
    @Column(name = "date_due")
    private Date dateDue;
    @Column(name = "date_completed")
    private Date dateCompleted;

    public Item(String description, Category category) {
        this.description = description;
        this.category = category;
        this.dateCreated = new Date();
    }

    public Item() {
    }
}
