package com.example.shopapp.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@MappedSuperclass
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class AbstractModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
}
