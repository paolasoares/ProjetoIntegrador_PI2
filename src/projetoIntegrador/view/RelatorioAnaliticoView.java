/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetoIntegrador.view;

import java.util.List;
import javax.swing.table.DefaultTableModel;
import projetoIntegrador.model.Produto;
import projetoIntegrador.model.Venda;

/**
 *
 * @author Paola
 */
public class RelatorioAnaliticoView extends javax.swing.JFrame {
    

    /**
     * Creates new form RelatorioAnaliticoView
     */
    public RelatorioAnaliticoView(Venda venda) {
        initComponents();
        atualizarListaDeProdutos(venda.getProdutos());
    }

    private void atualizarListaDeProdutos(List<Produto> produtos) {
        DefaultTableModel tableModel = new DefaultTableModel(0, 0);

        // adicionar cabeçalho da tabela
        String header[] = new String[]{"Produto", "Quantide", "Preço Unitário", "Total"};

        // adicionar cabeçalho no modelo da tabela     
        tableModel.setColumnIdentifiers(header);
        //definir modelo no objeto de tabela
        tblRelatorioAnalitico.setModel(tableModel);

        // adicionar linha dinamicamente à tabela      
        for (Produto produto : produtos) {
            Double totalProduto = produto.getQuantidadeNaVenda() * produto.getValor();
            tableModel.addRow(new Object[]{produto.getNome(), produto.getQuantidadeNaVenda(), produto.getValor(), totalProduto});
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Tabela = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        lblRelatorioAnalitico = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRelatorioAnalitico = new javax.swing.JTable();

        jTable3.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Nome:", "CPF:", "Data de cadastro:"
            }
        ));
        Tabela.setViewportView(jTable3);

        jPanel9.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.light"));

        jPanel10.setBackground(new java.awt.Color(189, 125, 82));

        lblRelatorioAnalitico.setFont(new java.awt.Font("Georgia", 1, 18)); // NOI18N
        lblRelatorioAnalitico.setForeground(new java.awt.Color(255, 255, 255));
        lblRelatorioAnalitico.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRelatorioAnalitico.setText("Relatorio Analítico");
        lblRelatorioAnalitico.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblRelatorioAnalitico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(lblRelatorioAnalitico, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        tblRelatorioAnalitico.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(189, 125, 82)));
        tblRelatorioAnalitico.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        tblRelatorioAnalitico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Produto", "Quantidade", "Preço Unitário", "Total"
            }
        ));
        jScrollPane1.setViewportView(tblRelatorioAnalitico);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1234, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane Tabela;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable3;
    private javax.swing.JLabel lblRelatorioAnalitico;
    private javax.swing.JTable tblRelatorioAnalitico;
    // End of variables declaration//GEN-END:variables
}
