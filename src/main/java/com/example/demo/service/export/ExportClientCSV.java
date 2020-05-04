package com.example.demo.service.export;

import com.example.demo.service.ArticleService;
import com.example.demo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.PrintWriter;

public class ExportClientCSV {

    @Autowired
    private ClientService clientService;

    public void exportAll(PrintWriter writer) {

        //génération d'un fichier CSV exemples avec 2 colonnes et 4 lignes
        writer.println("Libelle;Prix");
        writer.println("a1;p1");
        writer.println("a2;p2");
        writer.println("a3;p3");

        // TODO remplacer par les vrais articles de la base de données, tips : rechercher tous les articles : articleService.findAll();
    }

}
