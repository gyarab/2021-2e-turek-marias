/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jakub
 */
public class ExistingGamesScreenController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    ListView gamePickerView;
    private final ExistingGamesScreen existingGamesScreen;
    private final ScreenManager screenManager;

    public ExistingGamesScreenController() {
        existingGamesScreen = new ExistingGamesScreen(this);
        screenManager = new ScreenManager();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        existingGamesScreen.start();

    }

    @FXML
    public void createViewContent(List<ServerPrametr> param) {
        gamePickerView.getItems().clear();
        for (ServerPrametr sp : param) {

            gamePickerView.getItems().add("Název: " + sp.getName() + " " + sp.getIp());

        }

    }

    @FXML
    public void handelClikConect(MouseEvent event) throws IOException {
        existingGamesScreen.interrupt();
        int item = gamePickerView.getSelectionModel().getSelectedIndex();
        Stage stage = (Stage) gamePickerView.getScene().getWindow();
        GameScreenController cont = (GameScreenController) screenManager.changeScene("GameScreen.fxml", stage);
        existingGamesScreen.joinGame(item,cont);

    }

}
