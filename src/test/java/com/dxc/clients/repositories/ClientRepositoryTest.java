package com.dxc.clients.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.dxc.clients.models.Client;

@SpringBootTest
class ClientRepositoryTest {
	
	@Autowired
    private ClientRepository clientRepository;

	 @BeforeEach
	    public void setUp() {
		 
	        Client client = new Client();
	        client.setFirstName("Jane");
	        client.setLastName("Do");
	        client.setBirthDate(LocalDate.of(1980, 10, 01));
	        client.setCpf("439.759.637-94");
	        client.setEmail("janedo@mail.com");
	        clientRepository.save(client);
	        
	        Client client2 = new Client();
	        client2.setFirstName("Jhon");
	        client2.setLastName("Do");
	        client2.setBirthDate(LocalDate.of(1999, 10, 01));
	        client2.setCpf("739.482.357-16");
	        client2.setEmail("jhondo@mail.com");
	        clientRepository.save(client);
	    }
	 
	 @Test
	    public void testPagination() {

	        int pageNumber = 0;
	        int pageSize = 10;
	        Pageable pageable = PageRequest.of(pageNumber, pageSize);

	        Page<Client> clients = clientRepository.findAllClients(pageable);

	        assertEquals(pageNumber, clients.getNumber());
	        assertEquals(pageSize, clients.getSize());
	        assertTrue(clients.hasContent());
	        assertTrue(clients.isFirst());
	        assertTrue(clients.isLast());
	    }

}
