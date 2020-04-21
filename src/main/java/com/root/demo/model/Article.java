package com.root.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@Table(name = "Article")
class Article extends TimeStampModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    @NotNull
    @Getter @Setter @Accessors(chain = true)
    private String name;

    @Column(name = "content")
    @Getter @Setter @Accessors(chain = true)
    private String content;

    @ManyToOne(
        cascade = CascadeType.REMOVE)
    @JoinColumn(name = "article_owner")
    private User user;

    @ManyToOne(
        cascade = CascadeType.REMOVE
    )
    @JoinColumn(name = "article_category")
    private Category category;
}
