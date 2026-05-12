# Bar CRUD - Java Spring Boot

Back-end CRUD para sistema de bar com mesas, contas, pedidos, itens, produtos e pagamentos.

## Rodar
```bash
mvn spring-boot:run
```

API: http://localhost:8080  
H2: http://localhost:8080/h2-console  
JDBC URL: jdbc:h2:mem:barcrud

## Ordem de teste
1. GET /mesas
2. POST /produtos
3. POST /contas/abrir
4. POST /pedidos
5. POST /pedidos/{id}/itens
6. GET /contas/{id}/imprimir
7. POST /contas/{id}/fechar
8. POST /contas/{id}/pagamentos