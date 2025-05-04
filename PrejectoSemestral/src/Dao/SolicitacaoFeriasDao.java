package dao;

import Model.SolicitacaoFeriasModel;
import javax.persistence.*;
import javax.swing.*;
import java.util.List;

public class SolicitacaoFeriasDao {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("funcionarios_departamentos");

    public void gravar(SolicitacaoFeriasModel solicitacao) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(solicitacao);
            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "SOLICITAÇÃO REGISTRADA");
        } catch (Exception erro) {
            em.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, "ERRO AO REGISTRAR SOLICITAÇÃO\n" + erro);
        } finally {
            em.close();
        }
    }

    public void actualizar(SolicitacaoFeriasModel solicitacao) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(solicitacao);
            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "SOLICITAÇÃO ATUALIZADA");
        } catch (Exception erro) {
            em.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, "ERRO AO ATUALIZAR SOLICITAÇÃO\n" + erro);
        } finally {
            em.close();
        }
    }

    public List<SolicitacaoFeriasModel> listar() {
        EntityManager em = emf.createEntityManager();
        List<SolicitacaoFeriasModel> lista = null;
        try {
            lista = em.createQuery("SELECT s FROM SolicitacaoFeriasModel s", SolicitacaoFeriasModel.class).getResultList();
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "ERRO AO LISTAR SOLICITAÇÕES\n" + erro);
        } finally {
            em.close();
        }
        return lista;
    }

    public SolicitacaoFeriasModel buscarPorId(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(SolicitacaoFeriasModel.class, id);
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "ERRO AO BUSCAR SOLICITAÇÃO\n" + erro);
            return null;
        } finally {
            em.close();
        }
    }

    public void eliminar(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            SolicitacaoFeriasModel solicitacao = em.find(SolicitacaoFeriasModel.class, id);
            if (solicitacao != null) {
                em.remove(solicitacao);
                JOptionPane.showMessageDialog(null, "SOLICITAÇÃO REMOVIDA COM SUCESSO");
            } else {
                JOptionPane.showMessageDialog(null, "SOLICITAÇÃO NÃO ENCONTRADA");
            }
            em.getTransaction().commit();
        } catch (Exception erro) {
            em.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, "ERRO AO REMOVER SOLICITAÇÃO\n" + erro);
        } finally {
            em.close();
        }
    }
}
