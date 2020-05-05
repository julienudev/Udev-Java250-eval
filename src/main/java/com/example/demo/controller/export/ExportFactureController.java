package com.example.demo.controller.export;

import com.example.demo.entity.Client;
import com.example.demo.entity.Facture;
import com.example.demo.entity.LigneFacture;
import com.example.demo.repository.FactureRepository;
import com.example.demo.service.FactureService;
import com.example.demo.service.export.ExportFactureXlsxService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    //prevoir refactorisation dans service
    //Export Excel de toutes les factures d'un client / feuille client/feuilles factures
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

        //remplissage tableau feuille client
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


            //Creation feuilles factures
            Sheet sheetDetails = workbook.createSheet("Factures   " + facture.getId());

            //Creation headers details d'une facture
            String[] colDetailFacture = {"Libelle", "Quantité", "Prix", "Sous-Total"};
            Row headerRowDetails = sheetDetails.createRow(0);
            for (int col = 0; col < colDetailFacture.length; col++) {
                Cell cell = headerRowDetails.createCell(col);
                cell.setCellValue(colDetailFacture[col]);
            }

            //Creation détails contenu facture
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

            indexRow = indexRow + 1;
        }
    }


}

