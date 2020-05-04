package com.example.demo.controller.export;


import com.example.demo.service.export.ExportClientCSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping("export")
public class ExportClientController {

    @Autowired
    private ExportClientCSVService exportClientCSV;

    /**
     * Export des articles au format CSV, déclenché sur l'url http://.../export/clients/csv
     *
     * @param request  objet reprensantant la requête http
     * @param response objet reprensantant la réponse http
     */
    @GetMapping("/clients/csv")
    public void clientsCSV(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // positionne de metadata sur la réponse afin d'informer le navigateur que la réponse correspond à un fichier téléchargeable.
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"export-clients.csv\"");

        // Le writter est un objet provenant de la response dans lequel on va pouvoir écrire pour générer le contenu de l'export CSV.
        PrintWriter writer = response.getWriter();

        exportClientCSV.exportAll(writer);

        /*exportArticleCSV.exportAll( new PrintWriter(System.out));
        FileOutputStream out = new FileOutputStream("c:/temps/temp.csv")
        exportArticleCSV.exportAll( new PrintWriter(out));*/

    }
}
