package com.example.demo.controller.clientsidetemplating;

import com.example.demo.controller.clientsidetemplating.dto.ArticleDto;
import com.example.demo.controller.clientsidetemplating.dto.ClientDto;
import com.example.demo.controller.clientsidetemplating.dto.FactureDto;
import com.example.demo.controller.clientsidetemplating.dto.LigneFactureDto;
import com.example.demo.entity.Article;
import com.example.demo.entity.Client;
import com.example.demo.entity.Facture;
import com.example.demo.entity.LigneFacture;
import com.example.demo.service.FactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Controller pour exposition d'api REST des factures, utilisé dans le cas d'une application client side templating.
 */
@RestController
@RequestMapping("rest/factures")
public class FactureRestController {

    @Autowired
    private FactureService factureService;

    /**
     * Exposition d'une api déclenchée sur l'url http://..../factures.
     *
     * @return la liste des factures au format JSON. Voir la classe FactureDto pour le détail du format.
     */
    @GetMapping
    public List<FactureDto> getFactures() {
        // Transformation d'une liste de Facture en FactureDto
        return factureService
                .findAllFactures()
                .stream()
                .map(f -> factureDto(clientDto(f.getClient()), f))
                .collect(toList());
    }

    /**
     * Exposition d'une api déclenchée sur l'url http://..../factures.
     *
     * @return la liste des factures au format JSON. Voir la classe FactureDto pour le détail du format.
     */
    @GetMapping("{id}")
    public FactureDto getFacture(@PathVariable Long id) {
        // Transformation d'une Facture en FactureDto
        Facture facture = factureService.findById(id);
        return factureDto(clientDto(facture.getClient()), facture);
    }

    private ClientDto clientDto(Client client) {
        return new ClientDto(client.getId(), client.getNom(), client.getPrenom());
    }

    private FactureDto factureDto(ClientDto clientDto, Facture f) {
        List<LigneFactureDto> ligneFactureDtos = f.getLigneFactures()
                .stream()
                .map(lf -> ligneFactureDto(lf))
                .collect(toList());
        return new FactureDto(f.getId(), clientDto, ligneFactureDtos);
    }

    private LigneFactureDto ligneFactureDto(LigneFacture lf) {
        Article article = lf.getArticle();
        return new LigneFactureDto(new ArticleDto(article.getId(), article.getLibelle(), article.getPrix()), lf.getQuantite());
    }

}
