package com.example.crossword.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table (
        name = "crossword_word",
        uniqueConstraints = @UniqueConstraint(columnNames = {"crossword_id", "start_row", "start_column", "direction"}),
        indexes = {@Index(name = "idx_crossword_word_crossword_id", columnList = "crossword_id")}
)
public class CrosswordWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "word_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crossword_id", nullable = false)
    private Crossword crossword;

    @Column(name = "start_row")
    private Integer startRow;

    @Column(name = "start_column")
    private Integer startColumn;

    @Column(name = "hint",columnDefinition = "TEXT")
    private String hint;

    @Enumerated(EnumType.STRING)
    private Direction direction;

    public enum Direction { ACROSS, DOWN };
}
