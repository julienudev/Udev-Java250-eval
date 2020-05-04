package com.example.demo.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

/**
 * Classe permettant d'exposer des données au format JSON d'un client.
 */
public class ClientDto {
    public Long id;
    public String nom;
    public String prenom;
    public LocalDate dateNaissance;
    public Integer age;

    private static final DateFormat sdf = new SimpleDateFormat("yyyy");

    private  void getAge(){
        System.out.println(sdf);

    }
    public ClientDto(Long id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;


    }

}
