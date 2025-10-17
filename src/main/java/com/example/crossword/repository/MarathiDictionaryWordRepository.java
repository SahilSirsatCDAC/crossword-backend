package com.example.crossword.repository;

import com.example.crossword.model.MarathiDictionaryWord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarathiDictionaryWordRepository extends JpaRepository<MarathiDictionaryWord, Long> {
    List<MarathiDictionaryWord> findByMarathiWordContaining(String seq);
}
