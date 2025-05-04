package Controller;

import Model.SolicitacaoFeriasModel;
import dao.SolicitacaoFeriasDao;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class SolicitacaoFeriasController {
    SolicitacaoFeriasModel solicitacaoFeriasModel = new SolicitacaoFeriasModel();
    SolicitacaoFeriasDao solicitacaoFeriasDao = new SolicitacaoFeriasDao();

    public SolicitacaoFeriasController() {
    }

    public List<SolicitacaoFeriasModel> listar() {
        return solicitacaoFeriasDao.listar();
    }

    public void gravar(String nomeFuncionario, String departamentoFuncionario, Date dataInicio, Date dataFim, String motivo) {
        solicitacaoFeriasModel.setNomeFuncionario(nomeFuncionario);
        solicitacaoFeriasModel.setDepartamentoFuncionario(departamentoFuncionario);
        solicitacaoFeriasModel.setDataInicio(dataInicio);
        solicitacaoFeriasModel.setDataFim(dataFim);
        solicitacaoFeriasModel.setMotivo(motivo);

        solicitacaoFeriasDao.gravar(solicitacaoFeriasModel);
    }

    public void actualizar(int idSolicitacao, String nomeFuncionario, String departamentoFuncionario, Date dataInicio, Date dataFim, String motivo, String status) {
        solicitacaoFeriasModel.setIdSolicitacao(idSolicitacao);
        solicitacaoFeriasModel.setNomeFuncionario(nomeFuncionario);
        solicitacaoFeriasModel.setDepartamentoFuncionario(departamentoFuncionario);
        solicitacaoFeriasModel.setDataInicio(dataInicio);
        solicitacaoFeriasModel.setDataFim(dataFim);
        solicitacaoFeriasModel.setMotivo(motivo);
        solicitacaoFeriasModel.setStatus(status);

        solicitacaoFeriasDao.actualizar(solicitacaoFeriasModel);
    }

    public void eliminar(int id) {
        solicitacaoFeriasDao.eliminar(id);
    }

    public SolicitacaoFeriasModel buscarPorId(int id) {
        return solicitacaoFeriasDao.buscarPorId(id);
    }

    public void actualizarStatus(int id, String novoStatus) {
        SolicitacaoFeriasModel solicitacao = solicitacaoFeriasDao.buscarPorId(id);
        if (solicitacao != null) {
            solicitacao.setStatus(novoStatus);
            solicitacaoFeriasDao.actualizar(solicitacao);
        } else {
            JOptionPane.showMessageDialog(null, "Solicitação de férias com ID " + id + " não encontrada!");
        }
    }
}