import java.time.LocalDate;

class Serie {
    private int id;
    private String nome;
    private LocalDate lancamento;
    private String sinopse;
    private String streaming;

    Serie(String nome, LocalDate lancamento, String sinopse, String streaming){
        this.nome = nome;
        this.lancamento = lancamento;
        this.sinopse = sinopse;
        this.streaming = streaming;
    }

}
