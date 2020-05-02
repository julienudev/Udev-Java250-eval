package com.example.demo.controller.clientsidetemplating;

import com.example.demo.controller.clientsidetemplating.dto.ClientDto;
import com.example.demo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Controller pour exposition d'api REST des clients, utilisé dans le cas d'une application client side templating.
 */
@RestController
@RequestMapping("rest/clients")
public class ClientRestController {

    @Autowired
    private ClientService clientService;

    /**
     * Exposition d'une api déclenchée sur l'url http://..../clients.
     *
     * @return la liste des clients au format JSON. Voir la classe ClientDto pour le détail du format.
     */
    @GetMapping
    public List<ClientDto> getClients() {
        // Transformation d'une liste de Client en ClientDto
        return clientService
                .findAllClients()
                .stream()
                .map(c -> new ClientDto(c.getId(), c.getNom(), c.getPrenom()))
                .collect(toList());
    }

}
