package registro;

import java.io.File;
import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;

public class Arquivo <T extends Registro>
{
	final int TAM_CABECALHO = 12;
	RandomAccessFile arquivo;
	public String nomeEntidade;
	Constructor<T> construtor;
	HashExtensivel<ParIDEndereco> indiceDireto;

	public Arquivo (String ne, Constructor<T> c) throws Exception
	{
		File d = new File("./dados");
		if (!d.exists())
		{
			d.mkdir();
		}

		d = new File("./dados/" + ne);
		if (!d.exists())
		{
			d.mkdir();
		}

		this.nomeEntidade = "./dados/"+ne+"/"+ne+".db";
		this.construtor = c;

		arquivo = new RandomAccessFile(this.nomeEntidade, "rw");
		
		if (arquivo.length() < TAM_CABECALHO)
		{
			arquivo.writeInt(0);
			arquivo.writeLong(-1);
		}

		indiceDireto = new HashExtensivel<> (ParIDEndereco.class.getConstructor(), 4, "./dados/"+ne+"/"+ne+".d.db", "./dados/"+ne+"/"+ne+".c.db");
	}


	private void writeObj (T obj, boolean x) throws Exception // x == false -> create | x == true -> update
	{
		byte[] BA = obj.toByteArray();

		long endereco = getDeleted(BA.length);

		if (endereco == -1) // Sem espaco disponivel
		{
			// Ir para o final do arquivo
			arquivo.seek (arquivo.length());
			endereco = arquivo.getFilePointer();

			// Gravar dados
			arquivo.writeByte(' '); 			// Lapide
			arquivo.writeShort(BA.length); 	// Tamanho
		}
		else	// Espaco disponivel
		{
			// Ir para o registro excluido
			arquivo.seek(endereco);

			arquivo.writeByte(' '); // Limpar lapide
			arquivo.skipBytes(2); 	// Preservar indicador de tamanho
		}

		// Gravar objeto
		arquivo.write(BA);

		if (x)
		{
			indiceDireto.update (new ParIDEndereco (obj.getID(), endereco) );
		}
		else
		{
			indiceDireto.create (new ParIDEndereco (obj.getID(), endereco) );
		}
	}

	public int create (T obj) throws Exception
	{
		// Ler ultimo ID
		arquivo.seek(0);
		int proximoID = arquivo.readInt()+1; // Criar proximo

		// Gravar objeto
		obj.setID (proximoID);
		writeObj(obj, false);

		// Guardar novo ID
		arquivo.seek(0);
		arquivo.writeInt(proximoID);

		return (proximoID);
	}

	public T read (int ID) throws Exception
	{
		T res = null;

		T obj;
		short tam;
		byte[] BA;
		byte lapide;

		ParIDEndereco PID = indiceDireto.read(ID);
		
		if (PID != null)
		{
			arquivo.seek(PID.getEndereco());
			obj = construtor.newInstance();
			lapide = arquivo.readByte();
			
			if (lapide == ' ')
			{
				tam = arquivo.readShort();

				BA = new byte[tam];
				arquivo.read(BA);

				obj.fromByteArray(BA);
				if (obj.getID() == ID)
				{
					res = obj;
				}
			}
		}
		return (res);
	}

	public boolean delete (int ID) throws Exception
	{
		boolean res = false;

		T obj;
		short tam;
		byte[] BA;
		byte lapide;

		ParIDEndereco PIE = indiceDireto.read(ID);
		
		if (PIE != null)
		{
			arquivo.seek(PIE.getEndereco());
			obj = construtor.newInstance();
			lapide = arquivo.readByte();
			
			if (lapide == ' ')
			{
				tam = arquivo.readShort();

				BA = new byte[tam];
				arquivo.read(BA);

				obj.fromByteArray(BA);
				if (obj.getID() == ID)
				{
					if (indiceDireto.delete(ID))
					{
						arquivo.seek(PIE.getEndereco());
						arquivo.write('*');
						addDeleted(tam, PIE.getEndereco());

						res = true;
					}
				}
			}
		}
		return (res);
	}	

	public boolean update (T novoObj) throws Exception
	{
		boolean res = false;

		T obj = null;
		short tam = 0;
		byte[] BA = null;
		byte lapide = 0x0;

		ParIDEndereco PIE = indiceDireto.read(novoObj.getID());

		if (PIE != null)
		{
			arquivo.seek (PIE.getEndereco());
			obj = construtor.newInstance();
			lapide = arquivo.readByte();

			if (lapide == ' ')
			{
				tam = arquivo.readShort();
				BA = new byte[tam];

				arquivo.read(BA);
				obj.fromByteArray(BA);

				if (obj.getID() == novoObj.getID())
				{
					byte[] BA2 = novoObj.toByteArray();
					short tam2 = (short)BA2.length;

					if (tam2 <= tam)	// Sobrescreve o registro antigo
					{
						arquivo.seek(PIE.getEndereco()+3); // Pular lapide e tamanho
						arquivo.write(BA2);

					}
					else // Escreve o registro no final do arquivo
					{
						// Exclui o registro anterior
						arquivo.seek(PIE.getEndereco());
						arquivo.write('*');
						addDeleted(tam, PIE.getEndereco());
						
						writeObj(novoObj, true);

					}
					res = true;
				}
			}
		}
		return (res);
	}

	public long getDeleted (int tamanhoNecessario) throws Exception
	{
		long anterior = 4;	// Inicio da lista

		arquivo.seek(anterior);

		long endereco = arquivo.readLong(); // Endereco do elemento que sera testado
		long proximo = -1;					// Endereco do elemento seguinte

		int tamanho = -1;
		
		boolean stop = false;

		while (endereco != -1 && stop == false)
		{
			arquivo.seek(endereco+1);		// Ir para o proximo registro
			tamanho = arquivo.readShort();	// Ler tamanho do registro
			proximo = arquivo.readLong();	// Ler endereco do registro

			if (tamanho > tamanhoNecessario) // Se tem espaco para escrever
			{
				if (anterior == 4) // O elemento e o primeiro da lista
				{
					arquivo.seek(anterior);
				}
				else
				{	
					arquivo.seek(anterior+3); // Pular lapide e indicador de tamanho
				}
				arquivo.writeLong(proximo); // Guardar novo endereco
				
				stop = true;
			}
			else
			{
				anterior = endereco;
				endereco = proximo;
			}
		}
		return (endereco);
	}

    public void addDeleted(int tamanhoEspaco, long enderecoEspaco) throws Exception {
        long anterior = 4; // início da lista
        arquivo.seek(anterior);
        long endereco = arquivo.readLong(); // endereço do elemento que será testado
        long proximo = -1; // endereço do elemento seguinte da lista
        int tamanho;
        if(endereco==-1) {  // lista vazia
            arquivo.seek(4);
            arquivo.writeLong(enderecoEspaco);
            arquivo.seek(enderecoEspaco+3);
            arquivo.writeLong(-1);
        } else {
            do {
                arquivo.seek(endereco+1);
                tamanho = arquivo.readShort();
                proximo = arquivo.readLong();
                if(tamanho > tamanhoEspaco) {  // encontrou a posição de inserção (antes do elemento atual)
                    if(anterior == 4) // será o primeiro elemento da lista
                        arquivo.seek(anterior);
                    else
                        arquivo.seek(anterior+3);
                    arquivo.writeLong(enderecoEspaco);
                    arquivo.seek(enderecoEspaco+3);
                    arquivo.writeLong(endereco);
                    break;
                }
                if(proximo == -1) {  // fim da lista
                    arquivo.seek(endereco+3);
                    arquivo.writeLong(enderecoEspaco);
                    arquivo.seek(enderecoEspaco+3);
                    arquivo.writeLong(+1);
                    break;
                }
                anterior = endereco;
                endereco = proximo;
            } while (endereco!=-1);
        }
    }

	public void close() throws Exception
	{
		arquivo.close();
		indiceDireto.close();
	}
}
