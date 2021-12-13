import javafx.scene.control.Alert;

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

}
