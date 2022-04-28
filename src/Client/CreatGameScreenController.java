/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jakub
 */
public class CreatGameScreenController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    TextField portText;
    @FXML
    TextField nameText;

    ScreenManager manager = new ScreenManager();
    CreatGameScreen creatGameScreen = new CreatGameScreen();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void handelCreatClick(MouseEvent event) {
        int port;
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        try {
            port = Integer.parseInt(portText.getText());
        } catch (NumberFormatException ex) {
            manager.showExceptio(ex);
            return;
        }
        try {
            creatGameScreen.startNewGame(nameText.getText(), port);
            manager.changeScene("ExistingGamesScreen.fxml", stage);

        } catch (IOException ex) {
            manager.showExceptio(ex);
        }
    }

    public void handelBackClick(MouseEvent event) {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        try {
            manager.changeScene("MainScreen.fxml", stage);
        } catch (IOException ex) {
            manager.showExceptio(ex);
        }
    }

}
