package com.example.demo.service.export;

import com.example.demo.entity.Article;
import com.example.demo.entity.Client;
import com.example.demo.repository.ClientRepository;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class ExportClientXLSXService {

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public static ByteArrayInputStream clientsToExcel(List<Client> clients) throws IOException {
        //Tableau de headers
        String[] cols = {"Nom", "Prenom" , "Age"};

        //Date du jour
        LocalDate now = LocalDate.now();

        //nouveaux objets
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        //creation feuille excel "Clients"
        //CreationHelper createHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("Clients");

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        //headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Font headerFont = workbook.createFont();
        headerFont.setColor(IndexedColors.PINK.getIndex());
        //headerFont.setBoldweight( Font.BOLDWEIGHT_BOLD);



        headerStyle.setFont(headerFont);

        // Creation Header
        Row headerRow = sheet.createRow(0);
        for (int col = 0; col < cols.length; col++) {
            Cell cell = headerRow.createCell(col);
            cell.setCellValue(cols[col]);
            cell.setCellStyle(headerStyle);
        }

        // Creation Contenu
        int rowIndex = 1;
        for (Client client : clients) {
            //Row row = sheet.createRow(rowIndex++);
            Row row = sheet.createRow(rowIndex);
            rowIndex = rowIndex + 1;
            row.createCell(0).setCellValue(client.getNom());
            row.createCell(1).setCellValue(client.getPrenom());
            row.createCell(2).setCellValue(  (now.getYear() - client.getDateNaissance().getYear())+" ans"  );

        }
        workbook.write(out);

        return new ByteArrayInputStream(out.toByteArray());
        //workbook.close();
    }
}
