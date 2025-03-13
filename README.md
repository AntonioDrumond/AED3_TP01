# Trabalho Pratico 01

O primeiro trabalho prático realizado na disciplina Algoritmos e Estruturas de Dados III

## Integrantes
+ Antônio Drumond Cota de Sousa
+ Davi Ferreira Puddo
+ Raquel de Parde Motta
+ Laura Menezes Heráclito Alves

## Enunciado

### A ENTIDADE SÉRIE

- Neste primeiro projeto, nós vamos fazer um CRUD de séries de streaming. Assim, precisaremos gerenciar a inclusão, alteração, exclusão e consulta de séries. Cada série deve ter, nesta etapa do trabalho, pelo menos os seguintes dados:

  - Nome
  - Ano lançamento
  - Sinopse
  - Streaming (Netflix, Prime, Max, Disney, ...)

- Sinta-se à vontade para criar mais atributos que fizerem sentido para você e lembre-se de que toda entidade precisa de um identificador (ID) que, como vimos nas aulas, será um número sequencial inteiro. Note que esse tipo de entidade não tem um outro atributo exclusivo (como um CPF ou ISBN).

### A ENTIDADE EPISÓDIO

- Uma série é composta por vários episódios. E cada episódio pertence a uma única série. É por isso que dizemos que o relacionamento é de 1 para N (ou 1:N):

  - 1 série tem N episódios
  - 1 episódio pertence a 1 única série

- Cada episódio deve ter, pelo menos, os seguintes atributos (além do ID):

  - Nome
  - Temporada
  - Data de lançamento
  - Duração (em minutos)

- Novamente, sinta-se à vontade para criar mais atributos, como uma sinopse própria do episódio. Lembre-se de que você precisará de uma chave estrangeira nessa entidade (o ID da série).

### CÓDIGO QUE JÁ ESTÁ PRONTO

- Nesse projeto, você deve necessariamente usar o [CRUD genérico](https://github.com/kutova/AEDsIII/tree/main/CRUD2) que desenvolvemos em sala como base. Nosso CRUD cria registros com a seguinte estrutura:

  - Lápide - Byte que indica se o registro é válido ou se é um registro excluído;
  - Indicador de tamanho do registro - Número inteiro (short) que indica o tamanho do vetor de bytes;
  - Vetor de bytes - Bytes que descrevem a entidade (obtido por meio do método toByteArray() do próprio objeto da entidade).

- Além disso, você precisará usar as classes [TabelaHashExtensível](https://github.com/kutova/AEDsIII/tree/main/TabelaHashExtensivel) e [Árvore B+](https://github.com/kutova/AEDsIII/tree/main/ArvoreBMais) que disponibizei para criar os índices. Não vale inventar uma nova estrutura de dados para os índices nesse projeto, ok?

### PROGRAMA PRINCIPAL

- O programa principal agora já deve oferecer uma interface para o usuário, por meio da qual ele possa fazer inclusões, alterações, buscas e exclusões, para todas as entidades. A sugestão é que você ofereça uma interface inicial semelhante a esta:

```shell
    PUCFlix 1.0
    -----------
    > Início

    1) Séries
    2) Episódios
    3) Atores
    0) Sair
```

- Para cada entidade, a interface poderia ser semelhante a esta:

```shell
    PUCFlix 1.0
    -----------
    > Início > Séries

    1) Incluir
    2) Buscar
    3) Alterar
    4) Excluir
    0) Retornar ao menu anterior
```

- Lembre-se de que não colocamos código de interface com o usuário (visão) na mesma classe que o acesso aos dados (modelo). Tentaremos seguir o [padrão MVC](https://pt.wikipedia.org/wiki/MVC). Assim, você deveria criar uma classe VisaoSeries que conteria todas as operações de entrada e de saída de dados relacionadas a séries (não inclui o menu acima). Por exemplo, você poderia ter pelo menos uma função `leSerie()` e outra `mostraSerie()`. Finalmente, teria uma outra classe responsável pela lógica da operação que poderia se chamar `ControleSeries`. Essa última classe seria responsável pelo menu e pela lógica das operações de inclusão, alteração e exclusão, entre outras. Ela acessaria os arquivos necessários, bem como chamaria as funções da visão.

### O QUE DEVE SER FEITO?

- Implementar o CRUD de Séries.
- Implementar o CRUD de Episódios, assegurando que cada episódio pertença a uma série específica.
- Implementar o relacionamento 1:N com o par (idSerie; idEpisódio) usando a Árvore B+.
- Criar a visão e o controle de séries. Assegurar que uma série não possa ser excluída se algum episódio estiver vinculado a ela.
- Criar a visão e o controle de episódios. O usuário deve escolher previamente a série específica cujos episódios vai controlar
- Na visão das séries, permitir a visão dos episódios por temporada.

Observe que para tudo funcionar, você precisará acessar os arquivos e as visões de episódios e de séries em todas as classes de controle.

### FORMA DE ENTREGA

- Vocês devem postar o seu trabalho no GitHub e enviar apenas o URL do seu projeto. Criem um repositório específico para este projeto (ao invés de mandar o repositório pessoal de algum de vocês em que estejam todos os seus códigos). Acrescentem um arquivo readme.md ao projeto que será o relatório do trabalho de vocês. Nele, descrevam um pouco o esforço. Mesmo que eu tenha acabado de especificar, acima, o que eu gostaria que fosse feito, eu gostaria de ver a descrição do seu trabalho nas suas próprias palavras. Basicamente, vocês devem responder à seguinte pergunta: O que o trabalho de vocês faz?

- Em seguida, listem os nomes dos participantes e descrevam todas as classes criadas e os seus métodos principais. O objetivo é que vocês facilitem ao máximo a minha correção, de tal forma que eu possa entender com facilidade tudo aquilo que fizeram e dar uma nota justa.

- Finalmente, relatem um pouco a experiência de vocês, explicando questões como: Vocês implementaram todos os requisitos? Houve alguma operação mais difícil? Vocês enfrentaram algum desafio na implementação? Os resultados foram alcançados? ... A ideia, portanto, é relatar como foi a experiência de desenvolvimento do TP. Aqui, a ideia é entender como foi para vocês desenvolver este TP.

- Para concluir, vocês devem, necessariamente, responder ao seguinte checklist (copie as perguntas abaixo para o seu relatório e responda sim/não em frente a elas):

  - As operações de inclusão, busca, alteração e exclusão de séries estão implementadas e funcionando corretamente
  - As operações de inclusão, busca, alteração e exclusão de episódios, por série, estão implementadas e funcionando corretamente
  - Essas operações usam a classe CRUD genérica para a construção do arquivo e as classes Tabela Hash Extensível e Árvore B+ como índices diretos e indiretos?
  - O atributo de ID de série, como chave estrangeira, foi criado na classe de episódios
  - Há uma árvore B+ que registre o relacionamento 1:N entre episódios e séries
  - Há uma visualização das séries que mostre os episódios por temporada
  - A remoção de séries checa se há algum episódio vinculado a ela
  - A inclusão da série em um episódio se limita às séries existentes
  - O trabalho está funcionando corretamente
  - O trabalho está completo
  - O trabalho é original e não a cópia de um trabalho de outro grupo?

- Lembre-se de que, para essa atividade, eu avaliarei tanto o esforço quanto o resultado. Portanto, escrevam o relatório de forma que me ajude a observar o resultado.

### DISTRIBUIÇÃO DE PONTOS

- Essa atividade vale 5 pontos. A rubrica de avaliação estabelece os critérios que serão usados na correção.

- Atenção: o TP é específico por grupo. TPs copiados de outros grupos, que não evidenciem um esforço mínimo do próprio grupo, serão anulados.

- Se tiver dúvidas sobre o trabalho a fazer, me avise. Não deixe de observar que o URL com o código no GitHub deve ser entregue até o dia especificado na atividade.
