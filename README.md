# Seriguela Bar

Sistema web para gerenciamento operacional do Seriguela Bar. A aplicação permite controlar mesas, produtos, categorias, clientes, contas, pedidos, itens consumidos, fechamento de contas e pagamentos.

O projeto foi desenvolvido com Java, Spring Boot, Thymeleaf e H2 Database, usando uma interface responsiva com sidebar no desktop e menu drawer no mobile.

## Sumário

- [Visão Geral](#visão-geral)
- [Funcionalidades](#funcionalidades)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Pré-requisitos](#pré-requisitos)
- [Como Rodar o Projeto](#como-rodar-o-projeto)
- [Acesso ao Sistema](#acesso-ao-sistema)
- [Banco de Dados H2](#banco-de-dados-h2)
- [Dados Iniciais](#dados-iniciais)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Principais Telas](#principais-telas)
- [Regras de Negócio](#regras-de-negócio)
- [Rotas da Aplicação](#rotas-da-aplicação)
- [API REST](#api-rest)
- [Design System](#design-system)
- [Comandos Úteis](#comandos-úteis)
- [Observações de Desenvolvimento](#observações-de-desenvolvimento)
- [Possíveis Melhorias Futuras](#possíveis-melhorias-futuras)

## Visão Geral

O Seriguela Bar é um sistema CRUD com interface web para apoiar o atendimento de um bar. Ele centraliza as principais operações do dia a dia:

- cadastrar e gerenciar mesas;
- cadastrar produtos e categorias;
- abrir contas por cliente;
- criar pedidos para uma conta;
- adicionar ou remover itens dos pedidos;
- calcular totais individuais;
- aplicar taxa de serviço;
- fechar contas;
- registrar pagamentos via API;
- acompanhar pedidos agrupados por mesa.

A aplicação usa autenticação por login e mantém as páginas principais protegidas.

## Funcionalidades

### Autenticação

- Tela de login personalizada.
- Proteção das rotas principais com Spring Security.
- Redirecionamento automático para `/home` após login.
- Logout com invalidação da sessão.

### Home

- Tela inicial autenticada.
- Cards de acesso para Mesas, Pedidos e Produtos.
- Layout responsivo com sidebar e drawer mobile.

### Mesas

- Cadastro de novas mesas.
- Edição de nome e status.
- Exclusão de mesas sem contas vinculadas.
- Busca por nome.
- Filtro por status:
  - Livre;
  - Ocupada;
  - Fechada.
- Ordenação por nome:
  - Nome A - Z;
  - Nome Z - A.
- Dashboard com:
  - Total;
  - Livres;
  - Ocupadas;
  - Fechadas.
- Layout mobile em formato de cards, com gaveta de ações ao tocar em uma linha.

### Produtos

- Cadastro de produtos.
- Edição de produtos.
- Exclusão de produtos.
- Status de produto:
  - Ativo;
  - Inativo.
- Cadastro, edição e exclusão de categorias.
- Busca por nome do produto.
- Filtros e ordenações:
  - ID Menor - Maior;
  - ID Maior - Menor;
  - Nome A - Z;
  - Nome Z - A;
  - Maior preço;
  - Menor preço;
  - Status ativo/inativo.
- Dashboard com:
  - Total;
  - Ativos;
  - Inativos.
- Reutilização de ID de produto quando um produto é excluído e o ID fica disponível.
- Quando um produto já está vinculado a itens de pedido, a exclusão transforma o produto em inativo em vez de remover o registro.

### Pedidos

- Visualização de contas agrupadas por mesa.
- Abertura de cliente/conta em uma mesa.
- Criação de novos pedidos para uma conta.
- Adição de itens em pedidos existentes.
- Remoção de itens.
- Fechamento de conta individual.
- Filtro por:
  - cliente, mesa ou status da mesa;
  - mesa;
  - status do pedido: Aberto ou Fechado.
- Dashboard com:
  - mesas com pedidos;
  - contas abertas;
  - saldo aberto.
- Fluxos de adicionar cliente, criar pedido e adicionar item com atualização dinâmica da tela para reduzir piscadas/reloads.
- Produtos inativos não aparecem como opção para novos itens.

### Contas e Pagamentos

- Abertura de conta via tela de Pedidos ou API.
- Fechamento de conta.
- Registro de pagamento via API.
- Impressão textual da conta via API.
- Cálculo automático de:
  - subtotal;
  - taxa de serviço;
  - total.

## Tecnologias Utilizadas

### Back-end

- Java 17
- Spring Boot 3.3.5
- Spring MVC
- Spring Data JPA
- Hibernate
- Spring Validation
- Spring Security
- Maven

### Front-end

- Thymeleaf
- HTML5
- CSS3
- JavaScript
- Lucide Icons
- Google Fonts:
  - Fraunces;
  - Nunito.

### Banco de Dados

- H2 Database em memória
- Script inicial em `src/main/resources/data.sql`

## Pré-requisitos

Antes de rodar o projeto, tenha instalado:

- Java 17 ou superior;
- Maven;
- navegador web moderno.

Para verificar as versões:

```bash
java -version
mvn -version
```

## Como Rodar o Projeto

Na raiz do projeto, execute:

```bash
mvn spring-boot:run
```

A aplicação ficará disponível em:

```text
http://localhost:8080
```

Como a rota `/` redireciona para `/home`, usuários não autenticados serão enviados para a tela de login.

## Acesso ao Sistema

Usuário padrão criado pelo `data.sql`:

```text
E-mail: admin@seriguela.com
Senha: admin123
```

Após o login, o sistema redireciona para:

```text
http://localhost:8080/home
```

## Banco de Dados H2

O projeto usa banco em memória. Isso significa que os dados são recriados a cada inicialização da aplicação.

Console H2:

```text
http://localhost:8080/h2-console
```

Configurações de conexão:

```text
JDBC URL: jdbc:h2:mem:barcrud
User Name: sa
Password: 
```

Configuração principal em `application.properties`:

```properties
server.port=8080
spring.datasource.url=jdbc:h2:mem:barcrud
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=create
spring.jpa.defer-datasource-initialization=true
spring.jpa.show-sql=true
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

## Dados Iniciais

O arquivo `src/main/resources/data.sql` popula o sistema com dados para teste.

Atualmente ele cria:

- 6 mesas com status diferentes;
- 5 categorias;
- 8 produtos ativos;
- 7 contas;
- 9 pedidos;
- 17 itens de pedido;
- 1 usuário administrador.

Esses dados facilitam o teste imediato da tela de Pedidos, filtros, dashboards e totais.

## Estrutura do Projeto

```text
src/main/java/com/example/barcrud
├── BarCrudApplication.java
├── SecurityConfig.java
├── controller
│   ├── ContaController.java
│   ├── HomeController.java
│   ├── LoginController.java
│   ├── MesaController.java
│   ├── PedidoController.java
│   └── ProdutoController.java
├── dto
│   ├── AbrirContaRequest.java
│   ├── ItemPedidoRequest.java
│   ├── MesaRequest.java
│   ├── PagamentoRequest.java
│   ├── PedidoRequest.java
│   └── ProdutoRequest.java
├── exception
│   ├── BusinessException.java
│   ├── GlobalExceptionHandler.java
│   └── NotFoundException.java
├── model
│   ├── Categoria.java
│   ├── Conta.java
│   ├── ItemPedido.java
│   ├── Mesa.java
│   ├── Pagamento.java
│   ├── Pedido.java
│   ├── Produto.java
│   ├── StatusConta.java
│   ├── StatusMesa.java
│   └── Usuario.java
├── repository
│   ├── CategoriaRepository.java
│   ├── ContaRepository.java
│   ├── ItemPedidoRepository.java
│   ├── MesaRepository.java
│   ├── PagamentoRepository.java
│   ├── PedidoRepository.java
│   ├── ProdutoRepository.java
│   └── UsuarioRepository.java
└── service
    ├── ContaService.java
    ├── CustomUserDetailsService.java
    ├── MesaService.java
    ├── PedidoService.java
    └── ProdutoService.java
```

```text
src/main/resources
├── application.properties
├── data.sql
├── static/css
│   ├── login.css
│   ├── mesas.css
│   ├── pedidos.css
│   ├── produtos.css
│   └── style.css
└── templates
    ├── home.html
    ├── login.html
    ├── mesas.html
    ├── pedidos.html
    └── produtos.html
```

## Principais Telas

### `/login`

Tela de autenticação do sistema.

### `/home`

Tela inicial com navegação para as áreas principais.

### `/mesas`

Gerenciamento de mesas, com cadastro, edição, exclusão, filtros, busca, ordenação e dashboard.

### `/produtos`

Gerenciamento de produtos e categorias, com filtros, busca, ordenação, dashboard e controle de status ativo/inativo.

### `/pedidos`

Tela operacional principal para atendimento. Agrupa clientes por mesa, exibe contas, pedidos, itens, totais e ações de criação/fechamento.

## Regras de Negócio

### Mesas

- Toda mesa nasce com status `LIVRE`.
- Ao abrir uma conta em uma mesa, a mesa passa para `OCUPADA`.
- Ao fechar a última conta aberta de uma mesa, a mesa passa para `FECHADA`.
- Não é permitido excluir uma mesa que possui contas vinculadas.
- Não é permitido abrir pedido em mesa fechada.

### Produtos

- O produto possui:
  - ID;
  - nome;
  - categoria;
  - preço;
  - status ativo/inativo.
- O ID do produto é controlado manualmente para permitir reutilização do menor ID disponível.
- Ao criar um produto, uma categoria inexistente é registrada automaticamente.
- Não é possível excluir uma categoria vinculada a produtos.
- Se um produto já foi usado em algum item de pedido, ele não é removido fisicamente; ele é marcado como inativo.
- Produtos inativos não podem ser vendidos.

### Pedidos

- Um pedido sempre pertence a uma conta.
- Uma conta sempre pertence a uma mesa.
- Não é permitido criar pedido em conta fechada.
- Não é permitido adicionar item em conta fechada.
- Se um produto já existe no mesmo pedido, adicionar novamente aumenta a quantidade do item existente.
- Remover item de conta fechada não é permitido.

### Contas

- Uma conta pode ter taxa de serviço.
- A taxa de serviço atual é de 10%.
- O total da conta é:

```text
subtotal + taxa de serviço
```

- Não é possível fechar uma conta sem itens.
- Pagamento só pode ser registrado após o fechamento da conta.

## Rotas da Aplicação

### Rotas de tela

```text
GET  /login
GET  /logout
GET  /
GET  /home
GET  /mesas
GET  /produtos
GET  /pedidos
```

### Mesas

```text
GET  /mesas
POST /mesas
POST /mesas/editar/{id}
POST /mesas/excluir/{id}
```

Parâmetros de filtro em `GET /mesas`:

```text
q      -> busca por nome
status -> LIVRE, OCUPADA ou FECHADA
ordem  -> nome-asc ou nome-desc
```

### Produtos

```text
GET  /produtos
POST /produtos
POST /produtos/editar/{id}
POST /produtos/excluir/{id}
POST /produtos/categorias
POST /produtos/categorias/editar
POST /produtos/categorias/excluir
```

Parâmetros de filtro em `GET /produtos`:

```text
q      -> busca por nome
status -> todos, ativo ou inativo
ordem  -> id-asc, id-desc, nome-asc, nome-desc, preco-asc ou preco-desc
```

### Pedidos

```text
GET  /pedidos
POST /pedidos/abrir
POST /pedidos/contas/{contaId}/novo
POST /pedidos/{id}/itens
POST /pedidos/itens/{itemId}/remover
POST /pedidos/contas/{contaId}/fechar
```

Parâmetros de filtro em `GET /pedidos`:

```text
q      -> busca por cliente, mesa ou status
mesaId -> filtra por mesa
status -> ABERTA ou FECHADA
```

## API REST

Além das telas Thymeleaf, o projeto mantém endpoints REST para integrações e testes.

### Contas

```text
POST   /contas/abrir
GET    /contas
GET    /contas/{id}
PUT    /contas/{id}
DELETE /contas/{id}
POST   /contas/{id}/fechar
POST   /contas/{id}/pagamentos
GET    /contas/{id}/imprimir
```

Exemplo de abertura de conta:

```json
{
  "mesaId": 1,
  "cliente": "Joao",
  "individual": true,
  "taxaServico": true
}
```

Exemplo de pagamento:

```json
{
  "forma": "PIX",
  "valor": 52.50
}
```

### Produtos

```text
POST   /produtos/api
GET    /produtos/api
GET    /produtos/api/{id}
PUT    /produtos/api/{id}
DELETE /produtos/api/{id}
```

Exemplo de produto:

```json
{
  "nome": "Cerveja Garrafa",
  "categoria": "Cervejas",
  "preco": 12.00,
  "ativo": true
}
```

### Pedidos

```text
POST   /pedidos/api
GET    /pedidos/api
GET    /pedidos/api/{id}
PUT    /pedidos/api/{id}
DELETE /pedidos/api/{id}
POST   /pedidos/api/{id}/itens
DELETE /pedidos/api/itens/{itemId}
```

Exemplo de criação de pedido:

```json
{
  "contaId": 1
}
```

Exemplo de adição de item:

```json
{
  "produtoId": 1,
  "quantidade": 2
}
```

## Design System

O projeto usa um padrão visual próprio do Seriguela Bar.

### Cores

```css
--vermelho: #8F2325;
--verde: #3F5513;
--amarelo: #E29B18;
--branco: #FFFFFF;
```

### Gradiente padrão

```css
linear-gradient(135deg, var(--vermelho) 0%, var(--amarelo) 100%);
```

### Tipografia

- Títulos: Fraunces
- Corpo: Nunito

### Elementos visuais

- Sidebar no desktop.
- Menu drawer no mobile.
- Overlay de ruído com `.noise-overlay`.
- Bordas arredondadas.
- Sombras suaves.
- Ícones Lucide.
- Cards e dashboards consistentes entre Mesas, Pedidos e Produtos.

## Comandos Úteis

Compilar:

```bash
mvn -DskipTests compile
```

Rodar:

```bash
mvn spring-boot:run
```

Empacotar:

```bash
mvn -DskipTests package
```

Rodar o JAR gerado:

```bash
java -jar target/bar-crud-1.0.0.jar
```

Validar startup sem servidor web:

```bash
mvn -DskipTests spring-boot:run "-Dspring-boot.run.arguments=--spring.main.web-application-type=none"
```

## Observações de Desenvolvimento

- O banco é em memória, então os dados voltam ao estado do `data.sql` quando a aplicação reinicia.
- O projeto usa `spring.jpa.hibernate.ddl-auto=create`; as tabelas são recriadas automaticamente.
- O CSRF está desabilitado em `SecurityConfig`, facilitando os formulários e testes locais.
- O `NoOpPasswordEncoder` está configurado para aceitar senha em texto puro no ambiente de desenvolvimento.
- Para produção, o ideal é trocar para `BCryptPasswordEncoder`.
- Como o H2 é apenas para desenvolvimento, um banco persistente como PostgreSQL ou MySQL seria mais adequado em produção.
- O diretório `target/` contém artefatos de build e não deve ser editado manualmente.

## Possíveis Melhorias Futuras

- Adicionar testes automatizados de serviço e controller.
- Criar perfis separados para desenvolvimento e produção.
- Trocar H2 por PostgreSQL ou MySQL.
- Implementar senhas criptografadas com BCrypt.
- Adicionar controle de perfis/roles de usuário.
- Melhorar tratamento visual de erros em operações AJAX.
- Adicionar paginação para listas grandes.
- Criar relatórios de vendas por período.
- Criar controle de estoque.
- Registrar histórico de alterações em mesas, pedidos e produtos.
