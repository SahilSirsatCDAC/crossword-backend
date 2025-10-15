package com.example.crossword.repository;

import com.example.crossword.model.CrosswordWord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CrosswordWordRepository extends JpaRepository <CrosswordWord, Long> {
    List<CrosswordWord> findByCrosswordId(Long id);
}
