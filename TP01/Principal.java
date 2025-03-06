import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {

        Scanner sc;

        try {
            sc = new Scanner(System.in);
            int opcao;
            do {

            System.out.println("PUCFlix 1.0\n" +
                               "-----------\n" +
                               "> Início\n\n" +
                               "1) Séries\n" + 
                               "2) Episódios\n" + 
                               "3) Atores\n" + 
                               "0) Sair\n");

                System.out.print("\nOpção: ");
                try {
                    opcao = Integer.valueOf(sc.nextLine());
                } catch(NumberFormatException e) {
                    opcao = -1;
                }

                switch (opcao) {
                    case 1:
                        // (new MenuClientes()).menu();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida!");
                        break;
                }

            } while (opcao != 0);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
