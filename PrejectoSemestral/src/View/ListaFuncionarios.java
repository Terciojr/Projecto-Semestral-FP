package View;

import Model.FuncionarioModel;
import dao.FuncionarioDao;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.AbstractBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ListaFuncionarios extends JFrame {

    private JTable tabelaFuncionarios;
    private DefaultTableModel modeloTabela;
    private JButton botaoEditar;
    private JButton botaoExcluir;
    private JButton botaoVerPerfil;
    private JButton botaoVoltar; // Novo botão "Voltar"

    public ListaFuncionarios() {
        
        setTitle("Gerenciamento de Funcionários");
        setSize(800, 650); // Aumentei a altura para acomodar o novo botão
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel de fundo com azul morto
        JPanel painelFundo = new JPanel();
        painelFundo.setBackground(new Color(204, 204, 255)); // Azul morto
        add(painelFundo, BorderLayout.CENTER);
        painelFundo.setLayout(new BorderLayout());

        // Modelo da tabela
        modeloTabela = new DefaultTableModel();
        modeloTabela.addColumn("Nome");
        modeloTabela.addColumn("Cargo");
        modeloTabela.addColumn("Departamento");
        modeloTabela.addColumn("ID");

        // Tabela
        tabelaFuncionarios = new JTable(modeloTabela);
        tabelaFuncionarios.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Fonte bonita
        JScrollPane scrollPane = new JScrollPane(tabelaFuncionarios);
        painelFundo.add(scrollPane, BorderLayout.CENTER);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Usando FlowLayout para centralizar
        painelBotoes.setBackground(new Color(204, 204, 255)); // Azul morto

        botaoEditar = criarBotao("Editar", "Editar funcionário");
        botaoExcluir = criarBotao("Excluir", "Excluir funcionário");
        botaoVerPerfil = criarBotao("Ver Perfil", "Ver perfil do funcionário");
        botaoVoltar = criarBotao("Voltar", "Retornar à tela principal"); // Criando o botão Voltar

        painelBotoes.add(botaoEditar);
        painelBotoes.add(botaoExcluir);
        painelBotoes.add(botaoVerPerfil);
        painelBotoes.add(botaoVoltar); // Adicionando o botão Voltar

        add(painelBotoes, BorderLayout.SOUTH);

        // Ações dos botões (exemplo: exibe uma mensagem)
        botaoEditar.addActionListener(e -> {
            int linhaSelecionada = tabelaFuncionarios.getSelectedRow();
            if (linhaSelecionada >= 0) {
                // Aqui você chamaria o método para editar o funcionário, passando o ID
                JOptionPane.showMessageDialog(ListaFuncionarios.this, "Editar funcionário: " + modeloTabela.getValueAt(linhaSelecionada, 0));
            } else {
                JOptionPane.showMessageDialog(ListaFuncionarios.this, "Selecione um funcionário para editar.");
            }
        });

        botaoExcluir.addActionListener(e -> {
            int linhaSelecionada = tabelaFuncionarios.getSelectedRow();
            if (linhaSelecionada >= 0) {
                // Aqui você chamaria o método para excluir o funcionário, passando o ID
                JOptionPane.showMessageDialog(ListaFuncionarios.this, "Excluir funcionário: " + modeloTabela.getValueAt(linhaSelecionada, 0));
                modeloTabela.removeRow(linhaSelecionada); // Remove da tabela
            } else {
                JOptionPane.showMessageDialog(ListaFuncionarios.this, "Selecione um funcionário para excluir.");
            }
        });

    botaoVerPerfil.addActionListener(e -> {
    int linhaSelecionada = tabelaFuncionarios.getSelectedRow();
    if (linhaSelecionada >= 0) {
        try {
            Object idObj = modeloTabela.getValueAt(linhaSelecionada, 3);
            int idFuncionario = Integer.parseInt(idObj.toString());
            
            FuncionarioDao dao = new FuncionarioDao();
            FuncionarioModel funcionario = dao.buscarPorId(idFuncionario);
            
            if (funcionario != null) {
                new PerfilFuncionario(funcionario).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(ListaFuncionarios.this, 
                    "Funcionário não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(ListaFuncionarios.this, 
                "Erro ao carregar perfil: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    } else {
        JOptionPane.showMessageDialog(ListaFuncionarios.this, 
            "Selecione um funcionário para ver o perfil.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    });

        // ActionListener para o botão Voltar
        botaoVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TelaPrincipal().setVisible(true);
                ListaFuncionarios.this.dispose(); // Fechar a tela atual
            }
        });
        preenchertabela();
        setVisible(true);
        }

    private JButton criarBotao(String texto, String tooltip) {
        JButton botao = new JButton(texto);
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Fonte bonita
        botao.setFocusPainted(false);
        botao.setToolTipText(tooltip);
        botao.setBackground(new Color(0, 173, 181)); // Azul ciano
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
            g2d.setColor(new Color(0, 173, 181)); // Azul ciano
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
    DefaultTableModel tabela = (DefaultTableModel) tabelaFuncionarios.getModel(); // Correção aqui
    FuncionarioDao d = new FuncionarioDao();
    java.util.List<FuncionarioModel> lista;
    lista = d.listar();
    for (int i = 0; i < lista.size(); i++) {
        Object[] dados = {
            lista.get(i).getNome(),
            lista.get(i).getCargo(),
            lista.get(i).getDepartamento(),
            lista.get(i).getIdFuncionario()
        };
        tabela.addRow(dados);
    }
}

}