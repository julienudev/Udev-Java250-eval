package com.example.demo.controller.clientsidetemplating.dto;

import java.util.List;

/**
 * Classe permettant d'exposer des données au format JSON d'une facture.
 */
public class FactureDto {
    public Long id;
    public ClientDto client;
    public List<LigneFactureDto> ligneFactures;

    public FactureDto(Long id, ClientDto client, List<LigneFactureDto> ligneFactures) {
        this.id = id;
        this.client = client;
        this.ligneFactures = ligneFactures;
    }
}
