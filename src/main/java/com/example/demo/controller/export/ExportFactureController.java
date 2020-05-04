package com.example.demo.controller.export;

import com.example.demo.entity.Article;
import com.example.demo.entity.Facture;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.FactureRepository;
import com.example.demo.service.export.ExportArticleCSVService;
import com.example.demo.service.export.ExportArticleXLSXService;
import com.example.demo.service.export.ExportFactureXlsxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("export")
public class ExportFactureController {


    @Autowired
    private FactureRepository factureRepository;

    @Autowired
    private ExportFactureXlsxService exportFacturesService;

    @GetMapping("/clients/{id}/factures/xlsx")
    public ResponseEntity<InputStreamResource> factureXLSX(
            @PathVariable("id") Long clientId
    ) throws IOException {
        //recuper id Client
        //String idClient = "";
        //idClient = factureRepository.findByClient_Id(id).toString();

        // recupere list article
        List<Facture> factures =
                (List<Facture>) exportFacturesService.findAllByClientId(clientId);
        //An array of bytes that was provided by the creator of the stream.
        ByteArrayInputStream in = exportFacturesService.factureToExcel(factures);
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
