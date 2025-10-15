package com.example.crossword.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.EnumMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(
        name = "crossword_cell",
        uniqueConstraints = @UniqueConstraint(columnNames = {"crossword_id", "row_number", "column_number"}),
        indexes = @Index(name = "idx_crossword_cell_crossword_id", columnList = "crossword_id")
)
public class CrosswordCell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cell_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crossword_id", nullable = false)
    private Crossword crossword;

    //What kind of relationship is this???? Many-to-two?
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "word_id", nullable = false)
//    private CrosswordWord crosswordWord;

    @Column(name = "row_num", nullable = false)
    private Integer rowNum;

    @Column(name = "column_num", nullable = false)
    private Integer columnNum;

    @Column(name = "letter", length = 8)
    private String letter;

    @Column(name = "is_blocked", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean isBlocked = false;


}
