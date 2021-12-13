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
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
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

    public void salvaComoExcel() {
        XSSFWorkbook wb = new XSSFWorkbook();
        CreationHelper createHelper = wb.getCreationHelper();
        Tab[] tabs = tabpane.getTabs().toArray(new Tab[0]);
        for (Tab tab : tabs) {
            Sheet sheet = wb.createSheet(tab.getText());
            int rowCount = 0;
            Row row = sheet.createRow(rowCount);
            rowCount++;
            row.createCell(0).setCellValue(createHelper.createRichTextString("Nome"));
            row.createCell(1).setCellValue(createHelper.createRichTextString("Nota 1"));
            row.createCell(2).setCellValue(createHelper.createRichTextString("Nota 2"));
            row.createCell(3).setCellValue(createHelper.createRichTextString("Nota 3"));
            row.createCell(4).setCellValue(createHelper.createRichTextString("Nota Exame"));
            TableView table = (TableView) tab.getContent().lookup("TableView");
            Aluno[] alunos = (Aluno[]) table.getItems().toArray(new Aluno[0]);
            for (Aluno a : alunos) {
                Row newRow = sheet.createRow(rowCount);
                rowCount++;
                newRow.createCell(0).setCellValue(a.getNome());
                newRow.createCell(1).setCellValue(a.getNota1());
                newRow.createCell(2).setCellValue(a.getNota2());
                newRow.createCell(3).setCellValue(a.getNota3());
                newRow.createCell(4).setCellValue(a.getNotaExame());
            }
        }

        try (OutputStream fileOut = new FileOutputStream("workbook.xls")) {
            wb.write(fileOut);
        } catch (IOException e ){
            System.out.println("Deu Erro!");
        }

        Stage stage = (Stage) tabpane.getScene().getWindow();
        stage.close();

    }

}
