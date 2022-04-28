/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

/**
 *
 * @author jakub
 */
public class ExistingGamesScreen extends Thread {

    private final ExistingGamesScreenController existingGamesController;
    private List<ServerPrametr> parametrs;
    private boolean interupted;
    private final int COLDOWN;
    private ExistingGameFinder finder;

    public ExistingGamesScreen(ExistingGamesScreenController existingGamesController) {
        this.existingGamesController = existingGamesController;
        finder = new ExistingGameFinder();
        interupted = false;
        COLDOWN = 4000;
    }

    @Override
    public void run() {
        finder.start();
        try {
            sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ExistingGamesScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (!interupted) {
            parametrs = finder.getExistingGames();

            Platform.runLater(
                    () -> {
                        existingGamesController.createViewContent(parametrs);
                    }
            );

            try {
                sleep(COLDOWN);
            } catch (InterruptedException ex) {
                return;
            }
        }
        finder.interrupt();
    }

    public void joinGame(int index, GameScreenController cont) throws IOException {
        ServerPrametr serverPrametr = parametrs.get(index);
        Client client = Client.getClientInstance(serverPrametr.getIp(), serverPrametr.getPort(),"Tony", cont);
        client.start();
    
    }

    @Override
    public void interrupt() {
        interupted = true;
    }

}
