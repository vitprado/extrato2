/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.extratovisualfx;

import br.exacta.config.Config;
import br.exacta.dao.CurralDAO;
import br.exacta.persistencia.Curral;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Thales
 */
public class CurralController implements Initializable {

    @FXML
    private Button btnRemover;
    @FXML
    private Button btnSalvar;
    @FXML
    private ListView<Curral> ltvDados;
    @FXML
    private TextField txtNome;

    private final ObservableList<Curral> listaCurral = FXCollections.observableArrayList();
    private final CurralDAO curralDAO = new CurralDAO();
    
	// Carrega as preferencias
	Preferences userPrefs = Preferences.userNodeForPackage(EmpresaController.class);

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listaCurral.addAll(curralDAO.getTodosCurrais()); // tenho o resultado de todos os currais
        ltvDados.setItems(listaCurral.sorted());
         
        ltvDados.setCellFactory((ListView<Curral> param) -> {
            ListCell<Curral> listCell;
            
            listCell = new ListCell() {
                @Override
                protected void updateItem(Object item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        Curral currais = (Curral) item;
                        setText(currais.getCurDescricao());
                    } else {
                        setText("");
                    }
                }
            };
            return listCell;
        });

        txtNome.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                adicionarAction();
            }
        });

        // ADICIONAR  
        btnSalvar.setOnAction((ActionEvent event) -> {
            adicionarAction();
        });

        // REMOVER 
        btnRemover.setOnAction((ActionEvent event) -> {
            Curral itemSelecionado = ltvDados.getSelectionModel().getSelectedItem();
            if (itemSelecionado != null) {
                try {
                    curralDAO.removerCurral(itemSelecionado.getCurCodigo());
                    listaCurral.remove(itemSelecionado);
                    Config.caixaDialogo(Alert.AlertType.INFORMATION, "Curral removido com sucesso!");
                    
                } catch (Exception ex) {

                    Config.caixaDialogo(Alert.AlertType.ERROR, "Este curral esta em uma ordem registrada! \n " +
                            "Por favor, remova este curral de todas as ordens antes.");
                    Logger.getLogger(CurralController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            });
    }// Fim initialize

    private void adicionarAction() {
        Calendar d = Calendar.getInstance();

        if (!txtNome.getText().trim().isEmpty()) {
        	
            Curral novo = new Curral();
            novo.setCurDescricao(txtNome.getText());
            novo.setCurDataCadastro(d.getTime());
            
            if(curralDAO.getNomesCurraisDistinct().contains(novo.getCurDescricao())
            		&& !userPrefs.getBoolean("PERMITIR_CURRAL_DUPLICADO", true)) {
            	
            	Config.caixaDialogo(Alert.AlertType.ERROR, "Este curral já existe");
            	return;
            }

            try {
                curralDAO.adicionarCurral(novo);
            } catch (Exception ex) {
                Logger.getLogger(CurralController.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (listaCurral.add(novo)) {
                txtNome.setText("");
                //Config.caixaDialogo(Alert.AlertType.INFORMATION, "Curral salvo com sucesso!");
            } else {
                Config.caixaDialogo(Alert.AlertType.ERROR, "Não foi possível cadastrar o curral");
            }
        } else
            Config.caixaDialogo(Alert.AlertType.ERROR, "Houve algum problema, e não foi possível ser salvo o novo curral!");
    }

}
