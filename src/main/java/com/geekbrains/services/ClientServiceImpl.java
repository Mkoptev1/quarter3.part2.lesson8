package com.geekbrains.services;

import com.geekbrains.entities.Client;
import com.geekbrains.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl {
    private ClientRepository clientRepository;

    @Autowired
    public void setClientRepository(ClientRepository _clientRepository) {
        this.clientRepository = _clientRepository;
    }

    @Transactional(readOnly = true)
    public List<Client> getAll() {
        return (List<Client>)clientRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Client> get(Long client_id) {
        return clientRepository.findById(client_id);
    }

    @Transactional
    public void save(Client client) {
        clientRepository.save(client);
    }

    @Transactional
    public void delete(Long client_id) {
        clientRepository.deleteById(client_id);
    }

    @Transactional
    public Page<Client> showAllClient(Specification<Client> specification, Pageable pageable) {
        return (Page<Client>) clientRepository.findAll(specification, pageable);
    }
}
