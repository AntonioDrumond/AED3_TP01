package entidades;

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


    /*
    public void buscarEpisodio() {
        System.out.println("\nBusca de episodio");
        String cpf;
        boolean cpfValido = false;

        do {
            System.out.print("\nCPF (11 dígitos): ");
            cpf = console.nextLine();  // Lê o CPF digitado pelo usuário

            if(cpf.isEmpty())
                return; 

            // Validação do CPF (11 dígitos e composto apenas por números)
            if (cpf.length() == 11 && cpf.matches("\\d{11}")) {
                cpfValido = true;  // CPF válido
            } else {
                System.out.println("CPF inválido. O CPF deve conter exatamente 11 dígitos numéricos, sem pontos e traços.");
            }
        } while (!cpfValido);

        try {
            Episodio episodio = arqEpisodios.read(cpf);  // Chama o método de leitura da classe Arquivo
            if (episodio != null) {
                mostraEpisodio(episodio);  // Exibe os detalhes do episodio encontrado
            } else {
                System.out.println("Episodio não encontrado.");
            }
        } catch(Exception e) {
            System.out.println("Erro do sistema. Não foi possível buscar o episodio!");
            e.printStackTrace();
        }
    }   
    */


    /*
    public void incluirEpisodio() {
        System.out.println("\nInclusão de episodio");
        String nome = "";
        String cpf = "";
        float salario = 0;
        LocalDate dataNascimento = null;
        boolean dadosCorretos = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        do {
            System.out.print("\nNome (min. de 4 letras ou vazio para cancelar): ");
            nome = console.nextLine();
            if(nome.length()==0)
                return;
            if(nome.length()<4)
                System.err.println("O nome do episodio deve ter no mínimo 4 caracteres.");
        } while(nome.length()<4);

        do {
            System.out.print("CPF (11 dígitos sem pontos ou traço): ");
            cpf = console.nextLine();
            if(cpf.length()!=11 && cpf.length()!=0)
                System.err.println("O CPF deve ter exatamente 11 dígitos.");
        } while(cpf.length()!=11 && cpf.length()!=0);

        do {
            dadosCorretos = false;
            System.out.print("Salário: ");
            if (console.hasNextFloat()) {
                salario = console.nextFloat();
                dadosCorretos = true;
            } else {
                System.err.println("Salário inválido! Por favor, insira um número válido.");
            }
            console.nextLine(); // Limpar o buffer 
        } while(!dadosCorretos);

        do {
            System.out.print("Data de nascimento (DD/MM/AAAA): ");
            String dataStr = console.nextLine();
            dadosCorretos = false;
            try {
                dataNascimento = LocalDate.parse(dataStr, formatter);
                dadosCorretos = true;
            } catch (Exception e) {
                System.err.println("Data inválida! Use o formato DD/MM/AAAA.");
            }
        } while(!dadosCorretos);

        System.out.print("\nConfirma a inclusão da episodio? (S/N) ");
        char resp = console.nextLine().charAt(0);
        if(resp=='S' || resp=='s') {
            try {
                Episodio c = new Episodio(nome, cpf, salario, dataNascimento);
                arqEpisodios.create(c);
                System.out.println("Episodio incluído com sucesso.");
            } catch(Exception e) {
                System.out.println("Erro do sistema. Não foi possível incluir o episodio!");
            }
        }
    }
    */

    /*
    public void alterarEpisodio() {
        System.out.println("\nAlteração de episodio");
        String cpf;
        boolean cpfValido = false;

        do {
            System.out.print("\nCPF (11 dígitos): ");
            cpf = console.nextLine();  // Lê o CPF digitado pelo usuário

            if(cpf.isEmpty())
                return; 

            // Validação do CPF (11 dígitos e composto apenas por números)
            if (cpf.length() == 11 && cpf.matches("\\d{11}")) {
                cpfValido = true;  // CPF válido
            } else {
                System.out.println("CPF inválido. O CPF deve conter exatamente 11 dígitos numéricos, sem pontos e traços.");
            }
        } while (!cpfValido);


        try {
            // Tenta ler o episodio com o ID fornecido
            Episodio episodio = arqEpisodios.read(cpf);
            if (episodio != null) {
                System.out.println("Episodio encontrado:");
                mostraEpisodio(episodio);  // Exibe os dados do episodio para confirmação

                // Alteração de nome
                System.out.print("\nNovo nome (deixe em branco para manter o anterior): ");
                String novoNome = console.nextLine();
                if (!novoNome.isEmpty()) {
                    episodio.nome = novoNome;  // Atualiza o nome se fornecido
                }

                // Alteração de CPF
                System.out.print("Novo CPF (deixe em branco para manter o anterior): ");
                String novoCpf = console.nextLine();
                if (!novoCpf.isEmpty()) {
                    episodio.cpf = novoCpf;  // Atualiza o CPF se fornecido
                }

                // Alteração de salário
                System.out.print("Novo salário (deixe em branco para manter o anterior): ");
                String novoSalarioStr = console.nextLine();
                if (!novoSalarioStr.isEmpty()) {
                    try {
                        episodio.salario = Float.parseFloat(novoSalarioStr);  // Atualiza o salário se fornecido
                    } catch (NumberFormatException e) {
                        System.err.println("Salário inválido. Valor mantido.");
                    }
                }

                // Alteração de data de nascimento
                System.out.print("Nova data de nascimento (DD/MM/AAAA) (deixe em branco para manter a anterior): ");
                String novaDataStr = console.nextLine();
                if (!novaDataStr.isEmpty()) {
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        episodio.nascimento = LocalDate.parse(novaDataStr, formatter);  // Atualiza a data de nascimento se fornecida
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
    */


    /*
    public void excluirEpisodio() {
        System.out.println("\nExclusão de episodio");
        String cpf;
        boolean cpfValido = false;

        do {
            System.out.print("\nCPF (11 dígitos): ");
            cpf = console.nextLine();  // Lê o CPF digitado pelo usuário

            if(cpf.isEmpty())
                return; 

            // Validação do CPF (11 dígitos e composto apenas por números)
            if (cpf.length() == 11 && cpf.matches("\\d{11}")) {
                cpfValido = true;  // CPF válido
            } else {
                System.out.println("CPF inválido. O CPF deve conter exatamente 11 dígitos numéricos, sem pontos e traços.");
            }
        } while (!cpfValido);

        try {
            // Tenta ler o episodio com o ID fornecido
            Episodio episodio = arqEpisodios.read(cpf);
            if (episodio != null) {
                System.out.println("Episodio encontrado:");
                mostraEpisodio(episodio);  // Exibe os dados do episodio para confirmação

                System.out.print("\nConfirma a exclusão do episodio? (S/N) ");
                char resp = console.next().charAt(0);  // Lê a resposta do usuário

                if (resp == 'S' || resp == 's') {
                    boolean excluido = arqEpisodios.delete(cpf);  // Chama o método de exclusão no arquivo
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
    */


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
