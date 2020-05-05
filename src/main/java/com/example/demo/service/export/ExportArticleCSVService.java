package com.example.demo.service.export;

import com.example.demo.entity.Article;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.List;

@Service
public class ExportArticleCSVService {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleRepository articleRepository;


    public void exportAll(PrintWriter writer) {

        List<Article> allArticles = articleRepository.findAll();
        for (Article article : allArticles) {
            writer.println(article.getLibelle() + ";" + article.getPrix());

        }


        // TODO remplacer par les vrais articles de la base de données, tips : rechercher tous les articles : articleService.findAll();
    }

}
