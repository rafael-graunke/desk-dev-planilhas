package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jdk.jshell.execution.Util;
import main.Aluno;
import util.Utility;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerAdiciona extends Controller implements Initializable {

    @FXML
    Button add_btn;
    @FXML
    TextField nome_field, nota1_field, nota2_field, nota3_field, notaExame_field;
    TextField[] textFields;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textFields = new TextField[] {nome_field, nota1_field, nota2_field, nota3_field, notaExame_field};
    }

    public void retornaAluno () {
        String nome = nome_field.getText();

        boolean erro = false;
        for (TextField text : textFields) {
            if (text.getText().isBlank() || text.getText().isBlank()) {
                erro = true;
            }
        }
        if (erro) {
            Utility.showError("Erro ao adicionar aluno", "Verifique que todos os campos estão preenchidos");
        } else {
            try {
                double nota1 = Utility.stringToDouble(nota1_field.getText());
                double nota2 = Utility.stringToDouble(nota2_field.getText());
                double nota3 = Utility.stringToDouble(nota3_field.getText());
                double notaExame = Utility.stringToDouble(notaExame_field.getText());
                double[] notas = new double[] {nota1, nota2, nota3, notaExame};
                Aluno aluno = new Aluno(nome, notas);
                ((ControllerPlanilhas) main_controller).adicionaAluno(aluno);

                Stage stage = (Stage) add_btn.getScene().getWindow();
                stage.close();
            } catch (NumberFormatException e) {
                Utility.showError("Erro ao adicionar aluno", "Verifique que as notas são números. No caso de decimais utilize ponto ao invés de vírgula.");
            }

        }
    }


}
