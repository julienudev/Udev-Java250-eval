package com.example.demo.service.export;

import com.example.demo.entity.Article;
import com.example.demo.entity.Facture;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.FactureRepository;
import com.example.demo.service.ArticleService;
import com.example.demo.service.FactureService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
@Service
public class ExportFactureXlsxService {

    @Autowired
    private FactureService factureService;

    @Autowired
    private FactureRepository factureRepository;

    public List<Facture> findAllByClientId(Long clientId) {

        return factureRepository.findByClient_Id(clientId);
    }

    public static ByteArrayInputStream factureToExcel(List<Facture> factures) throws IOException {
        //Tableau de nom de colonnes
        String[] cols = {"Id", "Total"};

        //nouveaux objets
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        //creation feuille excel
        CreationHelper createHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("Factures");

        Font headerFont = workbook.createFont();
        headerFont.setColor(IndexedColors.BLUE.getIndex());
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Creation Header
        Row headerRow = sheet.createRow(0);
        for (int col = 0; col < cols.length; col++) {
            Cell cell = headerRow.createCell(col);
            cell.setCellValue(cols[col]);
            cell.setCellStyle(headerCellStyle);
        }

        // Creation Contenu
        int rowIndex = 1;
        for (Facture facture : factures) {
            //Row row = sheet.createRow(rowIndex++);
            Sheet sheet2 = workbook.createSheet("Factures   "+facture.getId());
            //String[] cols = {"Libelle", "Quantité"};
            Row row = sheet2.createRow(rowIndex);
            rowIndex = rowIndex + 1;
            row.createCell(0).setCellValue(facture.getId());
            row.createCell(1).setCellValue( facture.getTotal());

        }
        workbook.write(out);

        return new ByteArrayInputStream(out.toByteArray());
        //workbook.close();
    }
}
