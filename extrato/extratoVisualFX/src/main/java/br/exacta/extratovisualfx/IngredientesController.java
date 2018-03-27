/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.extratovisualfx;

import br.exacta.dao.IngredientesDAO;
import br.exacta.dto.IngredienteDTO;
import br.exacta.persistencia.Ingredientes;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Thales
 */
public class IngredientesController implements Initializable {

	@FXML
	private Button btnRemover;
	@FXML
	private Button btnSalvar;
	@FXML
	private TextField txtDescricao;
	@FXML
	private TextField txtAbreviacao;
	@FXML
	private TextField txtTolerancia;
	@FXML
	private TableView<IngredienteDTO> tvIngredientes;
	@FXML
	private TableColumn<IngredienteDTO, String> colAbreviacao;
	@FXML
	private TableColumn<IngredienteDTO, Integer> colTolerancia;

	private final IngredientesDAO ingredientesDAO = new IngredientesDAO();

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		carregaComponentes();

		btnSalvar.setOnAction((ActionEvent event) -> {
			btnSalvarAction();
		});

		btnRemover.setOnAction((ActionEvent event) -> {
			btnRemoverAction();
		});
	}

	private void carregaComponentes() {
		List<Ingredientes> listIngredientes = ingredientesDAO.getTodosIngredientes();
		listIngredientes.forEach(ingrediente -> tvIngredientes.getItems().add(new IngredienteDTO(ingrediente)));
		configuracaoTabela();
	}

	private void configuracaoTabela() {
		colAbreviacao.setCellValueFactory(new PropertyValueFactory<IngredienteDTO, String>("abreviacao"));
		colTolerancia.setCellValueFactory(new PropertyValueFactory<IngredienteDTO, Integer>("tolerancia"));
	}

	private void btnSalvarAction() {
		Calendar d = Calendar.getInstance();

		if (!txtDescricao.getText().trim().isEmpty() &&
				!txtAbreviacao.getText().trim().isEmpty() &&
				!txtTolerancia.getText().trim().isEmpty()) {

			Ingredientes novo = new Ingredientes();
			novo.setIngNome(txtDescricao.getText());
			novo.setIngAbreviacao(txtAbreviacao.getText());
			novo.setIngTolerancia(Integer.parseInt(txtTolerancia.getText()));
			novo.setIngDataCadastro(d.getTime());

			try {
				ingredientesDAO.adicionarIngrediente(novo);
				tvIngredientes.getItems().add(new IngredienteDTO(novo));
			} catch (Exception ex) {
				Logger.getLogger(NivelAcessoController.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	private void btnRemoverAction() {
		IngredienteDTO itemSelecionado = tvIngredientes.getSelectionModel().getSelectedItem();
		if (itemSelecionado != null) {
			try {
				ingredientesDAO.removerIngrediente(itemSelecionado.getIngrediente().getIngCodigo());
				tvIngredientes.getItems().remove(itemSelecionado);
			} catch (Exception ex) {
				Alert alert;
				alert = new Alert(Alert.AlertType.ERROR, "Este ingrediente esta em uma receita registrata! \n " +
						"Por favor, remova este ingrediente de todas as receitas que faça parte antes.");
				alert.initStyle(StageStyle.UTILITY);
				alert.setTitle("MENSAGEM DO SISTEMA");
				alert.showAndWait();
				Logger.getLogger(NivelAcessoController.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}
