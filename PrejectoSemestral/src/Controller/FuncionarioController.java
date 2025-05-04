package Controller;

import Model.FuncionarioModel;
import dao.FuncionarioDao;
import java.util.List;
//import view.ListagemFuncionario;

public class FuncionarioController {
    FuncionarioModel funcionarioModel = new FuncionarioModel();
    FuncionarioDao funcionarioDao = new FuncionarioDao();

    public FuncionarioController() {
    }

    public List<FuncionarioModel> listar() {
        return funcionarioDao.listar();
    }

    public void gravar(String nome, String cargo, double salario, String email, String senha, String departamento, java.util.Date dataNascimento, String morada, String telefone, String contatoEmergencia, String genero, String fotoPath) {
        funcionarioModel.setNome(nome);
        funcionarioModel.setCargo(cargo);
        funcionarioModel.setSalario(salario);
        funcionarioModel.setEmail(email);
        funcionarioModel.setSenha(senha);
        funcionarioModel.setDepartamento(departamento);
        funcionarioModel.setDataNascimento(dataNascimento);
        funcionarioModel.setMorada(morada);
        funcionarioModel.setTelefone(telefone);
        funcionarioModel.setContatoEmergencia(contatoEmergencia);
        funcionarioModel.setGenero(genero);
        funcionarioModel.setFotoPath(fotoPath); // Defina o caminho da foto
        funcionarioModel.setExiste(true);

        funcionarioDao.gravar(funcionarioModel);
    }

    public void actualizar(int idFuncionario, String nome, String cargo, double salario, String email, String senha, String departamento, java.util.Date dataNascimento, String morada, String telefone, String contatoEmergencia, String genero, String fotoPath) {
        funcionarioModel.setIdFuncionario(idFuncionario);
        funcionarioModel.setNome(nome);
        funcionarioModel.setCargo(cargo);
        funcionarioModel.setSalario(salario);
        funcionarioModel.setEmail(email);
        funcionarioModel.setSenha(senha);
        funcionarioModel.setDepartamento(departamento);
        funcionarioModel.setDataNascimento(dataNascimento);
        funcionarioModel.setMorada(morada);
        funcionarioModel.setTelefone(telefone);
        funcionarioModel.setContatoEmergencia(contatoEmergencia);
        funcionarioModel.setGenero(genero);
        funcionarioModel.setFotoPath(fotoPath); // Defina o caminho da foto
        funcionarioModel.setExiste(true);

        funcionarioDao.actualizar(funcionarioModel);
    }

    public void eliminar(int id) {
        funcionarioDao.eliminar(id);
    }

    public List<FuncionarioModel> listarEliminados() {
        return funcionarioDao.listarEliminados();
    }

    public void restaurar(int id) {
        funcionarioDao.restaurar(id);
    }
}