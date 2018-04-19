/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.extratovisualfx;

import br.exacta.config.Config;
import br.exacta.dao.EquipamentoDAO;
import br.exacta.dao.OrdemProcucaoDAO;
import br.exacta.dao.TratoDAO;
import br.exacta.dto.CurralPesoDTO;
import br.exacta.dto.TratoDTO;
import br.exacta.persistencia.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static javafx.collections.FXCollections.observableArrayList;
import static javafx.collections.FXCollections.observableList;

/**
 * FXML Controller class
 *
 * @author Thales
 */
public class OrdemController implements Initializable {

    @FXML
    private TextField txtOrdem;
    @FXML
    private TextField txtCapacidade;
    @FXML
    private Button btnCriarListaCurrais;
    @FXML
    private Button btnFinalizarOrdem;
    @FXML
    private Button btnInserirTrato;
    @FXML
    private ChoiceBox<Equipamento> cbbEquipamento;
    @FXML
    private TableView<TratoDTO> tvTratos;
    @FXML
    private TableColumn<TratoDTO, String> colNumeroTrato;
    @FXML
    private TableColumn<TratoDTO, String> colReceita;
    @FXML
    private TableColumn<TratoDTO, String> colPesoTotal;
    @FXML
    private TableColumn<TratoDTO, TratoDTO> colAcao;

    private final EquipamentoDAO equipamentoDAO = new EquipamentoDAO();
    private final ObservableList<Equipamento> observableEquipamentos = observableArrayList();

    private static List<Curral> curralList;
    private List<TratoDTO> listTratoDTO;
    private OrdemProducao ordemProducao;
    private Integer numeroTrato;
    private ListaOrdemController listaOrdemController;
    private Integer ordCodigo;

    public OrdemController(){
        this.listaOrdemController = null;
        this.ordCodigo = -1;
        curralList = new ArrayList<>();
        listTratoDTO = new ArrayList<>();
    }

    public OrdemController(ListaOrdemController listaOrdemController, Integer ordCodigo){
        this.listaOrdemController = listaOrdemController;
        this.ordCodigo = ordCodigo;
        curralList = new ArrayList<>();
        listTratoDTO = new ArrayList<>();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        numeroTrato = 0;
        carregaComponentes();
        atualizaListaItensCadastrados();
        configuracaoTabela();

        btnCriarListaCurrais.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                btnCriarListaCurraisAction();
            }
        });

        btnInserirTrato.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                btnInserirTratoAction();
            }
        });

        btnFinalizarOrdem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (cbbEquipamento.getValue() == null) {
                    return;
                }

                OrdemProcucaoDAO ordemProcucaoDAO = new OrdemProcucaoDAO();
                try {
                    List<Trato> tratoList = listTratoDTO.stream()
                            .map(tratoDTO -> {
                                Trato trato = tratoDTO.getTrato();
                                List<ItemTrato> items = new ArrayList<>();

                                tratoDTO.getListCurralPeso().forEach(tabela -> {
                                    ItemTrato itemTrato = new ItemTrato();
                                    if (tabela.getIttCodigo() != null){
                                        itemTrato.setIttCodigo(tabela.getIttCodigo());
                                    }
                                    itemTrato.setIttPeso(new BigDecimal(tabela.getPeso()));
                                    itemTrato.setCurral(tabela.getCurralEntity());
                                    itemTrato.setIttSequencia(tratoDTO.getListCurralPeso().indexOf(tabela));
                                    items.add(itemTrato);
                                });

                                trato.setItemTratos(items);
                                return trato;
                            })
                            .collect(Collectors.toList());

                    ordemProducao = new OrdemProducao(txtOrdem.getText(), cbbEquipamento.getValue(), tratoList);
                    if (ordCodigo != -1){
                        ordemProducao.setOrdCodigo(ordCodigo);
                        ordemProcucaoDAO.editarOrdemProcucao(ordemProducao);
                        listaOrdemController.pesquisarAction();
                    } else {
                        ordemProducao = ordemProcucaoDAO.adicionarOrdemProcucao(ordemProducao);
                    }
                    Stage stage = (Stage) btnFinalizarOrdem.getScene().getWindow();
                    stage.close();

                } catch (Exception e) {
                    e.fillInStackTrace();
                }
            }
        });
    }

    private void btnCriarListaCurraisAction(){
        ListaCurraisOrdemController curraisOrdemController = new ListaCurraisOrdemController(OrdemController.this);
        Config config = new Config();
        config.carregarAnchorPaneDialog("ListaCurraisOrdem", curraisOrdemController);
    }

    private void btnInserirTratoAction(){
        InserirTratoController inserirTratoController = new InserirTratoController(OrdemController.this);
        Config config = new Config();
        config.carregarAnchorPaneDialog("InserirTrato", inserirTratoController);
    }

    public void carregarTabela() {
        tvTratos.setItems(observableArrayList(listTratoDTO));
    }

    private void configuracaoTabela() {
        colNumeroTrato.setCellValueFactory(new PropertyValueFactory<TratoDTO, String>("numero"));
        colReceita.setCellValueFactory(new PropertyValueFactory<TratoDTO, String>("receita"));
        colPesoTotal.setCellValueFactory(new PropertyValueFactory<TratoDTO, String>("pesoTotal"));
        colAcao.setCellFactory(new Callback<TableColumn<TratoDTO, TratoDTO>, TableCell<TratoDTO, TratoDTO>>() {
            @Override
            public TableCell<TratoDTO, TratoDTO> call(TableColumn<TratoDTO, TratoDTO> param) {
                return new Botoes();
            }
        });
    }

    private void carregaComponentes() {
        // CRIA NUMERAÇÃO PARA ORDEM
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

        cbbEquipamento.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                txtCapacidade.setText(String.valueOf(observableEquipamentos.get(newValue.intValue()).getEqpCapacidade()));
            }
        });

    }

    private String criaNroOrdem() {
        Calendar c = Calendar.getInstance();
        int ANO = c.get(Calendar.YEAR);
        String NUMERO_ORDEM;
        OrdemProcucaoDAO ordemProcucaoDAO = new OrdemProcucaoDAO();

        NUMERO_ORDEM = ANO + "-" + String.valueOf(ordemProcucaoDAO.getOrdemProcucaoMaxNum() + 1);
        return NUMERO_ORDEM;
    }


    private void atualizaListaItensCadastrados() {
        if (ordCodigo != -1){
            curralList.clear();
            ordemProducao =  new OrdemProcucaoDAO().getOrdemProcucao(ordCodigo);
            txtOrdem.setText(ordemProducao.getOrdDescricao());
            cbbEquipamento.setValue(ordemProducao.getEquipamento());
            txtCapacidade.setText(String.valueOf(ordemProducao.getEquipamento().getEqpCapacidade()));
            ordemProducao.getTratos().sort(new Comparator<Trato>() {
                @Override
                public int compare(Trato o1, Trato o2) {
                    return o1.getTrtNumero().compareTo(o2.getTrtNumero());
                }
            });

            if (!ordemProducao.getTratos().isEmpty()){
                if (!ordemProducao.getTratos().get(0).getItemTratos().isEmpty()){
                    for (ItemTrato itemTrato: ordemProducao.getTratos().get(0).getItemTratos()){
                        curralList.add(itemTrato.getCurral());
                    }
                    listTratoDTO = new ArrayList<>();
                    for (Trato trato: ordemProducao.getTratos()){
                        List<CurralPesoDTO> curralPesoList = new ArrayList<>();
                        for (ItemTrato itemTrato: trato.getItemTratos()){
                            CurralPesoDTO curralPesoDTO = new CurralPesoDTO(itemTrato.getCurral(), itemTrato.getIttPeso());
                            curralPesoDTO.setIttCodigo(itemTrato.getIttCodigo());
                            curralPesoList.add(curralPesoDTO);
                        }
                        if (trato.getTrtNumero() > numeroTrato){
                            numeroTrato = trato.getTrtNumero();
                        }
                        TratoDTO tratoDTO = new TratoDTO(curralPesoList, trato.getReceita(), trato.getTrtNumero());
                        tratoDTO.setTrato(trato);
                        listTratoDTO.add(tratoDTO);
                    }
                }
                carregarTabela();
            }
        }
    }

    private class Botoes extends TableCell<TratoDTO, TratoDTO>{
        private Button btnEditar;
        private Button btnExcluir;
        InserirTratoController inserirTratoController;

        public Botoes(){
            btnEditar = new Button("Editar");
            btnEditar.setOnAction((ActionEvent event) -> {
                btnEditarAction();
            });
            btnExcluir = new Button("Excluir");
            btnExcluir.setOnAction((ActionEvent event) -> {
                btnExcluirAction();
            });
        }

        private void btnEditarAction(){
            inserirTratoController = new InserirTratoController(OrdemController.this, tvTratos.getItems().get(getTableRow().getIndex()));
            Config config = new Config();
            config.carregarAnchorPaneDialog("InserirTrato", inserirTratoController);
        }

        private void btnExcluirAction(){
            TratoDTO tratoDTOExcluir = OrdemController.this.getListTratoDTO().get(getTableRow().getIndex());
            if (ordCodigo != -1){
                try {
                    if (tratoDTOExcluir.getTrato().getTrtCodigo() != null)
                        new TratoDAO().removerTrato(tratoDTOExcluir.getTrato().getTrtCodigo());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            OrdemController.this.getListTratoDTO().remove(tratoDTOExcluir);

            Integer index = 0;
            for (TratoDTO tratoDTO : OrdemController.this.getListTratoDTO()){
                index++;
                tratoDTO.setNumero(index);
                tratoDTO.getTrato().setTrtNumero(index);
            }
            OrdemController.this.setNumeroTrato(index);
            OrdemController.this.carregarTabela();
        }

        @Override
        protected void updateItem(final TratoDTO record, boolean empty){
            super.updateItem(record, empty);
            if (!empty) {
                btnEditar.setText("Editar");
                btnExcluir.setText("Excluir");
                Text espaco = new Text(" ");
                HBox pane = new HBox(btnEditar, espaco, btnExcluir);
                setGraphic(pane);
            } else {
                setGraphic(null);
            }
        }
    }

    public TableView<TratoDTO> getTvTratos() {
        return tvTratos;
    }

    public void setTvTratos(TableView<TratoDTO> tvTratos) {
        this.tvTratos = tvTratos;
    }

    public EquipamentoDAO getEquipamentoDAO() {
        return equipamentoDAO;
    }

    public List<Curral> getCurralList() {
        return curralList;
    }

    public void setCurralList(List<Curral> curralList) {
        this.curralList = curralList;
    }

    public List<TratoDTO> getListTratoDTO() {
        return listTratoDTO;
    }

    public void setListTratoDTO(List<TratoDTO> listTratoDTO) {
        this.listTratoDTO = listTratoDTO;
    }

    public Integer getNumeroTrato() {
        return numeroTrato;
    }

    public void setNumeroTrato(Integer numeroTrato) {
        this.numeroTrato = numeroTrato;
    }
}
