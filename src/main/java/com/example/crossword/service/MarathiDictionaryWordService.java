package com.example.crossword.service;

import com.example.crossword.model.MarathiDictionaryWord;
import com.example.crossword.repository.MarathiDictionaryWordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.crossword.utils.StringUtils.normalize;

@Service
@RequiredArgsConstructor
public class MarathiDictionaryWordService {
    private final MarathiDictionaryWordRepository repo;

    @Transactional(readOnly = true)
    public List<MarathiDictionaryWord> searchWordsByStringSequence(String seq) {
        String normalizedSeq = normalize(seq);
        return repo.findByMarathiWordContaining(normalizedSeq);
    }
}
