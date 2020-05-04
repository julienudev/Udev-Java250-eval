package com.example.demo.service.export;

import com.example.demo.entity.Client;
import com.example.demo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.List;

@Service
public class ExportClientCSVService {


    @Autowired
    private ClientRepository clientRepository;

    public void exportAll(PrintWriter writer) {

        //génération d'un fichier CSV exemples avec 2 colonnes et 4 lignes

        List<Client> allClients = clientRepository.findAll();
        for (Client client : allClients) {
            writer.println(client.getNom() + ";" + client.getPrenom());

        }

        // TODO remplacer par les vrais articles de la base de données, tips : rechercher tous les articles : articleService.findAll();
    }

}
