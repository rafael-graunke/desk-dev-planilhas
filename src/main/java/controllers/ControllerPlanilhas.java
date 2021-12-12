package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerPlanilhas extends Controller implements Initializable {

    @FXML
    TabPane tabpane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public TableView criaNovaTabela(double width, double height) {
        //Cria colunas padrão para turma
        TableView tableView = new TableView();
        tableView.setMaxWidth(width);
        tableView.setMaxHeight(height);
        TableColumn col1 = new TableColumn();
        TableColumn col2 = new TableColumn();
        TableColumn col3 = new TableColumn();
        TableColumn col4 = new TableColumn();
        TableColumn col5 = new TableColumn();
        col1.setText("Nome");
        col2.setText("Nota 1");
        col3.setText("Nota 2");
        col4.setText("Nota 3");
        col5.setText("Nota Exame");

        tableView.getColumns().add(col1);
        tableView.getColumns().add(col2);
        tableView.getColumns().add(col3);
        tableView.getColumns().add(col4);
        tableView.getColumns().add(col5);

        return tableView;
    }

    public void criaNovaAba() {
        //Cria e adiciona aba (turma)
        Tab tab = new Tab();
        int number = tabpane.getTabs().size();
        tab.setText("Turma" + number);
        TableView tableView = criaNovaTabela(tabpane.getMaxWidth(), tabpane.getMaxHeight());

        tab.setContent(tableView);
        tabpane.getTabs().add(tab);
    }
}
