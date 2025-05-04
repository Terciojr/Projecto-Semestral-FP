package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RelatorioFaltas extends JFrame {

    private JTable tabelaFaltas;
    private DefaultTableModel modeloTabela;

    public RelatorioFaltas(List<RegistroFalta> faltas) {
        setTitle("Relatório de Faltas por Período");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        modeloTabela = new DefaultTableModel();
        tabelaFaltas = new JTable(modeloTabela);

        modeloTabela.addColumn("ID");
        modeloTabela.addColumn("Nome");
        modeloTabela.addColumn("Dia da Falta");

        JScrollPane scrollPane = new JScrollPane(tabelaFaltas);
        add(scrollPane, BorderLayout.CENTER);

        exibirFaltas(faltas);

        setVisible(true);
    }

    private void exibirFaltas(List<RegistroFalta> faltas) {
        modeloTabela.setRowCount(0);
        SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy");
        if (faltas != null) { // Adicionado verificação de null
            for (RegistroFalta falta : faltas) {
                modeloTabela.addRow(new Object[]{
                        falta.getId(),
                        falta.getNome(),
                        sdfData.format(falta.getDataFalta())
                });
            }
        }
    }

    static class RegistroFalta {
        private int id;
        private String nome;
        private Date dataFalta;

        public RegistroFalta() {
            // Construtor vazio, sem inicialização de campos
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public Date getDataFalta() {
            return dataFalta;
        }

        public void setDataFalta(Date dataFalta) {
            this.dataFalta = dataFalta;
        }
    }

}