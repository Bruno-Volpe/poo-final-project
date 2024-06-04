### Manual do Usuário para o Sistema POS Java Café

#### Visão Geral
O Sistema POS Java Café é uma aplicação simples de ponto de venda projetada para pequenos cafés. Ele permite que os usuários gerenciem o estoque, processem pedidos e calculem o custo total das compras, incluindo impostos. A aplicação possui uma interface gráfica (GUI) construída com Java Swing.

#### Instalação
1. Certifique-se de ter o Java Development Kit (JDK) instalado no seu computador.
2. Baixe os arquivos de código-fonte e compile-os usando seu IDE preferido ou linha de comando.

#### Executando a Aplicação
Para iniciar a aplicação, execute a classe `Main`. Isso abrirá a janela principal do sistema POS.

#### Interface Principal
A janela principal do sistema POS possui os seguintes componentes:
- **Botões**: Localizados na parte superior da janela, esses botões permitem que você execute várias ações.
- **Detalhes do Pedido**: Exibido no lado esquerdo da janela, esta área mostra os itens atuais no pedido.
- **Detalhes do Estoque**: Exibido no lado direito da janela, esta área mostra os itens atuais no estoque.

#### Botões e Suas Funções
1. **Adicionar ao Estoque**: Adicionar itens ao estoque.
2. **Adicionar à Comanda**: Adicionar itens do estoque ao pedido.
3. **Deletar da Comanda**: Remover itens do pedido atual.
4. **Deletar do Estoque**: Remover itens do estoque.
5. **Fechar a Conta**: Calcular o custo total do pedido atual, incluindo impostos, e exibir um recibo.

### Instruções Detalhadas

#### Adicionando Itens ao Estoque
1. Clique no botão `Adicionar ao Estoque`.
2. Digite o nome do produto na caixa de diálogo de entrada e clique em `OK`.
3. Digite o preço do produto na próxima caixa de diálogo de entrada e clique em `OK`.
4. Digite a quantidade do produto na última caixa de diálogo de entrada e clique em `OK`.
5. O novo item será adicionado ao estoque e exibido na área de Detalhes do Estoque.

#### Adicionando Itens ao Pedido
1. Clique no botão `Adicionar à Comanda`.
2. Digite o nome do produto na caixa de diálogo de entrada e clique em `OK`.
3. Digite a quantidade do produto na próxima caixa de diálogo de entrada e clique em `OK`.
4. Se o produto existir no estoque e houver quantidade suficiente, ele será adicionado ao pedido e exibido na área de Detalhes do Pedido.

#### Removendo Itens do Pedido
1. Clique no botão `Deletar da Comanda`.
2. Digite o nome do produto a ser removido na caixa de diálogo de entrada e clique em `OK`.
3. Digite a quantidade do produto a ser removido na próxima caixa de diálogo de entrada e clique em `OK`.
4. Se o produto existir no pedido e a quantidade for válida, ele será removido do pedido e retornado ao estoque. Os detalhes atualizados do pedido e do estoque serão exibidos.

#### Removendo Itens do Estoque
1. Clique no botão `Deletar do Estoque`.
2. Digite o nome do produto a ser removido na caixa de diálogo de entrada e clique em `OK`.
3. Se o produto existir no estoque, ele será removido. Os detalhes atualizados do estoque serão exibidos.

#### Fechando a Conta
1. Clique no botão `Fechar a Conta`.
2. O sistema calculará o custo total do pedido, incluindo um imposto de 10%.
3. Uma caixa de diálogo exibirá o custo total com impostos arredondado para duas casas decimais.
4. Os detalhes do pedido serão limpos e um recibo será gerado e impresso no console.

### Tratamento de Erros
- Se você inserir dados inválidos (por exemplo, valores não numéricos para preço ou quantidade), uma mensagem de erro será exibida.
- Se você cancelar qualquer caixa de diálogo de entrada, a operação será abortada e nenhuma alteração será feita.

### Dicas
- Sempre verifique a área de Detalhes do Estoque para garantir que você tem estoque suficiente antes de adicionar itens ao pedido.
- Use a área de Detalhes do Pedido para revisar o pedido atual antes de fechar a conta para garantir que todos os itens estão corretos.

### Solução de Problemas
- **Aplicação não inicia**: Certifique-se de que você tem o JDK instalado e o código-fonte foi corretamente compilado.
- **NullPointerException**: Certifique-se de que todas as caixas de diálogo de entrada são completadas ou canceladas corretamente para evitar valores nulos.
- **Erros de entrada inválida**: Verifique novamente se você está inserindo valores numéricos onde necessário (preço e quantidade).

Este manual do usuário deve ajudá-lo a operar o Sistema POS Java Café de forma eficaz. Se você encontrar algum problema, consulte a seção de solução de problemas ou procure assistência.