package io.siliconsavannah.backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Internal;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String title;
    String description;
    String content;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
    Long parentId;
    @CreationTimestamp
    LocalDateTime createdAt;
    @UpdateTimestamp
    LocalDateTime updatedAt;

}
