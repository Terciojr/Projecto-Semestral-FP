package View;

import Controller.FuncionarioController;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.AbstractBorder;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaCadastro extends JFrame {

    private JTextField campoNome, campoCargo, campoSalario, campoEmail, campoDataNascimento, campoMorada, campoTelefone, campoContatoEmergencia;
    private JPasswordField campoSenha;
    private JComboBox<String> comboGenero, comboDepartamento;
    private JButton botaoSalvar, botaoFoto, botaoCancelar, botaoVoltar; // Adicionado botão Voltar
    private JLabel labelFoto;
    private File fotoArquivo;
    private Connection conexao;
    private JPanel painelPrincipal; // Painel para organizar os componentes
    private JScrollPane scrollPane; // ScrollPane para tornar a janela rolável

    public TelaCadastro() {
        // Inicializa a conexão com o banco de dados
        try {
            String url = "jdbc:mysql://localhost:3306/funcionarios_departamentos";
            String usuario = "root"; // ou outro nome de usuário válido
            String senha = ""; // coloque a senha correta aqui, se houver
            conexao = DriverManager.getConnection(url, usuario, senha);
        } catch (SQLException ex) {
            Logger.getLogger(TelaCadastro.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            // Se a conexão falhar, você pode optar por encerrar a aplicação ou continuar sem os departamentos
            // System.exit(1);
        }

        initComponents();
    }

    private void initComponents() {
        setTitle("Cadastro de Funcionário");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Alterado para DISPOSE_ON_CLOSE
        setLocationRelativeTo(null);
        setLayout(new BorderLayout()); // Alterado para BorderLayout

        painelPrincipal = new JPanel(); // Criado o painel principal
        painelPrincipal.setLayout(new GridBagLayout()); // Layout do painel principal é GridBagLayout
        painelPrincipal.setBackground(new Color(240, 240, 240));

        scrollPane = new JScrollPane(painelPrincipal); // Adicionado ScrollPane ao painel principal
        add(scrollPane, BorderLayout.CENTER); // Adiciona o ScrollPane ao centro do JFrame

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        campoNome = criarCampo("Nome:", gbc, painelPrincipal); // Adicionado painelPrincipal como argumento
        campoCargo = criarCampo("Cargo:", gbc, painelPrincipal); // Adicionado painelPrincipal
        campoSalario = criarCampo("Salário:", gbc, painelPrincipal); // Adicionado painelPrincipal
        campoEmail = criarCampo("Email:", gbc, painelPrincipal);         // Adicionado painelPrincipal
        campoSenha = criarCampoSenha("Senha:", gbc, painelPrincipal); // Adicionado painelPrincipal
        comboDepartamento = criarComboBoxDepartamento("Departamento:", gbc, painelPrincipal); // Adicionado painelPrincipal
        campoDataNascimento = criarCampo("Data de Nascimento (dd/MM/yyyy):", gbc, painelPrincipal); // Adicionado painelPrincipal
        campoMorada = criarCampo("Morada:", gbc, painelPrincipal); // Adicionado painelPrincipal
        comboGenero = criarComboBox("Gênero:", gbc, painelPrincipal); // Adicionado painelPrincipal
        campoTelefone = criarCampo("Telefone:", gbc, painelPrincipal); // Adicionado painelPrincipal
        campoContatoEmergencia = criarCampo("Contato Emergência:", gbc, painelPrincipal); // Adicionado painelPrincipal

        botaoFoto = criarBotao("Adicionar Foto", "Selecionar foto do funcionário");
        botaoFoto.addActionListener(e -> escolherFoto());
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        painelPrincipal.add(botaoFoto, gbc); // Adicionado painelPrincipal

        labelFoto = new JLabel();
        gbc.gridy++;
        painelPrincipal.add(labelFoto, gbc); // Adicionado painelPrincipal

        JPanel painelBotoes = new JPanel(); // Painel para agrupar os botões
        painelBotoes.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelBotoes.setBackground(new Color(240, 240, 240));

        botaoSalvar = criarBotao("Salvar", "Salvar cadastro do funcionário");
        botaoCancelar = criarBotao("Cancelar", "Cancelar Cadastro"); // Criar botão Cancelar
        botaoVoltar = criarBotao("Voltar", "Voltar para a Tela Principal"); // Criar botão Voltar
        painelBotoes.add(botaoSalvar);
        painelBotoes.add(botaoCancelar);
        painelBotoes.add(botaoVoltar); // Adiciona o botão Voltar ao painel de botões

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        painelPrincipal.add(painelBotoes, gbc); // Adiciona o painel de botões ao painel principal

        botaoSalvar.addActionListener(e -> salvarFuncionario()); // Alterado para o novo método
        botaoCancelar.addActionListener(e -> dispose()); // Fecha a janela ao clicar em Cancelar
        botaoVoltar.addActionListener(new ActionListener() { // Adiciona ActionListener ao botão Voltar
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Fecha esta tela
                new TelaPrincipal().setVisible(true); // Abre a TelaPrincipal
            }
        });

        setVisible(true);
    }

    private JTextField criarCampo(String texto, GridBagConstraints gbc, JPanel painel) { // Adicionado painel como argumento
        JTextField campo = new JTextField(25);
        adicionarComponente(texto, campo, gbc, painel); // Passa o painel para o método adicionarComponente
        return campo;
    }

    private JPasswordField criarCampoSenha(String texto, GridBagConstraints gbc, JPanel painel) { // Adicionado painel
        JPasswordField campo = new JPasswordField(25);
        adicionarComponente(texto, campo, gbc, painel);
        return campo;
    }

    private JComboBox<String> criarComboBox(String texto, GridBagConstraints gbc, JPanel painel) { // Adicionado painel
        JComboBox<String> combo = new JComboBox<>(new String[]{"Masculino", "Feminino"});
        adicionarComponente(texto, combo, gbc, painel);
        return combo;
    }

    private JComboBox<String> criarComboBoxDepartamento(String texto, GridBagConstraints gbc, JPanel painel) { // Adicionado painel
        JComboBox<String> combo = new JComboBox<>();
        comboDepartamento = combo; // Atribui ao campo da classe
        adicionarComponente(texto, combo, gbc, painel);

        // Carrega os departamentos do banco de dados
        List<String> departamentos = carregarDepartamentosDoBanco();
        if (departamentos != null) {
            for (String departamento : departamentos) {
                combo.addItem(departamento);
            }
        }
        return combo;
    }

    private List<String> carregarDepartamentosDoBanco() {
        List<String> departamentos = new ArrayList<>();
        try {
            if (conexao != null) { // Verifica se a conexão foi estabelecida
                String sql = "SELECT nomeDepartamento FROM departamento"; // Substitua pelo nome da sua tabela de departamentos
                PreparedStatement pstmt = conexao.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    departamentos.add(rs.getString("nomeDepartamento")); // Substitua pelo nome da coluna do departamento
                }
                rs.close();
                pstmt.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(TelaCadastro.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Erro ao carregar departamentos: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return null; // Retorna null em caso de erro
        }
        return departamentos;
    }

    private void adicionarComponente(String texto, JComponent componente, GridBagConstraints gbc, JPanel painel) { // Adicionado painel
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        painel.add(label, gbc); // Adiciona ao painel

        componente.setFont(new Font("Arial", Font.PLAIN, 16));
        componente.setBorder(new RoundedBorder(8));
        componente.setToolTipText(texto);
        gbc.gridx = 1;
        painel.add(componente, gbc); // Adiciona ao painel

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

    private void escolherFoto() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Imagens", "jpg", "jpeg", "png"));
        int resultado = fileChooser.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            fotoArquivo = fileChooser.getSelectedFile();
            ImageIcon imagem = new ImageIcon(fotoArquivo.getAbsolutePath());
            labelFoto.setIcon(new ImageIcon(imagem.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
        }
    }

    private void salvarFuncionario() {
        if (validarCampos()) {
            String nome = campoNome.getText();
            String cargo = campoCargo.getText();
            double salario = Double.parseDouble(campoSalario.getText());
            String email = campoEmail.getText();
            String senha = new String(campoSenha.getPassword());
            String departamento = (String) comboDepartamento.getSelectedItem();
            String dataNascimentoTexto = campoDataNascimento.getText();
            String morada = campoMorada.getText();
            String genero = (String) comboGenero.getSelectedItem();
            String telefone = campoTelefone.getText();
            String contatoEmergencia = campoContatoEmergencia.getText();
            String fotoPath = null;

            if (fotoArquivo != null) {
                try {
                    // Salvar a foto em algum diretório e obter o caminho
                    String nomeArquivo = "foto_" + System.currentTimeMillis() + "_" + fotoArquivo.getName();
                    java.nio.file.Path destino = Paths.get("src/main/resources/fotos", nomeArquivo); // Ajuste o diretório conforme necessário
                    Files.copy(fotoArquivo.toPath(), destino);
                    fotoPath = destino.toString();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar a foto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date dataNasc = null;
            try {
                dataNasc = sdf.parse(dataNascimentoTexto);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "Formato de data inválido (dd/MM/yyyy)", "Erro", JOptionPane.ERROR_MESSAGE);
                tremerCampo(campoDataNascimento);
                return;
            }

            FuncionarioController controller = new FuncionarioController();
            controller.gravar(nome, cargo, salario, email, senha, departamento, dataNasc, morada, telefone, contatoEmergencia, genero, fotoPath);

            JOptionPane.showMessageDialog(this, "Cadastro salvo com sucesso!");
            limparCampos(); // Opcional: limpar os campos após o sucesso
        } else {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios!", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void limparCampos() {
        campoNome.setText("");
        campoCargo.setText("");
        campoSalario.setText("");
        campoEmail.setText("");
        campoSenha.setText("");
        comboDepartamento.setSelectedIndex(-1);
        campoDataNascimento.setText("");
        campoMorada.setText("");
        comboGenero.setSelectedIndex(-1);
        campoTelefone.setText("");
        campoContatoEmergencia.setText("");
        labelFoto.setIcon(null);
        fotoArquivo = null;
        resetarBordas();
    }

    private void resetarBordas() {
        campoNome.setBorder(new RoundedBorder(8));
        campoCargo.setBorder(new RoundedBorder(8));
        campoSalario.setBorder(new RoundedBorder(8));
        campoEmail.setBorder(new RoundedBorder(8));
        campoSenha.setBorder(new RoundedBorder(8));
        comboDepartamento.setBorder(BorderFactory.createLineBorder(new Color(0, 150, 136), 1)); // Cor padrão do RoundedBorder
        campoDataNascimento.setBorder(new RoundedBorder(8));
        campoMorada.setBorder(new RoundedBorder(8));
        campoTelefone.setBorder(new RoundedBorder(8));
        campoContatoEmergencia.setBorder(new RoundedBorder(8));
    }

    private boolean validarCampos() {
        boolean valido = true;
        if (campoNome.getText().isEmpty()) {
            tremerCampo(campoNome);
            valido = false;
        } else {
            campoNome.setBorder(new RoundedBorder(8));
        }
        if (campoCargo.getText().isEmpty()) {
            tremerCampo(campoCargo);
            valido = false;
        } else {
            campoCargo.setBorder(new RoundedBorder(8));
        }
        if (campoSalario.getText().isEmpty()) {
            tremerCampo(campoSalario);
            valido = false;
        } else {
            try {
                Double.parseDouble(campoSalario.getText());
                campoSalario.setBorder(new RoundedBorder(8));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Salário deve ser um número!", "Erro", JOptionPane.ERROR_MESSAGE);
                tremerCampo(campoSalario);
                valido = false;
            }
        }
        if (campoEmail.getText().isEmpty()) {
            tremerCampo(campoEmail);
            valido = false;
        } else {
            campoEmail.setBorder(new RoundedBorder(8));
        }
        if (campoSenha.getPassword().length == 0) {
            tremerCampo(campoSenha);
            valido = false;
        } else {
            campoSenha.setBorder(new RoundedBorder(8));
        }
        if (comboDepartamento.getSelectedItem() == null || comboDepartamento.getSelectedItem().toString().isEmpty()) {
            tremerCampo(comboDepartamento);
            valido = false;
        } else {
            comboDepartamento.setBorder(BorderFactory.createLineBorder(new Color(0, 150, 136), 1)); // Cor padrão
        }
        if (campoDataNascimento.getText().isEmpty()) {
            tremerCampo(campoDataNascimento);
            valido = false;
        } else {
            campoDataNascimento.setBorder(new RoundedBorder(8));
        }
        if (campoMorada.getText().isEmpty()) {
            tremerCampo(campoMorada);
            valido = false;
        } else {
            campoMorada.setBorder(new RoundedBorder(8));
        }
        if (campoTelefone.getText().isEmpty()) {
            tremerCampo(campoTelefone);
            valido = false;
        } else {
            campoTelefone.setBorder(new RoundedBorder(8));
        }
        if (campoContatoEmergencia.getText().isEmpty()) {
            tremerCampo(campoContatoEmergencia);
            valido = false;
        } else {
            campoContatoEmergencia.setBorder(new RoundedBorder(8));
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
