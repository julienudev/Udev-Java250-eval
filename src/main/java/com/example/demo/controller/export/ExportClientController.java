package com.example.demo.controller.export;


import com.example.demo.entity.Article;
import com.example.demo.entity.Client;
import com.example.demo.service.export.ExportClientCSVService;
import com.example.demo.service.export.ExportClientXLSXService;
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

@Controller
@RequestMapping("export")
public class ExportClientController {

    @Autowired
    private ExportClientCSVService exportClientCSV;
    @Autowired
    private ExportClientXLSXService exportClientXLSXService;

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

    }

    @GetMapping("/clients/xlsx")
    public ResponseEntity<InputStreamResource> articlesXLSX() throws IOException {
        // recupere list article (service -> repo )
        List<Client> clients = (List<Client>) exportClientXLSXService.findAll();
        //An array of bytes that was provided by the creator of the stream.
        ByteArrayInputStream in = exportClientXLSXService.clientsToExcel(clients);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=export-articles.xlsx");

        return new ResponseEntity<InputStreamResource>((new InputStreamResource(in)), headers, HttpStatus.OK);

        //alternative de retour
        /*return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(in));*/

    }

}
