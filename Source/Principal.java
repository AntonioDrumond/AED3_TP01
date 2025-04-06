import visao.*;

import java.util.Scanner;

import entidades.Episodio;
import entidades.Serie;
import registro.Arquivo;

public class Principal 
{
    public static void main(String[] args) 
	{
        Scanner sc;
        Arquivo<Serie> arqSeries = null; //!!!
        Arquivo<Episodio> arqEpisodios = null; //!!!

        try 
		{   
            arqSeries = new Arquivo<>("series", Serie.class.getConstructor());//!!!
            arqEpisodios = new Arquivo<>("episodios", Episodio.class.getConstructor());
            sc = new Scanner(System.in);
            int opcao;
            do 
			{

            System.out.println("PUCFlix 1.0\n" +
                               "-----------\n" +
                               "> Inicio\n\n" +
                               "1) Series\n" + 
                               "2) Episodios\n" + 
                               "3) Atores (nao implementado)\n" + 
                               "0) Sair\n");

                System.out.print("\nOpcao: ");
                try 
				{
                    opcao = Integer.valueOf(sc.nextLine());
                } 
				catch(NumberFormatException e) 
				{
                    opcao = -1;
                }

                switch (opcao) 
				{
                    case 1:
                        new MenuSeries().menu();
                    break;
                    case 2:
                        new MenuEpisodios().menu();
                    break;
                    case 0:
                    break;
                    default:
                        System.out.println("Opção inválida!");
                    break;
                }

            } while (opcao != 0);

        } 
		catch (Exception e) 
		{
            e.printStackTrace();

        }
    }

}
