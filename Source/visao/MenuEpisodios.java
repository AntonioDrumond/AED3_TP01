package visao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MenuEpisodios {
    
    ArquivoEpisodio arqEpisodios;
    private static Scanner console = new Scanner(System.in);

    public MenuEpisodios() throws Exception {
        arqEpisodios = new ArquivoEpisodio();
    }

    public void menu() {

        int opcao;
        do {

            System.out.println("\n\nAEDsIII");
            System.out.println("-------");
            System.out.println("> Início > Episódios");
            System.out.println("\n1 - Buscar");
            System.out.println("2 - Incluir");
            System.out.println("3 - Alterar");
            System.out.println("4 - Excluir");
            System.out.println("0 - Voltar");

            System.out.print("\nOpção: ");
            try {
                opcao = Integer.valueOf(console.nextLine());
            } catch(NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    buscarEpisodio();
                    break;
                case 2:
                    incluirEpisodio();
                    break;
                case 3:
                    alterarEpisodio();
                    break;
                case 4:
                    excluirEpisodio();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (opcao != 0);
    }


    public void buscarEpisodio() {
        System.out.println("\nBusca de episodio");
        String nome;
        try {
            Episodio episodio = arqEpisodios.read(nome);  // Chama o método de leitura da classe Arquivo
            if (episodio != null) {
                mostraEpisodio(episodio);  // Exibe os detalhes do episodio encontrado
            } 
            else {
                System.out.println("Episodio não encontrado.");
            }
        } catch(Exception e) {
            System.out.println("Erro do sistema. Não foi possível buscar o episodio!");
            e.printStackTrace();
        }
    }   


    public void incluirEpisodio() {
        System.out.println("\nInclusão de episodio");
        String nome = "";
        int IDSerie = 0;
        int temporada = 0;
        LocalDate lancamento = null;
        int duracao = 0;
        boolean dadosCorretos = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Serie[] sa = null;

        do{
            System.out.println("\nQual o nome da serie a qual esse episodio pertence? ");
            String nomeSerie = console.nextLine();
            sa = arqEpisodios.readNome(nomeSerie);
            if(sa == null)
                System.out.println("ERRO: Serie nao cadastrada. Tente novamente: ");
            else IDSerie = sa[0].getID();
        } while(sa==null);

        do {
            System.out.print("\nNome do episodio (min. de 1 letras ou vazio para cancelar): ");
            nome = console.nextLine();
            if(nome.length()==0)
                return;
            if(nome.length()<1)
                System.err.println("O nome do episodio deve ter no mínimo 1 caracteres.");
        } while(nome.length()<1);

        do{
            System.out.println("Temporada: (Numero inteiro positivo): ");
            temporada = console.nextInt();
            if(temporada<0)
                System.out.println("O numero da temporada deve ser inteiro e positivo: ");
        }while (temporada<0);

        do {
            System.out.print("Data de lancamento (DD/MM/AAAA): ");
            String dataStr = console.nextLine();
            dadosCorretos = false;
            try {
                lancamento = LocalDate.parse(dataStr, formatter);
                dadosCorretos = true;
            } catch (Exception e) {
                System.err.println("Data inválida! Use o formato DD/MM/AAAA.");
            }
        } while(!dadosCorretos);

        do{
            System.out.println("Duracao em minutos: (Numero inteiro positivo): ");
            temporada = console.nextInt();
            if(temporada<=0)
                System.out.println("A duracao deve ser um numero inteiro e positivo: ");
        }while (temporada<=0);

        System.out.print("\nConfirma a inclusão da episodio? (S/N) ");
        char resp = console.nextLine().charAt(0);
        if(resp=='S' || resp=='s') {
            try {
                Episodio c = new Episodio(IDSerie, nome, temporada, lancamento, duracao);
                arqEpisodios.create(c);
                System.out.println("Episodio incluído com sucesso.");
            } catch(Exception e) {
                System.out.println("Erro do sistema. Não foi possível incluir o episodio!");
            }
        }
    }

    public void alterarEpisodio() {
        System.out.println("\nAlteração de episodio");
        String nome;
        Episodio[] ea = null;
        Episodio episodio = null;

        try {
            // Tenta ler o episodio com o ID fornecido
            ea = arqEpisodios.readNome(nome);
            if (ea!=null) {
                episodio = ea[0];
                System.out.println("Episodio encontrado:");
                mostraEpisodio(episodio);  // Exibe os dados do episodio para confirmação

                // Alteração de nome
                System.out.print("\nNovo nome (deixe em branco para manter o anterior): ");
                String novoNome = console.nextLine();
                if (!novoNome.isEmpty()) {
                    episodio.nome = novoNome;  // Atualiza o nome se fornecido
                }

                // Alteração de temporada 
                System.out.print("Nova temporada (deixe em branco para manter a anterior): ");
                String novaTemporada = console.nextLine();
                if (!novaTemporada.isEmpty()) {
                    try{
                         episodio.temporada = Integer.parseInt(novaTemporada);  // Atualiza o CPF se fornecido
                    } catch(NumberFormatException e){
                        System.err.println("Temporada invalida. Valor mantido.");
                    }
                }

                // Alteração de duracao 
                System.out.print("Nova duracao (deixe em branco para manter o anterior): ");
                String novaDuracaoStr = console.nextLine();
                if (!novaDuracaoStr.isEmpty()) {
                    try {
                        episodio.duracao = Integer.parseInt(novaDuracaoStr);  // Atualiza o salário se fornecido
                    } catch (NumberFormatException e) {
                        System.err.println("Duracao inválida. Valor mantido.");
                    }
                }

                // Alteração de data de lancamento 
                System.out.print("Nova data de lancamento (DD/MM/AAAA) (deixe em branco para manter a anterior): ");
                String novaDataStr = console.nextLine();
                if (!novaDataStr.isEmpty()) {
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        episodio.lancamento = LocalDate.parse(novaDataStr, formatter);  // Atualiza a data de nascimento se fornecida
                    } catch (Exception e) {
                        System.err.println("Data inválida. Valor mantido.");
                    }
                }

                // Confirmação da alteração
                System.out.print("\nConfirma as alterações? (S/N) ");
                char resp = console.next().charAt(0);
                if (resp == 'S' || resp == 's') {
                    // Salva as alterações no arquivo
                    boolean alterado = arqEpisodios.update(episodio);
                    if (alterado) {
                        System.out.println("Episodio alterado com sucesso.");
                    } else {
                        System.out.println("Erro ao alterar o episodio.");
                    }
                } else {
                    System.out.println("Alterações canceladas.");
                }
            } else {
                System.out.println("Episodio não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro do sistema. Não foi possível alterar o episodio!");
            e.printStackTrace();
        }
        
    }


    public void excluirEpisodio() {
        System.out.println("\nExclusão de episodio");
        String nome;
        Episodio[] ea = null;
        Episodio episodio = null;

        try {
            // Tenta ler o episodio com o ID fornecido
            ea = arqEpisodios.readNome(nome);
            if (ea != null) {
                episodio = ea[0];
                System.out.println("Episodio encontrado:");
                mostraEpisodio(episodio);  // Exibe os dados do episodio para confirmação

                System.out.print("\nConfirma a exclusão do episodio? (S/N) ");
                char resp = console.next().charAt(0);  // Lê a resposta do usuário

                if (resp == 'S' || resp == 's') {
                    boolean excluido = arqEpisodios.delete(nome);  // Chama o método de exclusão no arquivo
                    if (excluido) {
                        System.out.println("Episodio excluído com sucesso.");
                    } else {
                        System.out.println("Erro ao excluir o episodio.");
                    }
                } else {
                    System.out.println("Exclusão cancelada.");
                }
            } else {
                System.out.println("Episodio não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro do sistema. Não foi possível excluir o episodio!");
            e.printStackTrace();
        }
    }


    public void mostraEpisodio(Episodio episodio) {
        if (episodio != null) {
            System.out.println("\nDetalhes da Episodio:");
            System.out.println("----------------------");
            System.out.printf("Nome.........: %s\n", episodio.nome);
            System.out.printf("Temporada....: %d\n", episodio.temporada);
            System.out.printf("lancamento...: %s\n", episodio.lancameto.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            System.out.printf("Duracao......: %d\n", episodio.duracao);
            System.out.println("----------------------");
        }
    }
}
