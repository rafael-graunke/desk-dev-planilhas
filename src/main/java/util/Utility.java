package util;

import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.Aluno;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;

public class Utility {

    public static void showError(String header,String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static int stringToInt(String string) {
        return Integer.parseInt(string);
    }

    public static double stringToDouble(String string) throws NumberFormatException {
        return Double.parseDouble(string);
    }

    public static XSSFWorkbook geraExcel(Tab[] turmas) {
        XSSFWorkbook wb = new XSSFWorkbook();
        CreationHelper createHelper = wb.getCreationHelper();
        for (Tab turma : turmas) {
            Sheet sheet = wb.createSheet(turma.getText());
            int rowCount = 0;
            Row row = sheet.createRow(rowCount);
            rowCount++;
            row.createCell(0).setCellValue(createHelper.createRichTextString("Nome"));
            row.createCell(1).setCellValue(createHelper.createRichTextString("Nota 1"));
            row.createCell(2).setCellValue(createHelper.createRichTextString("Nota 2"));
            row.createCell(3).setCellValue(createHelper.createRichTextString("Nota 3"));
            row.createCell(4).setCellValue(createHelper.createRichTextString("Nota Exame"));
            TableView<Aluno> table = (TableView) turma.getContent().lookup("TableView");
            Aluno[] alunos = table.getItems().toArray(new Aluno[0]);
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

        return wb;
    }

    public static File salvaArquivo() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel 97-2003", "*.xls")
                ,new FileChooser.ExtensionFilter("Excel 2007-365", "*.xlsx")
        );
        return fileChooser.showSaveDialog(stage);
    }

    public static File leArquivo() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel 97-2003", "*.xls")
                ,new FileChooser.ExtensionFilter("Excel 2007-365", "*.xlsx")
        );
        return fileChooser.showOpenDialog(stage);
    }
}
