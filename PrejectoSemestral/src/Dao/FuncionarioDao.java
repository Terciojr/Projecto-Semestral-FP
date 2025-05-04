package dao;

import Model.FuncionarioModel;
import javax.persistence.*;
import javax.swing.*;
import java.util.List;

public class FuncionarioDao {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("funcionarios_departamentos");

    public void gravar(FuncionarioModel funcionario) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(funcionario);
            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "FUNCIONÁRIO REGISTRADO");
        } catch (Exception erro) {
            em.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, "FUNCIONÁRIO NÃO REGISTRADO\n" + erro);
        } finally {
            em.close();
        }
    }

    public void actualizar(FuncionarioModel funcionario) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(funcionario);
            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "FUNCIONÁRIO ATUALIZADO");
        } catch (Exception erro) {
            em.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, "FUNCIONÁRIO NÃO ATUALIZADO\n" + erro);
        } finally {
            em.close();
        }
    }

    public List<FuncionarioModel> listar() {
        EntityManager em = emf.createEntityManager();
        List<FuncionarioModel> lista = null;
        try {
            em.getTransaction().begin();
            lista = em.createQuery("SELECT f FROM FuncionarioModel f WHERE f.existe = true", FuncionarioModel.class).getResultList();
            em.getTransaction().commit();
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "ERRO AO LISTAR FUNCIONÁRIOS\n" + erro);
        } finally {
            em.close();
        }
        return lista;
    }

    public void eliminar(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            FuncionarioModel funcionario = em.find(FuncionarioModel.class, id);
            if (funcionario != null) {
                funcionario.setExiste(false);
                em.merge(funcionario);
                JOptionPane.showMessageDialog(null, "FUNCIONÁRIO ELIMINADO COM SUCESSO");
            } else {
                JOptionPane.showMessageDialog(null, "FUNCIONÁRIO COM ID " + id + " NÃO ENCONTRADO");
            }
            em.getTransaction().commit();
        } catch (Exception erro) {
            em.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, "ERRO AO ELIMINAR FUNCIONÁRIO\n" + erro);
        } finally {
            em.close();
        }
    }

    public List<FuncionarioModel> listarEliminados() {
        EntityManager em = emf.createEntityManager();
        List<FuncionarioModel> lista = null;
        try {
            em.getTransaction().begin();
            lista = em.createQuery("SELECT f FROM FuncionarioModel f WHERE f.existe = false", FuncionarioModel.class).getResultList();
            em.getTransaction().commit();
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "ERRO AO LISTAR FUNCIONÁRIOS ELIMINADOS\n" + erro);
        } finally {
            em.close();
        }
        return lista;
    }

    public void restaurar(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            FuncionarioModel funcionario = em.find(FuncionarioModel.class, id);
            if (funcionario != null && !funcionario.isExiste()) {
                funcionario.setExiste(true);
                em.merge(funcionario);
                JOptionPane.showMessageDialog(null, "FUNCIONÁRIO RESTAURADO COM SUCESSO");
            } else if (funcionario == null) {
                JOptionPane.showMessageDialog(null, "FUNCIONÁRIO COM ID " + id + " NÃO ENCONTRADO");
            } else {
                JOptionPane.showMessageDialog(null, "FUNCIONÁRIO COM ID " + id + " JÁ ESTÁ ATIVO");
            }
            em.getTransaction().commit();
        } catch (Exception erro) {
            em.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, "ERRO AO RESTAURAR FUNCIONÁRIO\n" + erro);
        } finally {
            em.close();
        }
    }
    public FuncionarioModel buscarPorId(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(FuncionarioModel.class, id);
        } finally {
            em.close();
        }
    }
}
