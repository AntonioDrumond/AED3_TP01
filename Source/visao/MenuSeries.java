package visao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import entidades.Episodio;
import entidades.Serie;
import modelo.ArquivoEpisodios;
import modelo.ArquivoSeries;

public class MenuSeries 
{    
    ArquivoSeries arqSeries;
    ArquivoEpisodios arqEpisodios;

    private static Scanner console = new Scanner (System.in);

    public MenuSeries() throws Exception 
	{
        arqSeries = new ArquivoSeries();

        //para checar se tem episodios na serie antes de apaga-la
        arqEpisodios = new ArquivoEpisodios();
    }

    public void menu() 
	{
        int opcao;
        do 
		{
            System.out.println("\n\nAEDsIII");
            System.out.println("-------");
            System.out.println("> Inicio > Series");
            System.out.println("\n1 - Buscar");
            System.out.println("2 - Incluir");
            System.out.println("3 - Alterar");
            System.out.println("4 - Excluir");
            System.out.println("5 - Listar todos episodios da serie");
            System.out.println("6 - Listar episodios por temporada");
            System.out.println("7 - Listar todas as series cadastradas");
            System.out.println("0 - Voltar");

            System.out.print("\nOpcao: ");
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
                case 5:
                    // Listar todos os episodios
                    listarEpisodiosPorSerie();
                    break;
                case 6:
                    // Listar episodios por temporada
                    listarEpisodiosPorTemporada();
                    break;
                case 7:
                    //Listar todas as series cadastradas
                    listarTodasSeries();
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
        String nome;

		System.out.print("\nNome: ");
		nome = console.nextLine();  // Lê o Nome digitado pelo usuário

        try 
		{
            Serie[] series = arqSeries.readNome(nome);  // Chama o método de leitura da classe Arquivo
            
            if (series != null && series.length > 0) {

                for (Serie serie : series) {
                    mostraSerie(serie);  // Exibe os detalhes de cada serie encontrada
                }

            } else {

                System.out.println("Serie não encontrada.");

            }
        } 
		catch (Exception e) 
		{
            System.out.println ("Erro do sistema. Não foi possível buscar o serie!");
            e.printStackTrace();
        }
    }

    public void listarEpisodiosPorSerie() {

        System.out.println("\nListagem de episodios");
        String nome;

		System.out.print("\nNome da serie: ");
		nome = console.nextLine();  // Lê o Nome digitado pelo usuário

        try {

            Serie[] series = arqSeries.readNome(nome);

            if (series == null || series.length == 0) {

                System.out.println("Serie não encontrada.");

            }

            Serie s = series[0]; // Assuming the first match is the one we want
            System.out.println("Serie encontrada:");

            int idSerie = s.getID();  // Get the ID of the found series

            // Use the readPorSerie method to get all episodes linked to the series
            Episodio[] episodios = arqEpisodios.readPorSerie(idSerie);
    
            if (episodios == null || episodios.length == 0) {
                System.out.println("Nenhum episódio encontrado para esta série.");
                return;
            }
    
            System.out.println("\nEpisódios da série:");
            for (Episodio episodio : episodios) {
                System.out.println("----------------------------");
                System.out.println("Nome: " + episodio.getNome());
                System.out.println("Temporada: " + episodio.getTemporada());
                System.out.println("Duração: " + episodio.getDuracao() + " minutos");
                System.out.println("Data de Lançamento: " + episodio.getLancamento());
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar episódios da série!");
            e.printStackTrace();
        }
    }

    public void listarEpisodiosPorTemporada() {

        System.out.println("\nListagem de episódios por temporada");
        String nomeSerie;
    
        // Ask the user for the series name
        System.out.print("\nNome da serie: ");
        nomeSerie = console.nextLine();
    
        try {
            // Retrieve the series by name
            Serie[] series = arqSeries.readNome(nomeSerie);
    
            if (series == null || series.length == 0) {
                System.out.println("Série não encontrada.");
                return;
            }
    
            Serie serie = series[0]; // Assuming the first match is the desired series
            System.out.println("Série encontrada:");
            mostraSerie(serie);
    
            // Ask the user for the desired season
            System.out.print("\nDigite o número da temporada desejada: ");
            int temporadaDesejada = Integer.parseInt(console.nextLine());
    
            // Get all episodes of the series
            Episodio[] episodios = arqEpisodios.readPorSerie(serie.getID());
    
            if (episodios == null || episodios.length == 0) {
                System.out.println("Nenhum episódio encontrado para esta série.");
                return;
            }
    
            // Filter episodes by the desired season
            System.out.println("\nEpisódios da temporada " + temporadaDesejada + ":");
            boolean encontrouEpisodios = false;
            for (Episodio episodio : episodios) {
                if (episodio.getTemporada() == temporadaDesejada) {
                    System.out.println("----------------------------");
                    System.out.println("Nome: " + episodio.getNome());
                    System.out.println("Temporada: " + episodio.getTemporada());
                    System.out.println("Duração: " + episodio.getDuracao() + " minutos");
                    System.out.println("Data de Lançamento: " + episodio.getLancamento());
                    encontrouEpisodios = true;
                }
            }
    
            if (!encontrouEpisodios) {
                System.out.println("Nenhum episódio encontrado para a temporada " + temporadaDesejada + ".");
            }
    
        } catch (Exception e) {
            System.out.println("Erro ao listar episódios da temporada!");
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
        do {
            System.out.print("\nNome (min. de 3 letras ou vazio para cancelar): ");
            nome = console.nextLine();
            if (nome.length() == 0) {
                return;
            }
            if (nome.length() < 3) {
                System.err.println("O nome da serie deve ter no mínimo 3 caracteres.");
            }
        } while (nome.length() < 3);


        try {

            Serie[] series = arqSeries.readNome(nome); 

            if (series != null && series.length > 0) {
                System.err.println("Uma serie com esse nome já existe");
                return;
            }
            else
            {
                // Ler sinopse
                do 
                {
                    System.out.print("Sinopse (no mínimo 10 dígitos): ");
    
                    sinopse = console.nextLine();
                    if (sinopse.length() < 10)
                    {
                        System.err.println ("A sinopse deve ter no mínimo 10 dígitos.");
                    }
    
                } while (sinopse.length() < 10);
    
                // Ler streaming
                do 
                {
                    System.out.print("Streaming: (no mínimo 3 dígitos): ");

                    streaming = console.nextLine();

                    if (streaming.length() < 3)
                    {
                        System.err.println ("O streaming deve ter no mínimo 3 dígitos.");
                    }
                    
    
                } while (streaming.length() < 3);
    
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
                        Serie s = new Serie (nome, dataLancamento, sinopse, streaming);
                        arqSeries.create (s);
                        System.out.println ("Serie incluída com sucesso.");
                    } 
                    catch (Exception e) 
                    {
                        System.out.println("Erro do sistema. Não foi possível incluir a serie!");
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Erro do sistema. Não foi possível buscar a serie!");
            e.printStackTrace();
        }

        
    }

    public void alterarSerie() 
	{
        System.out.println("\nAlteracao de serie");
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
            Serie[] s = arqSeries.readNome(nome);

            if (s == null || s.length == 0){
                return;
            }

            Serie serie = s[0];

            if (serie != null) 
			{
                System.out.println ("Serie encontrada:");
                mostraSerie(serie);  // Exibe os dados do serie para confirmação

                // NOVO PASSO: Checar se há episódios vinculados a essa série
                Episodio[] epVinculados = arqEpisodios.readPorSerie(serie.getID());

                if (epVinculados != null && epVinculados.length > 0) {

                    System.out.println("Não é possível alterar o nome da série pois existem episódios ligado a ela.");

                }else{
                    // Alteração de nome
                    System.out.print("\nNovo nome (deixe em branco para manter o anterior): ");
                    String novoNome = console.nextLine();

                    //NOVO PASSO: Checar se o novo nome já está registrado
                    if (!novoNome.isEmpty()) 
                    {
                        // Verificar se o novo nome já está registrado
                        Serie[] seriesComMesmoNome = arqSeries.readNome(novoNome);
                        if (seriesComMesmoNome != null && seriesComMesmoNome.length > 0) 
                        {
                            System.out.println("Erro: Já existe uma série registrada com este nome.");
                            return;
                        }

                        serie.setNome(novoNome);  // Atualiza o nome se fornecido
                    }
                }

                // Alteração de sinopse
                System.out.print("Nova sinopse (deixe em branco para manter o anterior): ");
                String novaSinopse = console.nextLine();

                if (!novaSinopse.isEmpty()) 
				{
                    serie.setSinopse(novaSinopse);  
                }

                System.out.print("Novo streaming (deixe em branco para manter o anterior): ");
                String novoStreaming = console.nextLine();

                if (!novoStreaming.isEmpty()) 
				{
                    serie.setStreaming(novoStreaming);
                }

                // Alteração de data de lancamento
                System.out.print("Nova data de lancamento (DD/MM/AAAA) (deixe em branco para manter a anterior): ");
                String novaDataLancamento = console.nextLine();

                if (!novaDataLancamento.isEmpty()) 
				{
                    try 
					{
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        serie.setLancamento(LocalDate.parse(novaDataLancamento, formatter));
                    } 
					catch (Exception e) 
					{
                        System.err.println("Data inválida. Valor mantido.");
                    }
                }

                // Confirmação da alteração
                System.out.print("\nConfirma as alterações? (S/N) ");
                
                char resp = console.nextLine().charAt(0);

                if (resp == 'S' || resp == 's') 
				{
                    // Salva as alterações no arquivo
                    //System.out.println("Attempting to update the series...");
                    boolean alterado = arqSeries.update(serie);
                    //System.out.println("Update completed.");

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

    public void excluirSerie() {
        System.out.println("\nExclusão de serie");
        String nome;
        boolean nomeValido = false;

        do {
            System.out.print("\nNome (3 dígitos): ");
            nome = console.nextLine();
            if (nome.isEmpty()) {
                return;
            }
            if (nome.length() > 2) {
                nomeValido = true;
            } else {
                System.out.println("Nome inválido. O nome deve conter no mínimo 3 dígitos.");
            }
        } while (!nomeValido);

        try {
            // Tenta ler a serie com o nome fornecido
            Serie[] s = arqSeries.readNome(nome);
            if (s == null || s.length == 0) {
                System.out.println("Serie não encontrada.");
                return;
            }

            Serie serie = s[0];

            // NOVO PASSO: Checar se há episódios vinculados a essa série
            Episodio[] epVinculados = arqEpisodios.readPorSerie(serie.getID());

            if (epVinculados != null && epVinculados.length > 0) {
                System.out.println("Não é possível excluir a série pois existem episódios ligados a ela.");
                System.out.println("Exclua primeiro todos os episódios dessa série no menu EPISODIOS.");
                return;
            }

            System.out.println("Serie encontrada:");
            mostraSerie(serie);

            System.out.print("\nConfirma a exclusão do serie? (S/N) ");

            char resp = console.nextLine().charAt(0);

            if (resp == 'S' || resp == 's') {
                boolean excluido = arqSeries.delete(serie.getID());
                if (excluido) {
                    System.out.println("Serie excluída com sucesso.");
                } else {
                    System.out.println("Erro ao excluir a serie.");
                }
            } else {
                System.out.println("Exclusão cancelada.");
            }

        } catch (Exception e) {
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
            System.out.printf  ("Nome.........: %s\n", serie.getNome());
            System.out.printf  ("ID...........: %d\n", serie.getID());
            System.out.printf  ("Streaming....: %s\n", serie.getStreaming());
            System.out.printf  ("Sinopse......: %s\n", serie.getSinopse());
            System.out.printf  ("Nascimento: %s\n", serie.getLancamento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            System.out.println ("----------------------");
        }
    }

    public void listarTodasSeries() {
        System.out.println("\nListagem de todas as séries:");

        try {
            // Use the generic readAll method to get all series
            List<Serie> series = arqSeries.readAll();

            if (series.isEmpty()) {
                System.out.println("Nenhuma série encontrada.");
                return;
            }

            for (Serie serie : series) {
                mostraSerie(serie); // Display each series
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar todas as séries!");
            e.printStackTrace();
        }
    }
}
