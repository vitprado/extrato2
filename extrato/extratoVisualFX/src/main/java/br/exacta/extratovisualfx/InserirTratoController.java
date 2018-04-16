package br.exacta.extratovisualfx;

import br.exacta.dao.ReceitaDAO;
import br.exacta.dto.CurralPesoDTO;
import br.exacta.dto.TratoDTO;
import br.exacta.persistencia.Receita;
import br.exacta.persistencia.Trato;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static javafx.collections.FXCollections.observableArrayList;

public class InserirTratoController implements Initializable {

    @FXML
    private Button btnSalvar;
    @FXML
    private ChoiceBox<Receita> cbbReceitas;
    @FXML
    private TableView<CurralPesoDTO> tvTabela;
    @FXML
    private TableColumn<CurralPesoDTO, String> colCurral;
    @FXML
    private TableColumn<CurralPesoDTO, String> colPeso;

    private OrdemController ordem;
    private TratoDTO tratoDTO;

    public InserirTratoController(OrdemController ordem) {
        this.ordem = ordem;
        this.tratoDTO = null;
    }

    public InserirTratoController(OrdemController ordem, TratoDTO tratoDTO) {
        this.ordem = ordem;
        this.tratoDTO = tratoDTO;
    }

    private final ReceitaDAO receitaDAO = new ReceitaDAO();
    private final ObservableList<Receita> observableReceitas = observableArrayList();
    private final ObservableList<CurralPesoDTO> data = observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        componentes();
        tvTabela.setItems(data);
        carregarTabelaNova();
        configurarTabela();

        btnSalvar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (tratoDTO == null) {
                    enviaDadosGUI();
                } else {
                    tratoDTO.getTrato().setReceita(cbbReceitas.getValue());
                    tratoDTO.setReceita(cbbReceitas.getValue().getRctNome());
                    tratoDTO.getListCurralPeso().clear();
                    tratoDTO.getListCurralPeso().addAll(tvTabela.getItems());
                    tratoDTO.setPesoTotal(tratoDTO.calculaPesoTotal());
                }
                ordem.carregarTabela();
                Stage stage = (Stage) btnSalvar.getScene().getWindow();
                stage.close();
            }
        });
    }

    private void carregarTabelaNova() {
        if (this.tratoDTO == null) {
            data.addAll(ordem.getCurralList().stream()
                    .map(curral -> new CurralPesoDTO(curral, new BigDecimal("0")))
                    .collect(Collectors.toList()));
        } else {
            cbbReceitas.setValue(tratoDTO.getTrato().getReceita());
            data.addAll(tratoDTO.getListCurralPeso());
        }
    }

    private void configurarTabela() {
        colCurral.setCellValueFactory(new PropertyValueFactory<CurralPesoDTO, String>("curral"));

        colPeso.setCellValueFactory(new PropertyValueFactory<CurralPesoDTO, String>("peso"));
        setupPesoColumn();

        // single cell selection mode
        tvTabela.setEditable(true);
        tvTabela.getSelectionModel().cellSelectionEnabledProperty().set(true);

        tvTabela.setOnKeyPressed(event -> {
            if (event.getCode().isLetterKey() || event.getCode().isDigitKey()) {
                editFocusedCell();
            } else if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
                tvTabela.getSelectionModel().selectBelowCell();
//                event.consume();
            }
        });
    }

    private void editFocusedCell() {
        final TablePosition<CurralPesoDTO, ?> focusedCell = tvTabela
                .focusModelProperty().get().focusedCellProperty().get();
        tvTabela.edit(focusedCell.getRow(), focusedCell.getTableColumn());
    }

    private void setupPesoColumn() {
        colPeso.setCellFactory(EditCell.forTableColumn());
        colPeso.setOnEditCommit(event -> {
            final String value = event.getNewValue() != null
                    ? event.getNewValue() : event.getOldValue();
            ((CurralPesoDTO) event.getTableView().getItems()
                    .get(event.getTablePosition().getRow())).setPeso(value);
            tvTabela.refresh();
        });
    }


    private void componentes() {
        observableReceitas.addAll(receitaDAO.getTodaReceitaAtiva());
        cbbReceitas.setItems(observableReceitas);

        cbbReceitas.setConverter(new StringConverter<Receita>() {
            @Override
            public String toString(Receita object) {
                return object.getRctNome();
            }

            @Override
            public Receita fromString(String string) {
                return observableReceitas.stream()
                        .filter(receita -> string.equals(receita.getRctNome()))
                        .findFirst().get();
            }
        });
    }

    private void enviaDadosGUI() {
        ordem.setNumeroTrato(ordem.getNumeroTrato() + 1);
        ordem.getListTratoDTO().add(new TratoDTO(tvTabela.getItems(), cbbReceitas.getValue(), ordem.getNumeroTrato()));
    }
}
