package com.dxc.clients.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dxc.clients.models.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

	 @Query(value = " select c from Client c ")
	    Page<Client> findAllClients(final Pageable pageable);
	 
	 @Query(value = "SELECT * FROM tb_client WHERE email = :email", nativeQuery = true)
	    Optional<Client> findByEmail(@Param("email") String email);
}
