/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.extratovisualfx;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Vitor Prado
 */
public class EmpresaController implements Initializable {

	@FXML
	private AnchorPane apDados;
	@FXML
	private TextField txtEmpresa;
	@FXML
	private Button btnSalvar;
	@FXML
	private TextField txtRazaoSocial;
	@FXML
	private TextField txtCNPJ;
	@FXML
	private TextField txtEndereco;
	@FXML
	private CheckBox chkProgIdData;
	@FXML
	private CheckBox chkLimiteCapacidade;
	@FXML
	private CheckBox chkNImportarDuplicados;
	@FXML
	private CheckBox chkExportarUmaVez;
	@FXML
	private CheckBox chkPermitirCurraisDuplicados;
	@FXML
	private CheckBox chkPermitirIngDuplicados;
	@FXML
	private CheckBox chkPermitirReceitasDuplicadas;
	@FXML
	private CheckBox chkPermitirEquipDuplicados;
	@FXML
	private TabPane tabPane;

    private final static String empresa = "EMPRESA";
    private final static String razaosocial = "RAZAO";
    private final static String cnpj = "CNPJ";
    private final static String endereco = "ENDERECO";
    private final static String progIdData = "PROGRAMACAO_COM_ID_DATA";
    private final static String limiteCapacidade = "LIMITAR_CAPACIDADE_TRATO";
    private final static String nImportarDuplicados = "NAO_IMPORTAR_RESULTADOS_DUPLICADOS";
    private final static String exportarUmaVez = "EXPORTAR_PROG_UMA_VEZ";
    private final static String permitirCurralDuplicado = "PERMITIR_CURRAL_DUPLICADO";
    private final static String permitirIngDuplicado = "PERMITIR_ING_DUPLICADO";
    private final static String permitirReceitaDuplicada = "PERMITIR_RECEITA_DUPLICADA";
    private final static String permitirEquipDuplicado = "PERMITIR_EQUIP_DUPLICADO";

	// Carrega as preferencias
	Preferences userPrefs = Preferences.userNodeForPackage(EmpresaController.class);

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		tabPane.getSelectionModel().selectedItemProperty().addListener(
			    new ChangeListener<Tab>() {
			        @Override
			        public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
			        	if(t1.getId().isEmpty())
			        		return;
			        	
			            if(t1.getId().equals("tabEmpresa")) {
			            	loadEmpresa();			            	
			            }
			            if(t1.getId().equals("tabOperacional")) {
			            	loadOperacional();
			            }
			        }
			    }
			);
		
		loadEmpresa();
		
		// Monitora alteracoes nos campos
		
		// Empresa
		txtEmpresa.textProperty().addListener((observable, oldValue, newValue) -> {
			userPrefs.put(empresa, newValue);
		});
		txtRazaoSocial.textProperty().addListener((observable, oldValue, newValue) -> {
			userPrefs.put(razaosocial, newValue);
		});
		txtCNPJ.textProperty().addListener((observable, oldValue, newValue) -> {
			userPrefs.put(cnpj, newValue);
		});
		txtEndereco.textProperty().addListener((observable, oldValue, newValue) -> {
			userPrefs.put(endereco, newValue);
		});
		
        // Botao Salvar
        btnSalvar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	userPrefs.putBoolean(progIdData, chkProgIdData.isSelected());
            	userPrefs.putBoolean(limiteCapacidade, chkLimiteCapacidade.isSelected());
            	userPrefs.putBoolean(nImportarDuplicados, chkNImportarDuplicados.isSelected());
            	userPrefs.putBoolean(exportarUmaVez, chkExportarUmaVez.isSelected());
            	userPrefs.putBoolean(permitirCurralDuplicado, chkPermitirCurraisDuplicados.isSelected());
            	userPrefs.putBoolean(permitirIngDuplicado, chkPermitirIngDuplicados.isSelected());
            	userPrefs.putBoolean(permitirReceitaDuplicada, chkPermitirReceitasDuplicadas.isSelected());
            	userPrefs.putBoolean(permitirEquipDuplicado, chkPermitirEquipDuplicados.isSelected());
            }
        });
		
	}
	
	void loadEmpresa() {
		// Atualiza os valores dos campos
		txtEmpresa.setText(userPrefs.get(empresa, ""));
		txtRazaoSocial.setText(userPrefs.get(razaosocial, ""));
		txtCNPJ.setText(userPrefs.get(cnpj, ""));
		txtEndereco.setText(userPrefs.get(endereco, ""));			
	}
	
	void loadOperacional() {
		chkProgIdData.setSelected(userPrefs.getBoolean(progIdData, true));
		chkLimiteCapacidade.setSelected(userPrefs.getBoolean(limiteCapacidade, true));
		chkNImportarDuplicados.setSelected(userPrefs.getBoolean(nImportarDuplicados, true));
		chkExportarUmaVez.setSelected(userPrefs.getBoolean(exportarUmaVez, true));
		chkPermitirCurraisDuplicados.setSelected(userPrefs.getBoolean(permitirCurralDuplicado, true));
		chkPermitirIngDuplicados.setSelected(userPrefs.getBoolean(permitirIngDuplicado, true));
		chkPermitirReceitasDuplicadas.setSelected(userPrefs.getBoolean(permitirReceitaDuplicada, true));
		chkPermitirEquipDuplicados.setSelected(userPrefs.getBoolean(permitirEquipDuplicado, true));
	}


}