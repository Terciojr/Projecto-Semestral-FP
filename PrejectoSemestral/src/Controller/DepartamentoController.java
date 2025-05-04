package Controller;

import Dao.DepartamentoDao;
import Model.DepartamentoModel;
import java.util.List;
import javax.swing.JOptionPane;

public class DepartamentoController {
    DepartamentoModel departamentoModel = new DepartamentoModel();
    DepartamentoDao departamentoDao = new DepartamentoDao();

    public DepartamentoController() {
    }

    public List<DepartamentoModel> listar() {
        return departamentoDao.listar();
    }

    public void gravar(String nomeDepartamento, String descricaoDepartamento, String responsavelDepartamento, String cargoResponsavelDepartamento, String horaFuncionamentoDepartamento) {
        departamentoModel.setNomeDepartamento(nomeDepartamento);
        departamentoModel.setDescricaoDepartamento(descricaoDepartamento);
        departamentoModel.setResponsavelDepartamento(responsavelDepartamento);
        departamentoModel.setCargoResponsavelDepartamento(cargoResponsavelDepartamento);
        departamentoModel.setHoraFuncionamentoDepartamento(horaFuncionamentoDepartamento);

        departamentoDao.gravar(departamentoModel);
    }

    public void actualizar(int idDepartamento, String nomeDepartamento, String descricaoDepartamento, String responsavelDepartamento, String cargoResponsavelDepartamento, String horaFuncionamentoDepartamento) {
        departamentoModel.setIdDepartamento(idDepartamento);
        departamentoModel.setNomeDepartamento(nomeDepartamento);
        departamentoModel.setDescricaoDepartamento(descricaoDepartamento);
        departamentoModel.setResponsavelDepartamento(responsavelDepartamento);
        departamentoModel.setCargoResponsavelDepartamento(cargoResponsavelDepartamento);
        departamentoModel.setHoraFuncionamentoDepartamento(horaFuncionamentoDepartamento);

        departamentoDao.actualizar(departamentoModel);
    }

    public void eliminar(int id) {
        departamentoDao.eliminar(id);
    }

    public List<DepartamentoModel> listarEliminados() {
        return departamentoDao.listarEliminados();
    }

    public void restaurar(int id) {
        departamentoDao.restaurar(id);
    }
}