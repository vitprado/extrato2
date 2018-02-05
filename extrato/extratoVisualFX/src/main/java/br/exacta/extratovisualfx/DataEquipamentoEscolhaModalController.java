package br.exacta.extratovisualfx;

import br.exacta.dao.CarregamentoDAO;
import br.exacta.dao.DescarregamentoDAO;
import br.exacta.dto.ResumoCarregamentoDTO;
import br.exacta.dto.ResumoCarregamentoDTODAO;
import br.exacta.jpacontroller.CarregamentoJpaFilter;
import br.exacta.jpacontroller.DescarregamentoJpaFilter;
import br.exacta.persistencia.Carregamento;
import br.exacta.persistencia.Descarregamento;
import br.exacta.relatorio.pdf.RelatorioResumoCarregamentoPDF;
import br.exacta.relatorio.xls.RelatorioCarregamentoXLS;
import br.exacta.relatorio.xls.RelatorioDescarregamentoXLS;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.DirectoryChooser;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class DataEquipamentoEscolhaModalController implements Initializable {

    @FXML
    private Button btnLimpaFiltro;
    @FXML
    private Button btnGeraRelatorio;
    @FXML
    private DatePicker dpDataInicial;
    @FXML
    private DatePicker dpDataFinal;
    @FXML
    private ChoiceBox<String> cbEquipamento;

    public static final int CARREGAMENTO_ORIGEM_XLS = 0;
    public static final int DESCARREGAMENTO_ORIGEM_XLS = 1;

    public static final int CARREGAMENTO_ORIGEM_PDF = 3;

    private final int origem;

    public DataEquipamentoEscolhaModalController(int origem) {
        this.origem = origem;
    }

    private final CarregamentoDAO carregamentoDAO = new CarregamentoDAO();
    private final DescarregamentoDAO descarregamentoDAO = new DescarregamentoDAO();
    private final ResumoCarregamentoDTODAO resumoCarregamentoDTODAO = new ResumoCarregamentoDTODAO();
    private final ObservableList<String> comboEquipamentos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadComponents();

        btnLimpaFiltro.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dpDataInicial.setValue(null);
                dpDataFinal.setValue(null);
                cbEquipamento.setValue(null);
            }
        });

        btnGeraRelatorio.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (origem == CARREGAMENTO_ORIGEM_XLS) {
                    CarregamentoJpaFilter carregamentoJpaFilter = new CarregamentoJpaFilter();
                    carregamentoJpaFilter.setEquipamento(cbEquipamento.getValue());
                    carregamentoJpaFilter.setDataInicio(dpDataInicial.getValue());
                    carregamentoJpaFilter.setDatafim(dpDataFinal.getValue());
                    List<Carregamento> todosCarregamentos = carregamentoDAO.getCarregamentos(carregamentoJpaFilter);

                    RelatorioCarregamentoXLS relatorioCarregamentoXLS = new RelatorioCarregamentoXLS(new DirectoryChooser().showDialog(null));
                    relatorioCarregamentoXLS.geraRelatorio(todosCarregamentos);
                }

                if (origem == DESCARREGAMENTO_ORIGEM_XLS) {
                    DescarregamentoJpaFilter descarregamentoJpaFilter = new DescarregamentoJpaFilter();
                    descarregamentoJpaFilter.setEquipamento(cbEquipamento.getValue());
                    descarregamentoJpaFilter.setDataInicio(dpDataInicial.getValue());
                    descarregamentoJpaFilter.setDatafim(dpDataFinal.getValue());
                    List<Descarregamento> todoDescarregamentos = descarregamentoDAO.getDescarregamentos(descarregamentoJpaFilter);

                    RelatorioDescarregamentoXLS relatorioDescarregamentoXLS = new RelatorioDescarregamentoXLS(new DirectoryChooser().showDialog(null));
                    relatorioDescarregamentoXLS.geraRelatorio(todoDescarregamentos);
                }

                if (origem == CARREGAMENTO_ORIGEM_PDF) {
                    CarregamentoJpaFilter carregamentoJpaFilter = new CarregamentoJpaFilter();
                    carregamentoJpaFilter.setEquipamento(cbEquipamento.getValue());
                    carregamentoJpaFilter.setDataInicio(dpDataInicial.getValue());
                    carregamentoJpaFilter.setDatafim(dpDataFinal.getValue());
                    List<ResumoCarregamentoDTO> resumoCarregamentos = resumoCarregamentoDTODAO.buscaTodos(carregamentoJpaFilter);
                    RelatorioResumoCarregamentoPDF relatorioResumoCarregamentoPDF = new RelatorioResumoCarregamentoPDF(carregamentoJpaFilter);
                    try {
                        relatorioResumoCarregamentoPDF.geraRelatorio(resumoCarregamentos);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void loadComponents() {
        if (origem == CARREGAMENTO_ORIGEM_XLS) {
            List<String> equipamentos = carregamentoDAO.getEquipamentosDistinct();
            comboEquipamentos.addAll(equipamentos);
            cbEquipamento.setItems(comboEquipamentos);
        }

        if (origem == DESCARREGAMENTO_ORIGEM_XLS) {
            List<String> equipamentos = descarregamentoDAO.getEquipamentosDistinct();
            comboEquipamentos.addAll(equipamentos);
            cbEquipamento.setItems(comboEquipamentos);
        }

        if (origem == CARREGAMENTO_ORIGEM_PDF) {
            List<String> equipamentos = resumoCarregamentoDTODAO.getEquipamentosDistinct();
            comboEquipamentos.addAll(equipamentos);
            cbEquipamento.setItems(comboEquipamentos);
        }
    }
}
