package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RelatorioFuncionariosDepartamento extends JFrame {

    private JTable tabelaFuncionarios;
    private DefaultTableModel modeloTabela;

    public RelatorioFuncionariosDepartamento(List<FuncionarioDepartamento> funcionarios) {
        setTitle("Relatório de Funcionários por Departamento");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        modeloTabela = new DefaultTableModel();
        tabelaFuncionarios = new JTable(modeloTabela);

        modeloTabela.addColumn("Nome");
        modeloTabela.addColumn("Departamento");

        JScrollPane scrollPane = new JScrollPane(tabelaFuncionarios);
        add(scrollPane, BorderLayout.CENTER);

        exibirFuncionarios(funcionarios);

        setVisible(true);
    }

    private void exibirFuncionarios(List<FuncionarioDepartamento> funcionarios) {
        modeloTabela.setRowCount(0);
        for (FuncionarioDepartamento funcionario : funcionarios) {
            modeloTabela.addRow(new Object[]{funcionario.getNome(), funcionario.getDepartamento()});
        }
    }

    // Classe interna para representar um funcionário e seu departamento
    static class FuncionarioDepartamento {
        private String nome;
        private String departamento;

        public FuncionarioDepartamento(String nome, String departamento) {
            this.nome = nome;
            this.departamento = departamento;
        }

        public String getNome() {
            return nome;
        }

        public String getDepartamento() {
            return departamento;
        }
    }

}