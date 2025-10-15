package com.example.crossword.repository;

import com.example.crossword.model.CrosswordCell;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CrosswordCellRepository extends JpaRepository <CrosswordCell, Long> {

    List<CrosswordCell> findByCrosswordIdOrderByRowNumAscColumnNumAsc(Long crosswordId);
}
