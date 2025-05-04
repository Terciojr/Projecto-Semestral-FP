package View;

import Controller.RegistroPresencaController;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.AbstractBorder;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegistroPresencas extends JFrame {

    private JTextField campoNomeFuncionario;
    private JComboBox<String> comboFuncionarios;
    private JButton botaoRegistrarPresenca;
    private JTable tabelaPresencas;
    private DefaultTableModel modeloTabela;
    private JLabel labelDataHora;
    private Timer timer;
    private Connection conexao;
    private List<String> listaNomesFuncionarios;
    private JButton botaoVoltar; // Novo botão "Voltar"

    public RegistroPresencas() {
        setTitle("Registro de Presença");
        setSize(800, 550); // Reduzi a altura já que o campo ID foi removido
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(240, 240, 240));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        campoNomeFuncionario = criarTextField("Nome do Funcionário:", 80); // Aumentei o tamanho para 80
        listaNomesFuncionarios = new ArrayList<>();
        comboFuncionarios = new JComboBox<>(listaNomesFuncionarios.toArray(new String[0]));
        configurarComboBox(comboFuncionarios, "Selecione o Funcionário:", 80); // Passando o tamanho para o método
        adicionarComponente("Nome do Funcionário:", campoNomeFuncionario, gbc);
        gbc.gridy++;
        adicionarComponente("", comboFuncionarios, gbc); // Adiciona a ComboBox

        gbc.gridy++;
        botaoRegistrarPresenca = criarBotao("Registrar Presença", "Registrar a presença do funcionário");
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(botaoRegistrarPresenca, gbc);
        botaoRegistrarPresenca.addActionListener(e -> registrarPresenca());

        gbc.gridy++;
        labelDataHora = new JLabel();
        labelDataHora.setFont(new Font("Arial", Font.BOLD, 16));
        labelDataHora.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridwidth = 2;
        add(labelDataHora, gbc);
        atualizarDataHora();

        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        tabelaPresencas = criarTabela();
        JScrollPane scrollPane = new JScrollPane(tabelaPresencas);
        add(scrollPane, gbc);

        gbc.gridy++;
        botaoVoltar = criarBotao("Voltar", "Voltar para a tela principal");
        botaoVoltar.addActionListener(e -> {
            dispose();
            new TelaPrincipal().setVisible(true);
        });
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0; // Reset weight
        add(botaoVoltar, gbc);

        conectarBancoDados();
        inicializarComboBoxFuncionarios();

        // Adiciona listeners para atualizar a ComboBox ao digitar no campo de nome
        campoNomeFuncionario.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                atualizarComboBoxFuncionarios();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                atualizarComboBoxFuncionarios();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                atualizarComboBoxFuncionarios();
            }
        });

        // Timer para atualizar a data e hora a cada segundo
        timer = new Timer(1000, e -> atualizarDataHora());
        timer.start();

        setVisible(true);
    }

    private void conectarBancoDados() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/funcionarios_departamentos?useSSL=false&serverTimezone=UTC", "root", ""); // Substitua pelos seus dados
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados: " + e.getMessage());
            botaoRegistrarPresenca.setEnabled(false);
        }
    }

    private void inicializarComboBoxFuncionarios() {
        listaNomesFuncionarios.clear();
        comboFuncionarios.removeAllItems();
        try {
            String sql = "SELECT nome FROM funcionario";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listaNomesFuncionarios.add(rs.getString("nome"));
                comboFuncionarios.addItem(rs.getString("nome"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar funcionários: " + e.getMessage());
        }
    }

    private void atualizarComboBoxFuncionarios() {
        String nomePesquisa = campoNomeFuncionario.getText();
        listaNomesFuncionarios.clear();
        comboFuncionarios.removeAllItems();
        try {
            String sql = "SELECT nome FROM funcionario WHERE nome LIKE ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, "%" + nomePesquisa + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listaNomesFuncionarios.add(rs.getString("nome"));
                comboFuncionarios.addItem(rs.getString("nome"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar funcionários: " + e.getMessage());
        }
    }

    private JTextField criarTextField(String tooltip, int tamanho) {
        JTextField textField = new JTextField(tamanho);
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        textField.setBorder(new RoundedBorder(8));
        textField.setToolTipText(tooltip);
        return textField;
    }

    private void configurarComboBox(JComboBox<String> comboBox, String tooltip, int tamanho) { // Adicionei o parâmetro tamanho
        comboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        comboBox.setBorder(new RoundedBorder(8));
        comboBox.setToolTipText(tooltip);
        comboBox.setPreferredSize(new Dimension(tamanho * 10, 30)); // Defini a largura da ComboBox
        comboBox.setSelectedIndex(-1);
    }

    private JButton criarBotao(String texto, String tooltip) {
        JButton botao = new JButton(texto);
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Arial", Font.BOLD, 16));
        botao.setFocusPainted(false);
        botao.setToolTipText(tooltip);
        botao.setBackground(new Color(0, 150, 136));
        botao.setBorder(new RoundedBorder(8));
        return botao;
    }

    private JTable criarTabela() {
        modeloTabela = new DefaultTableModel();
        modeloTabela.addColumn("ID");
        modeloTabela.addColumn("Nome do Funcionário");
        modeloTabela.addColumn("Data/Hora");
        JTable tabela = new JTable(modeloTabela);
        tabela.setFont(new Font("Arial", Font.PLAIN, 16));
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setDefaultEditor(Object.class, null); // Impede a edição das células
        return tabela;
    }

    private void adicionarComponente(String texto, JComponent componente, GridBagConstraints gbc) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        add(label, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(componente, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
    }

    private void registrarPresenca() {
        if (comboFuncionarios.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Selecione um funcionário!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nomeFuncionario = (String) comboFuncionarios.getSelectedItem();
        String dataHora = labelDataHora.getText();

        // Buscar ID do funcionário no banco de dados
        RegistroPresencaController controller = new RegistroPresencaController();
        controller.gravar(
                nomeFuncionario,
                dataHora
        );
        
        JOptionPane.showMessageDialog(this, "Presenca REgistrada !");
        limparCampos();
    }

    

    

    private void atualizarDataHora() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dataHoraAtual = sdf.format(new Date());
        labelDataHora.setText("Data/Hora: " + dataHoraAtual);
    }

    private void limparCampos() {
        campoNomeFuncionario.setText("");
        comboFuncionarios.setSelectedIndex(-1);
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
}