package com.example.demo.controller.clientsidetemplating;

import com.example.demo.dto.FactureDto;
import com.example.demo.service.FactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        return factureService.findAllFactures();
    }

    /**
     * Exposition d'une api déclenchée sur l'url http://..../factures.
     *
     * @return la liste des factures au format JSON. Voir la classe FactureDto pour le détail du format.
     */
    @GetMapping("{id}")
    public FactureDto getFacture(@PathVariable Long id) {
        return factureService.findById(id);
    }

}
