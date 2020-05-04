package com.example.demo.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

/**
 * Entity représentant un client.
 */
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false)
    private String nom;

    @Column
    private LocalDate dateNaissance;

    /*public Integer getAge() {
        Date d=new Date();
        int year=d.getYear();
        age = d - (dateNaissance.getYear());
        return age;
    }*/

    public void setAge(Integer age) {
        this.age = age;
    }

    private Integer age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }



}
