/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.extratovisualfx;

import br.exacta.dao.IngredientesDAO;
import br.exacta.persistencia.Ingredientes;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
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
	private ListView<Ingredientes> ltvDados;
	@FXML
	private Text lblDescricao;
	@FXML
	private TextField txtDescricao;
	@FXML
	private TextField txtAbreviacao;
	@FXML
	private Text lblAbreviacao;
	@FXML
	private Text lblTolerancia;
	@FXML
	private TextField txtTolerancia;

	private final ObservableList<Ingredientes> listaIngrediente = FXCollections.observableArrayList();
	private final IngredientesDAO ingredientesDAO = new IngredientesDAO();

	/**
	 * Initializes the controller class.
	 */
	 @Override
	 public void initialize(URL url, ResourceBundle rb) {

		 ltvDados.setItems(listaIngrediente);
		 listaIngrediente.addAll(ingredientesDAO.getTodosIngredientes());

		 ltvDados.setCellFactory(new Callback<ListView<Ingredientes>, ListCell<Ingredientes>>() {
			 @Override
			 public ListCell<Ingredientes> call(ListView<Ingredientes> param) {
				 ListCell<Ingredientes> listCell;
				 listCell = new ListCell() {
					 @Override
					 protected void updateItem(Object item, boolean empty) {
						 super.updateItem(item, empty);
						 if (item != null) {
							 Ingredientes ingredientes = (Ingredientes) item;
							 setText(ingredientes.getIngNome() + " \t\t" + ingredientes.getIngAbreviacao() + " \t\t" + ingredientes.getIngTolerancia().toString());
						 } else {
							 setText("");
						 }
					 }
				 };
				 return listCell;
			 }
		 });

		 // ADICIONAR  
		 btnSalvar.setOnAction(new EventHandler<ActionEvent>() {
			 @Override
			 public void handle(ActionEvent event) {

				 if (!txtDescricao.getText().trim().isEmpty() &&
						 !txtAbreviacao.getText().trim().isEmpty() &&
						 !txtTolerancia.getText().trim().isEmpty()) {

					 Ingredientes novo = new Ingredientes();
					 novo.setIngNome(txtDescricao.getText());
					 novo.setIngAbreviacao(txtAbreviacao.getText());
					 novo.setIngTolerancia(Integer.parseInt(txtTolerancia.getText()));

					 try {
						 ingredientesDAO.adicionarIngrediente(novo);
					 } catch (Exception ex) {
						 Logger.getLogger(NivelAcessoController.class.getName()).log(Level.SEVERE, null, ex);
					 }
					 listaIngrediente.add(novo);
				 }
			 }
		 });

		 // REMOVER 
		 btnRemover.setOnAction(new EventHandler<ActionEvent>() {
			 @Override
			 public void handle(ActionEvent event) {
				 Ingredientes itemSelecionado = ltvDados.getSelectionModel().getSelectedItem();
				 if (itemSelecionado != null) {
					 try {
						 ingredientesDAO.removerIngrediente(itemSelecionado.getIngCodigo());
					 } catch (Exception ex) {
						 Logger.getLogger(NivelAcessoController.class.getName()).log(Level.SEVERE, null, ex);
					 }
					 listaIngrediente.remove(itemSelecionado);
				 }
			 }
		 });
	 }
}
