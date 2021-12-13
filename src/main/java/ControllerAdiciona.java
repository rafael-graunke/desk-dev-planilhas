import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerAdiciona extends Controller implements Initializable {

    @FXML
    Button add_btn;
    @FXML
    TextField nome_field, nota1_field, nota2_field, nota3_field, notaExame_field;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void retornaAluno () {
        String nome = nome_field.getText();
        int nota1 = Utility.stringToInt(nota1_field.getText());
        int nota2 = Utility.stringToInt(nota2_field.getText());
        int nota3 = Utility.stringToInt(nota3_field.getText());
        int notaExame = Utility.stringToInt(notaExame_field.getText());
        int[] notas = new int[] {nota1,nota2, nota3, notaExame};
        Aluno aluno = new Aluno(nome, notas);
        ((ControllerPlanilhas) main_controller).adicionaAluno(aluno);

        Stage stage = (Stage) add_btn.getScene().getWindow();
        stage.close();
    }


}
