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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
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
//                    ordem.getListTratoDTO().set(tableRow, new TratoDTO(tvTabela.getItems(), cbbReceitas.getValue(), tableRow + 1));
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
        tvTabela.setEditable(true);
        TableColumn curralCol = new TableColumn("CURRAL");
        curralCol.setMinWidth(150);
        curralCol.setCellValueFactory(
                new PropertyValueFactory<CurralPesoDTO, String>("Curral"));

        TableColumn pesoCol = new TableColumn("PESO");
        pesoCol.setMinWidth(100);
        pesoCol.setCellValueFactory(new PropertyValueFactory<CurralPesoDTO, String>("Peso"));
        pesoCol.setCellFactory(TextFieldTableCell.forTableColumn());
        pesoCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<CurralPesoDTO, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<CurralPesoDTO, String> event) {
                        ((CurralPesoDTO) event.getTableView().getItems().get(
                                event.getTablePosition().getRow())
                        ).setPeso(event.getNewValue());
                    }
                }
        );

        // single cell selection mode
        tvTabela.getSelectionModel().setCellSelectionEnabled(true);

        tvTabela.setItems(data);
        tvTabela.getColumns().addAll(curralCol, pesoCol);
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
