package com.example.crossword.repository;

import com.example.crossword.model.Crossword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrosswordRepository extends JpaRepository <Crossword, Long> {
}
