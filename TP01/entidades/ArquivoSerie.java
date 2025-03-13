import registro.*;

/*
 * Precisamos montar o indice indireto que linka nome da serie com ID em uma arvore B+
 */

public class ArquivoSerie extends registro.Arquivo<Serie> {

    Arquivo<Serie> arqSeries;
    HashExtensivel<ParIDEndereco> indiceDireto;

    public ArquivoSerie() throws Exception {
        super("serie", Serie.class.getConstructor());
        indiceDireto = new HashExtensivel<>(
            ParIDEndereco.class.getConstructor(), 
            4, 
            ".\\dados\\series\\indiceCPF.d.db",   // diretório - metadado
            ".\\dados\\series\\indiceCPF.c.db"    // cestos  - dado
        );
    }

     @Override
    public int create(Serie s) throws Exception {
        int id = super.create(s);
        // indiceDireto.create(new ParIDEndereco(s.(), id)); // Parece desnecessario
        return id;
    }

    public Serie read(String cpf) throws Exception {
        ParIDEndereco pie = indiceDireto.read(ParIDEndereco.hash(cpf));
        if(pie == null)
            return null;
        return read(pie.getId());
    }
    
    public boolean delete(String cpf) throws Exception {
        ParIDEndereco pie = indiceDireto.read(ParIDEndereco.hash(cpf));
        if(pie != null) 
            if(delete(pie.getId())) 
                return indiceDireto.delete(ParIDEndereco.hash(cpf));
        return false;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Serie c = super.read(id);
        if(c != null) {
            if(super.delete(id))
                return indiceDireto.delete(ParIDEndereco.hash(c.getCpf()));
        }
        return false;
    }

    @Override
    public boolean update(Serie novoSerie) throws Exception {
        Serie serieVelho = read(novoSerie.getCpf());
        if(super.update(novoSerie)) {
            if(novoSerie.getCpf().compareTo(serieVelho.getCpf())!=0) {
                indiceDireto.delete(ParIDEndereco.hash(serieVelho.getCpf()));
                indiceDireto.create(new ParIDEndereco(novoSerie.getCpf(), novoSerie.getId()));
            }
            return true;
        }
        return false;
    }
    
}
