package com.example.crossword.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(name = "crossword")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Crossword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crossword_id")
    private Long Id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "row_count", nullable = false)
    private Integer rowCount;

    @Column(name = "column_count", nullable = false)
    private Integer columnCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty", length = 20)
    private Difficulty difficulty;

    public enum Difficulty { EASY, MEDIUM, HARD, EXPERT }

    @Column(name = "is_ai", nullable = false)
    private boolean isAi = false;

    @Column(name = "language", nullable = false, length = 20)
    private String language;

    @CreationTimestamp
    @Column(name = "created_on", nullable = false, updatable = false)
    private OffsetDateTime createdOn;

}
