package entidades;
import registro.Registro;

import java.time.LocalDate;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.DataInputStream;

//falta metodos set e get

public class Episodio implements Registro 
{
	// Atributos

    private int ID;
    private int IDSerie;
    private String nome;
    private int temporada;
    private LocalDate lancamento;
    private int duracao;

	// Construtores
    
    public Episodio (int ID, int IDSerie, String nome, int temporada, LocalDate lancamento, int duracao)
	{
		this.ID = ID;
        this.IDSerie = IDSerie;
        this.nome = nome;
        this.temporada = temporada;
        this.lancamento = lancamento;
        this.duracao = duracao;
    }

	public Episodio (int IDSerie, String nome, int temporada, LocalDate lancamento, int duracao)
	{
		this (-1, IDSerie, nome, temporada, lancamento, duracao);
	}

	public Episodio ()
	{
		this (-1, -1, "", -1, LocalDate.now(), -1);
	}

	// Metodos Set

	public void setID (int ID)
	{
		this.ID = ID;
	}	

	// Metodos Get

	public int getID ()
	{
		return (this.ID);
	}

	public String getNome ()
	{
		return (this.nome);
	}

	public int getIDSerie (){
		
		return (this.IDSerie);
	}
	
	// Metodos ByteArray

	public byte[] toByteArray () throws Exception
	{
		ByteArrayOutputStream BAOS = new ByteArrayOutputStream();
		DataOutputStream DOS = new DataOutputStream (BAOS);

		DOS.writeInt (this.ID);
		DOS.writeInt (this.IDSerie);
		DOS.writeUTF (this.nome);
		DOS.writeInt (this.temporada);
		DOS.writeInt ( (int) this.lancamento.toEpochDay() );
		DOS.writeInt (this.duracao);

		return (BAOS.toByteArray());
	}

	public void fromByteArray (byte[] BA) throws Exception
	{
		if (BA == null)
		{
			throw new Exception ("ERRO: O vetor de bytes fornecido e null!");
		}
		else
		{
			ByteArrayInputStream BAIS = new ByteArrayInputStream (BA);
			DataInputStream DIS = new DataInputStream (BAIS);

			this.ID = DIS.readInt();
			this.IDSerie = DIS.readInt();
			this.nome = DIS.readUTF();
			this.temporada = DIS.readInt();
			this.lancamento = LocalDate.ofEpochDay( DIS.readInt() );
			this.duracao = DIS.readInt();
		}
	}
}
