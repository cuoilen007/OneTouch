package com.example.onetouchapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="product")
public class Product implements Serializable {
    @Id
    private String id;
    private String name;
    private Long price;
    private String description;
    private String linkImageFront;
    private String linkImageBack;
    private String nameImageFront;
    private String nameImageBack;

    @CreationTimestamp
    private Date createdAt;

    @CreationTimestamp
    private Date updatedAt;

    private Date deletedAt;

    @ManyToOne
    @JoinColumn(
            name = "category_id",
            referencedColumnName = "id",
            nullable = false
    )
    @JsonIgnoreProperties("products")
    private Category categoryId;

    public Product(String id, String name, Long price, String description, String linkImageFront, String linkImageBack, String nameImageFront, String nameImageBack, Category categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.linkImageFront = linkImageFront;
        this.linkImageBack = linkImageBack;
        this.nameImageFront = nameImageFront;
        this.nameImageBack = nameImageBack;
        this.categoryId = categoryId;
    }
}
