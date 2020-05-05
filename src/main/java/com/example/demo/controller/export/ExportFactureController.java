package com.example.demo.controller.export;

import com.example.demo.entity.Article;
import com.example.demo.entity.Client;
import com.example.demo.entity.Facture;
import com.example.demo.entity.LigneFacture;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.FactureRepository;
import com.example.demo.service.FactureService;
import com.example.demo.service.export.ExportArticleCSVService;
import com.example.demo.service.export.ExportArticleXLSXService;
import com.example.demo.service.export.ExportFactureXlsxService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    private FactureService factureService;

/*    @GetMapping("/clients/{id}/factures/xlsx")
    public ResponseEntity<InputStreamResource> factureXLSX(
            @PathVariable("id") Long clientId
    ) throws IOException {
        //recuper id Client
        //String idClient = "";
        //idClient = factureRepository.findByClient_Id(id).toString();

        // recupere list factures d un client
        List<Facture> factures =
                (List<Facture>) factureService.findFacturesClient(clientId);
        //An array of bytes that was provided by the creator of the stream.
        ByteArrayInputStream in = exportFacturesService.factureToExcel(factures);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=export-articles.xlsx");


        return new ResponseEntity<InputStreamResource>((new InputStreamResource(in)), headers, HttpStatus.OK);

        //alternative de retour
        *//*return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(in));
*//*
    }*/

    @GetMapping("/clients/{id}/factures/xlsx")
    public void factureXLSXByClient(@PathVariable("id") Long clientId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"factures-client-" + clientId + ".xlsx\"");
        List<Facture> factures = factureService.findFacturesClient(clientId);

        //Creation workbout, sheet et header
        Workbook workbook = new XSSFWorkbook();
        //Creation premier page avec total toutes factures + infos client
        Sheet sheet = workbook.createSheet("Client");
        Row headerRow = sheet.createRow(0);

        Cell cellNom = headerRow.createCell(0);
        cellNom.setCellValue("Nom");

        Cell cellPrenom = headerRow.createCell(1);
        cellPrenom.setCellValue("prenom");

        Cell cellId = headerRow.createCell(2);
        cellId.setCellValue("IdClient");



        Cell cellTotal = headerRow.createCell(3);
        cellTotal.setCellValue("Prix Total");

        //remplissage tableau Total des factures du client
        int indexRow = 1;
        for (Facture facture : factures) {

            Client c = facture.getClient();

            Row row = sheet.createRow(indexRow);

            Cell nom = row.createCell(0);
            nom.setCellValue(c.getNom());

            Cell prenom = row.createCell(1);
            prenom.setCellValue(c.getPrenom());

            Cell idClient = row.createCell(2);
            idClient.setCellValue(c.getId());



            Cell total = row.createCell(3);
            total.setCellValue(facture.getTotal());



            indexRow = indexRow + 1;
            //remplissage détails de chaque  facture du client
            Sheet sheetDetails = workbook.createSheet("Factures   " + facture.getId());

            //Creation headers details d'une facture
            String[] colDetailFacture = {"Libelle", "Quantité", "Prix", "Sous-Total"};
            Row headerRowDetails = sheetDetails.createRow(0);
            for (int col = 0; col < colDetailFacture.length; col++) {
                Cell cell = headerRowDetails.createCell(col);
                cell.setCellValue(colDetailFacture[col]);
            }
            /*Cell libelle = headerRowDetails.createCell(0);
            libelle.setCellValue("Libellé");
            Cell quantite = headerRowDetails.createCell(1);
            quantite  .setCellValue("Quantité");
            Cell prix = headerRowDetails.createCell(2);
            prix.setCellValue("Prix");
            Cell sousTotal = headerRowDetails.createCell(3);
            sousTotal.setCellValue("sousTotal");*/


            int indexDetails = 1;
            for (LigneFacture ligneFacture : facture.getLigneFactures()) {
                Row newRow = sheetDetails.createRow(indexDetails);
                newRow.createCell(0).setCellValue(ligneFacture.getArticle().getLibelle());
                newRow.createCell(1).setCellValue(ligneFacture.getQuantite());
                newRow.createCell(2).setCellValue(ligneFacture.getArticle().getPrix());
                newRow.createCell(3).setCellValue(ligneFacture.getSousTotal());
                indexDetails++;
            }
            workbook.write(response.getOutputStream());
            ((XSSFWorkbook) workbook).close();
        }
    }
}

