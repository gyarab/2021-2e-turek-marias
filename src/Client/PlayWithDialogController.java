/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import shared.Card;

/**
 * FXML Controller class
 *
 * @author jakub
 */
public class PlayWithDialogController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    ChoiceBox colorChoice;
    @FXML
    ChoiceBox valueChoice;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colorChoice.getItems().setAll("Listy", "Kule", "Žaludy", "Srdce");
        valueChoice.getItems().setAll("7", "8", "9", "10", "Spodek", "Svršek", "Eso");

    }

    public void handelClickOk(MouseEvent e) {
        Card c = new Card((String) valueChoice.getSelectionModel().getSelectedItem(), (String) colorChoice.getSelectionModel().getSelectedItem());
        Client.getClientInstance().sendData(c);
        Button b = (Button) e.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        stage.close();
    }

}
