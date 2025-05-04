package View;

import Controller.SolicitacaoFeriasController;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SolicitacaoFerias extends JFrame {

    private JTextField campoNome, campoDepartamento, campoMotivo;
    private JButton botaoSolicitar;
    private JLabel labelStatus;
    private JComboBox<String> comboDepartamentos;
    private List<String> listaDepartamentos;
    private JComboBox<String> comboNomes;
    private List<String> listaNomes;
    private Connection conexao;
    private JSpinner spinnerDataInicio;
    private JSpinner spinnerDataFim;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private SolicitacaoFeriasController controller;
    private JButton botaoVoltar; // Novo botão "Voltar"

    public SolicitacaoFerias() {
        setTitle("Solicitação de Férias/Folgas");
        setSize(700, 550); // Aumentei a altura para acomodar o novo botão
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(240, 240, 240));

        controller = new SolicitacaoFeriasController();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        campoNome = criarTextField("Nome:", 25);
        listaNomes = new ArrayList<>();
        comboNomes = new JComboBox<>(listaNomes.toArray(new String[0]));
        configurarComboBox(comboNomes, "Selecione o Nome:");
        adicionarComponente("Nome:", campoNome, gbc);
        gbc.gridy++;
        adicionarComponente("", comboNomes, gbc);

        gbc.gridy--;
        gbc.gridy++;
        campoDepartamento = criarTextField("Departamento:", 25);
        listaDepartamentos = new ArrayList<>();
        comboDepartamentos = new JComboBox<>(listaDepartamentos.toArray(new String[0]));
        configurarComboBox(comboDepartamentos, "Selecione o Departamento:");
        adicionarComponente("Departamento:", campoDepartamento, gbc);
        gbc.gridy++;
        adicionarComponente("", comboDepartamentos, gbc);

        gbc.gridy--;
        gbc.gridy++;
        spinnerDataInicio = configurarSpinner("Data de Início:", gbc);
        gbc.gridy++;
        spinnerDataFim = configurarSpinner("Data de Fim:", gbc);
        gbc.gridy++;
        campoMotivo = criarTextField("Motivo:", 30);
        adicionarComponente("Motivo:", campoMotivo, gbc);

        botaoSolicitar = criarBotao("Solicitar", "Enviar solicitação de férias/folgas");
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(botaoSolicitar, gbc);
        botaoSolicitar.addActionListener(e -> solicitarFerias());

        labelStatus = new JLabel("Status: Pendente");
        configurarLabel(labelStatus);
        gbc.gridy++;
        add(labelStatus, gbc);

        // Adicionando o botão "Voltar"
        botaoVoltar = criarBotao("Voltar", "Retornar à tela principal");
        gbc.gridy++;
        add(botaoVoltar, gbc);
        botaoVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TelaPrincipal().setVisible(true);
                SolicitacaoFerias.this.dispose(); // Fechar a tela atual
            }
        });

        conectarBancoDados();
        inicializarComboBoxes(); // Carregar todos os nomes e departamentos

        campoNome.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                buscarNome();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                buscarNome();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                buscarNome();
            }
        });

        campoDepartamento.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                buscarDepartamento();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                buscarDepartamento();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                buscarDepartamento();
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
            botaoSolicitar.setEnabled(false);
        }
    }

    private void inicializarComboBoxes() {
        buscarTodosNomes();
        buscarTodosDepartamentos();
    }

    private void buscarTodosNomes() {
        listaNomes.clear();
        comboNomes.removeAllItems();
        try {
            String sql = "SELECT nome FROM funcionario";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listaNomes.add(rs.getString("nome"));
                comboNomes.addItem(rs.getString("nome"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar nomes: " + e.getMessage());
        }
    }

    private void buscarTodosDepartamentos() {
        listaDepartamentos.clear();
        comboDepartamentos.removeAllItems();
        try {
            String sql = "SELECT nomeDepartamento FROM departamento";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listaDepartamentos.add(rs.getString("nomeDepartamento"));
                comboDepartamentos.addItem(rs.getString("nomeDepartamento"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar departamentos: " + e.getMessage());
        }
    }

    private void buscarNome() {
        String nomePesquisa = campoNome.getText();
        listaNomes.clear();
        comboNomes.removeAllItems();
        try {
            String sql = "SELECT nome FROM funcionario WHERE nome LIKE ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, "%" + nomePesquisa + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listaNomes.add(rs.getString("nome"));
                comboNomes.addItem(rs.getString("nome"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar nomes: " + e.getMessage());
        }
    }

    private void buscarDepartamento() {
        String departamentoPesquisa = campoDepartamento.getText();
        listaDepartamentos.clear();
        comboDepartamentos.removeAllItems();
        try {
            String sql = "SELECT nomeDepartamento FROM departamento WHERE nomeDepartamento LIKE ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, "%" + departamentoPesquisa + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listaDepartamentos.add(rs.getString("nomeDepartamento"));
                comboDepartamentos.addItem(rs.getString("nomeDepartamento"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar departamentos: " + e.getMessage());
        }
    }

    private JTextField criarTextField(String tooltip, int tamanho) {
        JTextField textField = new JTextField(tamanho);
        configurarComponente(textField, tooltip);
        return textField;
    }

    private JSpinner configurarSpinner(String texto, GridBagConstraints gbc) {
        JSpinner spinner = new JSpinner(new SpinnerDateModel());
        spinner.setFont(new Font("Arial", Font.PLAIN, 16));
        spinner.setBorder(new RoundedBorder(8));
        spinner.setToolTipText("Selecione a " + texto.substring(0, texto.length() - 1) + ":");
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinner, "dd/MM/yyyy");
        spinner.setEditor(dateEditor);
        adicionarComponente(texto, spinner, gbc);
        return spinner;
    }

    private void configurarComboBox(JComboBox<String> comboBox, String tooltip) {
        comboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        comboBox.setBorder(new RoundedBorder(8));
        comboBox.setToolTipText(tooltip);
        comboBox.setSelectedIndex(-1);
    }

    private void configurarLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 16));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(label, gbc);
    }

    private void adicionarComponente(String texto, JComponent componente, GridBagConstraints gbc) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        add(label, gbc);

        gbc.gridx = 1;
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

    private void solicitarFerias() {
        if (validarCampos()) {
            String nomeFuncionario = (String) comboNomes.getSelectedItem();
            String departamentoFuncionario = (String) comboDepartamentos.getSelectedItem();
            Date dataInicio = (Date) spinnerDataInicio.getValue();
            Date dataFim = (Date) spinnerDataFim.getValue();
            String motivo = campoMotivo.getText();

            controller.gravar(nomeFuncionario, departamentoFuncionario, dataInicio, dataFim, motivo);

            JOptionPane.showMessageDialog(this, "Solicitação enviada com sucesso!");
            labelStatus.setText("Status: Aguardando aprovação");
            limparCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios!", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void limparCampos() {
        campoNome.setText("");
        campoDepartamento.setText("");
        campoMotivo.setText("");
        comboNomes.setSelectedIndex(-1);
        comboDepartamentos.setSelectedIndex(-1);
        spinnerDataInicio.setValue(new Date());
        spinnerDataFim.setValue(new Date());
        resetarBordas();
    }

    private void resetarBordas() {
        campoNome.setBorder(new RoundedBorder(8));
        campoDepartamento.setBorder(new RoundedBorder(8));
        campoMotivo.setBorder(new RoundedBorder(8));
        comboNomes.setBorder(new RoundedBorder(8));
        comboDepartamentos.setBorder(new RoundedBorder(8));
        spinnerDataInicio.setBorder(new RoundedBorder(8));
        spinnerDataFim.setBorder(new RoundedBorder(8));
    }

    private boolean validarCampos() {
        boolean valido = true;
        if (comboNomes.getSelectedItem() == null) {
            tremerCampo(comboNomes);
            valido = false;
        } else {
            comboNomes.setBorder(new RoundedBorder(8));
        }
        if (comboDepartamentos.getSelectedItem() == null) {
            tremerCampo(comboDepartamentos);
            valido = false;
        } else {
            comboDepartamentos.setBorder(new RoundedBorder(8));
        }
        if (spinnerDataInicio.getValue() == null) {
            tremerCampo(spinnerDataInicio);
            valido = false;
        } else {
            spinnerDataInicio.setBorder(new RoundedBorder(8));
        }
        if (spinnerDataFim.getValue() == null) {
            tremerCampo(spinnerDataFim);
            valido = false;
        } else {
            spinnerDataFim.setBorder(new RoundedBorder(8));
        }
        if (campoMotivo.getText().isEmpty()) {
            tremerCampo(campoMotivo);
            valido = false;
        } else {
            campoMotivo.setBorder(new RoundedBorder(8));
        }
        return valido;
    }

    private void tremerCampo(JComponent campo) {
        Point posicaoOriginal = campo.getLocation();
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                campo.setLocation(posicaoOriginal.x - 5, posicaoOriginal.y);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                }
                campo.setLocation(posicaoOriginal.x + 5, posicaoOriginal.y);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                }
            }
            campo.setLocation(posicaoOriginal);
            campo.setBorder(new RoundedBorder(8, Color.RED, 2));
        });
        thread.start();
    }

    private void configurarComponente(JComponent componente, String tooltip) {
        componente.setFont(new Font("Arial", Font.PLAIN, 16));
        componente.setBorder(new RoundedBorder(8));
        componente.setToolTipText(tooltip);
    }

    static class RoundedBorder extends AbstractBorder {

        private int radius;
        private Color color;
        private int thickness;

        RoundedBorder(int radius) {
            this.radius = radius;
            this.color = new Color(0, 150, 136);
            this.thickness = 1;
        }

        RoundedBorder(int radius, Color color, int thickness) {
            this.radius = radius;
            this.color = color;
            this.thickness = thickness;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(thickness));
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