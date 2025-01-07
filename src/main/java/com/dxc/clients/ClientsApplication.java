package com.dxc.clients;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ClientsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientsApplication.class, args);
	}
	
	
	@RestController
	class HttpController {
		@GetMapping("/public")
		String publicRoute() {
			return "<h1>Rota Pública para teste.</h1>";
		}
	}

}
