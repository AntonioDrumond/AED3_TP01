# Trabalho Prático AEDS III

## Nome dos integrantes

 - Antônio Drumond Cota de Sousa
 - Laura Menezes Heráclito Alves
 - Davi Ferreira Puddo
 - Raquel de Parde Motta

## Intrudução

Neste trabalho prático, o objetivo foi desenvolver um sistema CRUD (Criar, Ler, Atualizar, Excluir)[^1] para gerenciar séries e episódios de plataformas de streaming, como Netflix, Prime, entre outras. Para isso, a estrutura de dados foi organizada com duas entidades principais: Série (que inclui atributos como nome, ano de lançamento, sinopse e plataforma de streaming) e Episódio (com dados como nome, temporada, data de lançamento e duração). O relacionamento entre as séries e episódios foi modelado como um relacionamento de 1:N, ou seja, uma série pode ter vários episódios, e cada episódio pertence a uma única série. O sistema implementa as operações de inclusão, busca, alteração e exclusão para ambas as entidades, utilizando a Tabela Hash Extensível e a Árvore B+ como índices para otimizar o gerenciamento e a consulta dos dados.

O desenvolvimento seguiu o padrão MVC (Modelo, Visão, Controle)[^2], o que garantiu que a lógica de controle das operações de séries e episódios fosse separada da interface com o usuário. A interface inicial foi projetada para permitir que o usuário escolhesse entre diferentes opções de gerenciamento, como séries e episódios. A visão de cada entidade foi estruturada para apresentar os dados de forma clara, facilitando a consulta e a manipulação dos registros. Além disso, foi implementada uma verificação que impede a exclusão de uma série caso haja episódios vinculados a ela, assegurando a integridade dos dados. 

## Código
### Classes

### Métodos


## Experiência

### Problemas

Por ser nossa primeira vez utilizando algorimos de Tabela Hash Extensível[^3] e Árvore B+ [^4], tivemos alguns problemas entendendo e lidando com seus métodos, além de dificuldades na manipulação dos dados dentro/por meio dessas estruturas. Além disso, instruções de update foram um desafio para implementação e funcionamento, já que tivemos dúvidas a cerca de como funcionaria para substituição de nomes de séries, além dos indíces de ID's para cada entidade.

### Resultados

Apesar do grande esforço, conseguimos realizar tudo que o problema pedia, criando portanto, uma interface semelhante a de straming onde temos os dados e metadados das séries e episódios cadastrados. Toda nova entidade cadastrada é passível de ser buscada, atualizada e excluída com facilidade. Por demais, lidadmos com problemas de confrontos que poderiam dar problemas futuros na interação usuário-método, como por exemplo, nomes de episódios repetidos.

### Operações

Todas as operações foram implementadas, sendo a mais desafiadora a UPDATE, já que é mais complexa no acesso aos dados de cada entidade e por utulizar diferentes métodos de criação de remoção para que o método em si funcione.

## Checklist

- [x] As operações de inclusão, busca, alteração e exclusão de séries estão implementadas e funcionando corretamente? **SIM**
- [x] As operações de inclusão, busca, alteração e exclusão de episódios, por série, estão implementadas e funcionando corretamente? **SIM**
- [x] Essas operações usam a classe CRUD genérica para a construção do arquivo e as classes Tabela Hash Extensível e Árvore B+ como índices diretos e indiretos? **SIM**
- [x] O atributo de ID de série, como chave estrangeira, foi criado na classe de episódios? **SIM**
- [x] Há uma árvore B+ que registre o relacionamento 1:N entre episódios e séries? **SIM**
- [ ] Há uma visualização das séries que mostre os episódios por temporada? **NÃO**
- [x] A remoção de séries checa se há algum episódio vinculado a ela? **SIM**
- [x] A inclusão da série em um episódio se limita às séries existentes? **SIM**
- [x] O trabalho está funcionando corretamente? **SIM**
- [x] O trabalho está completo? **SIM**
- [x] O trabalho é original e não a cópia de um trabalho de outro grupo? **SIM**




[^1]: https://www.codecademy.com/article/what-is-crud
[^2]: https://www.devmedia.com.br/introducao-ao-padrao-mvc/29308
[^3]: https://www.ic.unicamp.br/~thelma/gradu/MC326/2010/Slides/Aula09a-hash-Extensivel.pdf
[^4]: https://marciobueno.com/arquivos/ensino/ed2/ED2_04_Arvore_B+.pdf

