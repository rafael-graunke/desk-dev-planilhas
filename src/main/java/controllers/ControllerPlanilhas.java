package controllers;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.Aluno;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import util.Utility;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import static util.Utility.salvaArquivo;

public class ControllerPlanilhas extends Controller implements Initializable {

    @FXML
    TabPane tabpane;

    private int tabCount = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public TableView<Aluno> criaNovaTabela(double width, double height) {
        //Cria colunas padrão para turma
        TableView<Aluno> tableView = new TableView<>();
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
        tab.setText("Turma " + (tabCount + 1));
        tabCount ++;
        TableView tableView = criaNovaTabela(tabpane.getMaxWidth(), tabpane.getMaxHeight());
        tableView.setId("TableView");
        tab.setContent(tableView);
        tabpane.getTabs().add(tab);
    }

    public Tab criaNovaAba(String nomeDaTurma) {
        //Cria e adiciona aba (turma)
        Tab tab = new Tab();
        tab.setText(nomeDaTurma);
        tabCount ++;
        TableView tableView = criaNovaTabela(tabpane.getMaxWidth(), tabpane.getMaxHeight());
        tableView.setId("TableView");
        tab.setContent(tableView);
        tabpane.getTabs().add(tab);

        return tab;
    }

    public void removeAba() {
        //Remove aba selecionada
        Tab tab = tabpane.getSelectionModel().getSelectedItem();
        if (tab == null) {
            Utility.showError("Erro ao remover turma", "Não há nenhuma turma selecionada para ser removida");
        } else {
            tabpane.getTabs().remove(tab);
        }
    }

    public void removeAluno() {
        //Remove aluno selecionado
        Tab tab = tabpane.getSelectionModel().getSelectedItem();
        TableView<Aluno> tableView = (TableView<Aluno>) tab.getContent().lookup("TableView");
        Aluno aluno = tableView.getSelectionModel().getSelectedItem();
        if (aluno == null) {
            Utility.showError("Erro ao remover aluno", "Não há nenhum aluno selecionado para ser removido.");
        } else {
            tableView.getItems().remove(aluno);
        }
    }

    public void abreTelaAdiciona() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("adicionaAluno.fxml"));
            root = loader.load();
            ControllerAdiciona controller = loader.getController();
            controller.setMainController(this);
            Stage stage = new Stage();
            stage.setTitle("Adiciona main.Aluno");
            stage.setScene(new Scene(root, 305, 178));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void adicionaAluno(Aluno a) {
        //Adiciona aluno na aba selecionada
        Tab tab = tabpane.getSelectionModel().getSelectedItem();
        if (tab == null) {
            Utility.showError("Erro ao adicionar aluno", "Não há nenhuma turma selecionada/adicionada.");
        } else {
            TableView<Aluno> table = (TableView) tab.getContent().lookup("#TableView");
            table.getItems().add(a);
        }
    }

    public void salvaComoExcel() {
        Tab[] tabs = tabpane.getTabs().toArray(new Tab[0]);
        if (tabs.length == 0) {
            Utility.showError("Erro ao salvar planilha", "Não há nenhuma turma adicionada.");
        } else {
            XSSFWorkbook wb = Utility.geraExcel(tabs);

            File file = salvaArquivo();

            if (!(file == null)) {
                try (OutputStream fileOut = new FileOutputStream(file)) {
                    wb.write(fileOut);
                } catch (IOException e ){
                    Utility.showError("Erro ao salvar Excel", "Não foi possível salvar o arquivo no destino selecionado.");
                }

                Stage stage = (Stage) tabpane.getScene().getWindow();
                stage.close();
            }
        }
    }

    public void leExcel() {
        try {
            FileInputStream fis = new FileInputStream(Utility.leArquivo());
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            Iterator<Sheet> sheetIterator = wb.sheetIterator();
            while (sheetIterator.hasNext()){
                Sheet sheet = sheetIterator.next();
                Tab tab = criaNovaAba(sheet.getSheetName());
                TableView<Aluno> tableView = (TableView<Aluno>) tab.getContent().lookup("TableView");
                Iterator<Row> rowIterator = sheet.rowIterator();
                if (rowIterator.hasNext()) {
                    rowIterator.next();
                }
                while(rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    Iterator<Cell> cellIterator = row.cellIterator();
                    ArrayList<Double> notas = new ArrayList<>();
                    String nome = "";
                    while (cellIterator.hasNext()) {
                        XSSFCell cell = (XSSFCell) cellIterator.next();
                        switch (cell.getCellType()) {
                            case STRING:
                                nome = cell.getStringCellValue();
                                break;
                            case NUMERIC:
                                notas.add(cell.getNumericCellValue());
                                break;
                        }
                    }
                    Aluno aluno = new Aluno(nome, notas.get(0), notas.get(1), notas.get(2), notas.get(3));
                    tableView.getItems().add(aluno);
                }
            }

        } catch (FileNotFoundException e) {
            Utility.showError("Erro ao abrir arquivo", "Não foi possível abrir o arquivo selecionado");
        } catch (IOException e) {
            Utility.showError("Erro ao abrir arquivo", "O arquivo selecionado não corresponde a um arquivo Excel.");
        }

    }

    public void exportaPDF() throws DocumentException {
        try {
            FileOutputStream fos = new FileOutputStream(salvaArquivo());
            Document doc = PdfFactory.criaPDF();
            PdfFactory.criaAtributos(doc);
            PdfWriter writer = PdfWriter.getInstance(doc, fos);
            doc.open();
            for (Tab tab : tabpane.getTabs()) {
                doc.add(new Paragraph(tab.getText()));
                PdfPTable table = new PdfPTable(5);
                table.addCell("Nome");
                table.addCell("Nota 1");
                table.addCell("Nota 2");
                table.addCell("Nota 3");
                table.addCell("Nota Exame");
                TableView<Aluno> tableView = (TableView<Aluno>) tab.getContent().lookup("TableView");
                for (Aluno a : tableView.getItems()) {
                    table.addCell(a.getNome());
                    table.addCell(Double.toString(a.getNota1()));
                    table.addCell(Double.toString(a.getNota2()));
                    table.addCell(Double.toString(a.getNota3()));
                    table.addCell(Double.toString(a.getNotaExame()));
                }
                doc.add(table);
            }
            doc.close();
            writer.close();
        } catch (FileNotFoundException e) {
            Utility.showError("Erro ao salvar arquivo", "Não foi possível salvar o arquivo no local selecionado");
        }
    }

}
