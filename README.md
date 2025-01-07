# Descrição
Este sistema se propõe à criação de um projeto de microsserviço para manipulação do banco de dados de clientes podendo serem feitas consultas, exclusões, criações e alterações de clientes (CRUD)
Desenvolvido em Java 11, com Spring Boot versão 2.7.18 compatível com a versão do Java elencada. Banco H2.

# Estrutura

1. Model: Classes de modelo dos objetos de Cliente.
2. Repository: Interface responsável pela operações com o banco de dados que abstrai implementações JPA.
3. Service: Contém a lógica de negócios da aplicação. Realiza validações, manipula dados e coordena a comunicação entre o Repository e o Resource.
4. Controller: Implementa os endpoints da API RESTful que permitem que os clientes interajam com o sistema.
5. Config: Configuração de autenticação do spring security para permitir testes com postman

# Teste
* Queries Nativas: Queries nativas para consulta de clientes por email.
* Exceções Personalizadas: Exceção Personalizada para as classes Controller apenas como demonstração de customização de mensagens.
* Spring Security: Authenticação com Spring Secutiry usando configuração simplificada para testes com user e password cadastrados no application.properties para facilitar testes com a ferramenta postman usando Basic Auth
* Spring Test: Para testes de integração com o Spring Boot contendo em sua biblioteca JUnit, Mockito, entre outras.
* JUnit 5: Para testes unitários.
* Mockito: Para criar mocks e simular comportamentos em testes.