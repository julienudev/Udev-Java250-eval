package com.example.demo.service;

import com.example.demo.dto.ArticleDto;
import com.example.demo.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Service contenant les actions métiers liées aux articles.
 */
@Service
@Transactional
public class ArticleService {

    private ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<ArticleDto> findAll() {
        // Transformation d'une liste de Article en ArticleDto.
        return articleRepository
                .findAll()
                .stream()
                .map(a -> new ArticleDto(a.getId(), a.getLibelle(), a.getPrix()))
                .collect(toList());
    }

}
