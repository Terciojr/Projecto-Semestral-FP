package View;

import Controller.DepartamentoController;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.AbstractBorder;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CadastroDepartamento extends JFrame {

    private JTextField campoNome, campoResponsavel, campoCargoResponsavel;
    private JTextArea campoDescricao;
    private JComboBox<String> comboHoraFuncionamento;
    private JButton botaoSalvar;
    private JComboBox<String> comboResponsavelFuncionario; // ComboBox para Responsável
    private List<String> listaResponsaveis;
    private Connection conexao;
    private JButton botaoVoltar; // Novo botão "Voltar"

    public CadastroDepartamento() {
        setTitle("Cadastro de Departamento");
        setSize(1000, 850); // Aumentei a altura para acomodar o novo botão
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

        campoNome = criarTextField("Nome do Departamento:", 40);
        campoDescricao = criarTextArea("Descrição:", 40, 5);
        campoResponsavel = criarTextField("Responsável pelo Departamento:", 40);
        listaResponsaveis = new ArrayList<>();
        comboResponsavelFuncionario = new JComboBox<>(listaResponsaveis.toArray(new String[0])); // Inicializa ComboBox
        configurarComboBoxResponsavel(comboResponsavelFuncionario, "Selecione o responsável pelo departamento");
        campoCargoResponsavel = criarTextField("Cargo do Responsável:", 40);
        comboHoraFuncionamento = criarComboBoxHoras();

        adicionarComponente("Nome do Departamento:", campoNome, gbc);
        adicionarComponente("Descrição:", campoDescricao, gbc);
        adicionarComponente("Responsável pelo Departamento:", campoResponsavel, gbc);
        gbc.gridx = 1; // Define a coluna para o ComboBox
        gbc.gridy = 3; // Define a linha para o ComboBox (abaixo do campo Responsavel)
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(comboResponsavelFuncionario, gbc); // Adiciona ComboBox ao layout
        gbc.gridx = 0;  // Reseta coluna
        gbc.gridy = 4; // Próxima linha
        adicionarComponente("Cargo do Responsável:", campoCargoResponsavel, gbc);
        adicionarComponente("Hora de Funcionamento:", comboHoraFuncionamento, gbc);

        botaoSalvar = criarBotao("Salvar", "Salvar cadastro do departamento");
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(botaoSalvar, gbc);

        botaoSalvar.addActionListener(e -> salvarDepartamento());

        // Adicionando o botão "Voltar"
        botaoVoltar = criarBotao("Voltar", "Retornar à tela principal");
        gbc.gridy++;
        add(botaoVoltar, gbc);
        botaoVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TelaPrincipal().setVisible(true);
                CadastroDepartamento.this.dispose(); // Fechar a tela atual
            }
        });

        conectarBancoDados();
        inicializarComboBoxResponsavel();

        campoResponsavel.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                buscarResponsavel();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                buscarResponsavel();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                buscarResponsavel();
            }
        });

        setVisible(true);
    }

    private void conectarBancoDados() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/funcionarios_departamentos?useSSL=false&serverTimezone=UTC", "root", ""); // Substitua pelos seus dados
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados: " + e.getMessage());
            botaoSalvar.setEnabled(false);
        }
    }

    private void inicializarComboBoxResponsavel() {
        buscarTodosResponsaveis();
    }

    private void buscarTodosResponsaveis() {
        listaResponsaveis.clear();
        comboResponsavelFuncionario.removeAllItems();
        try {
            String sql = "SELECT nome FROM funcionario";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listaResponsaveis.add(rs.getString("nome"));
                comboResponsavelFuncionario.addItem(rs.getString("nome"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar responsáveis: " + e.getMessage());
        }
    }

    private void buscarResponsavel() {
        String responsavelPesquisa = campoResponsavel.getText();
        listaResponsaveis.clear();
        comboResponsavelFuncionario.removeAllItems();
        try {
            String sql = "SELECT nome FROM funcionario WHERE nome LIKE ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, "%" + responsavelPesquisa + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listaResponsaveis.add(rs.getString("nome"));
                comboResponsavelFuncionario.addItem(rs.getString("nome"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar responsáveis: " + e.getMessage());
        }
    }

    private JTextField criarTextField(String tooltip, int tamanho) {
        JTextField textField = new JTextField(tamanho);
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        textField.setBorder(new RoundedBorder(8));
        textField.setToolTipText(tooltip);
        return textField;
    }

    private JTextArea criarTextArea(String tooltip, int colunas, int linhas) {
        JTextArea textArea = new JTextArea(linhas, colunas);
        textArea.setFont(new Font("Arial", Font.PLAIN, 16));
        textArea.setBorder(new RoundedBorder(8));
        textArea.setToolTipText(tooltip);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        return textArea;
    }

    private JComboBox<String> criarComboBoxHoras() {
        List<String> opcoesHora = new ArrayList<>();
        opcoesHora.add("08:00 - 17:00");
        opcoesHora.add("09:00 - 18:00");
        opcoesHora.add("10:00 - 19:00");
        opcoesHora.add("Outro");

        JComboBox<String> comboBox = new JComboBox<>(opcoesHora.toArray(new String[0]));
        comboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        comboBox.setBorder(new RoundedBorder(8));
        comboBox.setToolTipText("Selecione a hora de funcionamento do departamento");
        return comboBox;
    }

    private void configurarComboBoxResponsavel(JComboBox<String> comboBox, String tooltip) {
        comboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        comboBox.setBorder(new RoundedBorder(8));
        comboBox.setToolTipText(tooltip);
        comboBox.setSelectedIndex(-1);
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

    private void salvarDepartamento() {
        boolean camposValidos = true;

        if (campoNome.getText().isEmpty()) {
            campoNome.setBorder(BorderFactory.createLineBorder(Color.RED));
            camposValidos = false;
        } else {
            campoNome.setBorder(new RoundedBorder(8));
        }

        if (campoDescricao.getText().isEmpty()) {
            campoDescricao.setBorder(BorderFactory.createLineBorder(Color.RED));
            camposValidos = false;
        } else {
            campoDescricao.setBorder(new RoundedBorder(8));
        }

        if (campoResponsavel.getText().isEmpty() && comboResponsavelFuncionario.getSelectedItem() == null) {
            campoResponsavel.setBorder(BorderFactory.createLineBorder(Color.RED));
            comboResponsavelFuncionario.setBorder(BorderFactory.createLineBorder(Color.RED));
            camposValidos = false;
        } else {
            campoResponsavel.setBorder(new RoundedBorder(8));
            comboResponsavelFuncionario.setBorder(new RoundedBorder(8));
        }

        if (campoCargoResponsavel.getText().isEmpty()) {
            campoCargoResponsavel.setBorder(BorderFactory.createLineBorder(Color.RED));
            camposValidos = false;
        } else {
            campoCargoResponsavel.setBorder(new RoundedBorder(8));
        }

        if (comboHoraFuncionamento.getSelectedItem() == null || comboHoraFuncionamento.getSelectedItem().toString().isEmpty()) {
            comboHoraFuncionamento.setBorder(BorderFactory.createLineBorder(Color.RED));
            camposValidos = false;
        } else {
            comboHoraFuncionamento.setBorder(new RoundedBorder(8));
        }

        if (!camposValidos) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
            return;
        }

        String horaFuncionamento = comboHoraFuncionamento.getSelectedItem().toString();
        if (horaFuncionamento.equals("Outro")) {
            horaFuncionamento = JOptionPane.showInputDialog(this, "Digite a hora de funcionamento:");
            if (horaFuncionamento == null || horaFuncionamento.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Hora de funcionamento inválida!");
                return;
            }
        }

        String responsavel = campoResponsavel.getText().isEmpty() ? comboResponsavelFuncionario.getSelectedItem().toString() : campoResponsavel.getText();

        // Chamada ao controller
        DepartamentoController controller = new DepartamentoController();
        controller.gravar(
                campoNome.getText(),
                campoDescricao.getText(),
                responsavel,
                campoCargoResponsavel.getText(),
                horaFuncionamento
        );

        JOptionPane.showMessageDialog(this, "Departamento salvo com sucesso!");

        // (Opcional) limpar campos
        campoNome.setText("");
        campoDescricao.setText("");
        campoResponsavel.setText("");
        comboResponsavelFuncionario.setSelectedIndex(-1);
        campoCargoResponsavel.setText("");
        comboHoraFuncionamento.setSelectedIndex(0);
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