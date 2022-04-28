package Client;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author jakub
 */
public class ScreenManager {

    public Initializable changeScene(String scene, Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(scene));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        return loader.getController();

    }

    public void showDialog(String dialog) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(dialog));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void showAlert(String mes) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Upozornění");
        alert.setTitle(mes);       
        alert.showAndWait();

    }
    public void showExceptio(Exception ex){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Upozornění");
        alert.setTitle(ex.getMessage());       
        alert.showAndWait();

    
    }
    

}
