package com.dxc.clients.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.dxc.clients.exceptions.ControllerException;
import com.dxc.clients.models.Client;
import com.dxc.clients.services.ClientService;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {
	
	@Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    private Client validClient;
    
    @BeforeEach
    public void setUp() {
        validClient = new Client();
        validClient.setFirstName("Jane");
        validClient.setLastName("Do");
        validClient.setBirthDate(LocalDate.of(1980, 10, 01));
        validClient.setEmail("janedo@mail.com");
        validClient.setCpf("439.759.637-94");
    }

    @Test
    public void testCreateClient() {

        Client client = new Client();
        when(clientService.save(client)).thenReturn(client);

        ResponseEntity<Client> response = clientController.createClient(client);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(client, response.getBody());
    }

    @Test
    public void testGetAllClients() {

        int page = 0;
        int size = 5;
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Client> mockPage = new PageImpl<>(List.of(new Client(), new Client()));

        when(clientService.findAll(pageRequest)).thenReturn(mockPage);

        ResponseEntity<Page<Client>> response = clientController.getAllClients(page, size);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPage, response.getBody());
    }

    @Test
    public void testGetClientById() throws ControllerException {

        Long clientId = 1L;
        Client mockClient = new Client();
        when(clientService.findById(clientId)).thenReturn(Optional.of(mockClient));

        ResponseEntity<Client> response = clientController.getClientById(clientId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockClient, response.getBody());
    }
	
    @Test
    public void testGetClientByIdNotFound() {

        Long clientId = 1L;
        when(clientService.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(ControllerException.class, () -> {
            clientController.getClientById(clientId);
        });
    }

    @Test
    public void testUpdateClient() throws ControllerException {

        Long clientId = 1L;
        Client clientDetails = new Client();
        clientDetails.setClientId(clientId);
        Client mockUpdatedClient = new Client();

        when(clientService.findById(clientId)).thenReturn(Optional.of(new Client()));
        when(clientService.updateClient(clientDetails)).thenReturn(mockUpdatedClient);

        ResponseEntity<Client> response = clientController.updateClient(clientId, clientDetails);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUpdatedClient, response.getBody());
    }
    
    @Test
    public void testUpdateClientNotFound() {

        Long clientId = 1L;
        Client clientDetails = new Client();

        when(clientService.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(ControllerException.class, () -> {
            clientController.updateClient(clientId, clientDetails);
        });
    }

    @Test
    public void testDeleteClient() throws ControllerException {

        Long clientId = 1L;

        when(clientService.findById(clientId)).thenReturn(Optional.of(new Client()));

        ResponseEntity<String> result = clientController.deleteClient(clientId);
        verify(clientService).deleteById(anyLong());
        Assertions.assertEquals(new ResponseEntity<>("Client 1 successfully deleted!", null, HttpStatus.OK), result);
    }
    
    @Test
    public void testDeleteClientNotFound() throws ControllerException {

    	Long clientId = 1L;

        when(clientService.findById(clientId)).thenReturn(Optional.of(new Client()));
        doNothing().when(clientService).deleteById(clientId); 

        ResponseEntity<String> response = clientController.deleteClient(clientId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Client 1 successfully deleted!", response.getBody());
    }

    
    @Test
    public void testGetClientByEmail_Success() throws ControllerException {
        String email = "janedo@mail.com";
        Map<String, String> body = new HashMap<>();
        body.put("email", email);

        when(clientService.findByEmail(email)).thenReturn(Optional.of(validClient));

        ResponseEntity<Client> response = clientController.getClientByEmail(body);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(validClient, response.getBody());
    }

    @Test
    public void testGetClientByEmail_EmailNotProvided() {
        Map<String, String> body = new HashMap<>();

        ControllerException thrown = assertThrows(ControllerException.class, () -> {
            clientController.getClientByEmail(body);
        });

        assertEquals("Email is required", thrown.getMessage());
    }

    @Test
    public void testGetClientByEmail_ClientNotFound() throws ControllerException {

        String email = "nonexistent@mail.com";
        Map<String, String> body = new HashMap<>();
        body.put("email", email);

        when(clientService.findByEmail(email)).thenReturn(Optional.empty());

        ControllerException thrown = assertThrows(ControllerException.class, () -> {
            clientController.getClientByEmail(body);
        });

        assertEquals("Client not found with email: " + email, thrown.getMessage());
    }

}
