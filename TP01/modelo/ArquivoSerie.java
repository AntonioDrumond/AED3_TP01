import registro.*;

/*
 * Precisamos montar o indice indireto que linka nome da serie com ID em uma arvore B+
 */

public class ArquivoSerie extends registro.Arquivo<Serie> {

    Arquivo<Serie> arqSeries;
    HashExtensivel<ParNomeId> indiceNome;

    public ArquivoSerie() throws Exception {
        super("serie", Serie.class.getConstructor());
        indiceNome = new HashExtensivel<>(
            ParNomeId.class.getConstructor(), 
            4, 
            ".\\dados\\series\\indiceNome.d.db",   // diretório - metadado
            ".\\dados\\series\\indiceNome.c.db"    // cestos  - dado
        );
    }

     @Override
    public int create(Serie s) throws Exception {
        int id = super.create(s);
        indiceNome.create(new ParNomeId(s.(), id)); // Parece desnecessario
        return id;
    }

    public Serie read(String cpf) throws Exception {
        ParNomeId pni = indiceNome.read(ParNomeId.hash(cpf));
        if(pni == null)
            return null;
        return read(pni.getId());
    }
    
    public boolean delete(String cpf) throws Exception {
        ParNomeId pni = indiceNome.read(ParNomeId.hash(cpf));
        if(pni != null) 
            if(delete(pni.getId())) 
                return indiceNome.delete(ParNomeId.hash(cpf));
        return false;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Serie c = super.read(id);
        if(c != null) {
            if(super.delete(id))
                return indiceNome.delete(ParNomeId.hash(c.getCpf()));
        }
        return false;
    }

    @Override
    public boolean update(Serie novoSerie) throws Exception {
        Serie serieVelho = read(novoSerie.getCpf());
        if(super.update(novoSerie)) {
            if(novoSerie.getCpf().compareTo(serieVelho.getCpf())!=0) {
                indiceNome.delete(ParNomeId.hash(serieVelho.getCpf()));
                indiceNome.create(new ParNomeId(novoSerie.getCpf(), novoSerie.getId()));
            }
            return true;
        }
        return false;
    }
    
}
