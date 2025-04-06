package registro; 

//objeto para entidade que será armazenada em arvore b+
//servirá para ASSOCIAR uma serie a seus episodios (relacionamento 1 para N)

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ParIdId implements registro.RegistroArvoreBMais<ParIdId> {

  private int id1;
  private int id2;
  private short TAMANHO = 8;

  public ParIdId() {
    this(-1, -1);
  }

  public ParIdId(int n1) {
    this(n1, -1);
  }

  public ParIdId(int n1, int n2) {
    try {
      this.id1 = n1; // ID da entidade agregadora SERIE
      this.id2 = n2; // ID da outra entidade
    } catch (Exception ec) {
      ec.printStackTrace();
    }
  }

  @Override
  public ParIdId clone() {
    return new ParIdId(this.id1, this.id2);
  }

  public short size() {
    return this.TAMANHO;
  }

  public int compareTo(ParIdId a) {
    if (this.id1 != a.id1)
      return this.id1 - a.id1;
    else
      // Só compara os valores de id2, se o id2 da busca for diferente de -1
      // Isso é necessário para que seja possível a busca de lista
      return this.id2 == -1 ? 0 : this.id2 - a.id2;
  }

  public String toString() {
    return String.format("%3d", this.id1) + ";" + String.format("%-3d", this.id2);
  }

  public byte[] toByteArray() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);
    dos.writeInt(this.id1);
    dos.writeInt(this.id2);
    return baos.toByteArray();
  }

  public void fromByteArray(byte[] ba) throws IOException {
    ByteArrayInputStream bais = new ByteArrayInputStream(ba);
    DataInputStream dis = new DataInputStream(bais);
    this.id1 = dis.readInt();
    this.id2 = dis.readInt();
  }

  public int getId2() {
    return id2;
  }

}