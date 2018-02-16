package br.exacta.extratovisualfx;

import br.exacta.dao.ReceitaDAO;
import br.exacta.persistencia.Curral;
import br.exacta.persistencia.ItemTrato;
import br.exacta.persistencia.Receita;
import br.exacta.persistencia.Trato;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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

    public InserirTratoController(OrdemController ordem) {
        this.ordem = ordem;
    }

    private final ReceitaDAO receitaDAO = new ReceitaDAO();
    private final ObservableList<Receita> observableReceitas = observableArrayList();
    private final ObservableList<CurralPesoDTO> data = observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        componentes();
        carregarTabela();

        btnSalvar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                enviaDadosGUI();
                ordem.carregarTabela();
                Stage stage = (Stage) btnSalvar.getScene().getWindow();
                stage.close();
            }
        });
    }

    private void carregarTabela() {
        data.addAll(ordem.getCurralList().stream()
                .map(curral -> new CurralPesoDTO(curral, new BigDecimal("0")))
                .collect(Collectors.toList()));
        configuracaoTabela();
    }

    private void configuracaoTabela(){
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

//        tvTabela.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//
//                if( event.getCode() == KeyCode.ENTER) {
//
//                    TablePosition pos = tvTabela.getFocusModel().getFocusedCell();
////                  event.consume(); // don't consume the event or else the values won't be updated;
//                    if (pos.getRow() < tvTabela.getItems().size() -1) {
//                        tvTabela.getSelectionModel().clearAndSelect( pos.getRow() + 1, pos.getTableColumn());
//                    }
//                    return;
//
//                }
//
//                // switch to edit mode on keypress, but only if we aren't already in edit mode
//                if( tvTabela.getEditingCell() == null) {
//                    if( event.getCode().isLetterKey() || event.getCode().isDigitKey()) {
//
//                        TablePosition focusedCellPosition = tvTabela.getFocusModel().getFocusedCell();
//                        tvTabela.edit(focusedCellPosition.getRow(), focusedCellPosition.getTableColumn());
//
//                    }
//                }
//
//            }
//        });
//
//        tvTabela.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//
//                if( event.getCode() == KeyCode.ENTER) {
//                    // move focus & selection
//                    // we need to clear the current selection first or else the selection would be added to the current selection since we are in multi selection mode
//                    TablePosition pos = tvTabela.getFocusModel().getFocusedCell();
//
//                    if (pos.getRow() == -1) {
//                        tvTabela.getSelectionModel().select(0);
//                    }
//                    // add new row when we are at the last row
////                    else if (pos.getRow() == tvTabela.getItems().size() -1) {
////                        addRow();
////                    }
//                    // select next row, but same column as the current selection
////                    else if (pos.getRow() < tvTabela.getItems().size() -1) {
////                        tvTabela.getSelectionModel().clearAndSelect( pos.getRow() + 1, pos.getTableColumn());
////                    }
//
//
//                }
//
//            }
//        });

        // single cell selection mode
        tvTabela.getSelectionModel().setCellSelectionEnabled(true);

        tvTabela.setItems(data);
        tvTabela.getColumns().addAll(curralCol, pesoCol);
    }

    private void componentes(){
        observableReceitas.addAll(receitaDAO.getTodoReceitas());
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
        ordem.getTratoLocalList().add(new TratoLocal(tvTabela.getItems(), cbbReceitas.getValue(), ordem.getNumeroTrato()));
    }

    public class TratoLocal {
        private ObservableList<CurralPesoDTO> dados;
        private Trato trato;
        private Integer numero;

        public TratoLocal(ObservableList<CurralPesoDTO> dados, Receita receita, Integer numero) {
            this.dados = dados;
            this.numero = numero;
            this.trato = new Trato();
            trato.setReceita(receita);
            trato.setTrtNumero(numero);
            List<ItemTrato> tratos = new ArrayList<>();
            for (int countCurral = 0; countCurral < ordem.getCurralList().size(); countCurral++){
                ItemTrato novoItemTrato = new ItemTrato();
                novoItemTrato.setCurralId(ordem.getCurralList().get(countCurral).getCurCodigo());
                novoItemTrato.setIttPeso(new BigDecimal(dados.get(countCurral).getPeso()));
                novoItemTrato.setTrato(this.getTrato());
                tratos.add(novoItemTrato);
            }
            trato.setItemTratos(tratos);
        }

        public ObservableList<CurralPesoDTO> getDados() {
            return dados;
        }

        public Receita getReceita() {
            return trato.getReceita();
        }

        public Integer getNumero() {
            return numero;
        }

        public Trato getTrato() {
            return trato;
        }

        public void setTrato(Trato trato) {
            this.trato = trato;
        }
    }

    public static class CurralPesoDTO {
        private final SimpleStringProperty curral;
        private final SimpleStringProperty peso;
        private final Curral curralEntity;

        private CurralPesoDTO(Curral curral, BigDecimal peso) {
            this.curral = new SimpleStringProperty(curral.getCurDescricao());
            this.peso = new SimpleStringProperty(peso.toString());
            this.curralEntity = curral;
        }

        public String getCurral() {
            return curral.get();
        }

        public void setCurral(String curral) {
            this.curral.set(curral);
        }

        public String getPeso() {
            return peso.get();
        }

        public void setPeso(String peso) {
            this.peso.set(peso);
        }

        public Curral getCurralEntity() {
            return curralEntity;
        }
    }
}
