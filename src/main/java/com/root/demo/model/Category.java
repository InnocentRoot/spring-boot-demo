package com.root.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Category")
class Category extends TimeStampModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    @NotNull
    @Getter @Setter
    private String name;

    @OneToMany(
        cascade = CascadeType.REMOVE,
        mappedBy = "category",
        orphanRemoval = true
    )
    private List<Article> articles;
}
