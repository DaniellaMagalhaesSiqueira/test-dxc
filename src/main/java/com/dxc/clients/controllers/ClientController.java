package com.dxc.clients.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dxc.clients.exceptions.ControllerException;
import com.dxc.clients.models.Client;
import com.dxc.clients.services.ClientService;

@RestController
@RequestMapping(value="/clients")
public class ClientController {
	
	@Autowired
	private ClientService clientService;
	
	@PostMapping
	public ResponseEntity<Client> createClient(@Valid @RequestBody Client client) {
		Client savedClient = clientService.save(client);
		return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<Page<Client>> getAllClients(@RequestParam(defaultValue= "0") int page, @RequestParam(defaultValue="5") int size) {
		PageRequest pageReq = PageRequest.of(page, size);
		Page<Client> clients = clientService.findAll(pageReq);
		return ResponseEntity.ok(clients);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Client> getClientById(@PathVariable("id") Long clientId) throws ControllerException {
		Client client = clientService.findById(clientId).orElseThrow(() -> {
			return new ControllerException("Id client not found: "+ clientId);
		});
		return ResponseEntity.ok(client);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Client> updateClient(@PathVariable("id") Long clientId, @Valid @RequestBody Client client) throws ControllerException {
		 if (clientId == null || clientService.findById(clientId).isEmpty()){
	            throw new ControllerException("Id client not found: " + clientId);
	        } else{
	            client.setClientId(clientId);
	            Client updatedClient = clientService.updateClient(client);
	            return ResponseEntity.ok(updatedClient);
	        }
	}
	
	   @DeleteMapping("{id}")
	    public ResponseEntity<String> deleteClient(@PathVariable("id") Long clientId) throws ControllerException{
	        clientService.findById(clientId).orElseThrow(() -> {
	            return new ControllerException("Id client not found: " + clientId);
	        });
	        clientService.deleteById(clientId);
	        return ResponseEntity.ok("Client " + clientId + " successfully deleted!");
	}
	   
	   @GetMapping("/email")
		public ResponseEntity<Client> getClientByEmail(@RequestBody  Map<String, String> body) throws ControllerException {
		   String email = body.get("email");
		   if (email == null || email.isEmpty()) {
		        throw new ControllerException("Email is required");
		    }
		    Client client = clientService.findByEmail(email).orElseThrow(() -> 
		        new ControllerException("Client not found with email: " + email)
		    );
			return ResponseEntity.ok(client);
		}
		
}
