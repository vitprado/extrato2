/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.extratovisualfx;

import br.exacta.config.Config;
import br.exacta.dao.CurralDAO;
import br.exacta.dao.ItemTratoDAO;
import br.exacta.dto.CurralPesoDTO;
import br.exacta.dto.TratoDTO;
import br.exacta.persistencia.Curral;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Thales
 */
public class ListaCurraisOrdemController implements Initializable {

    @FXML
    private Button btnInserirCurral;
    @FXML
    private Button btnRemoverLista;
    @FXML
    private Button btnSalvar;
    @FXML
    private ComboBox<Curral> cbbCurrais;
    @FXML
    private ListView<Curral> ltvDados;

    private final ObservableList<Curral> observableCurrais = FXCollections.observableArrayList();
    private final CurralDAO curralDAO = new CurralDAO();

    private OrdemController ordem;
    private List<Acao> listAcao;

    public ListaCurraisOrdemController(OrdemController ordem) {
        this.ordem = ordem;
        listAcao = new ArrayList<>();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregaComponentes();

        // ADICIONAR
        btnInserirCurral.setOnAction((ActionEvent event) -> {
            if (cbbCurrais.getValue() != null) {
                Acao novaAcao = new Acao();
                novaAcao.adicionar(ltvDados.getItems().size(), cbbCurrais.getValue());
                listAcao.add(novaAcao);
                ltvDados.getItems().add(cbbCurrais.getValue());
            } else {
                Config.caixaDialogo(Alert.AlertType.ERROR, "Erro ao inserir curral na lista!");
            }
        });

        // REMOVER
        btnRemoverLista.setOnAction((ActionEvent event) -> {
            Curral itemSelecionado = ltvDados.getSelectionModel().getSelectedItem();
            if (itemSelecionado != null) {
                Acao novaAcao = new Acao();
                novaAcao.remover(ltvDados.getSelectionModel().getSelectedIndex(), itemSelecionado);
                listAcao.add(novaAcao);
                ltvDados.getItems().remove(itemSelecionado);
            } else {
                Config.caixaDialogo(Alert.AlertType.ERROR, "Erro ao remover curral da lista!");
            }
        });

        btnSalvar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                enviaDadosGUI();
                Stage stage = (Stage) btnSalvar.getScene().getWindow();
                stage.close();
            }
        });
    }

    private void carregaComponentes() {
        this.ltvDados.getItems().addAll(ordem.getCurralList());
        cbbCurrais.setEditable(true);
        List<Curral> todosCurrais = curralDAO.getTodosCurrais();
        observableCurrais.addAll(todosCurrais);
        FilteredList<Curral> filteredItems = new FilteredList(observableCurrais, p -> true);

        cbbCurrais.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            cbbCurrais.show();
        });

        cbbCurrais.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            final TextField editor = cbbCurrais.getEditor();
            final Curral selected = cbbCurrais.getSelectionModel().getSelectedItem();

            Platform.runLater(() -> {
                if (selected == null || !selected.getCurDescricao().equals(editor.getText())) {
                    filteredItems.setPredicate(item -> {
                        if (item.getCurDescricao().toUpperCase().startsWith(newValue.toUpperCase())) {
                            return true;
                        } else {
                            return false;
                        }
                    });
                }
            });
        });

        cbbCurrais.setItems(filteredItems);

        cbbCurrais.setConverter(new StringConverter<Curral>() {
            @Override
            public String toString(Curral object) {
                if (!Objects.isNull(object)) {
                    return object.getCurDescricao();
                }
                return "";
            }

            @Override
            public Curral fromString(String string) {
                return observableCurrais.stream()
                        .filter(curral -> string.equals(curral.getCurDescricao()))
                        .findFirst().get();
            }
        });
    }

    private void enviaDadosGUI() {
        if (ordem.getCurralList().isEmpty()) {
            ordem.getCurralList().addAll(ltvDados.getItems());
        } else {
            if (!listAcao.isEmpty()) {
                for (Acao acao : listAcao) {
                    if (acao.getTipo().equals("adicionar")) {
                        ordem.getCurralList().add(acao.getCurral());
                        for (TratoDTO trato : ordem.getListTratoDTO()) {
                            trato.getListCurralPeso().add(new CurralPesoDTO(acao.getCurral(), new BigDecimal("0")));
                        }
                    }
                    if (acao.getTipo().equals("remover")) {
                        ordem.getCurralList().remove(acao.getCurral());
                        for (TratoDTO tratoDTO : ordem.getListTratoDTO()) {
                            CurralPesoDTO curralPesoDTO = tratoDTO.getCurralPeso(acao.getCurral());
                            if (curralPesoDTO != null && curralPesoDTO.getIttCodigo() != null) {
                                try {
                                    new ItemTratoDAO().removerItemTrato(curralPesoDTO.getIttCodigo());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            tratoDTO.getListCurralPeso().remove(curralPesoDTO);
                        }
                    }
                }
            }
        }
    }

    public class Acao {
        private Integer local;
        private Curral curral;
        private String tipo;

        public Acao() {
        }

        public void adicionar(Integer local, Curral curral) {
            this.local = local;
            this.curral = curral;
            this.tipo = "adicionar";
        }

        public void remover(Integer local, Curral curral) {
            this.local = local;
            this.curral = curral;
            this.tipo = "remover";
        }

        public Integer getLocal() {
            return local;
        }

        public void setLocal(Integer local) {
            this.local = local;
        }

        public Curral getCurral() {
            return curral;
        }

        public void setCurral(Curral curral) {
            this.curral = curral;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }
    }
}
