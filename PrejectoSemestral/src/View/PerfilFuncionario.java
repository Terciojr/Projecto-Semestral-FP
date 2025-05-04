package View;

import Model.FuncionarioModel;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class PerfilFuncionario extends JFrame {
    
    public PerfilFuncionario(FuncionarioModel funcionario) {
        setTitle("Perfil do Funcionário: " + funcionario.getNome());
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIconImage(new ImageIcon(getClass().getResource("/fotos/nome_da_imagem.extensao")).getImage());
        
        // Painel principal com borda e espaçamento
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Cabeçalho com foto e informações básicas
        JPanel headerPanel = createHeaderPanel(funcionario);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Painel de informações com abas
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Aba de Informações Pessoais
        tabbedPane.addTab("Informações Pessoais", createPersonalInfoPanel(funcionario));
        
        // Aba de Informações Profissionais
        tabbedPane.addTab("Informações Profissionais", createProfessionalInfoPanel(funcionario));
        
        // Aba de Contatos
        tabbedPane.addTab("Contatos", createContactInfoPanel(funcionario));
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton closeButton = new JButton("Fechar");
        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(closeButton);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel createHeaderPanel(FuncionarioModel funcionario) {
        JPanel headerPanel = new JPanel(new BorderLayout(15, 0));
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 15, 5)
        ));
        
        // Área da foto (placeholder)
        JLabel photoLabel = new JLabel(new ImageIcon(getClass().getResource("/resources/profile_placeholder.png")));
        photoLabel.setPreferredSize(new Dimension(100, 100));
        photoLabel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        
        // Painel de informações do cabeçalho
        JPanel infoPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        
        JLabel nameLabel = new JLabel(funcionario.getNome());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        
        JLabel positionLabel = new JLabel(funcionario.getCargo());
        positionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        positionLabel.setForeground(new Color(100, 100, 100));
        
        JLabel deptLabel = new JLabel("Departamento: " + funcionario.getDepartamento());
        deptLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        infoPanel.add(nameLabel);
        infoPanel.add(positionLabel);
        infoPanel.add(deptLabel);
        
        headerPanel.add(photoLabel, BorderLayout.WEST);
        headerPanel.add(infoPanel, BorderLayout.CENTER);
        
        return headerPanel;
    }
    
    private JPanel createPersonalInfoPanel(FuncionarioModel funcionario) {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        String dataNascimento = new SimpleDateFormat("dd/MM/yyyy").format(funcionario.getDataNascimento());
        
        addField(panel, "ID do Funcionário:", String.valueOf(funcionario.getIdFuncionario()));
        addField(panel, "Data de Nascimento:", dataNascimento);
        addField(panel, "Gênero:", funcionario.getGenero());
        addField(panel, "Morada:", funcionario.getMorada());
        addField(panel, "Contato de Emergência:", funcionario.getContatoEmergencia());
        
        return panel;
    }
    
    private JPanel createProfessionalInfoPanel(FuncionarioModel funcionario) {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        addField(panel, "Cargo:", funcionario.getCargo());
        addField(panel, "Departamento:", funcionario.getDepartamento());
        addField(panel, "Salário:", String.format("R$ %,.2f", funcionario.getSalario()));
        addField(panel, "Data de Admissão:", "01/01/2020"); // Adicionar campo no modelo
        addField(panel, "Tipo de Contrato:", "CLT"); // Adicionar campo no modelo
        
        return panel;
    }
    
    private JPanel createContactInfoPanel(FuncionarioModel funcionario) {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        addField(panel, "Email:", funcionario.getEmail());
        addField(panel, "Telefone:", formatPhoneNumber(funcionario.getTelefone()));
        addField(panel, "Telefone Residencial:", "(11) 1234-5678"); // Adicionar campo no modelo
        addField(panel, "Ramal:", "123"); // Adicionar campo no modelo
        
        return panel;
    }
    
    private void addField(JPanel panel, String label, String value) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panel.add(lbl);
        
        JTextField txt = new JTextField(value);
        txt.setEditable(false);
        txt.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txt.setBackground(panel.getBackground());
        panel.add(txt);
    }
    
    private String formatPhoneNumber(String phone) {
        // Lógica simples de formatação de telefone
        if (phone == null || phone.length() != 11) return phone;
        return String.format("(%s) %s-%s", 
            phone.substring(0, 2), 
            phone.substring(2, 7), 
            phone.substring(7));
    }
}