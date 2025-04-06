# Trabalho Prático AEDS III

## Nome dos integrantes

 - Antônio Drumond Cota de Sousa
 - Laura Menezes Heráclito Alves
 - Davi Ferreira Puddo
 - Raquel de Parde Motta

## Intrudução

Neste trabalho prático, o objetivo foi desenvolver um sistema CRUD (Criar, Ler, Atualizar, Excluir)[^1] para gerenciar séries e episódios de plataformas de streaming, como Netflix, Prime, entre outras. Para isso, a estrutura de dados foi organizada com duas entidades principais: Série (que inclui atributos como nome, ano de lançamento, sinopse e plataforma de streaming) e Episódio (com dados como nome, temporada, data de lançamento e duração). O relacionamento entre as séries e episódios foi modelado como um relacionamento de 1:N, ou seja, uma série pode ter vários episódios, enquanto cada episódio pertence a uma única série. O sistema implementa as operações de inclusão, busca, alteração e exclusão para ambas as entidades, utilizando a Tabela Hash Extensível e a Árvore B+ como índices para otimizar o gerenciamento e a consulta dos dados.

O desenvolvimento seguiu o padrão MVC (Modelo, Visão, Controle)[^2], o que garantiu que a lógica de controle das operações de séries e episódios fosse separada da interface com o usuário. A interface inicial foi projetada para permitir que o usuário escolhesse entre diferentes opções de gerenciamento, como séries e episódios. A visão de cada entidade foi estruturada para apresentar os dados de forma clara, facilitando a consulta e a manipulação dos registros. Além disso, foi implementada uma verificação que impede a exclusão de uma série, ou a alteração de seu nome, caso haja episódios vinculados a ela, assegurando a integridade referencial dos dados. 

## Experiência

Foi nosso primeiro contato com a manipulação de um programa tão grande, composto de várias classes, que vão de mais genéricas a mais específicas. Apesar de a quantidade de arquivos de código ter nos assustado no início, ao longo do desenvolvimento ficou evidente porque encapsular funcionalidades, planejar interfaces, criar classes genéricas e utilizar cláusulas de "extends" e "includes" são consideradas boas práticas de programação. O grupo também percebeu as vantagens da orientação a objetos em Java para produção em escala comparado a linguagens puramente procedimentais, onde o foco é apenas em funções, como C.

Foi extremamente produtivo poder reutilizar várias funcionalidades de classes genéricas para entidades específicas. A separação de "níveis" de funcionalidade também permitiu debugar com mais facilidade, pois apenas algumas classes tinham acesso ao arquivo, enquanto apenas algumas outras lidavam com input do usuário, enquanto outras só guardavam as informações e funções mais relevantes para uma entidade, etc. Ademais, os códigos do Menu e Principal ficaram bem mais sucintos ao utilizar recursos de diversas classes pré-elaboradas. Outro ponto é que quando precisávamos "dar uma olhada" rapidamente no que uma classe fazia em sua essência, sempre recorríamos às interfaces. 

Um quesito interessante é que o desenvolvimento deste programa complementou muito bem o conteúdo que estamos vendo na disciplina "Banco de Dados". Enquanto em BD trabalhamos com SQL, uma linguagem de consulta de alto nível, em AED3 pudemos ver "o que há por baixo", isto é, todo o trabalho interno feito para um CRUD ser bem sucedido. Ademais, todos os integrantes acreditam que o trabalho prático concretizou com muita clareza a teoria que vimos em sala.

### Desafios

Como mencionado anteriormente, o primeiro desafio foi o choque inicial com a quantidade de classes e linhas de código, mas que foi rapidamente superado. Ademais, por ser nossa primeira vez utilizando algorimos de Tabela Hash Extensível[^3] e Árvore B+ [^4], tivemos alguns problemas entendendo e lidando com seus métodos, além de dificuldades na manipulação dos dados dentro/por meio dessas estruturas. Especialmente na Árvore B+, cuja explicação ainda não vimos em sala, o armazenamento de índices ainda é um pouco "misterioso" para nós. 

Além disso, instruções de update foram um desafio para implementação e funcionamento, já que tivemos dúvidas a respeito de como funcionaria a alteração de nomes de séries sem prejudicar a integridade referencial, pois depreendemos que o relacionamento série-episódio fosse obrigatório para o lado do episódio. Para resolver essa questão, impedimos que uma série fosse deletada ou tivesse seu nome alterado se existissem episódios linkados a ela. Para maior praticidade do usuário, adicionamos então a funcionalidade no menu "Episódios" de excluir de uma só vez todos os episódios de uma determinada série.

Durante a testagem, também decidimos impedir o cadastro de séries de nomes iguais, porém não de episódios de nomes iguais. Se, ao buscar um nome de episódio, existir mais de um episódio com o mesmo nome, são listados todos os resultados correspondentes - no momento em que o usuário quer editar ou deletar, são mostradas as descrições de todos os episódios recuperados com o nome fornecido e é possível escolher a qual opção ele se refere.

### Resultados

Com grande esforço, conseguimos realizar tudo que o problema pedia. Criamos, portanto, uma interface semelhante "streaming-like" onde temos os dados e metadados das séries e episódios cadastrados. Toda nova entidade cadastrada é passível de ser buscada, atualizada e excluída com facilidade. Adicionamos também funcionalidades para listar todos os episódios de uma série, listar todas as séries cadastradas e excluir todos os episódios de uma série. Por demais, lidadmos confrontos que poderiam dar problemas futuros na interação usuário-método, como por exemplo, nomes de episódios repetidos.

## Código

### Classes 

#### Entidades

##### Episodio.java

A classe Episodio representa um episódio de uma série de TV, com atributos como ID único, ID da série, nome, número da temporada, data de lançamento (lancamento) e duração em minutos. Ela implementa a interface Registro, que provavelmente define métodos para serialização e desserialização. A classe inclui métodos como fromByteArray, que reconstrói um objeto Episodio a partir de um array de bytes, lendo seus atributos com um DataInputStream. Esse método garante que os dados sejam lidos na ordem correta, convertendo-os de volta para os tipos apropriados, como inteiros, strings e LocalDate. Isso é útil para armazenar e recuperar objetos Episodio em formatos binários, adequados para sistemas de armazenamento como banco de dados ou arquivos.

##### Serie.java

A classe Serie representa uma série de TV e implementa a interface Registro, que provavelmente define métodos para gerenciar registros. Ela possui atributos como ID único, nome, data de lançamento (lancamento), sinopse e nome da plataforma de streaming (streaming). A classe oferece dois construtores: um que inicializa todos os atributos, incluindo o ID, e outro que não inclui o ID, útil para criar novas instâncias onde o ID é atribuído posteriormente. Esse design torna a classe Serie adequada para aplicações que gerenciam informações sobre séries de TV, como bancos de dados ou catálogos de serviços de streaming.

#### Modelo

##### ArquivoEpisodios.java

A classe ArquivoEpisodios estende Arquivo<Episodio> e gerencia o armazenamento e indexação de objetos Episodio. Ela utiliza duas árvores B+ (ArvoreBMais) para indexação: uma para mapear nomes de episódios aos seus IDs (indiceNome) e outra para relacionar os IDs das séries aos IDs dos episódios (indiceRelacaoSerieEp). O construtor inicializa a estrutura de arquivos para armazenar os episódios, garantindo a existência das pastas necessárias, e configura os índices B+ com classes específicas (ParNomeId e ParIdId) e caminhos para os bancos de dados dos índices.

##### ArquivoSeries.java

A classe ArquivoSeries estende Arquivo<Serie> e gerencia o armazenamento e indexação de objetos Serie. Ela cria a estrutura de diretórios (./dados/serie) para armazenar os dados das séries e usa uma árvore B+ (indiceNome) para mapear nomes de séries aos seus IDs, permitindo buscas eficientes. O construtor configura a estrutura de arquivos e o índice B+, com grau 5, armazenando-o em ./dados/serie/indiceNome.db.

##### ParNomeId.java
A classe ParNomeId representa um par composto por uma String (nome) e um int (ID), projetada para ser armazenada em uma árvore B+ como um índice indireto para entidades como títulos. Ela implementa a interface RegistroArvoreBMais, que provavelmente define métodos para serialização, desserialização e comparação necessários para operações na árvore B+. A classe inclui utilitários para normalização de strings e codificação, garantindo compatibilidade e armazenamento eficiente na estrutura da árvore B+.

#### Registro

##### Arquivo.java

A classe Arquivo é genérica e gerencia o armazenamento de arquivos para entidades que implementam a interface Registro. Ela cria diretórios (./dados/<nome_da_entidade>) e um arquivo de banco de dados (<nome_da_entidade>.db) para armazenar registros serializados, usando RandomAccessFile para acesso direto. A classe utiliza um índice extensível baseado em hash (HashExtensivel<ParIDEndereco>) para indexar registros de forma eficiente.

##### ArvoreBMaisjava

A classe ArvoreBMais implementa uma estrutura de dados de árvore B+, projetada para armazenar e gerenciar pares de chaves, onde a primeira chave pode se repetir, mas a combinação das chaves deve ser única. Ela usa um tipo genérico T que estende a interface RegistroArvoreBMais, permitindo lidar com objetos personalizados enquanto garante a implementação dos métodos necessários para operações na árvore. A classe gerencia a estrutura da árvore e sua persistência usando RandomAccessFile, garantindo armazenamento e recuperação eficientes de dados diretamente de um arquivo.

##### HashExtensivel.java

A classe HashExtensivel implementa um mecanismo de hashing extensível para gerenciar o armazenamento de dados de forma eficiente, suportando operações como criação, leitura, atualização e exclusão baseadas em códigos de hash. Ela utiliza um arquivo de diretório (nomeArquivoDiretorio) e arquivos de cestos (nomeArquivoCestos) para gerenciar dinamicamente os dados, garantindo escalabilidade à medida que o conjunto de dados cresce. A classe é genérica, exigindo que o tipo T estenda a interface RegistroHashExtensivel, que assegura que os objetos armazenados implementem métodos de serialização e desserialização.

##### ParIDEndereco.java

A classe ParIDEndereco representa um par composto por um ID inteiro (chave) e um endereço longo (valor), projetado para ser usado em estruturas de hashing extensível. Ela implementa a interface RegistroHashExtensivel, fornecendo métodos para serialização, desserialização e gerenciamento de registros de tamanho fixo de 12 bytes.

##### ParIdId.java

A classe ParIdId representa um par de IDs inteiros (id1 e id2) e fornece métodos para comparação, serialização (toByteArray) e desserialização (fromByteArray). Ela implementa lógica para ordenação e busca, onde id1 é a chave primária e id2 é comparado condicionalmente para suportar buscas baseadas em listas.

##### Registro.java

A interface Registro define métodos para definir e obter um ID, além de métodos para serializar e desserializar objetos de e para arrays de bytes.

##### RegistroArvoreBMais.java

A interface RegistroArvoreBMais define métodos para registros de tamanho fixo, incluindo serialização, desserialização, comparação e clonagem, para suportar operações em uma estrutura de árvore B+.

##### RegistroHashExtensivel.java

The RegistroHashExtensivel interface defines methods for objects to be stored in an extensible hash table, including generating a hash code, determining fixed size, and handling serialization and deserialization.

#### Visao

##### MenuEpisodios.java

A classe MenuEpisodios fornece uma interface de usuário para gerenciar episódios, permitindo que os usuários realizem operações como buscar, adicionar, atualizar e excluir episódios. Ela inicializa instâncias de ArquivoEpisodios e ArquivoSeries para gerenciar o armazenamento e a recuperação dos dados de episódios e séries. O método menu exibe um menu baseado em texto com opções para o gerenciamento de episódios e processa a entrada do usuário para executar as ações correspondentes. A classe inclui tratamento de erros para entradas inválidas usando um bloco try-catch, garantindo que o programa continue funcionando sem problemas.

##### MenuSeries.java

A classe MenuSeries fornece uma interface de usuário para gerenciar séries de TV, permitindo que os usuários realizem operações como buscar, adicionar, atualizar e excluir séries. Ela inicializa instâncias de ArquivoSeries e ArquivoEpisodios para gerenciar o armazenamento e a recuperação dos dados de séries e episódios, garantindo a consistência dos dados. O método menu exibe um menu baseado em texto com opções para o gerenciamento de séries e processa a entrada do usuário para executar as ações correspondentes. A classe inclui uma funcionalidade para verificar se há episódios associados antes de excluir uma série, evitando inconsistências nos dados. Ela utiliza um objeto estático Scanner para ler a entrada do usuário no console para operações interativas.

#### Principal.java

A classe Principal serve como o ponto de entrada principal da aplicação, oferecendo uma interface baseada em menu para navegar entre as funcionalidades do MenuSeries e MenuEpisodios. Ela utiliza um loop para continuamente solicitar a entrada do usuário, tratando opções inválidas de forma adequada e permitindo que o programa seja encerrado quando o usuário selecionar a opção 0.

### Métodos gerais

#### ArquivoSeries.java

##### Create ( Serie s )

Adiciona um novo objeto `Serie` ao armazenamento e atualiza o índice baseado no nome.

##### readNome ( String nome )

Recupera todos os objetos `Serie` que correspondem a um nome específico.

#### ArquivoEpisodios.java

##### Construtor

Inicializa a classe `ArquivoEpisodios` configurando a estrutura de diretórios e os índices da árvore B+ para gerenciar episódios.

##### Create ( Episodio ep )

Adiciona um novo objeto `Episodio` ao armazenamento e atualiza os índices para nome e relações série-episódio.

##### readNome ( String nome )

Recupera todos os objetos `Episodio` que correspondem a um nome específico.

#### Arquivo.java

##### create ( T obj )

Adiciona um novo registro do tipo T ao arquivo.

##### read ( int id )

Recupera um registro pelo seu ID único.

##### update ( T obj )

Atualiza um registro existente no arquivo.

##### delete ( int id )

Exclui um registro marcando seu espaço como livre.

##### searchFreeSpace(int tamanhoNecessario)

Procura na lista de espaço livre um bloco grande o suficiente para armazenar um registro do tamanho necessário.

##### toByteArray() e fromByteArray(byte[] array)

Lida com a serialização e desserialização de objetos do tipo T.

##### seek(long endereco)

Move o ponteiro do arquivo para um endereço específico no arquivo.

##### listAll()

Lista todos os registros no arquivo.

#### MenuEpisodios.java

##### MenuEpisodios()

Inicializa a classe `MenuEpisodios` configurando as estruturas de arquivos necessárias para gerenciar episódios e séries.

##### menu()

Exibe um menu para gerenciar episódios e processa a entrada do usuário para executar as ações correspondentes.

##### buscarEpisodio()

Busca por um episódio com base nos critérios fornecidos pelo usuário.

##### incluirEpisodio()

Adiciona um novo episódio ao sistema.

##### alterarEpisodio()

Atualiza os detalhes de um episódio existente.

##### excluirEpisodio()

Exclui um episódio do sistema.

#### MenuSeries()

##### MenuSeries()

Inicializa a classe `MenuSeries` configurando as estruturas de arquivos necessárias para gerenciar séries e episódios.

##### menu()

Exibe um menu para gerenciar séries e processa a entrada do usuário para executar as ações correspondentes.

### Operações

Todas as operações foram implementadas, sendo a mais desafiadora a UPDATE, já que envolve a alteração de índices indiretos e por utilizar diferentes métodos de criação e de remoção de registros para que o método em si funcione.

## Checklist

- [x] As operações de inclusão, busca, alteração e exclusão de séries estão implementadas e funcionando corretamente? **SIM**
- [x] As operações de inclusão, busca, alteração e exclusão de episódios, por série, estão implementadas e funcionando corretamente? **SIM**
- [x] Essas operações usam a classe CRUD genérica para a construção do arquivo e as classes Tabela Hash Extensível e Árvore B+ como índices diretos e indiretos? **SIM**
- [x] O atributo de ID de série, como chave estrangeira, foi criado na classe de episódios? **SIM**
- [x] Há uma árvore B+ que registre o relacionamento 1:N entre episódios e séries? **SIM**
- [x] Há uma visualização das séries que mostre os episódios por temporada? **SIM**
- [x] A remoção de séries checa se há algum episódio vinculado a ela? **SIM**
- [x] A inclusão da série em um episódio se limita às séries existentes? **SIM**
- [x] O trabalho está funcionando corretamente? **SIM**
- [x] O trabalho está completo? **SIM**
- [x] O trabalho é original e não a cópia de um trabalho de outro grupo? **SIM**


[^1]: https://www.codecademy.com/article/what-is-crud
[^2]: https://www.devmedia.com.br/introducao-ao-padrao-mvc/29308
[^3]: https://www.ic.unicamp.br/~thelma/gradu/MC326/2010/Slides/Aula09a-hash-Extensivel.pdf
[^4]: https://marciobueno.com/arquivos/ensino/ed2/ED2_04_Arvore_B+.pdf

