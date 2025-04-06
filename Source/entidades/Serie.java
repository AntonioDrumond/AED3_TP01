package entidades;

import java.time.LocalDate;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import registro.EntidadeArquivo;

public class Serie implements EntidadeArquivo 
{
	// Atributos
    private int ID;
    private String nome;
    private LocalDate lancamento;
    private String sinopse;
    private String streaming;

	// Construtores
    public Serie (int ID, String nome, LocalDate lancamento, String sinopse, String streaming)
	{
		this.ID = ID;
        this.nome = nome;
        this.lancamento = lancamento;
        this.sinopse = sinopse;
        this.streaming = streaming;
    }

	public Serie (String nome, LocalDate lancamento, String sinopse, String streaming)
	{
		this (-1, nome, lancamento, sinopse, streaming);
	}

	public Serie ()
	{
		this (-1, "", LocalDate.now(), "", "");
	}

	// Metodos Set

	public void setID (int ID)
	{
		this.ID = ID;
	}

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setLancamento(LocalDate lancamento){
        this.lancamento = lancamento;
    }

    public void setSinopse(String sinopse){
        this.sinopse = sinopse;
    }

    public void setStreaming(String streaming){
        this.streaming = streaming;
    }

	// Metodos Get

	public int getID ()
	{
		return (this.ID);
	}

    public String getNome(){
        return this.nome;
    }

    public LocalDate getLancamento(){
        return this.lancamento;
    }

    public String getSinopse(){
        return this.sinopse;
    }

    public String getStreaming(){
        return this.streaming;
    }


	// Metodos ByteArray

	public byte[] toByteArray() throws Exception
	{
		ByteArrayOutputStream BAOS = new ByteArrayOutputStream();
		DataOutputStream DOS = new DataOutputStream (BAOS);

		DOS.writeInt(this.ID);
		DOS.writeUTF(this.nome);
		DOS.writeInt( (int)this.lancamento.toEpochDay() );
		DOS.writeUTF(this.sinopse);
		DOS.writeUTF(this.streaming);

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
			ByteArrayInputStream BAIS = new ByteArrayInputStream(BA);
			DataInputStream DIS = new DataInputStream (BAIS);
			
			this.ID = DIS.readInt();
			this.nome = DIS.readUTF();
			this.lancamento = LocalDate.ofEpochDay( DIS.readInt() );
			this.sinopse = DIS.readUTF();
			this.streaming = DIS.readUTF();
		}
	}

	// Outros Metodos

	public String toString ()
	{
		String res = ("\nID: " + this.ID 
					+ "\nNome: " + this.nome
					+ "\nLancamento: " + this.lancamento
					+ "\nSinopse: " + this.sinopse
					+ "\nStreaming: " + this.streaming);
		return (res);
	}
}
