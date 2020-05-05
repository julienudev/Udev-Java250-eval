package com.example.demo.service;

import com.example.demo.dto.ClientDto;
import com.example.demo.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static java.util.stream.Collectors.toList;

/**
 * Service contenant les actions métiers liées aux clients.
 */
@Service
@Transactional
public class ClientService {

    private ClientRepository clientRepository;




    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<ClientDto> findAllClients() {


        // Transformation d'une liste de Client en ClientDto
        return clientRepository
                .findAll()
                .stream()
                .map(c -> new ClientDto(c.getId(), c.getNom(), c.getPrenom()))

                .collect(toList());
    }
}
