package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerPlanilhas extends Controller implements Initializable {

    @FXML
    TabPane tabpane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void criaNovaTabela() {
        //Cria colunas padrão para turma
        TableView tableView = new TableView();


    }

    public void criaNovaAba() {
        //Cria e adiciona aba (turma)
        Tab tab = new Tab();
        int number = tabpane.getTabs().size();
        tab.setText("Teste" + number);
        tabpane.getTabs().add(tab);
    }
}
