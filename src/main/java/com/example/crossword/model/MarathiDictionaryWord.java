package com.example.crossword.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "marathi_words",
        uniqueConstraints = @UniqueConstraint(columnNames = {"marathi_word"})
)
public class MarathiDictionaryWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dictionary_word_id")
    private Long id;

    @Column(name = "marathi_word")
    private String marathiWord;

    @Column(name = "english_word")
    private String englishWord;

}
