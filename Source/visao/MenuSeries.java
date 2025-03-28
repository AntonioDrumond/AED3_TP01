package visao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import entidades.Serie;
import modelo.ArquivoSeries;

public class MenuSeries 
{    
    ArquivoSeries arqSeries;
    private static Scanner console = new Scanner (System.in);

    public MenuSeries() throws Exception 
	{
        arqSeries = new ArquivoSeries();
    }

    public void menu() 
	{
        int opcao;
        do 
		{
            System.out.println("\n\nAEDsIII");
            System.out.println("-------");
            System.out.println("> Início > Series");
            System.out.println("\n1 - Buscar");
            System.out.println("2 - Incluir");
            System.out.println("3 - Alterar");
            System.out.println("4 - Excluir");
            System.out.println("0 - Voltar");

            System.out.print("\nOpção: ");
            try 
			{
                opcao = Integer.valueOf(console.nextLine());
            } 
			catch (NumberFormatException e) 
			{
                opcao = -1;
            }

            switch (opcao) 
			{
                case 1:
                    buscarSerie();
                    break;
                case 2:
                    incluirSerie();
                    break;
                case 3:
                    alterarSerie();
                    break;
                case 4:
                    excluirSerie();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (opcao != 0);
    }

    public void buscarSerie () 
	{
        System.out.println("\nBusca de serie");
        String Nome;

		System.out.print("\nNome: ");
		Nome = console.nextLine();  // Lê o Nome digitado pelo usuário

        try 
		{
            Serie serie = arqSeries.readNome(nome);  // Chama o método de leitura da classe Arquivo
            if (serie != null) 
			{
                mostraSerie(serie);  // Exibe os detalhes do serie encontrado
            } 
			else 
			{
                System.out.println("Serie não encontrada.");
            }
        } 
		catch (Exception e) 
		{
            System.out.println ("Erro do sistema. Não foi possível buscar o serie!");
            e.printStackTrace();
        }
    }   

    public void incluirSerie () 
	{
        String nome = "";
		String sinopse = "";
		String streaming = "";
        LocalDate dataLancamento = null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("\nInclusão de serie");
		
		// Ler nome
        do 
		{
            System.out.print("\nNome (min. de 3 letras ou vazio para cancelar): ");
            nome = console.nextLine();
            if (nome.length() == 0)
			{
                return;
			}
            if (nome.length() < 4)
			{
                System.err.println("O nome da serie deve ter no mínimo 3 caracteres.");
			}

        } while (nome.length() < 4);

		if (arqSerie.readNome(nome) != null) // Verificar se uma serie com esse nome já existe
		{
			System.err.println ("Uma serie com esse nome já existe");
		}
		else
		{
			// Ler sinopse
			do 
			{
				System.out.print("Sinopse (no mínimo 10 dígitos): ");

				sinopse = console.nextLine();
				if (sinopse.length() < 11)
				{
					System.err.println ("A sinopse deve ter no mínimo 10 dígitos.");
				}

			} while (sinopse.length() < 11);

			// Ler streaming
			do 
			{
				System.out.print("Streaming: (no mínimo 3 dígitos): ");
				if (streaming.length() < 4)
				{
					System.err.println ("O streaming deve ter no mínimo 3 dígitos.");
				}
				streaming = console.nextLine();

			} while (streaming.length() < 4);

			// Ler data de lancamento
			boolean dadosCorretos = false;
			do 
			{
				System.out.print("Data de lancamento (DD/MM/AAAA): ");
				String dataStr = console.nextLine();

				try 
				{
					dataLancamento = LocalDate.parse(dataStr, formatter);
					dadosCorretos = true;
				} 
				catch (Exception e) 
				{
					System.err.println ("Data inválida! Use o formato DD/MM/AAAA.");
				}

			} while (!dadosCorretos);

			// Confirmar inclusão
			System.out.print("\nConfirma a inclusão da serie? (S/N) ");

			char resp = console.nextLine().charAt(0);

			if (resp == 'S' || resp == 's') 
			{
				try 
				{
					s = new Serie (nome, dataLancamento, sinopse, streaming);
					arqSeries.create (s);
					System.out.println ("Serie incluída com sucesso.");
				} 
				catch (Exception e) 
				{
					System.out.println("Erro do sistema. Não foi possível incluir a serie!");
				}
			}
		}
    }

    public void alterarSerie() 
	{
        System.out.println("\nAlteração de serie");
        String nome = "";
        boolean nomeValido = false;

        do 
		{
            System.out.print("\nNome (3 dígitos): ");
            nome = console.nextLine();  // Lê o nome digitado pelo usuário

            if (nome.isEmpty())
			{
                return; 
			}

            // Validação do nome
            if (nome.length() > 2) 
			{
                nomeValido = true;  // Nome válido
            } 
			else 
			{
                System.out.println("Nome inválido. O nome deve conter no mínimo 3 dígitos.");
            }

        } while (!nomeValido);

        try 
		{
            // Tenta ler a serie com o ID fornecido
            Serie serie = arqSeries.readNome(nome);

            if (serie != null) 
			{
                System.out.println ("Serie encontrada:");
                mostraSerie(serie);  // Exibe os dados do serie para confirmação

                // Alteração de nome
                System.out.print("\nNovo nome (deixe em branco para manter o anterior): ");
                String novoNome = console.nextLine();

                if (!novoNome.isEmpty()) 
				{
                    serie.nome = novoNome;  // Atualiza o nome se fornecido
                }

                // Alteração de CPF
                System.out.print("Nova sinopse (deixe em branco para manter o anterior): ");
                String novaSinopse = console.nextLine();

                if (!novaSinopse.isEmpty()) 
				{
                    serie.setSinopse(novaSinopse);  // Atualiza o CPF se fornecido
                }

                // Alteração de salário
                System.out.print("Novo streaming (deixe em branco para manter o anterior): ");
                String novoStreaming = console.nextLine();

                if (!novoStreaming.isEmpty()) 
				{
                    serie.setStreaming(novoStreaming);
                }

                // Alteração de data de nascimento
                System.out.print("Nova data de lancamento (DD/MM/AAAA) (deixe em branco para manter a anterior): ");
                String novaDataLancamento = console.nextLine();

                if (!novaDataLancamento.isEmpty()) 
				{
                    try 
					{
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        serie.setLancamento(LocalDate.parse(novaDataLancamento, formatter));  // Atualiza a data de nascimento se fornecida
                    } 
					catch (Exception e) 
					{
                        System.err.println("Data inválida. Valor mantido.");
                    }
                }

                // Confirmação da alteração
                System.out.print("\nConfirma as alterações? (S/N) ");
                char resp = console.next().charAt(0);
                if (resp == 'S' || resp == 's') 
				{
                    // Salva as alterações no arquivo
                    boolean alterado = arqSeries.update(serie);

                    if (alterado) 
					{
                        System.out.println("Serie alterada com sucesso.");
                    } 
					else 
					{
                        System.out.println("Erro ao alterar a serie.");
                    }
                } 
				else 
				{
                    System.out.println("Alterações canceladas.");
                }
            } 
			else 
			{
                System.out.println("Serie não encontrada.");
            }
        } 
		catch (Exception e) 
		{
            System.out.println("Erro do sistema. Não foi possível alterar o serie!");
            e.printStackTrace();
        }
    }

    public void excluirSerie() 
	{
        System.out.println("\nExclusão de serie");
        String nome;
        boolean nomeValido = false;

        do 
		{
            System.out.print("\nNome (3 dígitos): ");
            nome = console.nextLine();  // Lê o nome digitado pelo usuário

            if (nome.isEmpty())
			{
                return; 
			}

            // Validação do nome
            if (nome.length() > 2)
			{
                nomeValido = true;  // Nome válido
            } 
			else 
			{
                System.out.println("Nome inválido. O nome deve conter no mínimo 3 dígitos.");
            }

        } while (!nomeValido);

        try 
		{
            // Tenta ler a serie com o ID fornecido
            Serie serie = arqSeries.readNome(nome);
			
            if (serie != null) 
			{
                System.out.println("Serie encontrado:");
                mostraSerie(serie);  // Exibe os dados do serie para confirmação

                System.out.print("\nConfirma a exclusão do serie? (S/N) ");
                char resp = console.next().charAt(0);  // Lê a resposta do usuário

                if (resp == 'S' || resp == 's') 
				{
                    boolean excluido = arqSeries.delete(nome);  // Chama o método de exclusão no arquivo

                    if (excluido) 
					{
                        System.out.println("Serie excluído com sucesso.");
                    }
					else 
					{
                        System.out.println("Erro ao excluir o serie.");
                    }
                } 
				else 
				{
                    System.out.println("Exclusão cancelada.");
                }
            } 
			else 
			{
                System.out.println("Serie não encontrado.");
            }
        } 
		catch (Exception e)
		{
            System.out.println("Erro do sistema. Não foi possível excluir o serie!");
            e.printStackTrace();
        }
    }

    public void mostraSerie (Serie serie) 
	{
        if (serie != null) 
		{
            System.out.println ("\nDetalhes da Serie:");
            System.out.println ("----------------------");
            System.out.printf  ("Nome.........: %s\n", serie.nome);
            System.out.printf  ("ID...........: %d\n", serie.ID);
            System.out.printf  ("Streaming....: %s\n", serie.streming);
            System.out.printf  ("Sinopse......: %s\n", serie.sinopse);
            System.out.printf  ("Nascimento: %s\n", serie.lancamento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            System.out.println ("----------------------");
        }
    }
}
