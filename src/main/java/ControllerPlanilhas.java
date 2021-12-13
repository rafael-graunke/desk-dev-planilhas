import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerPlanilhas extends Controller implements Initializable {

    @FXML
    TabPane tabpane;

    private int tabCount = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public TableView criaNovaTabela(double width, double height) {
        //Cria colunas padrão para turma
        TableView tableView = new TableView();
        tableView.setMaxWidth(width);
        tableView.setMaxHeight(height);
        TableColumn<Aluno, String> col1 = new TableColumn<>("Nome");
        TableColumn<Aluno, Integer> col2 = new TableColumn<>("Nota 1");
        TableColumn<Aluno, Integer> col3 = new TableColumn<>("Nota 2");
        TableColumn<Aluno, Integer> col4 = new TableColumn<>("Nota 3");
        TableColumn<Aluno, Integer> col5 = new TableColumn<>("Nota Exame");
        col1.setCellValueFactory(new PropertyValueFactory<>("nome"));
        col2.setCellValueFactory(new PropertyValueFactory<>("nota1"));
        col3.setCellValueFactory(new PropertyValueFactory<>("nota2"));
        col4.setCellValueFactory(new PropertyValueFactory<>("nota3"));
        col5.setCellValueFactory(new PropertyValueFactory<>("notaExame"));

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
        tab.setText("Turma " + tabCount);
        tabCount ++;
        TableView tableView = criaNovaTabela(tabpane.getMaxWidth(), tabpane.getMaxHeight());
        tableView.setId("TableView");

        tab.setContent(tableView);
        tabpane.getTabs().add(tab);
    }

    public void removeAba() {
        Tab tab = tabpane.getSelectionModel().getSelectedItem();
        tabpane.getTabs().remove(tab);
    }

    public void abreTelaAdiciona() {

        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("adicionaAluno.fxml"));
            root = loader.load();
            ControllerAdiciona controller = loader.getController();
            controller.setMainController(this);
            Stage stage = new Stage();
            stage.setTitle("Adiciona Aluno");
            stage.setScene(new Scene(root, 305, 178));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void adicionaAluno(Aluno a) {
        Tab tab = tabpane.getSelectionModel().getSelectedItem();
        TableView table = (TableView) tab.getContent().lookup("#TableView");
        table.getItems().add(a);
    }

}
