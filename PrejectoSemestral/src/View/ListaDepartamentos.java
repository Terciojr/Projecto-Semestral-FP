package View;

import Dao.DepartamentoDao;
import Model.DepartamentoModel;
import Model.FuncionarioModel;
import dao.FuncionarioDao;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.AbstractBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ListaDepartamentos extends JFrame {

    private JTable tabelaDepartamentos;
    private DefaultTableModel modeloTabela;
    private JButton botaoEditar, botaoExcluir, botaoVoltar; // Adicionado botão Voltar

    public ListaDepartamentos() {
        setTitle("Lista de Departamentos");
        setSize(800, 650); // Aumentei a altura para acomodar o novo botão
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        modeloTabela = new DefaultTableModel();
        tabelaDepartamentos = new JTable(modeloTabela);

        modeloTabela.addColumn("Nome do Departamento");
        modeloTabela.addColumn("Descrição"); // Adicionada coluna Descrição
        modeloTabela.addColumn("Quantidade de Funcionários");
        modeloTabela.addColumn("Responsável");
        modeloTabela.addColumn("Hora de Funcionamento"); // Adicionada coluna Hora de Funcionamento

        // A tabela é inicializada vazia, sem dados

        JScrollPane scrollPane = new JScrollPane(tabelaDepartamentos);
        add(scrollPane, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Usando FlowLayout para centralizar
        botaoEditar = criarBotao("Editar", "Editar departamento selecionado");
        botaoExcluir = criarBotao("Excluir", "Excluir departamento selecionado");
        botaoVoltar = criarBotao("Voltar", "Retornar à tela principal"); // Criando o botão Voltar

        painelBotoes.add(botaoEditar);
        painelBotoes.add(botaoExcluir);
        painelBotoes.add(botaoVoltar); // Adicionando o botão Voltar ao painel

        add(painelBotoes, BorderLayout.SOUTH);

        botaoEditar.addActionListener(e -> editarDepartamento());
        botaoExcluir.addActionListener(e -> excluirDepartamento());

        // Adicionando ActionListener para o botão Voltar
        botaoVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TelaPrincipal().setVisible(true);
                ListaDepartamentos.this.dispose(); // Fechar a tela atual
            }
        });
        preenchertabela();
        setVisible(true);
    }

    private void editarDepartamento() {
        int linhaSelecionada = tabelaDepartamentos.getSelectedRow();
        if (linhaSelecionada >= 0) {
            // Lógica para editar o departamento selecionado
            JOptionPane.showMessageDialog(this, "Editar departamento: " + modeloTabela.getValueAt(linhaSelecionada, 0));
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um departamento para editar.");
        }
    }

    private void excluirDepartamento() {
        int linhaSelecionada = tabelaDepartamentos.getSelectedRow();
        if (linhaSelecionada >= 0) {
            // Lógica para excluir o departamento selecionado
            modeloTabela.removeRow(linhaSelecionada);
            JOptionPane.showMessageDialog(this, "Departamento excluído com sucesso.");
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um departamento para excluir.");
        }
    }

    private JButton criarBotao(String texto, String tooltip) {
        JButton botao = new JButton(texto);
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Arial", Font.BOLD, 14));
        botao.setFocusPainted(false);
        botao.setToolTipText(tooltip);
        botao.setBackground(new Color(0, 150, 136));
        botao.setBorder(new RoundedBorder(8));
        return botao;
    }

    static class RoundedBorder extends AbstractBorder {
        private int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(new Color(0, 150, 136));
            g2d.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius));
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius / 2, radius / 2, radius / 2, radius / 2);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.top = insets.right = insets.bottom = radius / 2;
            return insets;
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }
    }
    
    public void preenchertabela() {
    DefaultTableModel tabela = (DefaultTableModel) tabelaDepartamentos.getModel(); // Correção aqui
    DepartamentoDao d = new DepartamentoDao();
    java.util.List<DepartamentoModel> lista;
    lista = d.listar();
    for (int i = 0; i < lista.size(); i++) {
        Object[] dados = {
            lista.get(i).getIdDepartamento(),
            lista.get(i).getNomeDepartamento(),
            lista.get(i).getDescricaoDepartamento(),
            lista.get(i).getResponsavelDepartamento(),
            lista.get(i).getHoraFuncionamentoDepartamento()};
        tabela.addRow(dados);
    }}
    
    //metotodo pra preenccher tabela 
    
}