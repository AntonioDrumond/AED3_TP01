# Trabalho Prático AEDS III

## Intrudução
Este trabalho prático tem como objetivo o desenvolvimento de um sistema CRUD (Criar, Ler, Atualizar, Excluir)[^1] para gerenciar séries, episódios e atores de plataformas de streaming, como Netflix, Prime, entre outras. A estrutura de dados foi organizada com três entidades principais: Série (que inclui atributos como nome, ano de lançamento, sinopse e plataforma de streaming), Episódio (com dados como nome, temporada, data de lançamento e duração) e Ator (com informações como nome, data de nascimento e filmes/seriados em que atuou). O sistema implementa operações de inclusão, busca, alteração e exclusão para essas três entidades, utilizando a Tabela Hash Extensível e a Árvore B+ como índices, a fim de otimizar a busca e o gerenciamento dos dados.

Para garantir a organização e a separação de responsabilidades, o programa segue o padrão MVC (Modelo, Visão, Controle)[^2]. A lógica de controle das operações de séries, episódios e atores é mantida separada da interface com o usuário, promovendo uma melhor estruturação do código. A interface inicial permite ao usuário escolher entre diferentes opções (como gerenciar séries, episódios ou atores), e a visão de cada entidade apresenta os dados de forma adequada, possibilitando a consulta e manipulação dos registros. Além disso, o sistema assegura que uma série não seja excluída caso haja episódios vinculados a ela, garantindo a integridade dos dados ao associar episódios às séries existentes e possibilitando a gestão dos atores vinculados às séries e episódios.





[^1]: https://www.codecademy.com/article/what-is-crud
[^2]: https://www.devmedia.com.br/introducao-ao-padrao-mvc/29308
