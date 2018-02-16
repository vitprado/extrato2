/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.extratovisualfx;

import br.exacta.config.Config;
import br.exacta.dao.EquipamentoDAO;
import br.exacta.dao.OrdemProcucaoDAO;
import br.exacta.extratovisualfx.InserirTratoController.TratoLocal;
import br.exacta.persistencia.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * FXML Controller class
 *
 * @author Thales
 */
public class OrdemController implements Initializable {

    @FXML
    private TextField txtOrdem;
    @FXML
    private Button btnCriarListaCurrais;
    @FXML
    private Button btnFinalizarOrdem;
    @FXML
    private Button btnInserirLista;
    @FXML
    private ChoiceBox<Equipamento> cbbEquipamento;
    @FXML
    private TableView<TratoTabela> tvTratos;

    private final EquipamentoDAO equipamentoDAO = new EquipamentoDAO();
    private final ObservableList<Equipamento> observableEquipamentos = observableArrayList();
    private final ObservableList<TratoTabela> tratoData = observableArrayList();

    private List<Curral> curralList = new ArrayList<>();
    private List<TratoLocal> tratoLocalList = new ArrayList<>();
    private Integer numeroTrato;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregaComponentes();
        atualizaListaItensCadastrados();
        configuracaoTabela();
        numeroTrato = 0;

        btnFinalizarOrdem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                OrdemProcucaoDAO ordemProcucaoDAO = new OrdemProcucaoDAO();

                try {
                    List<Trato> tratoList = OrdemController.this.tratoLocalList.stream()
                            .map(tratoLocal -> {
                                Trato trato = tratoLocal.getTrato();
                                List<ItemTrato> items = new ArrayList<>();

                                tratoLocal.getDados().forEach(tabela -> {
                                    ItemTrato itemTrato = new ItemTrato();
                                    itemTrato.setIttPeso(new BigDecimal(tabela.getPeso()));
                                    itemTrato.setCurralId(tabela.getCurralEntity().getCurCodigo());
                                    items.add(itemTrato);
                                });

                                trato.setItemTratos(items);
                                return trato;
                            })
                            .collect(Collectors.toList());


                    OrdemProducao ordemProducao = new OrdemProducao(txtOrdem.getText(), cbbEquipamento.getValue(), tratoList);
                    ordemProducao = ordemProcucaoDAO.adicionarOrdemProcucao(ordemProducao);

                } catch (Exception e){
                    e.fillInStackTrace();
                }
            }
        });

        btnCriarListaCurrais.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ListaCurraisOrdemController inserirTrato = new ListaCurraisOrdemController(curralList);
                Config config = new Config();
                config.carregarAnchorPaneDialog("ListaCurraisOrdem", inserirTrato);
            }
        });

        btnInserirLista.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                actionInserirLista();
            }
        });
    }

    private void actionInserirLista(){
        InserirTratoController inserirTratoController = new InserirTratoController(this);
        Config config = new Config();
        config.carregarAnchorPaneDialog("InserirTrato", inserirTratoController);
    }

    public void carregarTabela() {
        tratoData.clear();
        tratoData.addAll(tratoLocalList.stream()
                .map(trato -> new  TratoTabela(trato.getReceita().getRctNome(), trato.getNumero()))
                .collect(Collectors.toList()));
        tvTratos.setItems(tratoData);
    }

    private void configuracaoTabela(){
        tvTratos.setEditable(true);
        TableColumn numeroCol = new TableColumn("NUMERO TRATO");
        numeroCol.setMinWidth(150);
        numeroCol.setCellValueFactory(
                new PropertyValueFactory<TratoLocal, String>("numero"));

        TableColumn receitaCol = new TableColumn("RECEITA");
        receitaCol.setMinWidth(200);
        receitaCol.setCellValueFactory(
                new PropertyValueFactory<TratoLocal, String>("receita"));

        tvTratos.setItems(tratoData);
        tvTratos.getColumns().addAll(numeroCol, receitaCol);
    }

    private void carregaComponentes() {

        // CRIA NUMERAÇÃO PARA ORDE
        String ORDEM = criaNroOrdem();
        txtOrdem.setText(ORDEM);

        // CARREGA TODOS OS EQUIPAMENTOS CADASTRADOS
        observableEquipamentos.addAll(equipamentoDAO.getTodosEquipamentos());
        cbbEquipamento.setItems(observableEquipamentos);

        cbbEquipamento.setConverter(new StringConverter<Equipamento>() {
            @Override
            public String toString(Equipamento object) {
                return object.getEqpDescricao();
            }

            @Override
            public Equipamento fromString(String string) {
                return observableEquipamentos.stream()
                        .filter(equipamento -> string.equals(equipamento.getEqpDescricao()))
                        .findFirst().get();
            }
        });

    }

    private String criaNroOrdem() {
        Calendar c = Calendar.getInstance();
        int ANO = c.get(Calendar.YEAR);
        String NUMERO_ORDEM;
        OrdemProcucaoDAO ordemProcucaoDAO = new OrdemProcucaoDAO();

        NUMERO_ORDEM = ANO + "-" + String.valueOf(ordemProcucaoDAO.countOrdemProcucao() + 1);
        // TRATAMENTO PARA INCREMENTAR NO BANCO UMA NOVA ORDEM (UTILZANDO A ANTERIOR)
        return NUMERO_ORDEM;
    }

    private void atualizaListaItensCadastrados() {

    }

    public List<Curral> getCurralList() {
        return curralList;
    }

    public void setCurralList(List<Curral> curralList) {
        this.curralList = curralList;
    }

    public List<TratoLocal> getTratoLocalList() {
        return tratoLocalList;
    }

    public void setTratoLocalList(List<TratoLocal> tratoLocalList) {
        this.tratoLocalList = tratoLocalList;
    }

    public Integer getNumeroTrato() {
        return numeroTrato;
    }

    public void setNumeroTrato(Integer numeroTrato) {
        this.numeroTrato = numeroTrato;
    }

    public class TratoTabela{
        private String receita;
        private Integer numero;

        public TratoTabela(String receita, Integer numero) {
            this.receita = receita;
            this.numero = numero;
        }

        public String getReceita() {
            return receita;
        }

        public Integer getNumero() {
            return numero;
        }
    }
}
