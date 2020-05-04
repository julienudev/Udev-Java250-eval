package com.example.demo.service.export;

import com.example.demo.entity.Article;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.service.ArticleService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExportArticleXLSXService {


    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public static ByteArrayInputStream articlesToExcel(List<Article> articles) throws IOException {
        //Tableau de nom de colonnes
        String[] cols = {"Id", "Libelle"};

        //nouveaux objets
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        //creation feuille excel
        CreationHelper createHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("Articles");

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
        for (Article article : articles) {
            //Row row = sheet.createRow(rowIndex++);
            Row row = sheet.createRow(rowIndex);
            rowIndex = rowIndex + 1;
            row.createCell(0).setCellValue(article.getId());
            row.createCell(1).setCellValue(article.getLibelle());

        }

        workbook.write(out);
        return new ByteArrayInputStream(out.toByteArray());
    }
}

