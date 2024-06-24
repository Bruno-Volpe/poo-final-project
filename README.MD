# Project Report: Sistema POS Java Café

## Grupo
- Bruno Giacomini Volpe - 14651980
- Guilherme Xavier - 14575641
- André Dalla Déa Trombini - 14558082

## 1. Requirements
- Gerenciamento de estoque.
- Processamento de pedidos.
- Cálculo do custo total das compras, incluindo impostos.
- Interface gráfica para interação do usuário.
- Suporte para salvar e carregar dados de inventário e pedidos em arquivos de texto (`.txt`).
- Interface gráfica aprimorada com botões para criar, adicionar, remover e fechar comandas.

## 2. Project Description
Nosso projeto implementa as funcionalidades principais do Sistema POS Java Café, conforme descrito nos requisitos. A interface gráfica é construída utilizando Java Swing, e os dados de inventário e pedidos são armazenados em arquivos de texto para persistência entre sessões.

### Implementação das Funcionalidades
- **Main**: Classe principal que inicia a aplicação.
- **POSUI**: Classe que define a interface gráfica e a lógica de interação com o usuário.
- **Inventory**: Classe que gerencia o inventário de produtos.
- **Order**: Classe que gerencia os pedidos (comandas).
- **Product**: Classe que representa um produto no inventário ou no pedido.

## 3. Comments About the Code
### Main
A classe `Main` é o ponto de entrada do aplicativo. Ela contém o método `main` que inicializa a interface gráfica chamando o construtor da classe `POSUI`.

### POSUI
A classe `POSUI` é responsável por toda a interface gráfica e lógica de interação com o usuário. Aqui estão alguns detalhes importantes:
- **Componentes Gráficos**: Utiliza componentes Swing como `JButton`, `JTextArea`, `JTabbedPane` e `JScrollPane` para construir a interface do usuário.
- **Métodos de Configuração**: `setupUI` configura o layout inicial da janela principal e adiciona botões e áreas de texto.
- **Carregamento e Salvamento de Dados**: Métodos `loadInventory` e `loadOrders` carregam dados de arquivos de texto, enquanto `saveInventory` e `saveOrders` salvam os dados atuais em arquivos de texto.
- **Interação com o Usuário**: Métodos como `addInventory`, `addItem`, `removeItemFromOrder` e `removeItemFromInventory` são acionados por botões e manipulam as interações do usuário com a aplicação.

### Inventory
A classe `Inventory` gerencia um mapa de produtos. 
- **Adição e Remoção de Produtos**: Métodos `addProduct` e `removeProduct` permitem adicionar e remover produtos do inventário.
- **Acesso aos Produtos**: O método `getProduct` retorna um produto específico, enquanto `getProducts` retorna todos os produtos no inventário.

### Order
A classe `Order` gerencia a lista de itens do pedido.
- **Adição e Remoção de Itens**: Métodos `addItem` e `removeItem` permitem adicionar e remover produtos do pedido.
- **Cálculo do Custo**: O método `getTotalCost` retorna o custo total do pedido, e `applyTaxes` aplica um imposto de 10% ao total.
- **Geração de Recibo**: O método `generateReceipt` imprime um recibo no console.

### Product
A classe `Product` representa um produto com nome, preço e quantidade.
- **Métodos de Acesso**: Métodos `getName`, `getPrice` e `getQuantity` permitem acesso às propriedades do produto.
- **Métodos de Modificação**: O método `setQuantity` permite modificar a quantidade do produto.

## 4. Test Plan
### Testes Manuais
#### Adicionar ao Estoque
1. Clique no botão `Adicionar ao Estoque`.
2. Insira o nome, preço e quantidade do produto.
3. Verifique se o produto é adicionado corretamente ao inventário.

#### Adicionar à Comanda
1. Clique no botão `Adicionar à Comanda`.
2. Insira o nome e a quantidade do produto.
3. Verifique se o produto é adicionado corretamente à comanda.

#### Remover da Comanda
1. Clique no botão `Deletar da Comanda`.
2. Insira o nome e a quantidade do produto a ser removido.
3. Verifique se o produto é removido corretamente da comanda e adicionado de volta ao inventário.

#### Fechar Comanda
1. Clique no botão `Fechar Comanda Atual`.
2. Verifique se o custo total, incluindo impostos, é calculado e exibido corretamente.
3. Verifique se o pedido é limpo após o fechamento.

#### Modificação de Quantidade no Estoque
1. Clique no botão `Adicionar ao Estoque`.
2. Insira o nome, preço e quantidade do produto.
3. Verifique se o produto é adicionado corretamente ao inventário com a quantidade especificada.

4. Clique no botão `Adicionar ao Estoque` novamente para o mesmo produto.
5. Insira a mesma quantidade ou uma quantidade diferente.
6. Verifique se a quantidade do produto no inventário é atualizada corretamente.

#### Remoção Completa de Produto do Estoque
1. Clique no botão `Adicionar ao Estoque`.
2. Insira o nome, preço e quantidade do produto.
3. Verifique se o produto é adicionado corretamente ao inventário.

4. Clique no botão `Deletar do Estoque`.
5. Insira o nome do produto a ser removido completamente.
6. Verifique se o produto é removido completamente do inventário.

#### Atualização e Fechamento de Comanda
1. Adicione alguns itens à comanda usando o botão `Adicionar à Comanda`.
2. Verifique se os itens são listados corretamente na comanda.

3. Modifique a quantidade de um item na comanda usando o botão `Editar Comanda`.
4. Insira o nome do produto e a nova quantidade.
5. Verifique se a quantidade do item na comanda é atualizada corretamente.

6. Feche a comanda usando o botão `Fechar Comanda Atual`.
7. Verifique se o custo total, incluindo impostos, é calculado corretamente e exibido na interface.
8. Verifique se a comanda é limpa após o fechamento, ou seja, não contém mais nenhum item.

### Testes de Verificação de Erros

#### Verificação de Entradas Inválidas
1. Tente adicionar um produto sem especificar todos os campos necessários (nome, preço, quantidade).
2. Verifique se a aplicação impede a adição do produto e exibe uma mensagem de erro adequada.

3. Tente fechar uma comanda sem adicionar nenhum item.
4. Verifique se a aplicação impede o fechamento da comanda e exibe uma mensagem de erro adequada.

#### Teste de Resiliência
1. Simule uma falha na leitura dos arquivos de dados (inventário e pedidos) durante a inicialização da aplicação.
2. Verifique se a aplicação lida corretamente com a falha e apresenta uma mensagem de erro ao usuário.

## 5. Test Results

### Testes Manuais
Todos os testes manuais foram realizados com sucesso, confirmando que as funcionalidades básicas e os cenários de erro estão operacionais conforme esperado.

## 6. Build Procedures
### Requisitos de Software
- Java Development Kit (JDK)

### Passos para Construção e Execução
1. Instalar o JDK.
2. Baixar o código-fonte do projeto.
3. Compilar o código usando um IDE (como IntelliJ ou Eclipse) ou linha de comando:
   ```bash
   javac Main.java
   ```
4. Executar a aplicação:
   ```bash
   java Main
   ```
5. A interface gráfica será aberta, permitindo a interação com o sistema POS.

## 7. Problems
### Principais Problemas
- **NullPointerException**: Inicialmente enfrentamos problemas com `NullPointerException` devido à ordem de inicialização dos componentes. Resolvido inicializando todos os componentes gráficos antes de carregar os dados.
- **Tratamento de Entrada**: Garantir que todas as entradas do usuário fossem válidas e tratadas corretamente exigiu a implementação de várias verificações e tratamento de exceções.

## 8. Comments
### Comentários Adicionais
O projeto fornece uma base sólida para um sistema POS básico, com possibilidade de expansão futura para incluir funcionalidades adicionais, como relatórios de vendas e integração com sistemas de pagamento. A experiência foi enriquecedora e proporcionou uma

compreensão mais profunda de desenvolvimento de aplicativos desktop em Java.