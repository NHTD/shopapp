package com.example.shopapp.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "categories")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category extends AbstractModel {
    @Column(name = "name", nullable = false)
    String name;
}
