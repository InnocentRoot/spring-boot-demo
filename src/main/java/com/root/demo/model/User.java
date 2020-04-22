package com.root.demo.model;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "`User`", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "userName")
})
@NoArgsConstructor
@Accessors(chain = true)
public class User extends TimeStampModel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    @Column(name = "email", length = 30)
    @NotNull
    @Length(max = 30)
    @Email
    private String email;

    @Getter
    @Setter
    @Column(name = "password")
    @NonNull
    @Length(max = 255)
    private String password;

    @Getter
    @Setter
    @Column(name = "firstName", length = 30)
    @NonNull
    @Length(max = 30)
    private String firstName;

    @Getter
    @Setter
    @Column(name = "lastName", length = 30)
    @NonNull
    private String lastName;

    @Getter
    @Setter
    @Column(name = "userName", length = 30)
    @NonNull
    private String userName;

    @OneToMany(
        cascade = CascadeType.REMOVE,
        mappedBy = "user",
        orphanRemoval = true
    )
    private List<Article> articles;
}
