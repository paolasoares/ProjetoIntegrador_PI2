/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetoIntegrador.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import projetoIntegrador.controller.ProdutoController;
import projetoIntegrador.controller.VendaController;
import projetoIntegrador.model.Cliente;
import projetoIntegrador.model.Produto;
import projetoIntegrador.model.Venda;

/**
 *
 * @author Paola
 */
public class VendaView extends javax.swing.JInternalFrame {

    private VendaController vendaController;
    private Produto produtoSelecionado;
    private final InicialView inicialView;

    public VendaView(InicialView inicialView) {
        initComponents();
        setupTxtNomeCliente();
        setupTxtNomeProduto();
        setupTxtQuantidade();
        this.vendaController = new VendaController(this);
        this.inicialView = inicialView;

    }

    public JMenuItem criarItem(Cliente cliente, Runnable acao) {
        JMenuItem menuItem = new JMenuItem(cliente.getNome());
        menuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                acao.run();
            }
        });
        return menuItem;

    }

    private JMenuItem criarItemProduto(Produto produto, Runnable acao) {
        JMenuItem menuItemProduto = new JMenuItem(produto.getNome());
        menuItemProduto.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                acao.run();
            }
        });
        return menuItemProduto;
    }

    public JPopupMenu criarPopup(List<Cliente> elementos) {
        final JPopupMenu popup = new JPopupMenu();
        for (Cliente cliente : elementos) {
            JMenuItem menu = criarItem(cliente, new Runnable() {

                @Override
                public void run() {
                    vendaController.selecionarCliente(cliente);
                }
            });
            popup.add(menu);
        }
        return popup;
    }

    private JPopupMenu criarPopupProdutos(List<Produto> elementosProdutos) {
        final JPopupMenu popupProdutos = new JPopupMenu();
        for (Produto produto : elementosProdutos) {
            JMenuItem menuProduto = criarItemProduto(produto, new Runnable() {

                @Override
                public void run() {
                    vendaController.selecionarProduto(produto);
                }
            });
            popupProdutos.add(menuProduto);
        }
        return popupProdutos;
    }

    public void mostrarResultados(List<Cliente> elementos) {
        JPopupMenu popupMenu = criarPopup(elementos);
        this.add(popupMenu);
        popupMenu.show(txtNomeClienteVenda, 0, txtNomeClienteVenda.getHeight());
    }

    public void mostrarResultadosProdutos(List<Produto> elementosProdutos) {
        JPopupMenu jPopupMenu = criarPopupProdutos(elementosProdutos);
        this.add(jPopupMenu);
        jPopupMenu.show(txtProdutoVenda, 0, txtProdutoVenda.getHeight());
    }

    public void setupTxtQuantidade() {
        txtQuantidadeProdutoVenda.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                adicionarQuantidade();
            }

            public void removeUpdate(DocumentEvent e) {
                adicionarQuantidade();
            }

            public void changedUpdate(DocumentEvent e) {
                adicionarQuantidade();
            }

            public void adicionarQuantidade() {
                vendaController.adicionarQuantidade(txtQuantidadeProdutoVenda.getText());
            }

        });
    }

    public void setupTxtNomeCliente() {
        txtNomeClienteVenda.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                pesquisarCliente();
            }

            public void removeUpdate(DocumentEvent e) {
                pesquisarCliente();
            }

            public void insertUpdate(DocumentEvent e) {
                pesquisarCliente();
            }

            public void pesquisarCliente() {
                vendaController.pesquisarCliente(txtNomeClienteVenda.getText());
            }
        });
    }

    public void atualizarCliente(String nome) {
        lblNomeClienteSelecionado.setText(nome);
        txtNomeClienteVenda.setText(nome);
    }

    public void setupTxtNomeProduto() {
        txtProdutoVenda.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                pesquisarProduto();

            }

            public void removeUpdate(DocumentEvent e) {
                pesquisarProduto();

            }

            public void insertUpdate(DocumentEvent e) {
                pesquisarProduto();

            }

            public void pesquisarProduto() {
                vendaController.pesquisarProduto(txtProdutoVenda.getText());
            }
        });
    }

    public void atualizarProduto(Produto produto) {
        txtProdutoVenda.setText(produto.getNome());
        lblValorUnidadeMostrar.setText(String.valueOf(produto.getValor()));
    }

    public void atualizarTotalProdutoAtual(double totalProdutoAtual) {
        lblValorTotalMostrar.setText(String.valueOf(totalProdutoAtual));
    }

    public void atualizarListaDeProdutos(List<Produto> produtos) {
        DefaultTableModel tableModel = new DefaultTableModel(0, 0);

        // adicionar cabeçalho da tabela
        String header[] = new String[]{"nome", "valor", "quantidade",};

        // adicionar cabeçalho no modelo da tabela     
        tableModel.setColumnIdentifiers(header);
        //definir modelo no objeto de tabela
        tblListaDeVendasDeProdutos.setModel(tableModel);

        // adicionar linha dinamicamente à tabela      
        for (Produto produto : produtos) {
            tableModel.addRow(new Object[]{produto.getNome(), produto.getValor(), produto.getQuantidadeNaVenda()});
        }

        tblListaDeVendasDeProdutos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                final int selectedRow = tblListaDeVendasDeProdutos.getSelectedRow();
                System.out.println("Selected Row " + selectedRow);
                if (selectedRow < 0 || selectedRow >= produtos.size()) {
                    return;
                }
                produtoSelecionado = produtos.get(selectedRow);
            }

        });
    }

    public void limparCamposProduto() {
        txtProdutoVenda.setText("");
        txtQuantidadeProdutoVenda.setText("");
        lblValorUnidadeMostrar.setText("");
        lblValorTotalMostrar.setText("");

    }

    public void limparNomeCliente() {
        txtNomeClienteVenda.setText("");
        lblNomeClienteSelecionado.setText("");
        lblValorTotalVenda.setText("");
    }
   

    public void atualizarTotalVenda(double total) {
        lblValorTotalVenda.setText(String.valueOf(total));
    }

    public void setQuantidadePadrão() {
        txtQuantidadeProdutoVenda.setText("1");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Botoes = new javax.swing.JPanel();
        btnFinalizarVenda = new javax.swing.JButton();
        btnCancelarVenda = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txtProdutoVenda = new javax.swing.JTextField();
        lblProdutoVenda = new javax.swing.JLabel();
        txtNomeClienteVenda = new javax.swing.JTextField();
        lblNomeClienteVenda = new javax.swing.JLabel();
        lblQuantidadeProdutoVenda = new javax.swing.JLabel();
        btnCadastrarClienteVenda = new javax.swing.JButton();
        lblValorUnidade = new javax.swing.JLabel();
        btnAdicionarVenda = new javax.swing.JButton();
        txtQuantidadeProdutoVenda = new javax.swing.JTextField();
        lblValorUnidadeMostrar = new javax.swing.JLabel();
        lblValorTotal = new javax.swing.JLabel();
        lblValorTotalMostrar = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        Tabela = new javax.swing.JScrollPane();
        tblListaDeVendasDeProdutos = new javax.swing.JTable();
        lblCarrinho = new javax.swing.JLabel();
        lblValorDaVenda = new javax.swing.JLabel();
        lblNomeCliente = new javax.swing.JLabel();
        lblNomeClienteSelecionado = new javax.swing.JLabel();
        lblValorTotalVenda = new javax.swing.JLabel();
        btnExcluirProdutoDaVenda = new javax.swing.JButton();
        jPanel26 = new javax.swing.JPanel();
        CadastroCliente15 = new javax.swing.JLabel();

        jPanel1.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.light"));

        Botoes.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Botoes.setForeground(new java.awt.Color(204, 204, 204));

        btnFinalizarVenda.setBackground(new java.awt.Color(189, 125, 82));
        btnFinalizarVenda.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        btnFinalizarVenda.setText("Finalizar Venda");
        btnFinalizarVenda.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(189, 125, 82)));
        btnFinalizarVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalizarVendaActionPerformed(evt);
            }
        });

        btnCancelarVenda.setBackground(new java.awt.Color(189, 125, 82));
        btnCancelarVenda.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        btnCancelarVenda.setText("Cancelar Venda");
        btnCancelarVenda.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(189, 125, 82)));
        btnCancelarVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarVendaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout BotoesLayout = new javax.swing.GroupLayout(Botoes);
        Botoes.setLayout(BotoesLayout);
        BotoesLayout.setHorizontalGroup(
            BotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnFinalizarVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(btnCancelarVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(879, Short.MAX_VALUE))
        );
        BotoesLayout.setVerticalGroup(
            BotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BotoesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(BotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFinalizarVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelarVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        txtProdutoVenda.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        lblProdutoVenda.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        lblProdutoVenda.setText("Produto:");

        txtNomeClienteVenda.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNomeClienteVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeClienteVendaActionPerformed(evt);
            }
        });

        lblNomeClienteVenda.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        lblNomeClienteVenda.setText("Nome do Cliente:");

        lblQuantidadeProdutoVenda.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        lblQuantidadeProdutoVenda.setText("Quantidade:");

        btnCadastrarClienteVenda.setBackground(new java.awt.Color(189, 125, 82));
        btnCadastrarClienteVenda.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        btnCadastrarClienteVenda.setText("Cadastrar Cliente");
        btnCadastrarClienteVenda.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(189, 125, 82)));
        btnCadastrarClienteVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarClienteVendaActionPerformed(evt);
            }
        });

        lblValorUnidade.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        lblValorUnidade.setText("Valor Unidade:");

        btnAdicionarVenda.setBackground(new java.awt.Color(189, 125, 82));
        btnAdicionarVenda.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        btnAdicionarVenda.setText("Adicionar ");
        btnAdicionarVenda.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(189, 125, 82)));
        btnAdicionarVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarVendaActionPerformed(evt);
            }
        });

        txtQuantidadeProdutoVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQuantidadeProdutoVendaActionPerformed(evt);
            }
        });

        lblValorUnidadeMostrar.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        lblValorUnidadeMostrar.setText("R$");

        lblValorTotal.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        lblValorTotal.setText("Valor Total Do Produto:");

        lblValorTotalMostrar.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        lblValorTotalMostrar.setText("R$");

        tblListaDeVendasDeProdutos.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        tblListaDeVendasDeProdutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Produto:", "Valor:", "Quantidade:"
            }
        ));
        Tabela.setViewportView(tblListaDeVendasDeProdutos);

        lblCarrinho.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        lblCarrinho.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCarrinho.setText("Carrinho");

        lblValorDaVenda.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        lblValorDaVenda.setText("Valor Total:");

        lblNomeCliente.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        lblNomeCliente.setText("Cliente:");

        lblNomeClienteSelecionado.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        lblNomeClienteSelecionado.setText("NOME DO CLIENTE");

        lblValorTotalVenda.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        lblValorTotalVenda.setText("Valor total venda");

        btnExcluirProdutoDaVenda.setBackground(new java.awt.Color(189, 125, 82));
        btnExcluirProdutoDaVenda.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        btnExcluirProdutoDaVenda.setText("Excluir produto do carrinho");
        btnExcluirProdutoDaVenda.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(189, 125, 82)));
        btnExcluirProdutoDaVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirProdutoDaVendaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Tabela, javax.swing.GroupLayout.DEFAULT_SIZE, 738, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnExcluirProdutoDaVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblValorTotalVenda)
                                    .addComponent(lblValorDaVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(lblNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblNomeClienteSelecionado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addContainerGap())))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblCarrinho, javax.swing.GroupLayout.DEFAULT_SIZE, 726, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNomeClienteSelecionado, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNomeCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Tabela, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblValorDaVenda)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblValorTotalVenda))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnExcluirProdutoDaVenda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addComponent(lblCarrinho, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 478, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 4, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNomeClienteVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNomeClienteVenda))
                        .addGap(29, 29, 29)
                        .addComponent(btnCadastrarClienteVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblProdutoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblQuantidadeProdutoVenda, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtQuantidadeProdutoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblValorUnidade)
                                            .addComponent(lblValorUnidadeMostrar, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(txtProdutoVenda, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(46, 46, 46)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblValorTotal)
                                    .addComponent(lblValorTotalMostrar, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(btnAdicionarVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblNomeClienteVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNomeClienteVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnCadastrarClienteVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addComponent(lblProdutoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtProdutoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(55, 55, 55)
                                .addComponent(lblQuantidadeProdutoVenda)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtQuantidadeProdutoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblValorUnidade)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblValorUnidadeMostrar, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAdicionarVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(lblValorTotal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblValorTotalMostrar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel26.setBackground(new java.awt.Color(189, 125, 82));

        CadastroCliente15.setFont(new java.awt.Font("Georgia", 1, 18)); // NOI18N
        CadastroCliente15.setForeground(new java.awt.Color(255, 255, 255));
        CadastroCliente15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CadastroCliente15.setText("Vendas");

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(CadastroCliente15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(CadastroCliente15, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Botoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(Botoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public void mostrarErro(String menssagem) {
        JOptionPane.showConfirmDialog(this, menssagem, "ERROR", 0);
    }
    private void btnFinalizarVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalizarVendaActionPerformed

        if (!vendaController.temCliente()) {
            mostrarErro("Selecione um cliente");
            return;
        }
        if (!vendaController.temProduto()) {
            mostrarErro("Selecione um produto");
            return;
        }

        vendaController.salvarVenda();
        JOptionPane.showMessageDialog(this, "Venda Realizada com sucesso!");
        limparNomeCliente();
        atualizarListaDeProdutos(Collections.EMPTY_LIST);
        vendaController.limparVenda();
    }//GEN-LAST:event_btnFinalizarVendaActionPerformed

    private void btnCancelarVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarVendaActionPerformed
        limparCamposProduto();
        limparNomeCliente();
        atualizarListaDeProdutos(Collections.EMPTY_LIST);
       

    }//GEN-LAST:event_btnCancelarVendaActionPerformed

    private void txtNomeClienteVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeClienteVendaActionPerformed

    }//GEN-LAST:event_txtNomeClienteVendaActionPerformed

    private void btnCadastrarClienteVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarClienteVendaActionPerformed
        inicialView.mostrarTelas("Clientes");


    }//GEN-LAST:event_btnCadastrarClienteVendaActionPerformed

    private void txtQuantidadeProdutoVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQuantidadeProdutoVendaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQuantidadeProdutoVendaActionPerformed

    private void btnAdicionarVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarVendaActionPerformed
        if (!vendaController.temProdutoSelecionado()) {
            mostrarErro("Adicione um produto");
            return;
        }
        vendaController.adicionarProdutoNaVenda();


    }//GEN-LAST:event_btnAdicionarVendaActionPerformed

    private void btnExcluirProdutoDaVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirProdutoDaVendaActionPerformed
        vendaController.excluirProdutoDoCarrinho(produtoSelecionado);
    }//GEN-LAST:event_btnExcluirProdutoDaVendaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Botoes;
    private javax.swing.JLabel CadastroCliente15;
    private javax.swing.JScrollPane Tabela;
    private javax.swing.JButton btnAdicionarVenda;
    private javax.swing.JButton btnCadastrarClienteVenda;
    private javax.swing.JButton btnCancelarVenda;
    private javax.swing.JButton btnExcluirProdutoDaVenda;
    private javax.swing.JButton btnFinalizarVenda;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblCarrinho;
    private javax.swing.JLabel lblNomeCliente;
    private javax.swing.JLabel lblNomeClienteSelecionado;
    private javax.swing.JLabel lblNomeClienteVenda;
    private javax.swing.JLabel lblProdutoVenda;
    private javax.swing.JLabel lblQuantidadeProdutoVenda;
    private javax.swing.JLabel lblValorDaVenda;
    private javax.swing.JLabel lblValorTotal;
    private javax.swing.JLabel lblValorTotalMostrar;
    private javax.swing.JLabel lblValorTotalVenda;
    private javax.swing.JLabel lblValorUnidade;
    private javax.swing.JLabel lblValorUnidadeMostrar;
    private javax.swing.JTable tblListaDeVendasDeProdutos;
    private javax.swing.JTextField txtNomeClienteVenda;
    private javax.swing.JTextField txtProdutoVenda;
    private javax.swing.JTextField txtQuantidadeProdutoVenda;
    // End of variables declaration//GEN-END:variables

}
