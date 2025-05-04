package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.AbstractBorder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListaSolicitacaoFerias extends JFrame {

    private JTable tabelaSolicitacoes;
    private DefaultTableModel modeloTabela;
    private JButton botaoEditar, botaoExcluir;
    private Connection conexao;
    private String urlBancoDeDados;
    private String usuarioBancoDeDados;
    private String senhaBancoDeDados;

    public ListaSolicitacaoFerias(String urlBancoDeDados, String usuarioBancoDeDados, String senhaBancoDeDados) {
        this.urlBancoDeDados = urlBancoDeDados;
        this.usuarioBancoDeDados = usuarioBancoDeDados;
        this.senhaBancoDeDados = senhaBancoDeDados;
        setTitle("Lista de Solicitações de Férias");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        modeloTabela = new DefaultTableModel();
        tabelaSolicitacoes = new JTable(modeloTabela);
        tabelaSolicitacoes.setFont(new Font("Arial", Font.PLAIN, 16));
        tabelaSolicitacoes.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));

        modeloTabela.addColumn("Nome");
        modeloTabela.addColumn("Departamento");
        modeloTabela.addColumn("Data de Início");
        modeloTabela.addColumn("Data de Fim");
        modeloTabela.addColumn("Motivo");
        modeloTabela.addColumn("Status");

        JScrollPane scrollPane = new JScrollPane(tabelaSolicitacoes);
        add(scrollPane, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        botaoEditar = criarBotao("Editar", "Editar solicitação de férias selecionada");
        botaoExcluir = criarBotao("Excluir", "Excluir solicitação de férias selecionada");

        painelBotoes.add(botaoEditar);
        painelBotoes.add(botaoExcluir);

        add(painelBotoes, BorderLayout.SOUTH);

        botaoEditar.addActionListener(e -> editarSolicitacaoFerias());
        botaoExcluir.addActionListener(e -> excluirSolicitacaoFerias());

        // Conectar ao banco de dados e carregar as solicitações
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexao = DriverManager.getConnection(urlBancoDeDados, usuarioBancoDeDados, senhaBancoDeDados);
            carregarSolicitacoes();
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados: " + e.getMessage());
            botaoEditar.setEnabled(false);
            botaoExcluir.setEnabled(false);
        }

        setVisible(true);
    }

    private void editarSolicitacaoFerias() {
        int linhaSelecionada = tabelaSolicitacoes.getSelectedRow();
        if (linhaSelecionada >= 0) {
            // Lógica para editar a solicitação de férias selecionada
            JOptionPane.showMessageDialog(this, "Editar solicitação de férias: " + modeloTabela.getValueAt(linhaSelecionada, 0));
            // Você pode abrir uma nova janela ou um diálogo para editar os detalhes da solicitação
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma solicitação de férias para editar.");
        }
    }

    private void excluirSolicitacaoFerias() {
        int linhaSelecionada = tabelaSolicitacoes.getSelectedRow();
        if (linhaSelecionada >= 0) {
            // Lógica para excluir a solicitação de férias selecionada
            try {
                String nomeFuncionario = (String) modeloTabela.getValueAt(linhaSelecionada, 0); // Supondo que o nome esteja na primeira coluna
                String sql = "DELETE FROM solicitacao_ferias WHERE funcionario_id = (SELECT id FROM funcionarios WHERE nome = ?)";
                PreparedStatement ps = conexao.prepareStatement(sql);
                ps.setString(1, nomeFuncionario);
                int resultado = ps.executeUpdate();
                if (resultado > 0) {
                    modeloTabela.removeRow(linhaSelecionada);
                    JOptionPane.showMessageDialog(this, "Solicitação de férias excluída com sucesso.");
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir solicitação de férias.");
                }
                ps.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir solicitação de férias: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma solicitação de férias para excluir.");
        }
    }

    private void carregarSolicitacoes() {
    try {
        String sql = "SELECT f.nome, d.nome_departamento, s.data_inicio, s.data_fim, s.motivo, s.status " +
                     "FROM solicitacao_ferias s " +
                     "JOIN funcionarios f ON s.funcionario_id = f.id " +
                     "JOIN departamentos d ON f.departamento_id = d.id_departamento";
        PreparedStatement ps = conexao.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String nome = rs.getString("nome");
            String departamento = rs.getString("nome_departamento");
            java.sql.Date dataInicio = rs.getDate("data_inicio");
            java.sql.Date dataFim = rs.getDate("data_fim");
            String motivo = rs.getString("motivo");
            String status = rs.getString("status");
            modeloTabela.addRow(new Object[]{nome, departamento, dataInicio, dataFim, motivo, status});
        }
        rs.close();
        ps.close();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Erro ao carregar solicitações de férias: " + e.getMessage());
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

    public static void main(String[] args) {
        // Substitua pelos seus dados de conexão
        String urlBancoDeDados = "jdbc:mysql://localhost:3306/funcionarios_departamentos?useSSL=false&serverTimezone=UTC";
        String usuarioBancoDeDados = "root";
        String senhaBancoDeDados = "";

        // Executa na thread de despacho de eventos Swing
        SwingUtilities.invokeLater(() -> {
            new ListaSolicitacaoFerias(urlBancoDeDados, usuarioBancoDeDados, senhaBancoDeDados);
        });
    }
}

