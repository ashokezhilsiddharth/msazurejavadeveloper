package com.chtrembl.petstore.pet.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Table(name = "pet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(length = 64, nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToMany
    @JoinTable(
            name = "pet_tag", // join table name
            joinColumns = @JoinColumn(name = "pet_id"), // FK to pet
            inverseJoinColumns = @JoinColumn(name = "tag_id") // FK to tag
    )
    private Set<Tag> tags = new HashSet<>();

    @Column(name = "photoURL", length = 255, nullable = false)
    private String photoURL;

    @Column(length = 64, nullable = false)
    private String status;


}
