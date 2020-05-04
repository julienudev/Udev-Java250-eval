package com.example.demo.controller.export;

import com.example.demo.entity.Article;
import com.example.demo.service.export.ExportArticleCSVService;
import com.example.demo.service.export.ExportArticleXLSXService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Controller pour réaliser l'export des articles.
 */
@Controller
@RequestMapping("export")
public class ExportArticleController {

    @Autowired
    private ExportArticleCSVService exportArticleCSV;

    @Autowired
    private ExportArticleXLSXService exportArticleXLSX;

    /**
     * Export des articles au format CSV, déclenché sur l'url http://.../export/articles/csv
     *
     * @param request  objet reprensantant la requête http
     * @param response objet reprensantant la réponse http
     */
    @GetMapping("/articles/csv")
    public void articlesCSV(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // positionne de metadata sur la réponse afin d'informer le navigateur que la réponse correspond à un fichier téléchargeable.
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"export-articles.csv\"");

        // Le writter est un objet provenant de la response dans lequel on va pouvoir écrire pour générer le contenu de l'export CSV.
        PrintWriter writer = response.getWriter();

        exportArticleCSV.exportAll(writer);

        /*exportArticleCSV.exportAll( new PrintWriter(System.out));
        FileOutputStream out = new FileOutputStream("c:/temps/temp.csv")
        exportArticleCSV.exportAll( new PrintWriter(out));*/

    }


    @GetMapping("/articles/xlsx")
    public ResponseEntity<InputStreamResource> articlesXLSX() throws IOException {
        // recupere list article
        List<Article> articles = (List<Article>) exportArticleXLSX.findAll();
        //An array of bytes that was provided by the creator of the stream.
        ByteArrayInputStream in = exportArticleXLSX.articlesToExcel(articles);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=export-articles.xlsx");


        return new ResponseEntity<InputStreamResource>((new InputStreamResource(in)), headers, HttpStatus.OK);

        //alternative de retour
        /*return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(in));
*/
    }
}


