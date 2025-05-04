package Controller;

import Model.FuncionarioModel;
import Model.RegistroPresencaModel;
import dao.RegistroPresencaDao;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class RegistroPresencaController {
    RegistroPresencaModel registroPresencaModel = new RegistroPresencaModel();
    RegistroPresencaDao registroPresencaDao = new RegistroPresencaDao();

    public RegistroPresencaController() {
    }

    public List<RegistroPresencaModel> listar() {
        return registroPresencaDao.listar();
    }

    public void gravar(String dataRegistro, String nome) {
        registroPresencaModel.setDataRegistro(dataRegistro);
        registroPresencaModel.setNome(nome);
        

        registroPresencaDao.gravar(registroPresencaModel);
    }

    public RegistroPresencaModel buscarPorId(int id) {
        return registroPresencaDao.buscarPorId(id);
    }
}