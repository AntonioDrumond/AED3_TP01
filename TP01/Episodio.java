import java.time.LocalDate;

class Episodio {
    private int id;
    private int idSerie;

    private String nome;
    private int temporada;
    private LocalDate lancamento;
    private int duracao;
    
    Episodio(int idSerie, String nome, int temporada, LocalDate lancamento, int duracao){

        this.idSerie = idSerie;
        this.nome = nome;
        this.temporada = temporada;
        this.lancamento = lancamento;
        this.duracao = duracao;
    }

}
