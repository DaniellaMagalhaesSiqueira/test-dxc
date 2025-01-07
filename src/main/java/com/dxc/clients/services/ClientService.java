package com.dxc.clients.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dxc.clients.models.Client;
import com.dxc.clients.repositories.ClientRepository;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository clientRepository;
	
	public Client save(Client client) {
		return clientRepository.save(client);
	}
	
	public Page<Client> findAll(Pageable pageable) {
		return clientRepository.findAllClients(pageable);
	}
	
	public Optional<Client> findById(Long clientId) {
		return clientRepository.findById(clientId);
	}
	
	 public Client updateClient(Client client){
		 
	        Client existClient = new Client();
	        existClient = clientRepository.findById(client.getClientId()).get();
	        existClient.setFirstName(client.getFirstName());
	        existClient.setLastName(client.getLastName());
	        existClient.setBirthDate(client.getBirthDate());
	        existClient.setEmail(client.getEmail());
	        existClient.setCpf(client.getCpf());
	        return clientRepository.save(existClient);
	    }
	 
	 public void deleteById(Long clientId){
		 clientRepository.deleteById(clientId);
	 }
	 
	 public Optional<Client> findByEmail(String email) {
		 return clientRepository.findByEmail(email);
	 }
	 
}
