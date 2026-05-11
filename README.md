# Bar CRUD - Java Spring Boot

Back-end CRUD para sistema de bar com mesas, contas, pedidos, itens, produtos e pagamentos.

## Rodar
```bash
mvn spring-boot:run
```

API: http://localhost:8081  
H2: http://localhost:8081/h2-console  
JDBC URL: jdbc:h2:mem:barcrud

# ==========================================
# CRUD BAR SYSTEM - TESTES COMPLETOS
# ==========================================

# ==========================================
# MESAS
# ==========================================

# LISTAR MESAS
irm http://localhost:8081/mesas

# CADASTRAR MESA
irm -Method POST -Uri "http://localhost:8081/mesas" -ContentType "application/json" -Body '{"numero":4}'

# BUSCAR MESA POR ID
irm http://localhost:8081/mesas/1

# EDITAR MESA
irm -Method PUT -Uri "http://localhost:8081/mesas/4" -ContentType "application/json" -Body '{"numero":10}'

# ==========================================
# PRODUTOS
# ==========================================

# LISTAR PRODUTOS
irm http://localhost:8081/produtos

# CADASTRAR PRODUTO
irm -Method POST -Uri "http://localhost:8081/produtos" -ContentType "application/json" -Body '{"nome":"Pizza","categoria":"Comida","preco":45.90,"ativo":true}'

# BUSCAR PRODUTO POR ID
irm http://localhost:8081/produtos/1

# EDITAR PRODUTO
irm -Method PUT -Uri "http://localhost:8081/produtos/1" -ContentType "application/json" -Body '{"nome":"Cerveja Lata","categoria":"BEBIDA","preco":7.00,"ativo":true}'

# ==========================================
# CONTAS
# ==========================================

# ABRIR CONTA
irm -Method POST -Uri "http://localhost:8081/contas/abrir" -ContentType "application/json" -Body '{"mesaId":1,"cliente":"Joao","individual":true,"taxaServico":true}'

# LISTAR CONTAS
irm http://localhost:8081/contas

# BUSCAR CONTA POR ID
irm http://localhost:8081/contas/1

# EDITAR CONTA
irm -Method PUT -Uri "http://localhost:8081/contas/1" -ContentType "application/json" -Body '{"cliente":"Joao Silva","individual":true,"taxaServico":true}'

# ==========================================
# PEDIDOS
# ==========================================

# CRIAR PEDIDO
irm -Method POST -Uri "http://localhost:8081/pedidos" -ContentType "application/json" -Body '{"contaId":1}'

# LISTAR PEDIDOS
irm http://localhost:8081/pedidos

# BUSCAR PEDIDO POR ID
irm http://localhost:8081/pedidos/1

# EDITAR PEDIDO
irm -Method PUT -Uri "http://localhost:8081/pedidos/1" -ContentType "application/json" -Body '{"contaId":1}'

# ==========================================
# ITENS DO PEDIDO
# ==========================================

# ADICIONAR ITEM AO PEDIDO
irm -Method POST -Uri "http://localhost:8081/pedidos/1/itens" -ContentType "application/json" -Body '{"produtoId":1,"quantidade":2}'

# ADICIONAR OUTRO ITEM
irm -Method POST -Uri "http://localhost:8081/pedidos/1/itens" -ContentType "application/json" -Body '{"produtoId":2,"quantidade":1}'

# REMOVER ITEM DO PEDIDO
irm -Method DELETE -Uri "http://localhost:8081/pedidos/1/itens/1"

# ==========================================
# CONTA
# ==========================================

# IMPRIMIR CONTA
irm http://localhost:8081/contas/1/imprimir

# REGISTRAR PAGAMENTO
irm -Method POST -Uri "http://localhost:8081/contas/1/pagamentos" -ContentType "application/json" -Body '{"forma":"PIX","valor":20.00}'

# FECHAR CONTA
irm -Method POST -Uri "http://localhost:8081/contas/1/fechar"

# ==========================================
# TESTES DE VALIDAÇÃO
# ==========================================

# PRODUTO COM PREÇO INVÁLIDO
irm -Method POST -Uri "http://localhost:8081/produtos" -ContentType "application/json" -Body '{"nome":"Erro","categoria":"Teste","preco":0,"ativo":true}'

# ITEM COM QUANTIDADE INVÁLIDA
irm -Method POST -Uri "http://localhost:8081/pedidos/1/itens" -ContentType "application/json" -Body '{"produtoId":1,"quantidade":0}'

# CONTA FECHADA RECEBENDO PEDIDO
irm -Method POST -Uri "http://localhost:8081/pedidos" -ContentType "application/json" -Body '{"contaId":1}'

# ==========================================
# TESTES DE DELETE
# EXECUTAR POR ÚLTIMO
# ==========================================

# EXCLUIR PEDIDO
irm -Method DELETE -Uri "http://localhost:8081/pedidos/1"

# EXCLUIR CONTA
irm -Method DELETE -Uri "http://localhost:8081/contas/1"

# EXCLUIR PRODUTO
irm -Method DELETE -Uri "http://localhost:8081/produtos/3"

# EXCLUIR MESA
irm -Method DELETE -Uri "http://localhost:8081/mesas/4"
