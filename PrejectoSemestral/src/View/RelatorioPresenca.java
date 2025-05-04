package View;

import Model.RegistroPresencaModel;
import dao.RegistroPresencaDao;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;

public class RelatorioPresenca extends JFrame {

    private JTable tabelaPresencas;
    private DefaultTableModel modeloTabela;
    private Connection conexao;

    public RelatorioPresenca() {
        setTitle("Relatório de Presenças");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 240, 240));

        // Configuração da tabela
        modeloTabela = new DefaultTableModel();
        modeloTabela.addColumn("ID");
        modeloTabela.addColumn("Nome do Funcionário");
        modeloTabela.addColumn("Data/Hora");

        tabelaPresencas = new JTable(modeloTabela);
        tabelaPresencas.setFont(new Font("Arial", Font.PLAIN, 14));
        tabelaPresencas.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tabelaPresencas.setRowHeight(25);
        tabelaPresencas.setDefaultEditor(Object.class, null); // Impede a edição

        JScrollPane scrollPane = new JScrollPane(tabelaPresencas);
        add(scrollPane, BorderLayout.CENTER);

        // Botão de voltar
        JButton botaoVoltar = new JButton("Voltar");
        botaoVoltar.setFont(new Font("Arial", Font.BOLD, 14));
        botaoVoltar.addActionListener(e -> {
            dispose();
            new TelaPrincipal().setVisible(true);
        });

        JPanel panelBotao = new JPanel();
        panelBotao.add(botaoVoltar);
        add(panelBotao, BorderLayout.SOUTH);

        // Conectar ao banco e carregar dados
        
        
        preenchertabela();
        setVisible(true);
    }

    

    public void preenchertabela() {
    DefaultTableModel tabela = (DefaultTableModel) tabelaPresencas.getModel(); // Correção aqui
    RegistroPresencaDao d = new RegistroPresencaDao();
    java.util.List<RegistroPresencaModel> lista;
    lista = d.listar();
    for (int i = 0; i < lista.size(); i++) {
        Object[] dados = {
            lista.get(i).getIdRegistro(),
            lista.get(i).getNome(),
            lista.get(i).getDataRegistro()};
        tabela.addRow(dados);
    }}
}