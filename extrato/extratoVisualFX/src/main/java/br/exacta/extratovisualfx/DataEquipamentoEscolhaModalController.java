package br.exacta.extratovisualfx;

import br.exacta.dao.CarregamentoDAO;
import br.exacta.dao.DescarregamentoDAO;
import br.exacta.jpacontroller.CarregamentoJpaFilter;
import br.exacta.jpacontroller.DescarregamentoJpaFilter;
import br.exacta.persistencia.Carregamento;
import br.exacta.persistencia.Descarregamento;
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

import java.io.File;
import java.net.URL;
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

    public static final int CARREGAMENTO_ORIGEM = 0;
    public static final int DESCARREGAMENTO_ORIGEM = 1;

    private final int origem;

    public DataEquipamentoEscolhaModalController(int origem) {
        this.origem = origem;
    }

    private final CarregamentoDAO carregamentoDAO = new CarregamentoDAO();
    private final DescarregamentoDAO descarregamentoDAO = new DescarregamentoDAO();
    private final ObservableList<String> comboEquipamentos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadComponents();

        btnGeraRelatorio.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (origem == CARREGAMENTO_ORIGEM) {
                    CarregamentoJpaFilter carregamentoJpaFilter = new CarregamentoJpaFilter();
                    carregamentoJpaFilter.setEquipamento(cbEquipamento.getValue());
                    carregamentoJpaFilter.setDataInicio(dpDataInicial.getValue());
                    List<Carregamento> todosCarregamentos = carregamentoDAO.getCarregamentos(carregamentoJpaFilter);

                    DirectoryChooser directoryChooser = new DirectoryChooser();
                    File file = directoryChooser.showDialog(null);
                    RelatorioCarregamentoXLS relatorioCarregamentoXLS = new RelatorioCarregamentoXLS(file);
                    relatorioCarregamentoXLS.geraRelatorio(todosCarregamentos);
                }

                if (origem == DESCARREGAMENTO_ORIGEM) {
                    DescarregamentoJpaFilter descarregamentoJpaFilter = new DescarregamentoJpaFilter();
                    descarregamentoJpaFilter.setEquipamento(cbEquipamento.getValue());
                    descarregamentoJpaFilter.setDataInicio(dpDataInicial.getValue());
                    List<Descarregamento> todoDescarregamentos = descarregamentoDAO.getDescarregamentos(descarregamentoJpaFilter);

                    DirectoryChooser directoryChooser = new DirectoryChooser();
                    File file = directoryChooser.showDialog(null);
                    RelatorioDescarregamentoXLS relatorioDescarregamentoXLS = new RelatorioDescarregamentoXLS(file);
                    relatorioDescarregamentoXLS.geraRelatorio(todoDescarregamentos);
                }
            }
        });
    }

    private void loadComponents() {
        if (origem == CARREGAMENTO_ORIGEM) {
            List<String> equipamentos = carregamentoDAO.getEquipamentosDistinct();
            comboEquipamentos.addAll(equipamentos);
            cbEquipamento.setItems(comboEquipamentos);
        }

        if (origem == DESCARREGAMENTO_ORIGEM) {
            List<String> equipamentos = descarregamentoDAO.getEquipamentosDistinct();
            comboEquipamentos.addAll(equipamentos);
            cbEquipamento.setItems(comboEquipamentos);
        }
    }
}
