package Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Classe de modelo para o departamento.
 * Mapeamento da entidade para a tabela "departamento" no banco de dados.
 * 
 * @author User
 */

@Entity
@Table(name = "departamento")
public class DepartamentoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Geração automática do id
    private int idDepartamento;

    private String nomeDepartamento;
    private String descricaoDepartamento;
    private String responsavelDepartamento;
    private String cargoResponsavelDepartamento;
    private String horaFuncionamentoDepartamento;

    private boolean existe;  // Indica se o departamento está ativo ou não

    // Construtor padrão
    public DepartamentoModel() {
    }

    // Getters e Setters

    public int getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getNomeDepartamento() {
        return nomeDepartamento;
    }

    public void setNomeDepartamento(String nomeDepartamento) {
        this.nomeDepartamento = nomeDepartamento;
    }

    public String getDescricaoDepartamento() {
        return descricaoDepartamento;
    }

    public void setDescricaoDepartamento(String descricaoDepartamento) {
        this.descricaoDepartamento = descricaoDepartamento;
    }

    public String getResponsavelDepartamento() {
        return responsavelDepartamento;
    }

    public void setResponsavelDepartamento(String responsavelDepartamento) {
        this.responsavelDepartamento = responsavelDepartamento;
    }

    public String getCargoResponsavelDepartamento() {
        return cargoResponsavelDepartamento;
    }

    public void setCargoResponsavelDepartamento(String cargoResponsavelDepartamento) {
        this.cargoResponsavelDepartamento = cargoResponsavelDepartamento;
    }

    public String getHoraFuncionamentoDepartamento() {
        return horaFuncionamentoDepartamento;
    }

    public void setHoraFuncionamentoDepartamento(String horaFuncionamentoDepartamento) {
        this.horaFuncionamentoDepartamento = horaFuncionamentoDepartamento;
    }

    public boolean isExiste() {
        return existe;
    }

    public void setExiste(boolean existe) {
        this.existe = existe;
    }
}
