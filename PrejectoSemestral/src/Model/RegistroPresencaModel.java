package Model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "registro_presenca") // Nome da tabela no banco de dados
public class RegistroPresencaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gera automaticamente o ID
    private int idRegistro;
    
    private String dataRegistro;
    private String nome;

    public RegistroPresencaModel() {
    }

    public int getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    

    public String getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(String dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
