/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jakub
 */
public class MainScreenController implements Initializable {

    /**
     * Initializes the controller class.
     */
    ScreenManager mainScreen = new ScreenManager();
    ExistingGamesScreen existingGamesScreen;
    FXMLLoader loader = new FXMLLoader();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void handelClicJoin(ActionEvent event) {
        Button b = (Button) event.getSource();
        try {
            mainScreen.changeScene("ExistingGamesScreen.fxml", (Stage) b.getScene().getWindow());
        } catch (IOException ex) {
            showErrorWindow(ex);
        }

    }

    @FXML
    public void handelClicNew(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
           mainScreen.changeScene("CreatGameScreen.fxml", (Stage) b.getScene().getWindow());
       
    }

   

    private void showErrorWindow(IOException ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Chyba");
        alert.setHeaderText("Chyba při načítání souboru aplikace");
        alert.setContentText(ex.getMessage());
        alert.showAndWait();

    }

}
