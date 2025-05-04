package Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Classe de modelo para a solicitação de férias.
 * Mapeamento da entidade para a tabela "solicitacao_ferias" no banco de dados.
 * 
 * @author User
 */

@Entity
@Table(name = "solicitacao_ferias")
public class SolicitacaoFeriasModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Geração automática do id
    private int idSolicitacao;

    private String nomeFuncionario;
    private String departamentoFuncionario;
    private Date dataInicio;
    private Date dataFim;
    private String motivo;
    private String status; // Ex: "Pendente", "Aprovada", "Rejeitada"

    // Construtor padrão
    public SolicitacaoFeriasModel() {
        this.status = "Pendente"; // Define o status inicial como "Pendente"
    }

    // Getters e Setters

    public int getIdSolicitacao() {
        return idSolicitacao;
    }

    public void setIdSolicitacao(int idSolicitacao) {
        this.idSolicitacao = idSolicitacao;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public String getDepartamentoFuncionario() {
        return departamentoFuncionario;
    }

    public void setDepartamentoFuncionario(String departamentoFuncionario) {
        this.departamentoFuncionario = departamentoFuncionario;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
