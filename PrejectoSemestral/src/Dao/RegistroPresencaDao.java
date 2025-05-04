package dao;

import Model.RegistroPresencaModel;
import javax.persistence.*;
import javax.swing.JOptionPane;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class RegistroPresencaDao {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("funcionarios_departamentos");

    public void gravar(RegistroPresencaModel registro) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(registro);
            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "PRESENÇA REGISTRADA");
        } catch (Exception erro) {
            em.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, "ERRO AO REGISTRAR PRESENÇA\n" + erro);
        } finally {
            em.close();
        }
    }

    public void actualizar(RegistroPresencaModel registro) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(registro);
            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "REGISTRO DE PRESENÇA ATUALIZADO");
        } catch (Exception erro) {
            em.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, "ERRO AO ATUALIZAR PRESENÇA\n" + erro);
        } finally {
            em.close();
        }
    }

    public List<RegistroPresencaModel> listar() {
    EntityManager em = emf.createEntityManager();
    List<RegistroPresencaModel> lista = new ArrayList<>(); // inicia como lista vazia
    try {
        lista = em.createQuery("SELECT r FROM RegistroPresencaModel r", RegistroPresencaModel.class).getResultList();
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "ERRO AO LISTAR PRESENÇAS\n" + erro);
        } finally {
            em.close();
        }
        return lista; // nunca será null
    }


    public RegistroPresencaModel buscarPorId(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(RegistroPresencaModel.class, id);
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "ERRO AO BUSCAR REGISTRO\n" + erro);
            return null;
        } finally {
            em.close();
        }
    }

    public void eliminar(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            RegistroPresencaModel registro = em.find(RegistroPresencaModel.class, id);
            if (registro != null) {
                em.remove(registro);
                em.getTransaction().commit();
                JOptionPane.showMessageDialog(null, "PRESENÇA REMOVIDA COM SUCESSO");
            } else {
                JOptionPane.showMessageDialog(null, "REGISTRO NÃO ENCONTRADO");
            }
        } catch (Exception erro) {
            em.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, "ERRO AO REMOVER REGISTRO\n" + erro);
        } finally {
            em.close();
        }
    }

    public boolean verificarFalta(String nome) {
        EntityManager em = emf.createEntityManager();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String codigoDiarioHoje = sdf.format(new Date());

            // Consulta para verificar se existe algum registro de presença do funcionário na data de hoje
            Query query = em.createQuery(
                    "SELECT COUNT(r) FROM RegistroPresencaModel r WHERE r.idFuncionario = :idFuncionario AND r.codigoDiario = :codigoDiarioHoje", Long.class);
            query.setParameter("idFuncionario", nome);
            query.setParameter("codigoDiarioHoje", codigoDiarioHoje);
            Long count = (Long) query.getSingleResult();

            return count == 0; // Se count for 0, não há registro de presença para hoje, então está com falta
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "ERRO AO VERIFICAR FALTA\n" + erro);
            return true; // Em caso de erro, retorna true para evitar comportamentos inesperados
        } finally {
            em.close();
        }
    }
}

