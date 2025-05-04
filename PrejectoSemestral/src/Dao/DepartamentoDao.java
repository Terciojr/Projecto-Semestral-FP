package Dao;

import Model.DepartamentoModel;
import javax.persistence.*;
import javax.swing.*;
import java.util.List;

public class DepartamentoDao {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("funcionarios_departamentos");

    public void gravar(DepartamentoModel departamento) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(departamento);
            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "DEPARTAMENTO REGISTRADO");
        } catch (Exception erro) {
            em.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, "DEPARTAMENTO NÃO REGISTRADO\n" + erro);
        } finally {
            em.close();
        }
    }

    public void actualizar(DepartamentoModel departamento) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(departamento);
            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "DEPARTAMENTO ATUALIZADO");
        } catch (Exception erro) {
            em.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, "DEPARTAMENTO NÃO ATUALIZADO\n" + erro);
        } finally {
            em.close();
        }
    }

    public List<DepartamentoModel> listar() {
        EntityManager em = emf.createEntityManager();
        List<DepartamentoModel> lista = null;
        try {
            lista = em.createQuery("SELECT d FROM DepartamentoModel d", DepartamentoModel.class).getResultList();
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "ERRO AO LISTAR DEPARTAMENTOS\n" + erro);
        } finally {
            em.close();
        }
        return lista;
    }

    public List<DepartamentoModel> listarEliminados() {
        EntityManager em = emf.createEntityManager();
        List<DepartamentoModel> lista = null;
        try {
            lista = em.createQuery("SELECT d FROM DepartamentoModel d WHERE d.existe = false", DepartamentoModel.class).getResultList();
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "ERRO AO LISTAR DEPARTAMENTOS ELIMINADOS\n" + erro);
        } finally {
            em.close();
        }
        return lista;
    }

    public void eliminar(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            DepartamentoModel departamento = em.find(DepartamentoModel.class, id);
            if (departamento != null) {
                departamento.setExiste(false);
                em.merge(departamento);
                JOptionPane.showMessageDialog(null, "DEPARTAMENTO ELIMINADO COM SUCESSO");
            } else {
                JOptionPane.showMessageDialog(null, "DEPARTAMENTO COM ID " + id + " NÃO ENCONTRADO");
            }
            em.getTransaction().commit();
        } catch (Exception erro) {
            em.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, "ERRO AO ELIMINAR DEPARTAMENTO\n" + erro);
        } finally {
            em.close();
        }
    }

    public void restaurar(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            DepartamentoModel departamento = em.find(DepartamentoModel.class, id);
            if (departamento == null) {
                JOptionPane.showMessageDialog(null, "DEPARTAMENTO COM ID " + id + " NÃO ENCONTRADO");
            } else if (departamento.isExiste()) {
                JOptionPane.showMessageDialog(null, "DEPARTAMENTO JÁ ESTÁ ATIVO");
            } else {
                departamento.setExiste(true);
                em.merge(departamento);
                JOptionPane.showMessageDialog(null, "DEPARTAMENTO RESTAURADO COM SUCESSO");
            }
            em.getTransaction().commit();
        } catch (Exception erro) {
            em.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, "ERRO AO RESTAURAR DEPARTAMENTO\n" + erro);
        } finally {
            em.close();
        }
    }
}
