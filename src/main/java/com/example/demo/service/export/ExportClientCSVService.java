package com.example.demo.service.export;

import com.example.demo.entity.Client;
import com.example.demo.repository.ClientRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ExportClientCSVService {


    @Autowired
    private ClientRepository clientRepository;

    public void exportAll(PrintWriter writer) {

        //génération d'un fichier CSV exemples avec 3 colonnes et xClients lignes
        LocalDate now = LocalDate.now();
        List<Client> allClients = clientRepository.findAll();

        //Creation headers liste tous les clients
        writer.println("Nom"+";"+"Prenom"+";"+"Age");

        for (Client client : allClients) {
            writer.println(client.getNom() + ";" + client.getPrenom()+
                    ";" + (now.getYear() - client.getDateNaissance().getYear())+" ans");

        }

        // TODO remplacer par les vrais articles de la base de données, tips : rechercher tous les articles : articleService.findAll();
    }

}
