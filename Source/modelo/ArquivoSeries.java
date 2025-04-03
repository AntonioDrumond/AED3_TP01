package modelo;

import registro.*;

import entidades.Serie;

import java.io.File;
import java.util.ArrayList;

public class ArquivoSeries extends Arquivo<Serie> {

  Arquivo<Serie> arqSeries;
  ArvoreBMais<ParNomeId> indiceNome;

  public ArquivoSeries() throws Exception {

    super("series", Serie.class.getConstructor());

    File directory = new File("./dados/serie");

    if (!directory.exists()) {
        directory.mkdirs();
    }

    indiceNome = new ArvoreBMais<>(
    ParNomeId.class.getConstructor(), 5, "./dados/serie" + "/indiceNome.db");

  }

  @Override
  public int create (Serie s) throws Exception {

    int id = super.create(s);

    indiceNome.create(new ParNomeId(s.getNome(), id));

    return id;

  }

  public Serie[] readNome (String nome) throws Exception {

    if(nome.length() == 0) return null;

    ArrayList<ParNomeId> pares = indiceNome.read(new ParNomeId(nome, -1));

    if(pares.size() > 0){

      Serie[] series = new Serie[pares.size()];

      int i = 0;

      for(ParNomeId par : pares){

        series[i++] = read(par.getId());
        i++;

      }

      return series;

    } else {

      return null;
    } 

  }

  @Override
  public boolean delete (int id) throws Exception{

    Serie s = read(id);

    if (s != null){

      if(super.delete(id))
	  {
        return indiceNome.delete(new ParNomeId(s.getNome(), id));
      }

    }

    return false;

  }

  @Override
  public boolean update (Serie novaSerie) throws Exception 
  {

    Serie s = read(novaSerie.getID());

    if(s != null){
      
      if(super.update(novaSerie))
      {

        if(!s.getNome().equals(novaSerie.getNome()))
        {
          indiceNome.delete(new ParNomeId(s.getNome(), s.getID()));
          indiceNome.create(new ParNomeId(novaSerie.getNome(), novaSerie.getID()));
        }
        return true;
    
      }
    
    }
    
    return false;
  }

}


//quando criarmos o ArquivoEpisodio, vamos ter ParNomeID e ParIDID
